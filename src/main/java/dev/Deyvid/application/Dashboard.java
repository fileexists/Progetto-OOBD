package dev.Deyvid.application;
import dev.Deyvid.database.Database;
import dev.Deyvid.misc.*;

import dev.Deyvid.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dashboard extends JFrame {
    private JPanel panel;
    private JButton filtriButton;
    private JComboBox orderBy;
    private JPanel corsePanel;
    private JPanel filterPanel;
    private User utente;
    private List<FetchFilter> filters;

    public Dashboard(User user) {
        this.utente = user;
        this.filters = new ArrayList<>();
        setContentPane(panel);
        initializeDashboard();
        filtriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox portoPartenza = new JCheckBox("Filtra per porto partenza");
                JCheckBox portoArrivo = new JCheckBox("Filtra per porto arrivo");
                JCheckBox scalo = new JCheckBox("Mostra corse con scalo");
                JCheckBox automezzi = new JCheckBox("Mostra corse con automezzi");
                JCheckBox prezzo = new JCheckBox("Filtra per prezzo");
                JCheckBox data = new JCheckBox("Filtra per data");

                JPanel filters_panel = new JPanel();
                filters_panel.setLayout(new GridLayout(0, 1));
                filters_panel.add(new JLabel("Seleziona i filtri per le corse"));
                filters_panel.add(portoPartenza);
                filters_panel.add(portoArrivo);
                filters_panel.add(scalo);
                filters_panel.add(automezzi);
                filters_panel.add(prezzo);
                filters_panel.add(data);
                int filter_dialog = JOptionPane.showConfirmDialog(null, filters_panel, "Lista Filtri", JOptionPane.OK_CANCEL_OPTION);
                if(filter_dialog == JOptionPane.OK_OPTION){
                    List<Porto> porti = App.getDatabase().fetchPorti();
                    JComboBox portoPartenzaBox = new JComboBox();
                    JComboBox portoArrivoBox = new JComboBox();

                    porti.forEach(porto -> {
                            portoPartenzaBox.addItem(porto.getID());
                            portoArrivoBox.addItem(porto.getID());
                        }
                    );

                    List<FetchFilter> filters = new ArrayList<>();



                    JPanel values_panel = new JPanel();
                    values_panel.setLayout(new GridLayout(0, 2));
                    values_panel.add(new JLabel("Immetti il valore dei filtri"));
                    values_panel.add(new JLabel(" "));

                    values_panel.add(new JLabel("Porto Partenza"));
                    values_panel.add(portoPartenzaBox);

                    values_panel.add(new JLabel("Porto arrivo"));
                    values_panel.add(portoArrivoBox);

                    values_panel.add(new JLabel("Prezzo"));
                    JTextField prezzoField = new JTextField();
                    prezzoField.setName("prezzo");
                    values_panel.add(prezzoField);

                    values_panel.add(new JLabel("Data partenza"));
                    JTextField dataField = new JTextField();
                    dataField.setName("data");
                    values_panel.add(dataField);

                    if(!portoPartenza.isSelected()) portoPartenzaBox.setEnabled(false);
                    if(!portoArrivo.isSelected()) portoArrivoBox.setEnabled(false);
                    if(!prezzo.isSelected()) prezzoField.setEnabled(false);
                    if(!data.isSelected()) dataField.setEnabled(false);

                    //if(automezzi.isSelected()) filters.add(FetchFilter.AUTOMEZZI.setValue());


                    int search = JOptionPane.showConfirmDialog(null, values_panel, "Lista Filtri", JOptionPane.OK_CANCEL_OPTION);
                    if(search == JOptionPane.OK_OPTION){
                        if(portoPartenza.isSelected())filters.add(FetchFilter.PORTO_PARTENZA.setValue((Integer) portoPartenzaBox.getSelectedItem()));

                        if(portoArrivo.isSelected()) filters.add(FetchFilter.PORTO_ARRIVO.setValue((Integer) portoArrivoBox.getSelectedItem()));

                        if(prezzo.isSelected()) filters.add(FetchFilter.PREZZO.setValue(Validator.getDouble(prezzoField)));

                        if(data.isSelected()) filters.add(FetchFilter.DATA.setValue(Validator.filterDateFormat(dataField)));


                        filters.add(FetchFilter.ORDER_DATA.order());
                        filters.add(FetchFilter.LIMIT.setValue(15));
                        Database.FetchResult result = App.getDatabase().fetchCorse(filters);
                        if(result == null) throw new Validator.InvalidData("Nessuna corsa trovata");

                        renderCorse(result);

                    }
                }
            }
        });
        orderBy.addItemListener(new ItemListener() {
            private Object previousItem = orderBy.getItemAt(0);
            public void itemStateChanged(ItemEvent ev) {

                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    if (!ev.getItem().equals(previousItem)) {
                        previousItem = ev.getItem();
                        Database.FetchResult result = null;
                        List<FetchFilter> filters = new ArrayList<>();

                        if(ev.getItem() == "Ordina per prezzo"){
                            filters.add(FetchFilter.ORDER_PREZZO.order());
                        }
                        else if(ev.getItem() == "Ordina per data"){
                            filters.add(FetchFilter.ORDER_DATA.order());
                        }
                        filters.add(FetchFilter.LIMIT.setValue(15));
                        result = App.getDatabase().fetchCorse(filters);
                        if(result == null){
                            dispose();
                            throw new Validator.InvalidData("Nessuna corsa trovata");
                        }
                        renderCorse(result);

                    }
                }
            }
        });
    }

    private void initializeDashboard() {
        SwingUtilities.invokeLater(() -> {
            // JFrame frame = new JFrame("Dashboard di " + utente.getUsername());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            corsePanel.setLayout(new BoxLayout(corsePanel, BoxLayout.Y_AXIS));
            orderBy.addItem("Ordina per data");
            orderBy.addItem("Ordina per prezzo");
        });
    }

    public void renderCorse(Database.FetchResult result){
        SwingUtilities.invokeLater(()->{
            corsePanel.removeAll();
            panel.removeAll();
            panel.add(filterPanel);
            for(Corsa corsa : result.getCorse()){
                JLabel label;
                String ritardo = "238 238 238";
                if(corsa.isInRitardo()) ritardo = "255 0 0";
                if(corsa.getScalo() != 0){

                    if(corsa.isInRitardo()) ritardo = "255 0 0";
                    label = new JLabel(String.format(
                            "<html><div style='border:2px solid black; padding:3px;'>" +
                                    "Porto partenza: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Porto arrivo: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Porto scalo: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Orario partenza: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Orario arrivo: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Orario scalo: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Prezzo: <span style='font-weight: normal;'>%.2f</span><br>" +
                                    "<h4 style='color:rgb(%s);font-weight: bold;'>IN RITARDO</h4>" +
                                    "</div></html>",
                            corsa.getPortoPartenza(), corsa.getPortoArrivo(), corsa.getScalo(),
                            corsa.getOrarioPartenza(), corsa.getOrarioArrivo(), corsa.getOrarioScalo(),
                            corsa.getPrezzo(), ritardo
                    ));
                }
                else{
                    label = new JLabel(String.format(
                            "<html><div style='border:2px solid black; padding:3px;'>" +
                                    "Porto partenza: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Porto arrivo: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Orario partenza: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Orario arrivo: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Prezzo: <span style='font-weight: normal;'>%.2f</span><br>" +
                                    "<h4 style='color:rgb(%s);font-weight: bold;'>IN RITARDO</h4>" +
                                    "</div></html>",
                            corsa.getPortoPartenza(), corsa.getPortoArrivo(),
                            corsa.getOrarioPartenza(), corsa.getOrarioArrivo(),
                            corsa.getPrezzo(), ritardo
                    ));
                }
                JButton button = new JButton("Prenota biglietto");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JCheckBox choice_1 = new JCheckBox(String.format("Da %d a %d", corsa.getPortoPartenza(), corsa.getPortoArrivo()));
                        JCheckBox choice_2 = null, choice_3 = null;
                        ButtonGroup group = new ButtonGroup();
                        JPanel choices_panel = new JPanel();
                        choices_panel.setLayout(new GridLayout(0, 1));
                        choices_panel.add(new JLabel("Seleziona la tratta che ti interessa"));
                        choices_panel.add(choice_1);
                        group.add(choice_1);

                        if (corsa.getScalo() != 0) {
                            choice_2 = new JCheckBox(String.format("Da %d a %d", corsa.getPortoPartenza(), corsa.getScalo()));
                            choice_3 = new JCheckBox(String.format("Da %d a %d", corsa.getScalo(), corsa.getPortoArrivo()));
                            choices_panel.add(choice_2);
                            choices_panel.add(choice_3);
                            group.add(choice_2);
                            group.add(choice_3);
                        }

                        JSpinner prenota_posti = new JSpinner();
                        prenota_posti.setValue(1);
                        choices_panel.add(new JLabel(" "));
                        choices_panel.add(new JLabel("Posti da prenotare"));
                        choices_panel.add(prenota_posti);

                        JSpinner prenota_posti_auto = new JSpinner();
                        prenota_posti_auto.setValue(0);
                        if(corsa.getNatante().getTipo() == TipoNatante.Traghetto){
                            choices_panel.add(new JLabel(" "));
                            choices_panel.add(new JLabel("Posti auto da prenotare"));
                            choices_panel.add(prenota_posti_auto);
                        }

                        int option = JOptionPane.showConfirmDialog(null, choices_panel, "Lista Tratte", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            if (choice_1.isSelected()) {

                                // DA PARTENZA AD ARRIVO
                                prenotaTratta(result, corsa.getID(), corsa.getPortoPartenza(), corsa.getPortoArrivo(), (Integer) prenota_posti.getValue(), (Integer) prenota_posti_auto.getValue());

                            } else if (choice_2 != null && choice_2.isSelected()) {

                                // DA PARTENZA A SCALO
                                prenotaTratta(result, corsa.getID(), corsa.getPortoPartenza(), corsa.getScalo(), (Integer) prenota_posti.getValue(), (Integer) prenota_posti_auto.getValue());

                            } else if (choice_3 != null && choice_3.isSelected()) {

                                // DA SCALO AD ARRIVO
                                prenotaTratta(result, corsa.getID(), corsa.getScalo(), corsa.getPortoArrivo(), (Integer) prenota_posti.getValue(), (Integer) prenota_posti_auto.getValue());

                            }
                            else{
                                throw new Validator.InvalidData("Seleziona almeno una tratta");
                            }
                        } else {
                            System.out.println("User canceled the operation.");
                        }
                    }

                });
                JPanel rowPanel = new JPanel(new BorderLayout());
                rowPanel.add(label, BorderLayout.WEST);
                rowPanel.add(button, BorderLayout.EAST);
                corsePanel.add(rowPanel);

                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(2, 2));
                corsePanel.add(spacer);
            }
            JScrollPane scrollPane = new JScrollPane(corsePanel);
            add(scrollPane);
            setSize(590, 380);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    public void prenotaTratta(Database.FetchResult result, int corsaID, int porto_partenza, int porto_arrivo, int posti, int posti_auto){
        Optional<Tratte> optionalTratta = result.getTratte().stream()
                .filter(filterTratta -> filterTratta.getCorsaID() == corsaID &&
                        porto_partenza == filterTratta.getPortoPartenza() &&
                        porto_arrivo == filterTratta.getPortoArrivo() )
                .findFirst();

        Tratte tratta = null;
        if(optionalTratta.isPresent()){
            tratta = optionalTratta.get();
            if(tratta.getPosti() < posti || tratta.getPostiAuto() < posti_auto) { System.out.println("ERRORE PRENOTAZIONE"); return;}
            tratta.setPosti(tratta.getPosti() - posti);
            tratta.setPostiAuto(tratta.getPostiAuto() - posti_auto);
            int index = result.getTratte().indexOf(tratta);
            if (index != -1) {
                result.getTratte().set(index, tratta);
                System.out.println(String.format("Prenotata corsa %d tratta da %d a %d e ora ha posti %d disponibili", corsaID, porto_partenza, porto_arrivo, tratta.getPosti()));
                App.getDatabase().updateTratta(tratta, corsaID);
            }
        }

    }

}
