package com.example.sistema_ventas_simple.schemeSQLite.tables;

public class ProductoTable {
    public static final String TABLE = "producto";

    public static final String PROD_ID = "prod_id";
    public static final String PROD_NOMBRE = "prod_nombre";
    public static final String PROD_PRECIO = "prod_precio";
    public static final String PROD_RUTA_FOTO = "prod_ruta_foto";

    public static final String CREATE_TABLE_PRODUCTO
            = "CREATE TABLE " + TABLE + " ( "
            + PROD_ID + " INT PRIMARY KEY, "
            + PROD_NOMBRE + " TEXT, "
            + PROD_PRECIO + " NUMERIC, "
            + PROD_RUTA_FOTO + " TEXT "
            + ");";
    public static final String DROP_TABLE_PRODUCTO = "DROP TABLE IF EXISTS " + TABLE + ";";
}

