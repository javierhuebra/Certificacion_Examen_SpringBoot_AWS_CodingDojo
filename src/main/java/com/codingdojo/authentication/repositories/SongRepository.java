package com.codingdojo.authentication.repositories;

import com.codingdojo.authentication.models.Song;
import com.codingdojo.authentication.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song,Long> {
    List<Song> findAll();
}
