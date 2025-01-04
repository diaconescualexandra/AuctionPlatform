package Model;

public class Artwork extends Art{
    private int height;
    private int width;
    private String technique;

    public Artwork(String title, String artist, String countryOfOrigin, int height, int width, String technique) {
        super(title, artist, countryOfOrigin);
        this.height = height;
        this.width = width;
        this.technique = technique;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTechnique() {
        return technique;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }
}
