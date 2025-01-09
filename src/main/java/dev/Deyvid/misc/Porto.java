package dev.Deyvid.misc;

public class Porto {

    private int id;
    private String comune;
    private String indirizzo;
    private String telefono;


    public Porto(int id, String comune, String indirizzo, String telefono){
        this.id = id;
        this.comune = comune;
        this.indirizzo= indirizzo;
        this.telefono = telefono;
    }

    public int getID() {
        return id;
    }

    public String getComune() {
        return comune;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }
}
