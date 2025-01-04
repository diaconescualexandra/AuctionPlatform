package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Motorcycle extends Automobile{

    private String motorcycleType;

    public Motorcycle(String title, double mileage, int year, String brand, String motorcycleType) {
        super(title, mileage, year, brand);
        this.motorcycleType = motorcycleType;
    }

    public Motorcycle () {super("", 0,0,"");}
    public void createMotor(Connection con, int itemId) throws SQLException {
        setItemId(itemId);

        String query = "INSERT INTO motorcycles (fkMotor, motorcycleType) VALUES (?, ?)";

        try (PreparedStatement pstm = con.prepareStatement(query)) {
            pstm.setInt(1, itemId); // Assuming 'itemId' references an 'automobile' entry
            pstm.setString(2, getMotorcycleType()); // Set the type of the motorcycle

            pstm.executeUpdate(); // Execute the insert
        }
    }


    public void readMotor (Connection con, int itemId) throws SQLException {
        readItem(con, itemId);
        String query = "select * from motorcycles where id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1,itemId);
            try (ResultSet rs = stmt.executeQuery())
            {
                if(rs.next()) {
                    this.motorcycleType = rs.getString("motorcycleType");

                }
            }
        }
    }

    public void updateMotor (Connection con, int iditem,String motorcycleType) throws SQLException {
        try {
            String query = "update motorcycles set motorcycleType = ? where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1,motorcycleType);
            stmt.setInt(2,iditem);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteMotor(Connection con, int iditem){
        try {
            String query = "delete from motorcycles where id=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,iditem);
            stmt.executeUpdate();
        }catch(Exception e)
        {e.printStackTrace();}
    }

    public String getMotorcycleType() {
        return motorcycleType;
    }

    public void setMotorcycleType(String motorcycleType) {
        this.motorcycleType = motorcycleType;
    }
}
