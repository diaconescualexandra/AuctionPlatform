package Model;

public class Photos extends Art{

    private String genre;
    private boolean signed;

    public Photos(String title,String artist, String countryOfOrigin, String genre, boolean signed) {
        super(title,artist, countryOfOrigin);
        this.genre = genre;
        this.signed = signed;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean getSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
