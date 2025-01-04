package Model;

public class Jewelry extends Item {
    private String stoneType;
    private String fineness;
    private String condition;


    public Jewelry(String title, String stoneType, String fineness, String condition) {
        super(title);
        this.stoneType = stoneType;
        this.fineness = fineness;
        this.condition = condition;
    }

    public String getStoneType() {
        return stoneType;
    }

    public void setStoneType(String stoneType) {
        this.stoneType = stoneType;
    }

    public String getFineness() {
        return fineness;
    }

    public void setFineness(String fineness) {
        this.fineness = fineness;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
