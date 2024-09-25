package com.example.likelion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long id;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Artist artist;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Album album;

    private LocalDate releaseDate;

    @Builder
    public Song(String title, Artist artist, Album album, LocalDate releaseDate) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
    }
}
