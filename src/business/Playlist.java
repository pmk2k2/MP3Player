package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Playlist {
    private long id;
    private String title, coverFile;
    private Date creationDate;
    private List<Track> tracks;
    private Track track;

    public Playlist(long id, String title, String coverFile, Date creationDate, List<Track> tracks, Track track) {
        this.id = id;
        this.title = title;
        this.coverFile = coverFile;
        this.creationDate = creationDate;
        this.tracks = new ArrayList<>();
        this.track = track;
    }

    public Playlist() {
        this.tracks = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverFile() {
        return coverFile;
    }

    public void setCoverFile(String coverFile) {
        this.coverFile = coverFile;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<Track> getTracks() {
        return tracks;
    }
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public Track getTrack(int no) {
        if(tracks!=null&&no<tracks.size()){
            return tracks.get(no);
        }
        return null;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public int numberOfTracks() {
        return tracks.size();
    }
    public void addTrack(Track track) {
        tracks.add(track);
    }

}
