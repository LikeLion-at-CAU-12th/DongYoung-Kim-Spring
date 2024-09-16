package com.example.likelion.repository;

import com.example.likelion.entity.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class SongRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Song song) {
        em.persist(song);
        return song.getId();
    }

    public Song findById(Long id) {
        return em.find(Song.class, id);
    }

    public Song findByTitle(String title) {
        try {
            return em.createQuery("select s from Song s where s.title = :title", Song.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
