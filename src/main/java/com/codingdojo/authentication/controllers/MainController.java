package com.codingdojo.authentication.controllers;

import com.codingdojo.authentication.models.Song;
import com.codingdojo.authentication.models.User;
import com.codingdojo.authentication.services.ColaboracionService;
import com.codingdojo.authentication.services.SongService;
import com.codingdojo.authentication.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    private final UserService userService;
    private final SongService songService;
    private final ColaboracionService colaboracionService;

    public MainController(UserService userService,
                          SongService songService,
                          ColaboracionService colaboracionService){

        this.userService = userService;
        this.songService = songService;
        this.colaboracionService = colaboracionService;
    }

    //NUEVA CANCION - GET
    @GetMapping("/new/song")
    public String renderNewSong(@ModelAttribute("song") Song newSong,
                                HttpSession sesion){
        //Validar si la sesion esta activa
        Long userId = (Long) sesion.getAttribute("idLogueado");
        if(userId == null){

            return "redirect:/loginRegistration";
        }

        return "newSong";
    }

    //NUEVA CANCION - POST
    @PostMapping("/new/song")
    public String crearSong(@Valid @ModelAttribute("song") Song newSong,
                            BindingResult result, HttpSession sesion,
                            Model viewModel){
        //Validar si la sesion esta activa
        Long userId = (Long) sesion.getAttribute("idLogueado");
        if(userId == null){

            return "redirect:/loginRegistration";
        }
        else if(result.hasErrors()){
            User usuario = userService.findUserById(userId);
            System.out.println("hay errores");
            return "newSong";
        }
        newSong.setAutor(userService.findUserById(userId));
        songService.crearSong(newSong);

        return "redirect:/home";
    }

    //DETALLE DE LA CANCION - GET
    @GetMapping("/songs/{idSong}")
    public String mostrarSongDetalle(@PathVariable("idSong") Long idSong,
                                     Model viewModel,
                                     HttpSession sesion){
        //Validar si la sesion esta activa
        Long userId = (Long) sesion.getAttribute("idLogueado");
        if(userId == null){

            return "redirect:/loginRegistration";
        }
        viewModel.addAttribute("cancionDetallada", songService.buscarCancionPorId(idSong));

        return "songDetail";
    }

    //EDITAR LA CANCION - GET
    @GetMapping("/songs/{idSong}/edit")
    public String mostrarViewEdicion(@PathVariable("idSong") Long idSong,
                                    // @ModelAttribute("song") Song song,
                                     HttpSession sesion,
                                     Model viewModel){
        //Validar si la sesion esta activa
        Long userId = (Long) sesion.getAttribute("idLogueado");
        if(userId == null){

            return "redirect:/loginRegistration";
        }

        Song songAeditar = songService.buscarCancionPorId(idSong);
        if (songAeditar == null){
            return "redirect:/home";
        }
        viewModel.addAttribute("letraInicial", songAeditar.getLetra());
        songAeditar.setLetra("");
        viewModel.addAttribute("song", songAeditar);
        viewModel.addAttribute("user", userService.findUserById(userId));
        return "songEdit";
    }

    //EDITAR LA CANCION Y ASIGNAR COLABORACION - PUT
    @PutMapping("/songs/{idSong}/edit")
    public String editarAsignarColSong(@Valid @ModelAttribute("song") Song song,
                                       BindingResult result,
                                       @PathVariable("idSong") Long idSong,
                                       HttpSession sesion,
                                       Model viewModel
                                       ){
        //Validar si la sesion esta activa
        Long userId = (Long) sesion.getAttribute("idLogueado");
        if(userId == null){

            return "redirect:/loginRegistration";
        }
        if(result.hasErrors()){
            User usuario = userService.findUserById(userId);
            System.out.println("hay errores");
            System.out.println(result.getAllErrors());
            Song songAeditar = songService.buscarCancionPorId(idSong);
            song.setAutor(songAeditar.getAutor());
            song.setId(idSong);
            viewModel.addAttribute("letraInicial", songAeditar.getLetra());
            songAeditar.setLetra("");
            viewModel.addAttribute("song", song);
            viewModel.addAttribute("user", usuario);
            return "songEdit";
        }
        Song cancionOriginal = songService.buscarCancionPorId(idSong);//Busco la cancion original porque no quiero perder la letra original

        song.setAutor(cancionOriginal.getAutor());
        song.setId(idSong);
        colaboracionService.crearColaboracion(userService.findUserById(userId), song,song.getLetra());//Creo la colab con la letra que viene desde el form
        song.setLetra(cancionOriginal.getLetra());//Le vuelvo a asignar la letra de la cancion original y mas abajo voy a crear una colaboracion pero no pierdo mi letra y la nueva letra se guarda como colaboracion.



        songService.crearSong(song);
        return "redirect:/songs/"+idSong;
    }

    //ELIMINAR SONG - DELETE
    @DeleteMapping("/songs/{idSong}/delete")
    public String eleminarSong(@PathVariable("idSong")Long idSong){
        songService.eliminarSong(idSong);
        return "redirect:/home";
    }
}
