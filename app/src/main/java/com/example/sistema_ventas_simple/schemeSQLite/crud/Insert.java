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

public class Insert {

    public static void registrar(Context context, Object param, String table) {
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

                db.insert(ClienteTable.TABLE, ClienteTable.CLIE_ID, values);
                SessionPreferences.get(context).setCliente(cliente.getClie_id());

                break;

            case ProductoTable.TABLE:
                Producto producto = (Producto) param;

                values.put(ProductoTable.PROD_ID, producto.getProd_id());
                values.put(ProductoTable.PROD_NOMBRE, producto.getProd_nombre());
                values.put(ProductoTable.PROD_PRECIO, producto.getProd_precio());
                values.put(ProductoTable.PROD_RUTA_FOTO, producto.getProd_ruta_foto());

                db.insert(ProductoTable.TABLE, ProductoTable.PROD_ID, values);
                SessionPreferences.get(context).setProducto(producto.getProd_id());

                break;

            case VentaCabeceraTable.TABLE:
                VentaCabecera ventaCab = (VentaCabecera) param;

                values.put(VentaCabeceraTable.VC_ID, ventaCab.getVc_id());
                values.put(VentaCabeceraTable.VC_FECHA, ventaCab.getVc_fecha());
                values.put(VentaCabeceraTable.VC_HORA, ventaCab.getVc_hora());
                values.put(VentaCabeceraTable.VC_COMENTARIO, ventaCab.getVc_comentario());
                values.put(VentaCabeceraTable.VC_MONTO, ventaCab.getVc_monto());
                values.put(VentaCabeceraTable.CLIE_NOMBRE, ventaCab.getClie_nombre());

                db.insert(VentaCabeceraTable.TABLE, VentaCabeceraTable.VC_ID, values);
                SessionPreferences.get(context).setVentaCabecera(ventaCab.getVc_id());

                break;

            case VentaDetalleTable.TABLE:
                VentaDetalle ventaDet = (VentaDetalle) param;

                values.put(VentaDetalleTable.VD_ID, ventaDet.getVd_id());
                values.put(VentaDetalleTable.VD_CANTIDAD, ventaDet.getVd_cantidad());
                values.put(VentaDetalleTable.VD_PRECIO, ventaDet.getVd_precio());
                values.put(VentaDetalleTable.VC_ID, ventaDet.getVc_id());
                values.put(VentaDetalleTable.PROD_NOMBRE, ventaDet.getProd_nombre());
                values.put(VentaDetalleTable.PROD_RUTA_FOTO, ventaDet.getProd_ruta_foto());

                db.insert(VentaDetalleTable.TABLE, VentaDetalleTable.VD_ID, values);
                SessionPreferences.get(context).setVentaDetalle(ventaDet.getVd_id());

                break;
        }

    }

    public static void registrarVentaDetalle(Context context, List<ProductoVenta> lista, int vc_id){
        int codigo = 0;
        for(ProductoVenta item : lista){
            codigo = SessionPreferences.get(context).getVentaDetalle();
            registrar(context, new VentaDetalle(codigo,
                    item.getProd_cantidad(),
                    item.getProd_precio_venta(),
                    vc_id,
                    item.getProd_nombre(),
                    item.getProd_ruta_foto()), VentaDetalleTable.TABLE );
        }
    }
}
