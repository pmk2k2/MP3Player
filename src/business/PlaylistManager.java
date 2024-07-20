package business;

import com.mpatric.mp3agic.Mp3File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {
    private List<Playlist> playlists;

    public PlaylistManager() {
        playlists = new ArrayList<>();
    }

    public Playlist getPlaylist(String name) {
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(name)) {
                return playlist;
            }
        }
        return null;
    }

    public List<Track> getAllTracks() {
        List<Track> allTracks = new ArrayList<>();

        for (Playlist playlist : playlists) {
            for (Track track : playlist.getTracks()) {
                allTracks.add(track);
            }
        }
        return allTracks;
    }
    private List<String> readM3UFile(String m3uFilePath) {
        List<String> mp3FilePaths = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(m3uFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("/") && line.endsWith(".mp3")) {
                    mp3FilePaths.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mp3FilePaths;
    }
    private Track readMetadataFromMP3File(String mp3FilePath) {
        try {
            Mp3File mp3File = new Mp3File(mp3FilePath);
            String title = mp3File.getId3v2Tag().getTitle();
            String artist = mp3File.getId3v2Tag().getArtist();
            String album = mp3File.getId3v2Tag().getAlbum();
            int length = (int) mp3File.getLength();

            return new Track(title, artist, length, album, mp3FilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Playlist createPlaylistFromM3U(String m3uFilePath) {
        List<String> mp3FilePaths = readM3UFile(m3uFilePath);
        Playlist playlist = new Playlist();
        for (String mp3FilePath : mp3FilePaths) {
            Track track = readMetadataFromMP3File(mp3FilePath);

            if (track != null) {
                playlist.addTrack(track);
            }
        }
        playlists.add(playlist);

        return playlist;
    }
}
