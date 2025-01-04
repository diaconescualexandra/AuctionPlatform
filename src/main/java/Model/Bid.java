package Model;

public class Bid {

    private int idBid;
    private Double amount;
    private User bidder;
    private Item item;

    public Bid(Double amount, User bidder, Item item) {
        this.amount = amount;
        this.bidder = bidder;
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "amount=" + amount +
                ", bidder=" + bidder.getUsername() +
                ", item=" + item.getTitle() +
                '}';
    }
}
