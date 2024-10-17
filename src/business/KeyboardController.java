package business;

import java.util.Scanner;

public class KeyboardController {
    private MP3Player mp3player;
    private PlaylistManager playlistManager;

    public KeyboardController() {
        this.mp3player = new MP3Player();
        this.playlistManager = new PlaylistManager();
    }

    public void start() {
        String input;
        String[] commands;
        Scanner sc=new Scanner(System.in);
        do{
            input= sc.next();
            commands=input.split(" ");
            if(input.equals("quit")){
                break;
            }
            switch (commands[0]) {
                case "play" -> mp3player.play("");
                case "pause" -> mp3player.pause();
                case "volume" -> {
                    float v = sc.nextFloat();
                    mp3player.volume(v);
                }
                case "selectplaylist" -> {
                    String name= sc.next();
                    if (name.equals("playlist1")) {
                        Playlist selectedPlaylist = playlistManager.createPlaylistFromM3U("/Users/macbook/IdeaProjects/eibo/src/tracks/list.m3u");
                        selectedPlaylist.setTitle(name);
                        selectedPlaylist=playlistManager.getPlaylist(name);
                        mp3player.setPlaylist(selectedPlaylist);
                        playlistManager.getAllTracks();
                        if (selectedPlaylist!=null){
                            System.out.println("Playlist selected");
                        }
                    }else {
                        System.out.println("Playlist not found.");
                    }
                }
                case "selecttrack" -> {
                    int id = sc.nextInt();
                    mp3player.selectTrack(id);
                }
                case "skip" ->{
                    mp3player.skip();
                }
                case "skipback" -> {
                    mp3player.skipBack();
                }
                case "shuffle" -> {
                    mp3player.shuffle(true);
                }
                case "repeat" ->{
                    mp3player.repeat(true);
                }
            }
        }while(true);

    }
}
