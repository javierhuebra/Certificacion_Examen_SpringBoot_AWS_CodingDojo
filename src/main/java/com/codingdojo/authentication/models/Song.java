package com.codingdojo.authentication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El titulo no puede ser nulo")
    private String titulo;

    @NotBlank(message = "El genero no puede ser nulo")
    private String genero;

    @NotBlank(message = "La letra no puede ser nula")
    private String letra;

    //RELACION CON USUARIOS N : 1 Con User (Una cancion tiene un autor, un autor tiene muchas canciones)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User autor;

    //RELACION CON COLABORACIONES N : 1 Con Colaboracion (Una cancion tiene muchas colaboraciones, una colaboracion solo tiene una cancion)
    @OneToMany(mappedBy = "cancionColaborada", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private List<Colaboracion> colaboracionesRecibidas;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    public Song() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public List<Colaboracion> getColaboracionesRecibidas() {
        return colaboracionesRecibidas;
    }

    public void setColaboracionesRecibidas(List<Colaboracion> colaboracionesRecibidas) {
        this.colaboracionesRecibidas = colaboracionesRecibidas;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
