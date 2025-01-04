//package Model;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class Seller extends User {
//    private Set<Item> sellersItems;
//
//    private List<Seller> sellers;
//
//    public List<Seller> getSellers() {
//        return sellers;
//    }
//
//    public void setSellers(List<Seller> sellers) {
//        this.sellers = sellers;
//    }
//
//    public Seller (String username){
//
//        super(username);
//        ongoingAuctions = new HashSet<>();
//        sellersItems = new HashSet<>();
//    }
//
//    public void createAuction (Auction auction, Item item) {
//        if ( item != null)
//        { auction.setItem(item);
//            ongoingAuctions.add(auction);
//            System.out.println("Auction created succesfully!");
//    }else {
//            System.out.println("Cannot create auction, item is empty!");
//        }
//}
//
//
//
//    public void addSeller ( Seller seller) {
//        sellers.add(seller);
//        System.out.println("A new seller account was created");
//    }
//
//    public HashSet<Auction> getOngoingAuctions() {
//        return ongoingAuctions;
//    }
//
//    public void addItem(Item item) {
//        sellersItems.add(item);
//    }
//}
