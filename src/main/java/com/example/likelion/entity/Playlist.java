package com.example.likelion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long id;

    private String title;

    private String description;

    @Builder
    public Playlist(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
