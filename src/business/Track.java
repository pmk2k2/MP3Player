package business;

public class Track {
    private String title, artist, album, mp3FilePath;
    private int length;
    private byte[] imageData;

    public Track(String title, String artist, int length, String album, String mp3FilePath, byte[] imageData) {
        this.title=title;
        this.artist=artist;
        this.length=length;
        this.album=album;
        this.mp3FilePath=mp3FilePath;
        this.imageData = imageData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getMp3FilePath() {
        return mp3FilePath;
    }

    public void setMp3FilePath(String mp3FilePath) {
        this.mp3FilePath = mp3FilePath;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return getTitle()+" - " +getArtist();
    }

}
