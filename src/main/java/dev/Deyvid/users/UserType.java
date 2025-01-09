package dev.Deyvid.users;

public enum UserType {

    USER ("Utente"),
    COMPAGNIA ("Compagnia"),
    ADMIN ("Amministratore");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static UserType getByValue(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.getName().equalsIgnoreCase(value)) {
                return userType;
            }
        }
        return null;
    }

}
