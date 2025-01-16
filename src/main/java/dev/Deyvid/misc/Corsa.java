package dev.Deyvid.misc;

import dev.Deyvid.application.App;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Corsa {

    private int portoPartenza;
    private int portoArrivo;
    private int scalo;
    private int idCompagnia;
    private String orarioArrivo, orarioPartenza, orarioScalo;
    private String dataInizio, dataFine;
    private String cadenza;
    private double prezzo, prezzoRidotto;
    private double sovrapprezzoBagagli, sovrapprezzoPrenotazione;
    private Natante natante;
    private boolean inRitardo;
    private int id;

    public Corsa(int portoPartenza, int portoArrivo, int scalo, int idCompagnia, String orarioArrivo, String orarioPartenza, String orarioScalo, String dataInizio, String dataFine, String cadenza, double prezzo, double prezzoRidotto, double sovrapprezzoBagagli, double sovrapprezzoPrenotazione, Natante natante, boolean inRitardo){
        this.portoPartenza = portoPartenza;
        this.portoArrivo = portoArrivo;
        this.scalo = scalo;
        this.idCompagnia = idCompagnia;
        this.orarioArrivo = orarioArrivo;
        this.orarioPartenza = orarioPartenza;
        this.orarioScalo = orarioScalo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.cadenza = cadenza;
        this.prezzo = prezzo;
        this.prezzoRidotto = prezzoRidotto;
        this.sovrapprezzoBagagli = sovrapprezzoBagagli;
        this.sovrapprezzoPrenotazione = sovrapprezzoPrenotazione;
        this.inRitardo = inRitardo;
        this.natante = natante;
    }

    public Corsa(int portoPartenza, int portoArrivo, int scalo, int idCompagnia, String orarioArrivo, String orarioPartenza, String orarioScalo, String dataInizio, String dataFine, String cadenza, double prezzo, double prezzoRidotto, double sovrapprezzoBagagli, double sovrapprezzoPrenotazione, Natante natante, int id, boolean inRitardo){
        this.portoPartenza = portoPartenza;
        this.portoArrivo = portoArrivo;
        this.scalo = scalo;
        this.idCompagnia = idCompagnia;
        this.orarioArrivo = orarioArrivo;
        this.orarioPartenza = orarioPartenza;
        this.orarioScalo = orarioScalo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.cadenza = cadenza;
        this.prezzo = prezzo;
        this.prezzoRidotto = prezzoRidotto;
        this.sovrapprezzoBagagli = sovrapprezzoBagagli;
        this.sovrapprezzoPrenotazione = sovrapprezzoPrenotazione;
        this.natante = natante;
        this.inRitardo = inRitardo;
        this.id = id;
    }

    public int getPortoPartenza() {
        return portoPartenza;
    }

    public int getPortoArrivo() {
        return portoArrivo;
    }

    public int getScalo() { return scalo; }

    public String getOrarioArrivo() { return orarioArrivo; }

    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    public String getOrarioScalo() {
        return orarioScalo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public double getPrezzoRidotto() {
        return prezzoRidotto;
    }

    public double getSovrapprezzoBagagli() {
        return sovrapprezzoBagagli;
    }

    public double getSovrapprezzoPrenotazione() {
        return sovrapprezzoPrenotazione;
    }

    public Natante getNatante() {
        return natante;
    }

    public int getIdCompagnia() {
        return idCompagnia;
    }

    public String getCadenza() {
        return cadenza;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public boolean isInRitardo() { return inRitardo; }

    public int getID() {
        return id;
    }

}
