package com.codingdojo.authentication.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "colaboraciones")
public class Colaboracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String letra;

    //RELACION CON USUARIO N : 1  de User (Un usuario puede tener muchas colaboraciones, pero una colaboracion un solo usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User colaborador;

    //RELACION CON USUARIO N : 1 de Song (Una cacion puede tener muchas colaboraciones, pero una colaboracion tiene solo una cancion)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song cancionColaborada;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    public Colaboracion() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public User getColaborador() {
        return colaborador;
    }

    public void setColaborador(User colaborador) {
        this.colaborador = colaborador;
    }

    public Song getCancionColaborada() {
        return cancionColaborada;
    }

    public void setCancionColaborada(Song cancionColaborada) {
        this.cancionColaborada = cancionColaborada;
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
