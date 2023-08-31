package com.codingdojo.authentication.services;

import com.codingdojo.authentication.models.Colaboracion;
import com.codingdojo.authentication.models.Song;
import com.codingdojo.authentication.models.User;
import com.codingdojo.authentication.repositories.ColaboracionRepository;
import org.springframework.stereotype.Service;

@Service
public class ColaboracionService {
    private final ColaboracionRepository colaboracionRepository;

    public ColaboracionService(ColaboracionRepository colaboracionRepository){
        this.colaboracionRepository = colaboracionRepository;
    }

    //CREAR COLABORACION
    public void crearColaboracion(User colaborador, Song song, String anexoLetra){
        Colaboracion colab = new Colaboracion();
        colab.setColaborador(colaborador);
        colab.setCancionColaborada(song);
        colab.setLetra(anexoLetra);

        colaboracionRepository.save(colab);
    }
}
