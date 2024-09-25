package com.example.likelion.repository;

import com.example.likelion.entity.*;
import com.example.likelion.exception.CustomException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PlaylistRepositoryTest {

    @Autowired
    PlaylistRepository playlistRepository;

    @PersistenceContext
    EntityManager em;

    Long playlistId;
    Long songId;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .username("jane Doe")
                .email("janeDoe@email.com")
                .build();

        Artist artist = Artist.builder()
                .name("Jane Doe")
                .build();

        Album album = Album.builder()
                .title("Jane Doe's Album")
                .description("Jane Doe's Album")
                .releaseDate(LocalDate.now())
                .artist(artist)
                .build();

        Song song = Song.builder()
                .title("testSong")
                .album(album)
                .artist(artist)
                .releaseDate(LocalDate.now())
                .build();

        Playlist playlist = Playlist.builder()
                .title("existedPlaylist")
                .description("existedPlaylist")
                .build();

        em.persist(member);
        em.persist(artist);
        em.persist(album);
        em.persist(song);
        em.persist(playlist);

        em.flush();
        playlistId = playlist.getId();
        songId = song.getId();
    }

    @Test
    @DisplayName("회원이 플레이리스트를 성공적으로 생성")
    void createPlaylistSuccess() {
        Playlist playlist = Playlist.builder()
                .title("testPlaylist")
                .description("testPlaylist")
                .build();
        Long playlistId = playlistRepository.save(playlist);

        Playlist result = playlistRepository.findPlaylistById(playlistId);
        assertNotNull(result);
        assertEquals(playlist.getTitle(), result.getTitle());
    }

    @Test
    @DisplayName("이름이 중복된 플레이리스트 생성 시 실패")
    void createPlaylistFail() {
        Playlist playlist = Playlist.builder()
                .title("existedPlaylist")
                .description("existedPlaylist")
                .build();
        Assertions.assertThrows(CustomException.class, () -> playlistRepository.save(playlist));
    }

    @Test
    @DisplayName("성공적으로 곡이 추가되는지 확인")
    void addSongToPlaylistSuccess() {
        Long playlistSongId = playlistRepository.addSongToPlaylist(playlistId, songId);

        PlaylistSong result = playlistRepository.findPlaylistSongById(playlistSongId);
        assertNotNull(result);
        assertEquals(playlistId, result.getPlaylist().getId());
        assertEquals(songId, result.getSong().getId());
    }

    @Test
    @DisplayName("존재하지 않는 곡을 추가 시도할 시 실패")
    void addSongToPlaylistFail() {
        assertThrows(CustomException.class, () -> playlistRepository.addSongToPlaylist(playlistId, 99999L));
    }
}