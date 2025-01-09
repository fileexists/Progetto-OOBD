package dev.Deyvid.misc;

import dev.Deyvid.application.App;

import java.util.List;

public class Compagnia {




    private String nome;
    private int id;
    private String telefono, email, sito;

    public Compagnia(int id, String nome, String telefono, String email, String sito){
        this.nome = nome;
        this.id = id;
        this.telefono= telefono;
        this.email = email;
        this.sito = sito;
    }

    public int getID() {
        return id;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getSito() {
        return sito;
    }

    public List<Natante> getNatanti() {
        return App.getDatabase().fetchNatanti(this);
    }

    public String getNome() {
        return nome;
    }

    public List<Corsa> getCorse() {
        return App.getDatabase().fetchCorse(this);
    }

    public enum Fields {
        EMAIL,
        PHONE_NUMBER,
        WEBSITE
    }
}
