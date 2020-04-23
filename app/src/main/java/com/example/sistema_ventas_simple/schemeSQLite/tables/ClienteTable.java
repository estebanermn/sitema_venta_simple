package com.example.sistema_ventas_simple.schemeSQLite.tables;

public class ClienteTable {

    public static final String TABLE = "cliente";

    public static final String CLIE_ID = "clie_id";
    public static final String CLIE_NOMBRE = "clie_nombre";
    public static final String CLIE_NUM_CEL = "clie_num_cel";
    public static final String CLIE_EMAIL = "clie_email";
    public static final String CLIE_DIRECCION = "clie_direccion";

    //creación de tabla
    public static final String CREATE_TABLE_CLIENTE =
            "CREATE TABLE " + TABLE + " ("
                    + CLIE_ID + " INT PRIMARY KEY, "
                    + CLIE_NOMBRE + " TEXT, "
                    + CLIE_NUM_CEL + " TEXT, "
                    + CLIE_EMAIL + " TEXT, "
                    + CLIE_DIRECCION + " TEXT"
                    + ");";
    public static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS " + TABLE + ";";

}
