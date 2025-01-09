package dev.Deyvid.application;

import dev.Deyvid.database.Database;
import dev.Deyvid.misc.Corsa;
import dev.Deyvid.misc.Validator;
import dev.Deyvid.users.LoginErrors;
import dev.Deyvid.users.User;
import dev.Deyvid.users.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class App {
    private JPanel panel1;
    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel logLabel;
    private static Database database;
    private static JFrame frame;

    public static Database getDatabase() {
        return database;
    }

    public App() {
        if(database == null){
            database = new Database();
            database.registerDatabase();
        }
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User utente = App.getDatabase().login(Validator.stringLength(username, 4, 16), Validator.stringLength(password, 4, 16));
                if(utente.getError() == LoginErrors.WRONG_PASSWORD){
                    logLabel.setText("<html><span style='color:red;'>Password errata</span></html>");
                    return;
                }
                else if(utente.getError() == LoginErrors.NON_EXISTENT) {
                    logLabel.setText("<html><span style='color:red;'>Utente non registrato</span></html>");
                    return;
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        frame.dispose();
                        if (utente.getUserType() == UserType.USER) {
                            Dashboard dashboard = new Dashboard(utente);
                        } else if (utente.getUserType() == UserType.COMPAGNIA) {
                            DashCompagnia dashCompagnia = new DashCompagnia(utente);
                        }
                    }

                });
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User utente = new User(Validator.stringLength(username, 4, 16), Validator.stringLength(password, 8, 48), UserType.USER).register();
                if (utente.getError() == LoginErrors.ALREADY_REGISTERED) {
                    logLabel.setText("<html><span style='color:red;'>Utente già registrato</span></html>");
                    return;
                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        frame.dispose();
                        if (utente.getUserType() == UserType.USER) {
                            Dashboard dashboard = new Dashboard(utente);
                        } else if (utente.getUserType() == UserType.COMPAGNIA) {
                            DashCompagnia dashCompagnia = new DashCompagnia(utente);
                        }
                    }

                });

            }
        });
    }

    public static void runApp(){
        frame = new JFrame("App");
        frame.setContentPane(new App().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        runApp();
    }

}
