package com.example.sistema_ventas_simple.schemeSQLite.tables;

public class VentaDetalleTable {

    public static final String TABLE = "venta_detalle";

    public static final String VD_ID = "vd_id";
    public static final String VD_CANTIDAD = "vd_cantidad";
    public static final String VD_PRECIO = "vd_precio";
    public static final String VC_ID = "vc_id";
    public static final String PROD_NOMBRE = "prod_nombre";
    public static final String PROD_RUTA_FOTO = "prod_ruta_foto";

    public static final String CREATE_TABLE_VENTA_DETALLE
            = " CREATE TABLE " + TABLE + "("
            + VD_ID + " INT PRIMARY KEY,"
            + VD_CANTIDAD + " INT,"
            + VD_PRECIO + " NUMERIC,"
            + VC_ID + " INT,"
            + PROD_NOMBRE + " TEXT,"
            + PROD_RUTA_FOTO + " TEXT );";

    public static final String DROP_TABLE_VENTA_DETALLE = "DROP TABLE IF EXISTS " + TABLE + ";";

}
