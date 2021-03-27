import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO extends AbstractDAO <List<Warehouses>,Integer> {

    @Override
    public boolean createTable() {
        String query_Text ="create table IF NOT EXISTS storage_locations(id int(10) not null auto_increment primary key," +
                " name varchar(100) not null, capacity int(10) not null);";
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement() )
        {
            statement.addBatch(query_Text);
            statement.executeBatch();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;

        }
    }

    @Override
    public boolean addNewEntry(List<Warehouses> list) {
        String Query_Text =
            "insert into storage_locations (name, capacity) values (?,?)";
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query_Text) )
        {
            for (Warehouses warehouse : list) {
                statement.setString(1, warehouse.getName());
                statement.setInt(2, warehouse.getCapacity());
                statement.addBatch();
            }
           statement.executeBatch();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Warehouses> findAll(String query_Text) {
        List<Warehouses> list = new ArrayList<>();
        if (query_Text.isEmpty()) {
            query_Text="SELECT * FROM storage_locations";
        }
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_Text) )
        {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (query_Text.contains("busy")) {
                    list.add(new Warehouses(rs.getInt("id"),rs.getString("name"),
                            rs.getInt("capacity"), rs.getInt("busy")));}
                else {
                    list.add(new Warehouses(rs.getInt("id"),rs.getString("name"),
                            rs.getInt("capacity")));}
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Warehouses> findAllWithBusy() {
        String query_Text="SELECT storage_locations.*, sum(leftover.quantity) as busy " +
                "FROM storage_locations left join leftover " +
                "on storage_locations.id=leftover.id_location " +
                "group by storage_locations.id";
        return findAll(query_Text);
    }

    @Override
    public List<Warehouses> findEntryByNumber(Integer n, String query_Text) {
        List<Warehouses> list = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_Text) )
        {
            statement.setInt(1, n);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.findColumn("busy")!=0) {
                list.add(new Warehouses(rs.getInt("id"),rs.getString("name"),
                        rs.getInt("capacity"), rs.getInt("busy")));}
                else {
                    list.add(new Warehouses(rs.getInt("id"),rs.getString("name"),
                            rs.getInt("capacity")));}
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Warehouses findEntryAndBusyById(int n) {
        Warehouses warehouse = null;
        String query_Text="SELECT storage_locations.*, sum(leftover.quantity) as busy " +
                "FROM storage_locations left join leftover " +
                "on storage_locations.id=leftover.id_location " +
                "where storage_locations.id=?";
        List<Warehouses> list = findEntryByNumber(n, query_Text);
        if(list.size()!=0) { warehouse=list.get(0);}
        return warehouse;
    }
}

