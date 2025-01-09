package dev.Deyvid.misc;

public class Natante {

    private String nome;
    private int capienza;
    private int capienzaAuto = 0;
    private TipoNatante tipo;
    private Compagnia compagnia;
    private int id;

    public Natante(String nome, int typeID, int capienza, int capienzaAuto, Compagnia compagnia, int id){
        this.nome = nome;
        this.capienza = capienza;
        this.capienzaAuto = capienzaAuto;
        this.tipo = TipoNatante.getByInt(typeID);
        this.compagnia = compagnia;
        this.id = id;

    }

    public int getID(){return id;}

    public String getNome() {
        return nome;
    }

    public int getCapienza() {
        return capienza;
    }

    public int getCapienzaAuto() {
        return capienzaAuto;
    }

    public TipoNatante getTipo() {
        return tipo;
    }

    public Compagnia getCompagnia() {
        return compagnia;
    }
}
