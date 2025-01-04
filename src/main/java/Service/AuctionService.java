package Service;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AuctionService {

    static final String DB_URL = "jdbc:sqlserver://auctionserver.database.windows.net:1433;" +
            "database=auctionDB;" +
            "user=alexandra@auctionserver;" +
            "password=auctionJava12;" +
            "encrypt=true;" +
            "trustServerCertificate=false;" +
            "hostNameInCertificate=*.database.windows.net;" +
            "loginTimeout=30";
    //static final String USER = "alexandra@auctionserver";
    //static final String PASS = "auctionJava12";
    private Connection con;

    User user;
    private List<Item> items;
    private List <Auction> listOfAuctions;


    public AuctionService() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            this.con = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to Azure SQL Database!");
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to Azure SQL Database.");
            e.printStackTrace();
        }

    }
    public Connection getConnection() {
        return con;
    }

    public AuctionService(User user) {
        this();
        this.user = user;
        this.items = new ArrayList<>();
        this.listOfAuctions = new ArrayList<>();
    }

    public void displayItems() {
        System.out.println("Available items:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getTitle());
        }
    }
    public void createAuction(Item item,User user ) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the deadline for the auction (YYYY-MM-DD):");
        String deadline = scanner.nextLine();
        System.out.println("Enter the minimum bid for the auction ");
        int minBid = scanner.nextInt();

        try {

            if (user.getIdUser() == 0) {
                user.createUser(con);
            }

            Auction auction = new Auction(item, new ArrayList<>(), true, deadline, minBid, user);
            auction.createAuction(con, item, user, deadline, minBid);
            listOfAuctions.add(auction);

        }
        catch (SQLException e)
        {e.printStackTrace();}
    }

