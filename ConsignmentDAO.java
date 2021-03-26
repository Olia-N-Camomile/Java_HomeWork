import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConsignmentDAO extends AbstractDAO <Consignment, Warehouses> {

    public void updateEntry(List<String> listQT) {
        try (Connection connection = ConnectorDB.getConnection();
             Statement statement = connection.createStatement() )
        {
            for (String query_Text : listQT) {
                statement.addBatch(query_Text);
            }
            statement.executeBatch();
            }
        catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public boolean createTable() {
        String query_Text ="create table IF NOT EXISTS leftover(id int(10) not null auto_increment primary key," +
                " id_location int not null, code_shoe varchar(10) not null, quantity int(10)," +
                " foreign key (id_location) references storage_locations (id) on delete cascade," +
                " foreign key (code_shoe) references shoes (vendor_code) on delete cascade);";
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
    public boolean addNewEntry(Consignment consignment) {
        String Query_Text =
                "insert into leftover(id_location, code_shoe, quantity) values(?, ?, ?)";
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query_Text) )
        {
            Set<Shoe> shoeSet = consignment.shoeMap.keySet();
            for (Shoe shoe : shoeSet) {
                int q = consignment.shoeMap.get(shoe);
                statement.setInt(1, consignment.warehouse.id);
                statement.setString(2, shoe.code);
                statement.setInt(3, q);
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
    public Consignment findEntryByNumber(Warehouses warehouses, String query_Text) {
        Map<Shoe, Integer> shoeMap = new HashMap<>();
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_Text) )
        {
            statement.setInt(1, warehouses.id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                /*
                shoeMap.put(new Shoe(rs.getString("code_shoe"),rs.getString("name"),
                        rs.getString("color"),  rs.getString("material"),
                        rs.getFloat("size")), rs.getInt("quantity"));
                 */
                shoeMap.put(new Shoe(rs.getString("code_shoe")),rs.getInt("quantity"));
                }
            return new Consignment(shoeMap, warehouses);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Consignment findAll(String query_Text) {
        if (query_Text.isEmpty()) {
            query_Text = "select leftover.*, gr_lf.qt, gr_lf.capacity " +
                    "from leftover inner join " +
                    "(select leftover.id_location as id_l, sum(leftover.quantity) as qt, " +
                    "storage_locations.capacity " +
                    "from leftover inner join storage_locations " +
                    "on leftover.id_location=storage_locations.id  " +
                    "group by id_location) as gr_lf " +
                    "on leftover.id_location=gr_lf.id_l " +
                    "order by id_location";
        }
        String textS="";
        int id_location=-1;
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_Text) )
        {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getInt("id_location")!=id_location)
                {System.out.println("на складе № = "+rs.getInt("id_location")+" вместимостью "+
                        rs.getInt("capacity")+" пар - остаток "+rs.getInt("qt")+" пар, в том числе:");}
                System.out.println(" артикул "+rs.getString("code_shoe")+" количество = "+rs.getInt("quantity"));
                id_location=rs.getInt("id_location");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
