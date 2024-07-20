package business;

import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class MP3Player {
    private SimpleAudioPlayer audioPlayer;
    private SimpleMinim minim;
    private Playlist actPlaylist;
    private int trackNo;

    public MP3Player() {
        minim = new SimpleMinim(true);
        audioPlayer = new SimpleAudioPlayer(null, true);
        audioPlayer = minim.loadMP3File("/Users/macbook/IdeaProjects/eibo1/src/Tracks/01 Bring Mich Nach Hause.mp3");
        this.trackNo= trackNo;
    }

    public void play(String fileName) {
        audioPlayer = minim.loadMP3File(fileName);
        System.out.println(fileName);
        audioPlayer.play();
    }
    public void play() {
        audioPlayer.play();
    }

    public void pause() {
        audioPlayer.pause();
    }

    void volume(float value) {
        audioPlayer.setGain(value);
        System.out.print("Volume: "+value);
    }

    void setPlaylist(Playlist playlist){
        actPlaylist=playlist;
        System.out.println("Playing: " + actPlaylist.getTitle());
    }
    void selectTrack(int no){
        if (actPlaylist != null) {
            System.out.println("Number of tracks in the playlist: " + actPlaylist.numberOfTracks());
            if (no >= 0 && no < actPlaylist.numberOfTracks()) {
                Track selectedTrack = actPlaylist.getTrack(no);

                if (selectedTrack != null) {
                    play(selectedTrack.getMp3FilePath());
                    System.out.println("Playing " + selectedTrack.getMp3FilePath());
                } else {
                    System.out.println("Track not found.");
                }
            } else {
                System.out.println("No playlist selected.");
            }
        }
    }
    void skip(){
        if(audioPlayer.isPlaying()){
            audioPlayer.pause();
        }
        if(trackNo< actPlaylist.numberOfTracks()){
            trackNo++;
            selectTrack(trackNo);}
    }
    void skipBack(){
        if(audioPlayer.isPlaying()){
            audioPlayer.pause();
        }
        if(trackNo>0){
            trackNo--;
            selectTrack(trackNo);}
    }
    void suffle( boolean on){

    }
    void repeat(boolean on){

    }
}
