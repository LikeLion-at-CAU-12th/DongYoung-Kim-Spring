package com.example.likelion.repository;

import com.example.likelion.entity.Album;
import com.example.likelion.entity.Artist;
import com.example.likelion.entity.Song;
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

@SpringBootTest
@Transactional
class SongRepositoryTest {

    @Autowired
    SongRepository songRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp() {
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

        em.persist(artist);
        em.persist(album);
        em.persist(song);

        em.flush();
    }

    @Test
    @DisplayName("제목으로 곡 검색 성공")
    void findByTitleSuccess() {
        Song result = songRepository.findByTitle("testSong");
        Assertions.assertEquals("testSong", result.getTitle());
    }

    @Test
    @DisplayName("존재하지 않는 곡으로 검색 시 결과 없음")
    void findByTitleFail() {
        Song result = songRepository.findByTitle("whatSong");
        Assertions.assertNull(result);
    }
}