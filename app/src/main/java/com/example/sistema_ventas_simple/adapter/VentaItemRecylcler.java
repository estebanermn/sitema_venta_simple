package com.example.sistema_ventas_simple.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.data.model.ProductoVenta;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VentaItemRecylcler extends RecyclerView.Adapter<VentaItemRecylcler.ViewHolderVenta> {

    List<ProductoVenta> listProductoVenta;
    EditText totalVenta;

    public VentaItemRecylcler(List<ProductoVenta> listProductoVenta, EditText totalVenta) {
        this.listProductoVenta = listProductoVenta;
        this.totalVenta = totalVenta;
    }

    @NonNull
    @Override
    public ViewHolderVenta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_venta, parent, false);

        return new ViewHolderVenta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVenta holder, int position) {
        holder.bind(listProductoVenta.get(position));
    }

    @Override
    public int getItemCount() {
        return listProductoVenta.size();
    }




    class ViewHolderVenta extends RecyclerView.ViewHolder {

        EditText cantidad, precio;
        TextView nombre, totalProducto;
        ImageView imagen;
        Button quitar;

        public ViewHolderVenta(@NonNull View itemView) {
            super(itemView);

            cantidad = itemView.findViewById(R.id.rivEtCantidad);
            precio = itemView.findViewById(R.id.rivEtPrecio);
            nombre = itemView.findViewById(R.id.rivTvNombre);
            totalProducto = itemView.findViewById(R.id.rivTvTotal);
            imagen = itemView.findViewById(R.id.rivCivImagen);
            quitar = itemView.findViewById(R.id.rivBQuitar);

            cantidad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    modificarValores(cantidad, precio, totalProducto, getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            precio.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    modificarValores(cantidad, precio, totalProducto, getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            quitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(getAdapterPosition());
                }
            });

        }

        void bind(final ProductoVenta productoVenta) {

            cantidad.setText(String.valueOf(productoVenta.getProd_cantidad()));
            precio.setText(String.valueOf(productoVenta.getProd_precio()));
            totalProducto.setText(String.valueOf(productoVenta.getProd_total()));
            nombre.setText(productoVenta.getProd_nombre());

            if (productoVenta.getProd_ruta_foto().length() < 0 || productoVenta.getProd_ruta_foto().isEmpty()) {
                Picasso.get().load(R.drawable.caja_producto).into(imagen);
            } else {
                Picasso.get().load(productoVenta.getProd_ruta_foto())
                        .error(R.drawable.caja_producto)
                        .centerCrop()
                        .resize(65, 65)
                        .into(imagen);
            }
        }


    }


    private void remove(int adapterPosition) {
        listProductoVenta.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        sumarLista();
    }

    public void sumarLista() {
        Double temp = 0.0;
        for (ProductoVenta item : listProductoVenta) {
            temp += item.getProd_total();
        }
        totalVenta.setText(String.valueOf(temp));
    }

    private void modificarValores(EditText cantidad, EditText precio, TextView totalProducto, int adapterPosition) {
        if (cantidad.getText().length() > 0) {
            if (precio.getText().length() > 0) {
                int cant = Integer.parseInt(cantidad.getText().toString());
                Double pre = Double.parseDouble(precio.getText().toString());

                totalProducto.setText(String.valueOf(cant * pre));
                listProductoVenta.get(adapterPosition).setProd_cantidad(cant);
                listProductoVenta.get(adapterPosition).setProd_precio_venta(pre);
                listProductoVenta.get(adapterPosition).setProd_total(cant * pre);
                sumarLista();
            }
        }
    }

}
