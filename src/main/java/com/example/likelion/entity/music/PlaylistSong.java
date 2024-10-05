package com.example.likelion.entity.music;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"song_id", "playlist_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_song_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Builder
    public PlaylistSong(Song song, Playlist playlist) {
        this.song = song;
        this.playlist = playlist;
    }
}
