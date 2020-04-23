package com.example.sistema_ventas_simple.schemeSQLite.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sistema_ventas_simple.data.model.VentaDetalle;
import com.example.sistema_ventas_simple.schemeSQLite.ConectionSQLiteHelper;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ClienteTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ProductoTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaCabeceraTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaDetalleTable;

import java.util.List;

public class Delete {

    public static void eliminar(Context context, int codigo, String table) {
        ConectionSQLiteHelper con = new ConectionSQLiteHelper(context);

        SQLiteDatabase db = con.getWritableDatabase();

        switch (table) {
            case ClienteTable.TABLE:
                db.delete(ClienteTable.TABLE, ClienteTable.CLIE_ID + " = ?",
                        new String[]{String.valueOf(codigo)});
                break;
            case ProductoTable.TABLE:
                db.delete(ProductoTable.TABLE, ProductoTable.PROD_ID + " = ?",
                        new String[]{String.valueOf(codigo)});
                break;
            case VentaCabeceraTable.TABLE:
                db.delete(VentaCabeceraTable.TABLE, VentaCabeceraTable.VC_ID + " = ?",
                        new String[]{String.valueOf(codigo)});
                break;
            case VentaDetalleTable.TABLE:
                db.delete(VentaDetalleTable.TABLE, VentaDetalleTable.VD_ID + " = ?",
                        new String[]{String.valueOf(codigo)});
                break;
        }
    }

    public static void eliminarVenta(Context context, List<VentaDetalle> lista, int vc_id){
        //elimnar la venta cabecera
        eliminar(context, vc_id,VentaCabeceraTable.TABLE);

        for(VentaDetalle item : lista){
            eliminar(context, item.getVd_id(), VentaDetalleTable.TABLE);
        }
    }

}
