import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShoeDAO extends AbstractDAO <List<Shoe>, String> {

    @Override
    public boolean createTable() {
        String query_Text ="create table IF NOT EXISTS shoes(vendor_code varchar(15) not null primary key," +
                " name varchar(50) not null, size float, color varchar(20), material varchar(20));";
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
    public boolean addNewEntry(List <Shoe> list) {
        String Query_Text =
                "insert into shoes(vendor_code, name, size, color, material) values(?, ?, ?, ?, ?)";
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(Query_Text) )
        {
            for (Shoe shoe : list) {
                statement.setString(1, shoe.getCode());
                statement.setString(2, shoe.getName());
                statement.setFloat(3, shoe.getSize());
                statement.setString(4, shoe.getColor());
                statement.setString(5, shoe.getMaterial());
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
    public List<Shoe> findAll(String query_Text) {
        List<Shoe> list = new ArrayList<>();
        if (query_Text.isEmpty()) {
            query_Text="select * from shoes";
        }
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_Text) )
        {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Shoe(rs.getString(1),rs.getString(2),
                        rs.getString("color"),  rs.getString("material"),
                        rs.getFloat("size")));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    @Override
    public List<Shoe> findEntryByNumber(String n, String query_Text) {
        List<Shoe> list = new ArrayList<>();
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query_Text) )
        {
            statement.setString(1, n);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Shoe(rs.getString(1),rs.getString(2),
                        rs.getString("color"),  rs.getString("material"),
                        rs.getFloat("size")));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Shoe findEntryByCode(String code) {
        Shoe shoe = null;
        String query_Text="select * from shoes where vendor_code=?";
        List<Shoe> list = findEntryByNumber(code, query_Text);
        if(list.size()!=0) { shoe=list.get(0);}
        return shoe;
    }
}
