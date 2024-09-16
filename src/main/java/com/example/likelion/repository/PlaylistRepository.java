package com.example.likelion.repository;

import com.example.likelion.entity.Playlist;
import com.example.likelion.entity.PlaylistSong;
import com.example.likelion.entity.Song;
import com.example.likelion.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PlaylistRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Playlist playlist) {
        if (findPlaylistByTitle(playlist.getTitle()) != null) {
            throw new CustomException("이름이 같은 플레이리스트가 이미 존재합니다.");
        }
        em.persist(playlist);
        return playlist.getId();
    }

    public Playlist findPlaylistById(Long id) {
        return em.find(Playlist.class, id);
    }

    public PlaylistSong findPlaylistSongById(Long id) {
        return em.find(PlaylistSong.class, id);
    }

    public Playlist findPlaylistByTitle(String title) {
        try {
            return em.createQuery("select p from Playlist p where p.title = :title", Playlist.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = em.find(Playlist.class, playlistId);
        if (playlist == null) {
            throw new CustomException("플레이리스트가 존재하지 않습니다.");
        }

        Song song = em.find(Song.class, songId);
        if (song == null) {
            throw new CustomException("곡이 존재하지 않습니다.");
        }

        PlaylistSong playlistSong = PlaylistSong.builder()
                .playlist(playlist)
                .song(song)
                .build();

        em.persist(playlistSong);
        return playlistSong.getId();
    }
}
