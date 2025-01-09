package dev.Deyvid.misc;

public enum TipoNatante {

    Traghetto(1),
    Aliscafo(2),
    Motonave(3);

    private final int value;

    TipoNatante(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TipoNatante getByInt(int intValue) {
        for (TipoNatante enumValue : values()) {
            if (enumValue.getValue() == intValue) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + intValue);
    }
}
