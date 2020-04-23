package com.example.sistema_ventas_simple.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductoItemRecycler extends RecyclerView.Adapter<ProductoItemRecycler.ViewHolderProducto> {

    private List<Producto> listProducto;
    private OnItemClickListener itemClickListener;
    private Context context;

    public ProductoItemRecycler(List<Producto> listProducto, OnItemClickListener itemClickListener) {
        this.listProducto = listProducto;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_producto, parent, false);
        return new ViewHolderProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProducto holder, int position) {
        holder.bind(listProducto.get(position),itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    public interface OnItemClickListener {
        void OnClickItem(Producto producto, int position);
    }

    class ViewHolderProducto extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView nombre, precio;
        LinearLayout llSelection;

        public ViewHolderProducto(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ripCivImagen);
            nombre = itemView.findViewById(R.id.ripTvNombre);
            precio = itemView.findViewById(R.id.ripTvPrecio);
            llSelection = itemView.findViewById(R.id.ripLlItemSeleccionado);

        }

        void bind(final Producto producto, final OnItemClickListener listener) {
            nombre.setText(producto.getProd_nombre());
            precio.setText(String.valueOf(producto.getProd_precio()));

            llSelection.setBackground(ContextCompat.getDrawable(context, producto.getProd_seleccionado() ? R.color.productoSelecction : R.color.white ));

            if (producto.getProd_ruta_foto().length() <= 1 || producto.getProd_ruta_foto().isEmpty()) {
                Picasso.get().load(R.drawable.caja_producto).into(image);
            } else {
                Picasso.get().load(producto.getProd_ruta_foto())
                        .resize(65, 65)
                        .error(R.drawable.caja_producto_error)
                        .centerCrop()
                        .into(image);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClickItem(producto, getAdapterPosition());
                }
            });
        }
    }
}
