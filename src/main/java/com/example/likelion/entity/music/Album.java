package com.example.likelion.entity.music;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    private String title;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Artist artist;

    private LocalDate releaseDate;

    @Builder
    public Album(String title, String description, Artist artist, LocalDate releaseDate) {
        this.title = title;
        this.description = description;
        this.artist = artist;
        this.releaseDate = releaseDate;
    }
}
