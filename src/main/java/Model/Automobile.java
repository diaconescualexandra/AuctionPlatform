package Model;

import java.sql.*;

public class Automobile extends Item{
    private double mileage;
    private int year;
    private String brand;

    public Automobile(String title,  double mileage, int year, String brand) {
        super(title);
        this.mileage = mileage;
        this.year = year;
        this.brand = brand;
    }

    public Automobile() {
        super("");
    }


//    public Automobile(String title, int itemId, double mileage, int year, String brand) {
//        super(title);
//        setItemId(itemId);
//        this.mileage = mileage;
//        this.year = year;
//        this.brand = brand;
//    }

    public void createAutom(Connection con, int itemId) throws SQLException {
        setItemId(itemId);

        // Modify the query to remove 'id' from the INSERT statement as it should be auto-generated
        String query = "INSERT INTO automobile (mileage, year, brand, idItemFK) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setDouble(1, getMileage()); // Use the mileage from the current object
            pstm.setInt(2, getYear());    // Use the year from the current object
            pstm.setString(3, getBrand());   // Use the brand from the current object
            pstm.setInt(4, itemId);          // Use the itemId from the argument

            pstm.executeUpdate();
        }
    }



    public void readAutom (Connection con, int itemId) throws SQLException {
        readItem(con, itemId);
        String query = "select * from automobile where id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1,itemId);
            try (ResultSet rs = stmt.executeQuery())
            {
                if(rs.next()) {
                    this.mileage = rs.getDouble("mileage");
                    this.year = rs.getInt("year");
                    this.brand = rs.getString("brand");
                }
            }
        }
    }

    public void updateAutom (Connection con, int iditem, double mileage, int year, String brand) throws SQLException {
        try {
            String query = "update automobile set mileage = ?, year = ?, brand = ? where id =?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setDouble(1, mileage);
            stmt.setInt(2,year);
            stmt.setString(3,brand);
            stmt.setInt(4,iditem);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteAutom(Connection con, int iditem){
        try {
            String query = "delete from automobile where id=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,iditem);
            stmt.executeUpdate();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public double getMileage() {
        return mileage;
    }

    public int getYear() {
        return year;
    }

    public String getBrand() {
        return brand;
    }
}
