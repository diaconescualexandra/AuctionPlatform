package Model;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

    private int idUser;
    private final String username;
    private List<User> users;
    private HashSet<Auction> ongoingAuctions;
    private Set<Item> usersItems;



    public User(String username){

        this.username = username;
        this.usersItems = new HashSet<>();
        this.ongoingAuctions = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public void addBidders ( User bidder) {
        users.add(bidder);
        System.out.println("A new bidder account was created");
    }

    public void createUser(Connection con) throws SQLException {
        // Updated query: removed 'idusers' since it's auto-increment
        String query = "INSERT INTO users (username) VALUES (?)";

        try (PreparedStatement pstm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, username);  // Set the username from the object

            pstm.executeUpdate();

            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                idUser = generatedKeys.getInt(1);  // Retrieve the generated user ID
            } else {
                throw new SQLException("Failed to retrieve the generated ID for user.");
            }
        }
    }


//    public void createAuction(Auction auction ,Item item)
//    {
//        auction.createAuction(item, this);
//        ongoingAuctions.add( auction);
//    }

    public void addItem(Item item) {
        usersItems.add(item);
    }

    public int getIdUser() {
        return idUser;
    }
}
