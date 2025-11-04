package com.docencia.repo.jpa;

import com.docencia.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INoteJpaRepository extends JpaRepository<Note, String> {

    Optional<Note> findFirstByTitle(String title);

}
