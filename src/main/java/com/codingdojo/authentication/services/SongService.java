package com.codingdojo.authentication.services;

import com.codingdojo.authentication.models.Song;
import com.codingdojo.authentication.repositories.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }
    //CREAR CANCION
    public Song crearSong(Song song){
        return songRepository.save(song);
    }

    //BUSCAR TODAS LAS CANCIONES
    public List<Song> traerTodasLasCanciones(){
        return songRepository.findAll();
    }

    //BUSCAR UNA CANCION POR ID
    public Song buscarCancionPorId(Long id){
        return songRepository.findById(id).orElse(null);
    }

    //ELIMINAR UNA CANCION
    public void eliminarSong(Long id){
        songRepository.deleteById(id);
    }
}
