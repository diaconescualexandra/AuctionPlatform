package Runner;
import Model.*;
import Service.AuctionService;
//import Service.AuctionService;

import java.util.Scanner;

import java.sql.*;

public class AuctionRunner {



    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        User user = new User(username);

        AuctionService auctionService = new AuctionService(user);

        try {


            Connection con = auctionService.getConnection();

            Automobile automobile = new Automobile();

            automobile.updateAutom(con, 53, 1000, 2000, "bmw");
            automobile.deleteAutom(con, 53);
            automobile.readAutom(con, 54);

            Car car = new Car();
            car.readCar(con, 53);
            car.updateCar(con, 53, 10);
            car.deleteCar(con, 62);


            Motorcycle motorcycle = new Motorcycle();
            motorcycle.readMotor(con, 54);
            motorcycle.updateMotor(con,54,"cruiser");
            motorcycle.deleteMotor(con, 58);


            boolean running = true;
            while (running) {
                displayMenu(auctionService);
                System.out.println("Do you want to continue? (y/n)");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("y")) {
                    running = false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


  }


    private static void displayMenu(AuctionService auctionService) {



        System.out.println("""
            Welcome to the auction platform!

            Select an option from the menu below:
            1. Add a new item
            2. Display auctions
            3. Place bid
            4. Display bids
            5. Close an auction
            6. Edit Deadline
            7. View highest bid
            8. View active bids
            9. Edit auction title
            10. Edit Auction
            11. Exit
            """);

        Scanner scanner = new Scanner(System.in);
        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> auctionService.createItem();
            case 2 -> auctionService.displayAuctions();
            case 3 -> auctionService.placeBid();
            case 4 -> auctionService.displayBids();
            case 5 -> auctionService.closeAuction();
            case 6 -> auctionService.editDeadline();
            case 7 -> auctionService.viewHighestBid();
            case 8 -> auctionService.viewActiveBidsForAuction();
            case 9 -> auctionService.editAuctionDescription();
            case 10 -> auctionService.editAuction();
            case 11 -> System.exit(0);

            default -> System.out.println("Invalid option");
        }


    }

}
