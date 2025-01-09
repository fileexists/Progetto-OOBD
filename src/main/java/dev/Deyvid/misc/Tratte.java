package dev.Deyvid.misc;

public class Tratte {

    private int corsaID;
    private int posti;
    private int posti_auto;
    private int porto_partenza;
    private int porto_arrivo;

    private String orarioArrivo, orarioPartenza;

    public Tratte(int corsaID, int porto_partenza, String orarioPartenza, int porto_arrivo, String orarioArrivo, int posti, int posti_auto){
        this.corsaID = corsaID;
        this.porto_partenza = porto_partenza;
        this.porto_arrivo = porto_arrivo;
        this.posti = posti;
        this.posti_auto = posti_auto;
        this.orarioArrivo = orarioArrivo;
        this.orarioPartenza = orarioPartenza;
    }

    public Tratte(int corsaID, int posti, int posti_auto){
        this.corsaID = corsaID;
        this.posti = posti;
        this.posti_auto = posti_auto;
    }

    public int getCorsaID() {
        return corsaID;
    }

    public int getPosti() {
        return posti;
    }

    public int getPostiAuto() {
        return posti_auto;
    }

    public int getPortoPartenza() {
        return porto_partenza;
    }

    public int getPortoArrivo() {
        return porto_arrivo;
    }

    public String getOrarioArrivo() {
        return orarioArrivo;
    }

    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public void setPostiAuto(int posti_auto) {
        this.posti_auto = posti_auto;
    }

}
