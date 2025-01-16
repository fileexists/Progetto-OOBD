package dev.Deyvid.database;

import dev.Deyvid.misc.*;
import dev.Deyvid.users.LoginErrors;
import dev.Deyvid.users.User;
import dev.Deyvid.users.UserType;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private Connection connection;
    public void registerDatabase() {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://192.168.1.101:5432/progetto",
                            "progetto", "progetto1!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Database connected!");
    }

    public User register(User user) {
        String sql = "INSERT INTO Utenti (username, password, usertype) " +
                "SELECT ?, ?, ? " +
                "WHERE NOT EXISTS (SELECT 1 FROM Utenti WHERE username = ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getUserType().getName());
            preparedStatement.setString(4, user.getUsername());
            int register = preparedStatement.executeUpdate();
            if(register == 0) return new User(LoginErrors.ALREADY_REGISTERED);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User login(String username, String password) {
        String sql = "SELECT password, usertype FROM Utenti WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (password.equals(storedPassword)) {
                        String userTypeValue = resultSet.getString("usertype");
                        UserType userType = UserType.getByValue(userTypeValue);
                        if (userType != null) return new User(username, password, userType);
                    }
                    return new User(LoginErrors.WRONG_PASSWORD);
                }
                return new User(LoginErrors.NON_EXISTENT);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Porto> fetchPorti(){
        String queryPorto = "SELECT * FROM Porto";
        List<Porto> porti = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryPorto)){
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                porti.add(new Porto(porti.size() + 1, set.getString(1), set.getString(2), set.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return porti;
    }

    public List<Corsa> fetchCorse(Compagnia compagnia){
        String sql= "SELECT * FROM Corse WHERE idCompagnia = ?";
        List<Corsa> corse = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, compagnia.getID());
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                int portoPartenza = set.getInt(4);
                int portoArrivo = set.getInt(5);
                int scalo = set.getInt(6);

                boolean inRitardo = set.getBoolean(17);

                String orarioPartenza = timestampToString(set.getTimestamp(7));
                String orarioArrivo = timestampToString(set.getTimestamp(8));
                String orarioScalo = timestampToString(set.getTimestamp(9));


                String cadenza = set.getString(12);
                String dataInizio = dateToString(set.getDate(10));
                String dataFine = dateToString(set.getDate(11));

                cadenza = cadenza.replaceAll("[{}]", "");
                double prezzo = set.getDouble(13);
                double prezzoRidotto = set.getDouble(14);
                double sovrapprezzoPrenotazione = set.getDouble(15);
                double sovrapprezzoBagaglio = set.getDouble(16);
                int nid = set.getInt(2);
                Natante natante = getNatanteFromID(nid, compagnia);

                Corsa corsa = new Corsa(portoPartenza, portoArrivo,scalo, compagnia.getID(), orarioArrivo, orarioPartenza, orarioScalo, dataInizio, dataFine, cadenza, prezzo, prezzoRidotto, sovrapprezzoBagaglio, sovrapprezzoPrenotazione, natante, set.getInt(1), inRitardo);
                corse.add(corsa);

            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return corse;
    }
    public void deleteCorsa(Corsa corsa){
        String tratte_table = "DELETE FROM TRATTE WHERE cid = ?";
        String corse_table = "DELETE FROM CORSE WHERE cid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(tratte_table); PreparedStatement statement = connection.prepareStatement(corse_table)) {
            preparedStatement.setInt(1, corsa.getID());
            preparedStatement.executeUpdate();
            statement.setInt(1, corsa.getID());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void delayCorsa(Corsa corsa){
        String corse_table = "UPDATE CORSE SET inRitardo = true WHERE cid = ?";
        try (PreparedStatement statement = connection.prepareStatement(corse_table)) {
            statement.setInt(1, corsa.getID());
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public FetchResult fetchCorse(List<FetchFilter> filters){
        String sql = "SELECT * FROM Corse";

        if(filters != null){
            if(filters.contains(FetchFilter.PREZZO) ||filters.contains(FetchFilter.DATA) || filters.contains(FetchFilter.AUTOMEZZI) || filters.contains(FetchFilter.SCALO) || filters.contains(FetchFilter.PORTO_ARRIVO) || filters.contains(FetchFilter.PORTO_PARTENZA)){
                sql += " WHERE ";
            }
            int c = 1;
            for(FetchFilter filter : filters){
                sql += filter.getQuery();
                if(c < filters.size() - 2 )
                    sql += " AND ";
                c++;
            }
        }
        List<Corsa> corse = new ArrayList<>();
        List<Tratte> tratte = new ArrayList<>();
        FetchResult result = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                int portoPartenza = set.getInt(4);
                int portoArrivo = set.getInt(5);
                int scalo = set.getInt(6);

                boolean inRitardo = set.getBoolean(17);

                String orarioPartenza = timestampToString(set.getTimestamp(7));
                String orarioArrivo = timestampToString(set.getTimestamp(8));
                String orarioScalo = timestampToString(set.getTimestamp(9));


                String cadenza = set.getString(12);
                String dataInizio = dateToString(set.getDate(10));
                String dataFine = dateToString(set.getDate(11));
                cadenza = cadenza.replaceAll("[{}]", "");
                double prezzo = set.getDouble(13);
                double prezzoRidotto = set.getDouble(14);
                double sovrapprezzoPrenotazione = set.getDouble(15);
                double sovrapprezzoBagaglio = set.getDouble(16);
                int nid = set.getInt(2);
                int compagniaID = set.getInt(3);

                Compagnia compagnia = getCompagniaFromID(compagniaID);

                Natante natante = getNatanteFromID(nid, compagnia);

                Corsa corsa = new Corsa(portoPartenza, portoArrivo,scalo, compagnia.getID(), orarioArrivo, orarioPartenza, orarioScalo, dataInizio, dataFine, cadenza, prezzo, prezzoRidotto, sovrapprezzoBagaglio, sovrapprezzoPrenotazione, natante, set.getInt(1), inRitardo);
                corse.add(corsa);
                Tratte partialTratta = fetchTrattaData(corsa, portoPartenza, portoArrivo);
                Tratte tratta = new Tratte(corsa.getID(), portoPartenza, orarioPartenza, portoArrivo, orarioArrivo, partialTratta.getPosti(), partialTratta.getPostiAuto());
                Tratte tratta_2 = null, tratta_3 = null;
                if (scalo != 0) {
                    Tratte partialTratta_2 = fetchTrattaData(corsa, portoPartenza, scalo);
                    tratta_2 = new Tratte(corsa.getID(), portoPartenza, orarioPartenza, scalo, orarioScalo, partialTratta_2.getPosti(), partialTratta_2.getPostiAuto());

                    Tratte partialTratta_3 = fetchTrattaData(corsa, scalo, portoArrivo);
                    tratta_3 = new Tratte(corsa.getID(), scalo, orarioScalo, portoArrivo, orarioArrivo, partialTratta_3.getPosti(), partialTratta_2.getPostiAuto());

                    tratte.add(tratta_2);
                    tratte.add(tratta_3);
                }

                tratte.add(tratta);
                result = new FetchResult(corse, tratte);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Date timestampToDate(String timestampString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(timestampString, formatter);
        return Date.valueOf(localDate);
    }

    private static String timestampToString(Timestamp timestamp) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            formatter.setLenient(true);
            return formatter.format(timestamp);
        } catch (Exception e) {
            return "N/A";
        }
    }


    private static String dateToString(Date orario) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            formatter.setLenient(true);
            return formatter.format(orario);
        } catch (Exception e) {
            return "N/A";
        }
    }

    public Compagnia getCompagniaFromID(int id){
        String sql = "SELECT * from Compagnia where id=?";
        Compagnia compagnia = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,id);
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                String nome = set.getString(2);
                String telefono = set.getString(3);
                String email = set.getString(4);
                String sito = set.getString(5);
                compagnia = new Compagnia(id, nome, telefono, email, sito);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return compagnia;
    }



    public Natante getNatanteFromID(int id, Compagnia compagnia){
        String sql = "Select * from Natanti where id=?";
        Natante natante = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()){
                String nome =  set.getString(2);
                int typeID = set.getInt(3);
                int capienza = set.getInt(4);
                int capienza_auto = set.getInt(5);

                natante = new Natante(nome, typeID, capienza, capienza_auto, compagnia, id);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return natante;
    }

    public void saveNatante(String nome, int tipo, int posti, int postiAuto, int idCompagnia){
        String sql = "INSERT INTO Natanti(nome, typeid, posti, postiauto, idcompagnia) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, tipo);
            preparedStatement.setInt(3, posti);
            preparedStatement.setInt(4, postiAuto);
            preparedStatement.setInt(5, idCompagnia);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void creaCorsa(Corsa corsa){
        String sql = "INSERT INTO corse(nid, idcompagnia, porto_partenza, porto_arrivo, scalo, orario_partenza, orario_arrivo, " +
                "orario_scalo, data_inizio, data_fine, cadenza, prezzo, prezzo_rid, sovraprezzo_prenot, sovraprezzo_bag) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Integer scalo = corsa.getScalo() == -1 ? null : corsa.getScalo();
            String[] cadenzaArray = corsa.getCadenza().split(",\\s*");

            preparedStatement.setInt(1, corsa.getNatante().getID());
            preparedStatement.setInt(2, corsa.getIdCompagnia());
            preparedStatement.setInt(3, corsa.getPortoPartenza());
            preparedStatement.setInt(4, corsa.getPortoArrivo());
            preparedStatement.setObject(5, scalo);

            Timestamp orarioPartenza = Timestamp.valueOf(LocalDateTime.parse(corsa.getOrarioPartenza(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            Timestamp orarioArrivo = Timestamp.valueOf(LocalDateTime.parse(corsa.getOrarioArrivo(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            Timestamp orarioScalo = (scalo == null) ? null : Timestamp.valueOf(LocalDateTime.parse(corsa.getOrarioScalo(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

            preparedStatement.setTimestamp(6, orarioPartenza);
            preparedStatement.setTimestamp(7, orarioArrivo);
            preparedStatement.setTimestamp(8, orarioScalo);


            preparedStatement.setDate(9, Date.valueOf(LocalDate.parse(corsa.getDataInizio(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            preparedStatement.setDate(10, Date.valueOf(LocalDate.parse(corsa.getDataFine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            preparedStatement.setDate(9, timestampToDate(corsa.getDataInizio()));
            preparedStatement.setDate(10, timestampToDate(corsa.getDataFine()));
            preparedStatement.setArray(11, connection.createArrayOf("varchar", cadenzaArray));
            preparedStatement.setDouble(12, corsa.getPrezzo());
            preparedStatement.setDouble(13, corsa.getPrezzoRidotto());
            preparedStatement.setDouble(14, corsa.getSovrapprezzoPrenotazione());
            preparedStatement.setDouble(15, corsa.getSovrapprezzoBagagli());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTratta(Tratte tratta, int cid) {
        String sql = "UPDATE tratte SET posti = ?, postiauto = ? WHERE cid = ? AND porto_partenza = ? AND porto_arrivo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, tratta.getPosti());
            preparedStatement.setInt(2, tratta.getPostiAuto());
            preparedStatement.setInt(3, cid);
            preparedStatement.setInt(4, tratta.getPortoPartenza());
            preparedStatement.setInt(5, tratta.getPortoArrivo());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateCorsa(Corsa corsa, int cid) {
        String sql = "UPDATE corse SET idcompagnia = ?, porto_partenza = ?, porto_arrivo = ?, scalo = ?, " +
                "orario_partenza = ?, orario_arrivo = ?, orario_scalo = ?, data_inizio = ?, data_fine = ?, " +
                "cadenza = ?, prezzo = ?, prezzo_rid = ?, sovraprezzo_prenot = ?, sovraprezzo_bag = ?, nid = ? " +
                "WHERE cid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            Integer scalo = corsa.getScalo() == -1 ? null : corsa.getScalo();
            String[] cadenzaArray = corsa.getCadenza().split(",\\s*");

            preparedStatement.setInt(1, corsa.getIdCompagnia());
            preparedStatement.setInt(2, corsa.getPortoPartenza());
            preparedStatement.setInt(3, corsa.getPortoArrivo());
            preparedStatement.setObject(4, scalo);

            Timestamp orarioPartenza = Timestamp.valueOf(LocalDateTime.parse(corsa.getOrarioPartenza(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            Timestamp orarioArrivo = Timestamp.valueOf(LocalDateTime.parse(corsa.getOrarioArrivo(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            Timestamp orarioScalo = (scalo == null) ? null : Timestamp.valueOf(LocalDateTime.parse(corsa.getOrarioScalo(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

            preparedStatement.setTimestamp(5, orarioPartenza);
            preparedStatement.setTimestamp(6, orarioArrivo);
            preparedStatement.setTimestamp(7, orarioScalo);

            preparedStatement.setDate(8, Date.valueOf(LocalDate.parse(corsa.getDataInizio(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            preparedStatement.setDate(9, Date.valueOf(LocalDate.parse(corsa.getDataFine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            preparedStatement.setArray(10, connection.createArrayOf("varchar", cadenzaArray));
            preparedStatement.setDouble(11, corsa.getPrezzo());
            preparedStatement.setDouble(12, corsa.getPrezzoRidotto());
            preparedStatement.setDouble(13, corsa.getSovrapprezzoPrenotazione());
            preparedStatement.setDouble(14, corsa.getSovrapprezzoBagagli());
            preparedStatement.setInt(15, corsa.getNatante().getID());


            preparedStatement.setInt(16, cid);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Natante> fetchNatanti(Compagnia compagnia){
        String sql = "SELECT * FROM Natanti WHERE idcompagnia = ?";
        List<Natante> natanti = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, compagnia.getID());
            ResultSet set = preparedStatement.executeQuery();
            while(set.next()){
                int id = set.getInt(1);
                String nome = set.getString(2);
                int typeID = set.getInt(3);
                int posti = set.getInt(4);
                int postiAuto = set.getInt(5);
                Natante natante = new Natante(nome, typeID, posti, postiAuto, compagnia, id);
                natanti.add(natante);
            }
            return natanti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateCompagnia(String utente, String telefono, String email, String sito){
        String sql = "UPDATE Compagnia SET telefono = ?, email = ?, sito = ? WHERE nome = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, telefono);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, sito);
            preparedStatement.setString(4, utente);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateNatante(String nome, int posti, int postiAuto, int typeid, int idNatante){
        String sql = "UPDATE Natanti SET nome = ?, posti = ?, postiauto = ?, typeid = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,nome );
            preparedStatement.setInt(2, posti);
            preparedStatement.setInt(3, postiAuto);
            preparedStatement.setInt(4, typeid);
            preparedStatement.setInt(5, idNatante);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Compagnia fetchCompanyData(String nome){
        String sql = "SELECT * FROM Compagnia WHERE nome = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()){
                int id = set.getInt(1);
                String telefono = set.getString(3);
                String email = set.getString(4);
                String sito = set.getString(5);
                return new Compagnia(id, nome, telefono, email, sito);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Tratte fetchTrattaData(Corsa corsa, int porto_partenza, int porto_arrivo){
        String sql = "SELECT * FROM tratte WHERE cid = ? AND porto_partenza = ? AND porto_arrivo = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, corsa.getID());
            preparedStatement.setInt(2, porto_partenza);
            preparedStatement.setInt(3, porto_arrivo);
            ResultSet set = preparedStatement.executeQuery();
            if(set.next()){
                int posti = set.getInt(7);
                int posti_auto = set.getInt(8);
                return new Tratte(corsa.getID(), posti, posti_auto);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCompanyData(String nome, String update, Compagnia.Fields campo){
        String sql = "";
        if(campo == Compagnia.Fields.EMAIL) sql = "UPDATE Compagnia SET email = ? WHERE nome = ?";
        if(campo == Compagnia.Fields.PHONE_NUMBER) sql = "UPDATE Compagnia SET telefono = ? WHERE nome = ?";
        if(campo == Compagnia.Fields.WEBSITE) sql = "UPDATE Compagnia SET sito = ? WHERE nome = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, update);
            preparedStatement.setString(2, nome);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public class FetchResult {
        private List<Corsa> corse;
        private List<Tratte> tratte;

        public FetchResult(List<Corsa> corse, List<Tratte> tratte) {
            this.corse = corse;
            this.tratte = tratte;
        }

        public List<Corsa> getCorse() {
            return corse;
        }

        public List<Tratte> getTratte() {
            return tratte;
        }
    }



}


