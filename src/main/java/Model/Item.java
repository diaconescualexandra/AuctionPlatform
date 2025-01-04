package Model;

import java.sql.*;

public class Item {
    private int itemId;
    private String title;
    public Item(String title) {
        this.title = title;
//        this.itemId = itemId;
    }

    public Item() {this.title = "";}

    public int createItem(Connection con) throws SQLException {

        String query = "INSERT INTO items (title) VALUES (?)";

        try (PreparedStatement pstm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, getTitle());
            pstm.executeUpdate();

            ResultSet generatedKeys = pstm.getGeneratedKeys();

            if (generatedKeys.next()) {
                itemId = generatedKeys.getInt(1);
                return itemId;
            } else {
                throw new SQLException("Failed to retrieve the generated ID for item.");
            }
        }
    }


    public void readItem (Connection con, int itemId) throws SQLException {
        String query = "select * from items where iditem = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1,itemId);
            try (ResultSet rs = stmt.executeQuery())
            {
                if(rs.next()) {
                    this.itemId = itemId;
                    this.title = rs.getString("title");
                }
            }
        }
    }

    public void updateItem (Connection con, int iditem, String title) throws SQLException {
        try {
            String query = "update items set title = ? where iditem = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setInt(2,iditem);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteItem(Connection con, int iditem){
        try {
            String query = "delete from items where iditem=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,iditem);
            stmt.executeUpdate();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    @Override
    public String toString() {
        return "Item id: " + itemId + " Title: " + title;
    }
}
