package dev.Deyvid.misc;

public enum FetchFilter {


    PORTO_PARTENZA("porto_partenza = ? or scalo = ?"),
    PORTO_ARRIVO("porto_arrivo = ? or scalo = ?"),
    SCALO("scalo IS NOT NULL"),
    AUTOMEZZI("SELECT * FROM natanti WHERE id = ?"),
    PREZZO("prezzo <= ?"),
    LIMIT(" LIMIT ?"),
    ORDER_PREZZO(" ORDER BY prezzo"),
    ORDER_DATA(" ORDER BY orario_partenza"),
    DATA("orario_partenza >= '?'")


    ;

    private String query;
    private String updatedQuery;

    FetchFilter(String query) {
        this.query = query;
    }

    public String getQuery() {
        return updatedQuery;
    }

    public FetchFilter order() {
        this.updatedQuery = query;
        return this;
    }


    public FetchFilter setValue(int filterValue) {
        this.updatedQuery = query.replace("?", String.valueOf(filterValue));
        return this;
    }

    public FetchFilter setValue(double filterValue) {
        this.updatedQuery = query.replace("?", String.valueOf(filterValue));
        return this;
    }

    public FetchFilter setValue(String filterValue) {
        this.updatedQuery = query.replace("?", String.valueOf(filterValue));
        return this;
    }

}
