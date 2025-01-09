package dev.Deyvid.application;

import dev.Deyvid.misc.*;
import dev.Deyvid.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DashCompagnia extends JFrame {


    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JButton logoutButton;
    private JButton editPhoneNumberButton;
    private JButton editWebsiteButton;
    private JButton editEmailButton;
    private JPanel natanti;
    private JPanel corse;
    private JPanel account;
    private JTabbedPane natantiTabbed;
    private JTabbedPane corseTabbed;
    private JPanel gestisciCorse;
    private JPanel aggiungiCorse;
    private JPanel gestisciNatanti;
    private JPanel aggiungiNatanti;
    private JComboBox tipoNatante;
    private JSpinner postiAuto;
    private JSpinner postiNatante;
    private JButton salvaNatante;
    private User utente;
    private Compagnia compagnia;
    private JTextField nomeNatante;
    private JComboBox natanteCompagnia;
    private JComboBox portoPartenza;
    private JComboBox portoArrivo;
    private JComboBox portoScalo;
    private JCheckBox scaloCheckBox;
    private JButton creaCorsa;
    private JTextField orarioPartenza;
    private JTextField orarioArrivo;
    private JTextField orarioScalo;
    private JTextField periodo;
    private JTextField cadenza;
    private JTextField prezzo;
    private JTextField prezzoRidotto;
    private JTextField sovrapprezzoBagagli;
    private JTextField sovrapprezzoPrenotazione;
    private JLabel labelOrarioPartenza;
    private JLabel labelPortoPartenza;
    private JLabel labelPortoArrivo;
    private JLabel labelOrarioArrivo;
    private JLabel labelPortoScalo;
    private JLabel labelOrarioScalo;
    private JLabel labelPeriodo;
    private JLabel labelCadenza;
    private JLabel labelPrezzo;
    private JLabel labelPrezzoRidotto;
    private JLabel labelSovrappBagagli;
    private JLabel labelSovrappPrenotazione;
    private JLabel labelPostiAuto;
    private JLabel labelPostiNatante;
    private JLabel labelTipoNatante;
    private JLabel labelNomeNatante;
    private JLabel labelWebsite;
    private JLabel labelEmail;
    private JLabel labelPhoneNumber;

    private Map<Integer, Integer> modify_natanti;
    private Map<Integer, Integer> modify_corse;

    public DashCompagnia(User utente) {
        this.utente = utente;
        getCompanyData(utente.getUsername());
        checkPhoneNumber();
        modify_natanti = new HashMap<>(); // usato per evitare conflitti di bottone salva Natante
        modify_corse = new HashMap<>(); // usato per evitare conflitti di bottone salva Natante
        /*
         *
         * POPOLAZIONE DELLE GUI
         *
         */
        setContentPane(panel);


        /*
         *
         * GESTORE EVENTI
         *
         */
        handleAddNatante();
        handleAddCorsa();
        handleAccountSection();

        /*
         *
         * UTILS
         *
         */

        addPlaceholder(cadenza, "Lunedi, Martedi");
        addPlaceholder(orarioPartenza, "13:30");
        addPlaceholder(orarioScalo, "14:30");
        addPlaceholder(orarioArrivo, "15:30");
        addPlaceholder(periodo, "01-01-2023/31-05-2023");
        addPlaceholder(prezzo, "15.0");
        addPlaceholder(prezzoRidotto, "10.0");
        addPlaceholder(sovrapprezzoBagagli, "5.0");
        addPlaceholder(sovrapprezzoPrenotazione, "7.5");

        //addComponentListener(new ComponentAdapter() {
        //    @Override
        //    public void componentResized(ComponentEvent e) {
        //        printWindowSize();
        //    }
        //});

    }

    /*
     *
     *
     *   SEZIONE ACCOUNT
     *
     *
     */

    private void checkPhoneNumber() {
        if (compagnia.getTelefono() == null || compagnia.getTelefono().equalsIgnoreCase("")) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Dashboard di " + utente.getUsername());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setSize(new Dimension(1000,1000));


                JTextField numeroTelefono = new JTextField();
                JTextField email = new JTextField();
                JTextField sito = new JTextField();

                JPanel missing_data = new JPanel();
                missing_data.setLayout(new GridLayout(0, 1));
                missing_data.add(new JLabel("Compila con i dati richiesti!"));
                missing_data.add(new JLabel(" "));

                missing_data.add(new JLabel("Telefono*"));
                missing_data.add(numeroTelefono);

                missing_data.add(new JLabel("Emal"));
                missing_data.add(email);

                missing_data.add(new JLabel("Sito web"));
                missing_data.add(sito);

                missing_data.add(new JLabel(" "));
                missing_data.add(new JLabel("* (campo obbligatorio)"));

                int missing_data_dialog = -1;
                int valid = 0;
                while(valid == 0){
                    missing_data_dialog = JOptionPane.showConfirmDialog(null, missing_data, "Dati mancanti", JOptionPane.OK_CANCEL_OPTION);
                    if(missing_data_dialog == JOptionPane.OK_OPTION){
                        if(Validator.phoneNumberFormat(numeroTelefono.getText())){
                            valid = 1;
                            JOptionPane.showMessageDialog(frame, "Puoi modificare i dati nella sezione Account", "", JOptionPane.INFORMATION_MESSAGE);
                            App.getDatabase().updateCompagnia(utente.getUsername(), numeroTelefono.getText(),
                                   email.getText(), sito.getText());
                            dispose();
                            renderNatanti();
                            renderCorse();

                        }
                        else JOptionPane.showMessageDialog(frame, "Inserisci un numero di telefono valido!", "", JOptionPane.ERROR_MESSAGE);
                    }
                }
                frame.getContentPane().add(panel, BorderLayout.CENTER);
                frame.setSize(590, 380);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } else {
            renderNatanti();
            renderCorse();
        }
        getCompanyData(utente.getUsername());
    }

    private void handleAccountSection(){
        editPhoneNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = JOptionPane.showInputDialog(null, "Numero di Telefono: ", compagnia.getTelefono());
                if(phoneNumber == null) return;
                while(!Validator.phoneNumberFormat(phoneNumber)) {
                    JOptionPane.showMessageDialog(null, "Formato numero di telefono errato", "", JOptionPane.ERROR_MESSAGE);
                    phoneNumber = JOptionPane.showInputDialog(null, "Numero di Telefono: ", compagnia.getTelefono());
                    if(phoneNumber == null) break;
                }
                App.getDatabase().updateCompanyData(utente.getUsername(), phoneNumber, Compagnia.Fields.PHONE_NUMBER);
                getCompanyData(utente.getUsername());
            }
        });

        editWebsiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String website = JOptionPane.showInputDialog(null, "Sito Web: ", compagnia.getSito());
                App.getDatabase().updateCompanyData(utente.getUsername(), website, Compagnia.Fields.WEBSITE);
                getCompanyData(utente.getUsername());
            }
        });

        editEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = JOptionPane.showInputDialog(null, "Email: ", compagnia.getEmail());
                if(email == null) return;
                while(!Validator.emailFormat(email)) {
                    JOptionPane.showMessageDialog(null, "Formato email errato", "", JOptionPane.ERROR_MESSAGE);
                    email = JOptionPane.showInputDialog(null, "Email: ", compagnia.getEmail());
                    if(email == null) break;
                }
                App.getDatabase().updateCompanyData(utente.getUsername(), email, Compagnia.Fields.EMAIL);
                getCompanyData(utente.getUsername());
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        dispose();
                        utente = null;
                        compagnia = null;
                        App.runApp();
                    }
                });
            }
        });
    }


    /*
     *
     *   SEZIONE NATANTI
     *
     */
    public void renderNatanti() {
        SwingUtilities.invokeLater(() -> {
            gestisciNatanti.removeAll();
            natantiTabbed.removeAll();
            gestisciNatanti.setLayout(new BoxLayout(gestisciNatanti, BoxLayout.Y_AXIS));
            for (Natante natante : compagnia.getNatanti()) {
                String posti_auto = natante.getCapienzaAuto() == 0 ? "<span style='color:red;font-weight: normal;'>" + natante.getCapienzaAuto() :
                        "<span style='color:green;font-weight:normal;'>" + natante.getCapienzaAuto();
                JLabel label = new JLabel(String.format(
                        "<html><div style='border:2px solid black; padding:3px;'>" +
                            "Nome natante: <span style='font-weight: normal;'>%s</span><br>" +
                            "Tipo natante: <span style='font-weight: normal;'>%s</span><br>" +
                            "Posti Natante: <span style='font-weight: normal;'>%d</span><br>" +
                            "Posti Auto: %s" +
                        "</div></html>",
                        natante.getNome(), natante.getTipo(), natante.getCapienza(),
                        posti_auto
                ));

                JButton button = new JButton("Modifica Natante");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manageNatante(natante);
                    }
                });

                JPanel riga = new JPanel(new BorderLayout());
                riga.add(label, BorderLayout.WEST);
                riga.add(button, BorderLayout.EAST);
                gestisciNatanti.add(riga);

                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(2, 2));
                gestisciNatanti.add(spacer);
            }
            JScrollPane scrollPane = new JScrollPane(gestisciNatanti);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            natantiTabbed.addTab("Gestisci", scrollPane);
            natantiTabbed.addTab("Aggiungi", aggiungiNatanti);

            setSize(590, 380);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void manageNatante(Natante natante){

        SwingUtilities.invokeLater(() -> {
            modify_natanti.put(compagnia.getID(), natante.getID());
            natantiTabbed.setSelectedIndex(1); // Switcho sull'opzione "Aggiungi" al lato

            salvaNatante.setText("Modifica Natante");

            nomeNatante.setText(natante.getNome());
            postiNatante.setValue(natante.getCapienza());
            postiAuto.setValue(natante.getCapienzaAuto());
            tipoNatante.setSelectedIndex(natante.getTipo().getValue() - 1);
            salvaNatante.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!nomeNatante.getText().isEmpty() && modify_natanti.containsValue(natante.getID())){
                        App.getDatabase().updateNatante(nomeNatante.getText(), Validator.getPositiveIntegerNotZero(postiNatante),
                                Validator.getPositiveIntegerNotZero(postiAuto), tipoNatante.getSelectedIndex() + 1,natante.getID());
                        nomeNatante.setText("");
                        postiNatante.setValue(0);
                        postiAuto.setValue(0);
                        tipoNatante.setSelectedItem(0);
                        salvaNatante.setText("Salva Natante");
                        modify_natanti.remove(compagnia.getID(), natante.getID());
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(null, "<html><p style='color:green;'>Natante modificato con successo!</p></html>", "", JOptionPane.INFORMATION_MESSAGE);
                            renderNatanti();
                        });
                    }
                }
            });
        });
    }

    public void handleAddNatante(){

        if(tipoNatante.getSelectedIndex() == 0){
            postiAuto.setEnabled(true);
        }
        tipoNatante.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if(e.getItem() != "Traghetto"){
                        postiAuto.setEnabled(false);
                        postiAuto.setValue(0);
                    }
                    else{
                        postiAuto.setVisible(true);
                        postiAuto.setEnabled(true);
                    }
                }
            }
        });
        salvaNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nomeNatante.getText().isEmpty() && !modify_natanti.containsKey(compagnia.getID())){
                    App.getDatabase().saveNatante(nomeNatante.getText(),
                            tipoNatante.getSelectedIndex() + 1,
                            Validator.getPositiveIntegerNotZero(postiNatante),
                            Validator.getPositiveIntegerNotZero(postiAuto), compagnia.getID());
                    nomeNatante.setText("");
                    postiNatante.setValue(0);
                    postiAuto.setValue(0);
                    tipoNatante.setSelectedItem(0);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "<html><p style='color:green;'>Natante salvato con successo!</p></html>", "", JOptionPane.INFORMATION_MESSAGE);
                        renderNatanti();
                    });
                }
            }
        });
    }


    /*
     *
     *  CORSE
     *
     */

    public void renderCorse() {
        SwingUtilities.invokeLater(() -> {
            gestisciCorse.removeAll();
            corseTabbed.removeAll();
            gestisciCorse.setLayout(new BoxLayout(gestisciCorse, BoxLayout.Y_AXIS));

            for (Corsa corsa : compagnia.getCorse()) {
                JLabel label;
                String local_cadenza = corsa.getCadenza();
                /*String cadenza = String.join(", ", corsa.getCadenza());
                if (cadenza.length() > 24) {
                    int index = -1;
                    for (int i = 24; i < cadenza.length(); i++) {
                        if (cadenza.charAt(i) == ',') {
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        cadenza = cadenza.substring(0, index + 1) + "<br>   " + cadenza.substring(index + 1);
                    }
                }*/
                if(corsa.getScalo() != 0){
                    label = new JLabel(String.format(
                            "<html><div style='border:2px solid black; padding:3px;'>" +
                                    "Porto partenza: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Porto arrivo: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Porto scalo: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Orario partenza: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Orario arrivo: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Orario scalo: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Posti totali: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Cadenza: <span style='font-weight: normal;'>%s</span><br>" +
                                    "</div></html>",
                            corsa.getPortoPartenza(), corsa.getPortoArrivo(), corsa.getScalo(),
                            corsa.getOrarioPartenza(), corsa.getOrarioArrivo(), corsa.getOrarioScalo(),
                            corsa.getNatante().getCapienza(), local_cadenza
                    ));
                }
                else{
                    label = new JLabel(String.format(
                            "<html><div style='border:2px solid black; padding:3px;'>" +
                                    "Porto partenza: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Porto arrivo: <span style='font-weight: normal;'>%d</span><br>" +
                                    "Orario partenza: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Orario arrivo: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Posti totali: <span style='font-weight: normal;'>%s</span><br>" +
                                    "Cadenza: <span style='font-weight: normal;'>%s</span>" +
                                    "</div></html>",
                            corsa.getPortoPartenza(), corsa.getPortoArrivo(),
                            corsa.getOrarioPartenza(), corsa.getOrarioArrivo(),
                            corsa.getNatante().getCapienza(), local_cadenza
                    ));
                }
                JButton manage = new JButton("Modifica Corsa");
                JButton delete = new JButton("Elimina Corsa");
                manage.addActionListener(e -> {
                    manageCorsa(corsa);
                    //dispose();
                });
                delete.addActionListener(e ->{
                    deleteCorsa(corsa);
                });

                JPanel riga = new JPanel(new BorderLayout());
                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

                buttonsPanel.add(manage);
                buttonsPanel.add(delete);
                riga.add(label, BorderLayout.WEST);
                riga.add(buttonsPanel, BorderLayout.EAST);
                gestisciCorse.add(riga);

                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(2, 2));
                gestisciCorse.add(spacer);
            }


            JScrollPane scrollPane = new JScrollPane(gestisciCorse);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            corseTabbed.addTab("Gestisci", scrollPane);
            corseTabbed.addTab("Aggiungi", aggiungiCorse);

            setSize(590, 380);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void manageCorsa(Corsa corsa){
        SwingUtilities.invokeLater(() -> {

            clearCorsaListeners();

            modify_corse.put(compagnia.getID(), corsa.getID());
            corseTabbed.setSelectedIndex(1); // Switcho sull'opzione "Aggiungi" al lato
            creaCorsa.setText("Modifica Corsa");

            natanteCompagnia.setSelectedItem(corsa.getNatante().getNome());

            portoPartenza.setSelectedItem(corsa.getPortoPartenza());
            orarioPartenza.setText(corsa.getOrarioPartenza());

            portoArrivo.setSelectedItem(corsa.getPortoArrivo());
            orarioArrivo.setText(corsa.getOrarioArrivo());


            if(corsa.getScalo() != 0) {
                portoScalo.setSelectedItem(corsa.getScalo());
                orarioScalo.setText(corsa.getOrarioScalo());

                scaloCheckBox.setSelected(true);
                portoScalo.setEnabled(true);
                orarioScalo.setEnabled(true);
            }

            periodo.setText(convertPeriodo(corsa.getDataInizio(), corsa.getDataFine()));
            cadenza.setText(corsa.getCadenza());


            prezzo.setText(String.valueOf(corsa.getPrezzo()));
            prezzoRidotto.setText(String.valueOf(corsa.getPrezzoRidotto()));

            sovrapprezzoPrenotazione.setText(String.valueOf(corsa.getSovrapprezzoPrenotazione()));
            sovrapprezzoBagagli.setText(String.valueOf(corsa.getSovrapprezzoBagagli()));

            creaCorsa.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(modify_corse.containsValue(corsa.getID())){
                        int scalo = scaloCheckBox.isSelected() ? portoScalo.getSelectedIndex() + 1 : -1;

                        String orario_scalo = scaloCheckBox.isSelected() ? Validator.hourFormat(orarioScalo) : null;
                        String[] periodi = Validator.periodoFormat(periodo).split("/");

                        LocalDate inizio = LocalDate.parse(periodi[0]);
                        LocalDate fine = LocalDate.parse(periodi[1]);

                        List< LocalDate > dates =
                                inizio
                                        .datesUntil( fine)
                                        .filter(
                                                localDate -> EnumSet.of( DayOfWeek.TUESDAY , DayOfWeek.THURSDAY ).contains( localDate.getDayOfWeek() )
                                        )
                                        .toList()
                                ;

                        for(LocalDate date : dates){
                            System.out.println(date);
                        }

                        Corsa newCorsa = new Corsa(portoPartenza.getSelectedIndex() + 1, portoArrivo.getSelectedIndex() + 1,
                                scalo, compagnia.getID(), Validator.hourFormat(orarioArrivo), Validator.hourFormat(orarioPartenza), orario_scalo, periodi[0],
                                periodi[1], Validator.cadenzaFormat(cadenza), Validator.getDouble(prezzo), Validator.getDouble(prezzoRidotto),
                                Validator.getDouble(sovrapprezzoBagagli), Validator.getDouble(sovrapprezzoPrenotazione),
                                compagnia.getNatanti().get(natanteCompagnia.getSelectedIndex())
                        );
                        App.getDatabase().updateCorsa(newCorsa, corsa.getID());
                        creaCorsa.setText("Crea Corsa");
                        modify_corse.remove(compagnia.getID(), corsa.getID());
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(null, "<html><p style='color:green;'>Corsa modificata con successo!</p></html>", "", JOptionPane.INFORMATION_MESSAGE);
                            renderCorse();
                        });
                    }
                }
            });
        });
    }
    private void deleteCorsa(Corsa corsa){
        App.getDatabase().deleteCorsa(corsa);
        renderCorse();
    }
    public void handleAddCorsa(){
        getNatanti();

        for(Porto porto : App.getDatabase().fetchPorti()) {
            portoArrivo.addItem(porto.getID());
            portoPartenza.addItem(porto.getID());
            portoScalo.addItem(porto.getID());
        }


        // Fetch dei natanti quando fai NUOVA CORSA
        //corseTabbed.addChangeListener(new ChangeListener() {
        //    @Override
        //    public void stateChanged(ChangeEvent e) {
        //        int index = tabbedPane.getSelectedIndex();
        //        if (index == 1)
        //            getNatanti();
        //    }
        //});
        //natanteCompagnia.addPopupMenuListener(new PopupMenuListener() {
        //    @Override
        //    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        //        getNatanti();
        //    }
        //    @Override
        //    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
        //    @Override
        //    public void popupMenuCanceled(PopupMenuEvent e) {}
        //});
        scaloCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portoScalo.setEnabled(scaloCheckBox.isSelected());
                orarioScalo.setEnabled(scaloCheckBox.isSelected());
            }
        });
        creaCorsa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!modify_corse.containsKey(compagnia.getID())) {
                    int scalo = scaloCheckBox.isSelected() ? portoScalo.getSelectedIndex() + 1 : -1;

                    String orario_scalo = scaloCheckBox.isSelected() ? Validator.hourFormat(orarioScalo) : null;
                    String[] periodi = Validator.periodoFormat(periodo).split("/");
                    LocalDate inizio = LocalDate.parse(periodi[0], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalDate fine = LocalDate.parse(periodi[1],  DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    List<DayOfWeek> dayOfWeeks = Arrays.stream(Validator.cadenzaFormat(cadenza).split(","))
                            .map(String::trim)
                            .map(WeekDays.Days::valueOf)
                            .map(day -> day.dayOfWeek)
                            .collect(Collectors.toList());

                    List<LocalDate> dates = inizio.datesUntil(fine)
                            .filter(date -> dayOfWeeks.contains(date.getDayOfWeek()))
                            .collect(Collectors.toList());
                    String ora_partenza = orarioPartenza.getText();
                    String ora_arrivo= orarioArrivo.getText();
                    String ora_scalo= orarioScalo.getText();
                    for(LocalDate date : dates){
                        String actual = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        orarioArrivo.setText(actual + " "+ ora_arrivo);
                        orarioPartenza.setText(actual + " "+ ora_partenza);
                        if(orario_scalo != null){
                            orario_scalo = actual + " " + ora_scalo;
                            orarioScalo.setText(orario_scalo);
                        }
                        Corsa corsa = new Corsa(portoPartenza.getSelectedIndex() + 1, portoArrivo.getSelectedIndex() + 1,
                                scalo, compagnia.getID(), Validator.dateFormat(orarioArrivo), Validator.dateFormat(orarioPartenza), orario_scalo, periodi[0],
                                periodi[1], Validator.cadenzaFormat(cadenza), Validator.getDouble(prezzo), Validator.getDouble(prezzoRidotto),
                                Validator.getDouble(sovrapprezzoBagagli), Validator.getDouble(sovrapprezzoPrenotazione),
                                compagnia.getNatanti().get(natanteCompagnia.getSelectedIndex())
                        );
                        App.getDatabase().creaCorsa(corsa);
                    }
                    orarioArrivo.setText(ora_arrivo);
                    orarioPartenza.setText(ora_partenza);
                    orarioScalo.setText(ora_scalo);

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "<html><p style='color:green;'>Corsa creata con successo!</p></html>", "", JOptionPane.INFORMATION_MESSAGE);
                        renderCorse();
                    });
                }
            }
        });
    }

    private void getNatanti() {
        natanteCompagnia.removeAllItems();
        List<Natante> natanti = compagnia.getNatanti();
        for(Natante natante : natanti) {
            natanteCompagnia.addItem(natante.getNome());
        }
    }



    /*
     *
     *  UTILS
     *
     */

    /*private void printWindowSize() {
        // Get the size of the JFrame after resizing
        Dimension windowSize = getSize();

        // Retrieve width and height
        int windowWidth = (int) windowSize.getWidth();
        int windowHeight = (int) windowSize.getHeight();

        // Output the window size
        System.out.println("Window Width: " + windowWidth);
        System.out.println("Window Height: " + windowHeight);
    }*/
    public void getCompanyData(String nome) {
        compagnia = App.getDatabase().fetchCompanyData(nome);

        String phoneNumber = compagnia.getTelefono() == null || compagnia.getTelefono().isEmpty() ? "N/A" : compagnia.getTelefono();
        labelPhoneNumber.setText(phoneNumber);

        String email = compagnia.getEmail() == null || compagnia.getEmail().isEmpty() ? "N/A" : compagnia.getEmail();
        labelEmail.setText(email);

        String website = compagnia.getSito() == null || compagnia.getSito().isEmpty() ? "N/A" : compagnia.getSito();
        labelWebsite.setText(website);


    }
    public static void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setToolTipText(placeholder);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { if (textField.getText().equals(placeholder)) textField.setText(""); }
            @Override
            public void focusLost(FocusEvent e) { if (textField.getText().isEmpty()) textField.setText(placeholder); }
        });
    }

    private String convertPeriodo(String inizio, String fine){
        LocalDateTime data_inizio = LocalDateTime.parse(inizio, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        LocalDateTime data_fine = LocalDateTime.parse(fine, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        String formattedDate1 = data_inizio.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String formattedDate2 = data_fine.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String periodo = formattedDate1 + "/" + formattedDate2;
        return periodo;
    }

    private void clearCorsaListeners(){
        for (ActionListener listener : creaCorsa.getActionListeners()) {
            creaCorsa.removeActionListener(listener);
        }
        for (ActionListener listener : scaloCheckBox.getActionListeners()) {
            scaloCheckBox.removeActionListener(listener);
        }
    }
}