//    public void readI() {
//        Item i = new Item();
//        i.readItem(con,i.getItemId() );
//    }

    public void displayAuctions()  {
        String query = "SELECT a.auctionId, a.itemId, a.deadline, a.minBid, a.userId, a.isOpen, i.title " +
                "FROM auctions a JOIN items i ON a.itemId = i.iditem";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("List of auctions: ");
            while (rs.next()) {
                int auctionId = rs.getInt("auctionId");
                int itemId = rs.getInt("itemId");
                String deadline = rs.getString("deadline");
                int minBid = rs.getInt("minBid");
                String title = rs.getString("title");
                String userId = rs.getString("userId");
                Boolean isOpen = rs.getBoolean("isOpen");

                Item item = new Item(title);
                item.setItemId(itemId);

                Auction auction = new Auction(item, null);
                auction.setDeadline(deadline);
                auction.setMinBid(minBid);
                auction.setOpen(isOpen);



                listOfAuctions.add(auction);

                item.readItem(con, item.getItemId());
                System.out.println(auction);
            }
        }catch (SQLException e) {e.printStackTrace();}
    }



    public void createItem() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose the type of the item to add:");
        System.out.println("1.Automobile");
        System.out.println("2.Jewerly");
        System.out.println("3.Art");
        System.out.println("Enter your choice:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1: createAutomobile();
                    break;
            case 2:
                createJewerly();
                break;
            case 3:
                createArt();
                break;
            default:
                System.out.println("Invalid choice");

        }
    }

    public void createAutomobile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose type of automobile:");
        System.out.println("1.Car");
        System.out.println("2.Motorcycle");
        System.out.println("Enter your choice");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                createCar();
                break;
            case 2:
                createMotorcycle();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void createCar() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter car title: ");
        String title = scanner.nextLine();
        System.out.print("Enter car mileage: ");
        double mileage = scanner.nextDouble();
        System.out.print("Enter car year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter car brand: ");
        String brand = scanner.nextLine();

        try {
            Item item = new Item(title);
            item.createItem(con);
            int itemId = item.getItemId();


            System.out.print("Enter number of doors: ");
            int noOfDoors = scanner.nextInt();
            scanner.nextLine();

            Automobile automobile = new Automobile(title, mileage, year, brand);
            automobile.createAutom(con,itemId);

            Car car = new Car(title, mileage, year, brand, noOfDoors);
            car.createCar(con, itemId);

            user.addItem(car);
            createAuction(car, user);
            items.add(car);

            System.out.println("Car added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void createMotorcycle() {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Enter motorcycle title: ");
        String title = scanner.nextLine();
        System.out.print("Enter motorcycle mileage: ");
        int mileage = scanner.nextInt();
        System.out.print("Enter motorcycle year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter motorcycle brand: ");
        String brand = scanner.nextLine();

        try {
            Item item = new Item(title);
            item.createItem(con);
            int itemId = item.getItemId();


            System.out.print("Enter motorcycle type: ");
            String motorcycleType = scanner.nextLine();
            scanner.nextLine();

            Automobile automobile = new Automobile(title, mileage, year, brand);
            automobile.createAutom(con,itemId);

            Motorcycle motorcycle = new Motorcycle(title, mileage, year, brand, motorcycleType);
            motorcycle.createMotor(con,itemId);

            user.addItem(motorcycle);
            createAuction(motorcycle, user);
            items.add(motorcycle);

            System.out.println("Motorcycle added successfully!");


        } catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void createJewerly() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Configure the jewerly");

        System.out.println("Enter username:");
        int username = scanner.nextInt();

        System.out.print("Enter jewerly title: ");
        String title = scanner.nextLine();
        System.out.println("Enter the stone type");
        String stoneType = scanner.nextLine();
        System.out.println("Enter fineness");
        String fineness = scanner.nextLine();
        System.out.print("Enter condition ");
        String condition = scanner.nextLine();
        scanner.nextLine();

        Jewelry jewelry = new Jewelry(title, stoneType, fineness, condition);
        user.addItem(jewelry);
        createAuction(jewelry, user);
        items.add(jewelry);

        System.out.println("Jewerly added successfully");
    }

    public void createArt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose type of art:");
        System.out.println("1.Artwork");
        System.out.println("2.Photos");
        System.out.println("Enter your choice");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                createArtwork();
                break;
            case 2:
                createPhoto();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void createArtwork() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        int username = scanner.nextInt();
        System.out.print("Enter artwork title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artwork artist ");
        String artist = scanner.nextLine();
        System.out.print("Enter artwork country of origin");
        String countryOfOrigin = scanner.nextLine();
        System.out.print("Enter artwork height");
        int height = scanner.nextInt();
        System.out.print("Enter artwork width");
        int width = scanner.nextInt();
        System.out.print("Enter artwork technique");
        String technique = scanner.nextLine();
        scanner.nextLine();

        Artwork artwork = new Artwork(title, artist, countryOfOrigin, height, width, technique);
        user.addItem(artwork);
        createAuction(artwork, user);
        items.add(artwork);
        System.out.println("Artwork added successfully!");
    }

    public void createPhoto() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter username:");
        int username = scanner.nextInt();
        System.out.print("Enter photo title: ");
        String title = scanner.nextLine();
        System.out.print("Enter photo artist ");
        String artist = scanner.nextLine();
        System.out.print("Enter photo country of origin");
        String countryOfOrigin = scanner.nextLine();
        System.out.print("Enter photo genre");
        String genre = scanner.nextLine();
        System.out.print("Is it signed? y/n");
        String signedYN = scanner.nextLine();
        boolean signed = "y".equalsIgnoreCase(signedYN);
        scanner.nextLine();

        Photos photo = new Photos(title, artist, countryOfOrigin, genre, signed);
        user.addItem(photo);
        createAuction(photo, user);
        items.add(photo);

        System.out.println("Photo added successfully!");
    }

    public void placeBid() {
        Scanner scanner = new Scanner(System.in);

        displayItems();

        System.out.println("Enter the title of the item you want to place a bid for:");
        String itemTitle = scanner.nextLine();

        for (Auction auction : listOfAuctions) {
            if (auction.getItem().getTitle().equals(itemTitle) && auction.isOpen()) {
                double minBid = auction.getLowestBid();
                System.out.println("Minimum bid for this auction is: " + minBid);
                System.out.println("Current lowest bid for this auction is: " + minBid);

                System.out.println("Enter your bid amount:");
                double amount = scanner.nextDouble();

                scanner.nextLine();

                if (amount > minBid && amount >= minBid) {
                    System.out.println("Enter your username:");
                    String username = scanner.nextLine();

                    auction.placeBid(amount, new User(username), auction.getItem());
                    return;
                } else {
                    System.out.println("Your bid must be higher than the min bid. ");
                    return;
                }
            }
        }
        System.out.println("Item not found or auction is closed.");
    }



    public void displayBids() {
        for (Auction auction : listOfAuctions) {
            System.out.println("Bids for item '" + auction.getItem().getTitle() + "':");
            for (Bid bid : auction.getBids()) {
                System.out.println(bid);
            }
        }}

    public void closeAuction() {
        displayItems();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the title of the item whose auction you want to close:");
        String itemTitle = scanner.nextLine();

        for (Auction auction : listOfAuctions) {
            if (auction.getItem().getTitle().equals(itemTitle)) {
                auction.setOpen(false);
                System.out.println("Auction for item '" + itemTitle + "' has been closed.");
                String updateQuery = "update auctions set isOpen = ? where auctionId = ?";
                try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
                    stmt.setBoolean(1, false);
                    stmt.setInt(2, auction.getIdAuction());
                    stmt.executeUpdate();
                } catch (SQLException e) {e.printStackTrace();}
                return;
            }
        }
        System.out.println("Item not found or auction is already closed.");
    }

    public void editDeadline() {
        Scanner scanner = new Scanner(System.in);

        displayItems();

        System.out.println("Enter the title of the item whose auction's deadline you want to edit:");
        String itemTitle = scanner.nextLine();

        Auction targetAuction = null;
        for (Auction auction : listOfAuctions) {
            if (auction.getItem().getTitle().equalsIgnoreCase(itemTitle) && auction.isOpen()) {
                targetAuction = auction;
                break;
            }
        }

        if (targetAuction != null) {
            System.out.println("Enter the new deadline for the auction (YYYY-MM-DD):");
            String newDeadline = scanner.nextLine();
            targetAuction.setDeadline(newDeadline);
            System.out.println("Deadline for the auction has been updated successfully.");
        } else {
            System.out.println("Item not found or auction is closed.");
        }
    }

    public void editAuction() {
        Scanner scanner = new Scanner(System.in);

        displayItems();

        System.out.println("Enter the id of the item whose auction's you want to edit:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        Auction targetAuction = null;
        for (Auction auction : listOfAuctions) {
            Item item = auction.getItem();
            if (item != null) {
                if (id == item.getItemId() && auction.isOpen()) {
                    targetAuction = auction;
                    break;
                }
            }
        }

        if (targetAuction != null) {
            System.out.println("Enter the new deadline for the auction:");
            String newDeadline = scanner.nextLine();
            System.out.println("Enter the new minimum bid of the auction:");
            int newMinBid = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter the new state of the auction (true/false):");
            boolean newisOpen = scanner.nextBoolean();

            targetAuction.setDeadline(newDeadline);
            targetAuction.setMinBid(newMinBid);
            targetAuction.setOpen(newisOpen);
            System.out.println("Auction has been updated successfully.");

            try {
                targetAuction.updateAuction(con, newMinBid, newDeadline, newisOpen, targetAuction.getIdAuction());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Item not found or auction is closed.");
        }
    }




    public void viewHighestBid() {
        Scanner scanner = new Scanner(System.in);

//        displayAuctions();

        System.out.println("Enter the title of the item to view its highest bid:");
        String itemTitle = scanner.nextLine();

        Auction targetAuction = null;
        for (Auction auction : listOfAuctions) {
            if (auction.getItem().getTitle().equalsIgnoreCase(itemTitle)) {
                targetAuction = auction;
                break;
            }
        }

        if (targetAuction != null) {
            double highestBid = 0.0;
            Bid highestBidObj = null;
            for (Bid bid : targetAuction.getBids()) {
                if (bid.getAmount() > highestBid) {
                    highestBid = bid.getAmount();
                    highestBidObj = bid;
                }
            }
            if (highestBidObj != null) {
                System.out.println("Highest bid for '" + itemTitle + "':");
                System.out.println("Bidder: " + highestBidObj.getBidder().getUsername());
                System.out.println("Amount: " + highestBid);
            } else {
                System.out.println("No bids found for '" + itemTitle + "'.");
            }
        } else {
            System.out.println("Auction for item '" + itemTitle + "' not found.");
        }

    }

    public void viewActiveBidsForAuction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the title of the auction to view active bids:");
        String auctionTitle = scanner.nextLine();

        Auction selectedAuction = null;
        for (Auction auction : listOfAuctions) {
            if (auction.getItem().getTitle().equalsIgnoreCase(auctionTitle)) {
                selectedAuction = auction;
                break;
            }
        }

        if (selectedAuction != null && selectedAuction.isOpen() && !selectedAuction.getBids().isEmpty()) {
            System.out.println("Active Bids for Auction: " + selectedAuction.getItem().getTitle());
            for (Bid bid : selectedAuction.getBids()) {
                System.out.println("Bidder: " + bid.getBidder().getUsername() + ", Amount: " + bid.getAmount());
            }
        } else {
            System.out.println("No active bids for the specified auction.");
        }

    }

    public void editAuctionDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title of auction you want to edit");
        String auctionTitleToBeEdited = scanner.nextLine();

        Auction selectedAuction = null;

        for (Auction auction : listOfAuctions) {
            if (auction.getItem().getTitle().equalsIgnoreCase(auctionTitleToBeEdited)) {
                selectedAuction = auction;
                break;
            }
        }

        if (selectedAuction != null && selectedAuction.isOpen()) {
            System.out.println("Enter the new title for the auction:");
            String newTitle = scanner.nextLine();
            selectedAuction.getItem().setTitle(newTitle);
            System.out.println("Auction title updated successfully!");

            try
            {
                selectedAuction.getItem().updateItem(con, selectedAuction.getItem().getItemId(),newTitle);

            }catch(SQLException e) {e.printStackTrace();}
        } else {
            System.out.println("Auction not found or it is closed. Title cannot be edited.");
        }
    }



    public void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception here if needed
        }
    }

    }





