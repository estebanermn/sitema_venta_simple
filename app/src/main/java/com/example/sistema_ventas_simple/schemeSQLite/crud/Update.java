package com.example.sistema_ventas_simple.schemeSQLite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistema_ventas_simple.data.model.Cliente;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.example.sistema_ventas_simple.data.model.ProductoVenta;
import com.example.sistema_ventas_simple.data.model.VentaCabecera;
import com.example.sistema_ventas_simple.data.model.VentaDetalle;
import com.example.sistema_ventas_simple.data.preferences.SessionPreferences;
import com.example.sistema_ventas_simple.schemeSQLite.ConectionSQLiteHelper;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ClienteTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ProductoTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaCabeceraTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaDetalleTable;

import java.util.List;

public class Update {

    public static void actualizar(Context context, Object param, String table) {
        ConectionSQLiteHelper con = new ConectionSQLiteHelper(context);

        SQLiteDatabase db = con.getWritableDatabase();

        ContentValues values = new ContentValues();

        switch (table) {
            case ClienteTable.TABLE:
                Cliente cliente = (Cliente) param;

                values.put(ClienteTable.CLIE_ID, cliente.getClie_id());
                values.put(ClienteTable.CLIE_NOMBRE, cliente.getClie_nombre());
                values.put(ClienteTable.CLIE_NUM_CEL, cliente.getClie_num_cel());
                values.put(ClienteTable.CLIE_EMAIL, cliente.getClie_email());
                values.put(ClienteTable.CLIE_DIRECCION, cliente.getClie_direccion());

                db.update(ClienteTable.TABLE, values,
                        ClienteTable.CLIE_ID + " = ?",
                        new String[]{String.valueOf(cliente.getClie_id())});

                break;

            case ProductoTable.TABLE:
                Producto producto = (Producto) param;

                values.put(ProductoTable.PROD_ID, producto.getProd_id());
                values.put(ProductoTable.PROD_NOMBRE, producto.getProd_nombre());
                values.put(ProductoTable.PROD_PRECIO, producto.getProd_precio());
                values.put(ProductoTable.PROD_RUTA_FOTO, producto.getProd_ruta_foto());

                db.update(ProductoTable.TABLE, values,
                        ProductoTable.PROD_ID + " = ?",
                        new String[]{String.valueOf(producto.getProd_id())});
                break;
        }

    }

}
