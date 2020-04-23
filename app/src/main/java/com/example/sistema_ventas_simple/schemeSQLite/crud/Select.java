package com.example.sistema_ventas_simple.schemeSQLite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistema_ventas_simple.data.model.Cliente;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.example.sistema_ventas_simple.data.model.VentaCabecera;
import com.example.sistema_ventas_simple.data.model.VentaDetalle;
import com.example.sistema_ventas_simple.data.util.Methods;
import com.example.sistema_ventas_simple.schemeSQLite.ConectionSQLiteHelper;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ClienteTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ProductoTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaCabeceraTable;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaDetalleTable;

import java.util.ArrayList;
import java.util.List;

public class Select {

    private static ConectionSQLiteHelper con = null;
    private static SQLiteDatabase db = null;

    private static List<Object> seleccionarRegistros(Context context, String table) {
        List<Object> listaRetorno = new ArrayList<>();
        con = new ConectionSQLiteHelper(context);
        db = con.getReadableDatabase();

        Cursor cLista = db.query(table, null, null, null, null, null, null);
        while (cLista.moveToNext()) {
            switch (table) {
                case ClienteTable.TABLE:
                    listaRetorno.add(
                            new Cliente(
                                    cLista.getInt(cLista.getColumnIndex(ClienteTable.CLIE_ID)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTable.CLIE_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTable.CLIE_NUM_CEL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTable.CLIE_EMAIL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTable.CLIE_DIRECCION))
                            )
                    );
                    break;
                case ProductoTable.TABLE:
                    listaRetorno.add(
                            new Producto(
                                    cLista.getInt(cLista.getColumnIndex(ProductoTable.PROD_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTable.PROD_NOMBRE)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTable.PROD_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTable.PROD_RUTA_FOTO)),
                                    false
                            )
                    );
                    break;
            }
        }
        return listaRetorno;
    }

    public static void seleccionarClientes(Context context, List<Cliente> lista, String buscar) {
        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ClienteTable.TABLE);
        for (Object item : tempLista) {
            Cliente _item = (Cliente) item;
            if (buscar.length() > 0) {

                if (_item.getClie_nombre().length() >= buscar.length()) {
                    String cadenaRecortada = _item.getClie_nombre().substring(0, buscar.length());

                    if (buscar.equals(cadenaRecortada)) lista.add(_item);
                }

            } else {
                lista.add(_item);
            }
        }
    }

    public static void seleccionarProductos(Context context, List<Producto> lista, String buscar) {
        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ProductoTable.TABLE);
        for (Object item : tempLista) {
            Producto _item = (Producto) item;
            if (buscar.length() > 0) {

                if (_item.getProd_nombre().length() >= buscar.length()) {
                    String cadenaRecortada = _item.getProd_nombre().substring(0, buscar.length());

                    if (buscar.equals(cadenaRecortada)) lista.add(_item);
                }

            } else {
                lista.add(_item);
            }
        }
    }

    public static void seleccionarVentaCabecera(Context context, List<VentaCabecera> lista, String fecha, String buscar) {
        con = new ConectionSQLiteHelper(context);
        db = con.getReadableDatabase();

        Cursor temLista;

        String query = Methods.concat(new Object[]{"select * from ", VentaCabeceraTable.TABLE, " where "
                , VentaCabeceraTable.VC_FECHA, " = ? order by ", VentaCabeceraTable.VC_FECHA, " , ", VentaCabeceraTable.VC_HORA, " desc"

        });

        temLista = db.rawQuery(query, new String[]{fecha});
        while (temLista.moveToNext()) {
            VentaCabecera item = new VentaCabecera(
                    temLista.getInt(temLista.getColumnIndex(VentaCabeceraTable.VC_ID)),
                    temLista.getString(temLista.getColumnIndex(VentaCabeceraTable.VC_FECHA)),
                    temLista.getString(temLista.getColumnIndex(VentaCabeceraTable.VC_HORA)),
                    temLista.getDouble(temLista.getColumnIndex(VentaCabeceraTable.VC_MONTO)),
                    temLista.getString(temLista.getColumnIndex(VentaCabeceraTable.VC_COMENTARIO)),
                    temLista.getString(temLista.getColumnIndex(VentaCabeceraTable.CLIE_NOMBRE))
            );

            if (buscar.length() > 0) {

                if (item.getClie_nombre().length() >= buscar.length()) {
                    String cadenaRecortada = item.getClie_nombre().substring(0, buscar.length());

                    if (buscar.equals(cadenaRecortada)) lista.add(item);
                }

            } else {
                lista.add(item);
            }

        }

    }

    public static void seleccionarVentaDetalle(Context context, List<VentaDetalle> lista, int vc_id) {

        con = new ConectionSQLiteHelper(context);
        db = con.getReadableDatabase();

        Cursor temLista;

        String query = Methods.concat(new Object[]{"select * from ", VentaDetalleTable.TABLE, " where "
                , VentaDetalleTable.VC_ID, " = ? order by ", VentaDetalleTable.PROD_NOMBRE, " desc"
        });

        temLista = db.rawQuery(query, new String[]{String.valueOf(vc_id)});
        while (temLista.moveToNext()) {
            lista.add(new VentaDetalle(
                    temLista.getInt(temLista.getColumnIndex(VentaDetalleTable.VD_ID)),
                    temLista.getInt(temLista.getColumnIndex(VentaDetalleTable.VD_CANTIDAD)),
                    temLista.getDouble(temLista.getColumnIndex(VentaDetalleTable.VD_PRECIO)),
                    temLista.getInt(temLista.getColumnIndex(VentaDetalleTable.VC_ID)),
                    temLista.getString(temLista.getColumnIndex(VentaDetalleTable.PROD_NOMBRE)),
                    temLista.getString(temLista.getColumnIndex(VentaDetalleTable.PROD_RUTA_FOTO))
            ));
        }
    }
}
