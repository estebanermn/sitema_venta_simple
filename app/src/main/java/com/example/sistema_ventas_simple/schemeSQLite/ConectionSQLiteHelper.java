package com.example.sistema_ventas_simple.schemeSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sistema_ventas_simple.schemeSQLite.tables.ClienteTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ProductoTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaCabeceraTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaDetalleTable;

public class ConectionSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "venta_simple";

    public ConectionSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ProductoTable.CREATE_TABLE_PRODUCTO);
        db.execSQL(ClienteTable.CREATE_TABLE_CLIENTE);
        db.execSQL(VentaCabeceraTable.CREATE_TABLE_VENTA_CABECERA);
        db.execSQL(VentaDetalleTable.CREATE_TABLE_VENTA_DETALLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(ProductoTable.DROP_TABLE_PRODUCTO);
        db.execSQL(ClienteTable.DROP_TABLE_CLIENTE);
        db.execSQL(VentaCabeceraTable.DROP_TABLE_VENTA_CABECERA);
        db.execSQL(VentaDetalleTable.DROP_TABLE_VENTA_DETALLE);

    }
}
