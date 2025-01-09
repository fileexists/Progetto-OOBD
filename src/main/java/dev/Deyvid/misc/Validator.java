package dev.Deyvid.misc;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static class InvalidData extends RuntimeException {

        private static final long serialVersionUID = 7052164163215272979L;

        public InvalidData(String msg) {
            super(msg);
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null,
                        "<html><p style='color:red;'>"+msg+"</p></html>",
                        "",
                        JOptionPane.ERROR_MESSAGE);
            });
        }

        @Override
        public void printStackTrace() {}
    }

    public static String stringLength(JTextField textField, int min, int max){
        String text = textField.getText();
        if(text.length() < min || text.length() > max)
            throw new InvalidData("Il campo " + textField.getName() + " deve avere lunghezza compresa tra " + min + " e "+ max);
        return text;
    }

    public static String dateFormat(JTextField textField) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(textField.getText());
            return textField.getText();
        } catch (ParseException e) {
            throw new InvalidData("Il campo " + textField.getName() + " è errato (dd-MM-yyyy HH:mm)");
        }
    }

    public static String filterDateFormat(JTextField textField) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(textField.getText());
            return textField.getText();
        } catch (ParseException e) {
            throw new InvalidData("Il campo " + textField.getName() + " è errato (yyyy-MM-dd HH:mm)");
        }
    }

    public static String hourFormat(JTextField textField) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(textField.getText());
            return textField.getText();
        } catch (ParseException e) {
            throw new InvalidData("Il campo " + textField.getName() + " è errato (HH:mm)");
        }
    }

    public static String periodoFormat(JTextField textField) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy/dd-MM-yyyy");
        sdf.setLenient(true);
        try {
            sdf.parse(textField.getText());
            return textField.getText();
        } catch (ParseException e) {
            throw new InvalidData("Il campo " + textField.getName() + " è errato (dd-MM-yyyy/dd-MM-yyyy)");
        }
    }

    public static double getDouble(JTextField textField) {
        try {
            return Double.parseDouble(textField.getText());
        } catch (NumberFormatException e) {
            throw new InvalidData("Il campo " + textField.getName() + " è errato (Es: 15.00)");
        }
    }

    public static int getPositiveIntegerNotZero(JTextField textField) {
        try {
            int i = Integer.parseInt(textField.getText());
            if (i <= 0) {
                throw new InvalidData("Il campo " + textField.getName() + " deve contenere un intero maggiore di 0");
            }
            return i;
        } catch (NumberFormatException e) {
            throw new InvalidData("Il campo " + textField.getName() + " deve contenere un intero maggiore di 0");
        }
    }
    public static int getPositiveIntegerNotZero(JSpinner spinner) {
        try {
            if(!spinner.isEnabled()) return 0;
            int i = Integer.parseInt(spinner.getValue().toString());
            if (i <= 0) {
                throw new InvalidData("Il campo " + spinner.getName() + " deve contenere un intero maggiore di 0");
            }
            return i;
        } catch (NumberFormatException e) {
            throw new InvalidData("Il campo " + spinner.getName() + " deve contenere un intero maggiore di 0");
        }
    }


    public static String cadenzaFormat(JTextField textField) {
        String text = textField.getText();
        String[] validDays = {"Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"};
        String[] parts = text.split(",");
        for (String part : parts) {
            String trimmedPart = part.trim();
            boolean isValid = false;
            for (String day : validDays) {
                if (trimmedPart.equals(day)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                throw new InvalidData("Il campo " + textField.getName() + " è errato (Controlla i valori inseriti)");
            }
        }
        return text;
    }

    public static boolean phoneNumberFormat(String phoneNumber) {
        String phoneRegex = "^\\d{10}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean emailFormat(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
