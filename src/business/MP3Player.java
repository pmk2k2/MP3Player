package business;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Collections;
import java.util.List;

public class MP3Player {
    private SimpleAudioPlayer audioPlayer;
    private SimpleMinim minim;
    private Playlist actPlaylist;
    private int trackNo;
    private boolean isPlaying;
    private Thread playThread;
    private Thread timeThread;
    private SimpleObjectProperty<Track> actTrack;
    private SimpleIntegerProperty currentTime;
    private int lastPlaybackPosition;

    public MP3Player() {
        minim = new SimpleMinim();
        audioPlayer = minim.loadMP3File("/Users/macbook/IdeaProjects/eibo/src/tracks/01 Bring Mich Nach Hause.mp3");
        //Benutze den Default Song-Path
        isPlaying = false;
        currentTime = new SimpleIntegerProperty();
        actTrack = new SimpleObjectProperty<>();
    }

    public void play(String fileName) {
        this.actTrack.setValue(actPlaylist.getTrack(trackNo));
        isPlaying = true;
        playThread = new Thread() {
            public void run() {
                if(audioPlayer==null|| !isPlaying()){
                    audioPlayer= minim.loadMP3File(fileName);
                    if (lastPlaybackPosition > 0) {
                        audioPlayer.play(lastPlaybackPosition);
                    } else {
                        audioPlayer.play();
                    }
                }
                else{
                    audioPlayer.play();
                }
            }

        };
        playThread.setDaemon(true);
        playThread.start();

        if(audioPlayer==null){
            currentTime.setValue(0);
        }
        else{
            currentTime.setValue(currentTime.getValue());
        }
            if (isPlaying) {

                if (timeThread != null && timeThread.isAlive()) {
                    timeThread.interrupt();
                }//mechanism to manage the playback time. When the user initiates an action like skipping to the next track (skip() method is called), the current playback thread (timeThread) is interrupted to stop updating the playback time for the previous track. This helps in maintaining proper synchronization and avoiding unnecessary processing for the previous track when it's no longer playing.

                timeThread = new Thread(() -> {
                    while (isPlaying) {
                        currentTime.setValue(currentTime.getValue() + 1);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            return;
                        }
                        if (currentTime.getValue() >= actPlaylist.getTrack(trackNo).getLength()) {
                            skip();
                        }
                    }
                });

                timeThread.start();
            }
    }

    public void seek( int selectedPosition, String fileName) {
        if (playThread != null && playThread.isAlive()) {
            playThread.interrupt();
            timeThread.interrupt();
            audioPlayer.pause();
        }
        if (timeThread != null && timeThread.isAlive()) {
            timeThread.interrupt();
        }
            playThread = new Thread() {
                public void run() {
                    audioPlayer = minim.loadMP3File(fileName);

                    if (playThread != null && playThread.isAlive()) {
                        playThread.interrupt();
                    }
                    audioPlayer.pause();
                    audioPlayer.play(selectedPosition);

                }

            };
            playThread.setDaemon(true);
            playThread.start();

                        timeThread = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                currentTime.setValue(selectedPosition / 1000);

                                while (isPlaying) {
                                    currentTime.setValue(currentTime.getValue() + 1);

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {

                                        return;
                                    }
                                }
                            }
                        });

                        timeThread.start();
    }

    public void play(){
        audioPlayer.play();
        isPlaying=true;

    }

    public void pause() {
        if (audioPlayer.isPlaying()) {
            lastPlaybackPosition = audioPlayer.position();
            audioPlayer.pause();
            isPlaying = false;
            if (timeThread != null && timeThread.isAlive()) {
                timeThread.interrupt();
            }
        }
    }


    public void volume(double value) {
        audioPlayer.setGain(0 + (-60) * (1 - (float)(value) / 100));
    }

    public void setPlaylist(Playlist playlist){
        actPlaylist=playlist;
        System.out.println("Playing: " + actPlaylist.getTitle());
    }
    public Track selectTrack(int no){
        Track selectedTrack= null;
        if (actPlaylist != null) {
            System.out.println("Number of tracks in the playlist: " + actPlaylist.numberOfTracks());
            if (no >= 0 && no < actPlaylist.numberOfTracks()) {
                selectedTrack = actPlaylist.getTrack(no);
                this.actTrack.setValue(selectedTrack);
                if (selectedTrack != null) {
                    pause();
                    play(selectedTrack.getMp3FilePath());
                    System.out.println("Playing " + selectedTrack.getMp3FilePath());
                } else {
                    System.out.println("Track not found.");
                }
            } else {
                System.out.println("No playlist selected.");
            }
        }
        return selectedTrack;
    }

    public synchronized void skip(){
        currentTime.setValue(0);
        lastPlaybackPosition=0;
        if (timeThread != null && timeThread.isAlive()) {
            timeThread.interrupt();
        }
        if(audioPlayer.isPlaying()){
            audioPlayer.pause();
        }
        trackNo++;
        if(trackNo< actPlaylist.numberOfTracks()){
            selectTrack(trackNo);
            this.actTrack.setValue(actPlaylist.getTrack(trackNo));
        }
        else{
            trackNo = 0;
            selectTrack(this.trackNo);
            this.actTrack.setValue(actPlaylist.getTrack(trackNo));
        }
    }
    public synchronized void skipBack(){
        lastPlaybackPosition=0;
        currentTime.setValue(0);
        if(audioPlayer.isPlaying()){
            audioPlayer.pause();
        }
        if(trackNo>0){
            trackNo--;
            selectTrack(trackNo);
            this.actTrack.setValue(actPlaylist.getTrack(trackNo));}
        else{
            trackNo = 0;
            selectTrack(trackNo);
            this.actTrack.setValue(actPlaylist.getTrack(trackNo));
        }
    }
    public synchronized void shuffle( boolean on){
        currentTime.setValue(0);
        lastPlaybackPosition=0;
        if (actPlaylist != null) {
            List<Track> tracks = actPlaylist.getTracks();

            if (tracks != null && !tracks.isEmpty()) {
                Collections.shuffle(tracks);
                audioPlayer.pause();
                play(tracks.get(0).getMp3FilePath());
                this.actTrack.setValue(tracks.get(0));
            } else {
                System.out.println("No tracks in playlist to shuffle.");
            }
        } else {
            System.out.println("No playlist selected.");
        }
    }
    public synchronized void repeat(boolean on){
        lastPlaybackPosition=0;
        currentTime.setValue(0);
        audioPlayer.loop();
    }

    public boolean isPlaying() {
        return audioPlayer.isPlaying();
    }
    public SimpleIntegerProperty currentTimeProperty() {
        return currentTime;
    }
    public int getCurrentTime() {
        return currentTime.get();
    }
    public SimpleObjectProperty<Track> actTrackProperty() {
        return actTrack;
    }
    public Track getActTrack() {
        return actTrack.get();
    }

    public int getTrackNo() {
        this.actTrack.setValue(actPlaylist.getTrack(trackNo));
        return trackNo;
    }

    public void setTrackNo(int trackNo) {
        this.actTrack.setValue(actPlaylist.getTrack(trackNo));
        this.trackNo = trackNo;
    }

    public void setLastPlaybackPosition(int lastPlaybackPosition) {
        this.lastPlaybackPosition = lastPlaybackPosition;
    }

}
