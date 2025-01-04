package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Car extends Automobile{


    private int noDoors;

    public Car(String title, double mileage, int year, String brand, int noDoors) {
        super(title, mileage, year, brand);
        this.noDoors = noDoors;
    }

//    public Car(String title, int itemId, double mileage, int year, String brand, int noOfDoors) {
//        super(title, itemId, mileage, year, brand);
//        this.noDoors = noOfDoors;
//    }

    public Car () {super("", 0,0,"");}


    public void createCar(Connection con, int itemId) throws SQLException {
        setItemId(itemId);


        String query = "INSERT INTO car (fkAutomId, noDoors) VALUES (?, ?)";

        try (PreparedStatement pstm = con.prepareStatement(query)) {

            pstm.setInt(1, itemId);
            pstm.setInt(2, getNoDoors()); // Set the number of doors for the car

            pstm.executeUpdate();  // Execute the insert
        }
    }


    public void readCar (Connection con, int itemId) throws SQLException {
        readItem(con, itemId);
        String query = "select * from car where id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1,itemId);
            try (ResultSet rs = stmt.executeQuery())
            {
                if(rs.next()) {
                    this.noDoors = rs.getInt("noDoors");

                }
            }
        }
    }

    public void updateCar (Connection con, int iditem,int noDoors) throws SQLException {
        try {
            String query = "update car set noDoors = ? where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,noDoors);
            stmt.setInt(2,iditem);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteCar(Connection con, int iditem){
        try {
            String query = "delete from car where id=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,iditem);
            stmt.executeUpdate();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public int getNoDoors() {
        return noDoors;
    }

    public void setNoOfDoors(int noOfDoors) {
        this.noDoors = noOfDoors;
    }
}
