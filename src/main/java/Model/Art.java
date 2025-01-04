package Model;

public class Art extends Item{

    private String artist;
    private String countryOfOrigin;

    public Art(String title, String artist, String countryOfOrigin) {
        super(title);
        this.artist = artist;
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getArtist() {
        return artist;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

}
