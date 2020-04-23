package com.example.sistema_ventas_simple.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.data.model.VentaCabecera;

import java.util.List;

public class HistorialItemRecycler extends RecyclerView.Adapter<HistorialItemRecycler.ViewHolderHistorial> {

    List<VentaCabecera> listCabecera;
    OnItemClickListener itemClickListener;

    public HistorialItemRecycler(List<VentaCabecera> listCabecera, OnItemClickListener itemClickListener) {
        this.listCabecera = listCabecera;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderHistorial onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_historial, parent, false);
        return new ViewHolderHistorial(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistorial holder, int position) {
        holder.bind(listCabecera.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listCabecera.size();
    }


    public interface OnItemClickListener {
        void onItemClick(VentaCabecera ventaCabecera, int position);
    }

    class ViewHolderHistorial extends RecyclerView.ViewHolder {

        TextView nombre, fecha, hora, total;


        public ViewHolderHistorial(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.rivhTvNombre);
            fecha = itemView.findViewById(R.id.rivhTvFecha);
            hora = itemView.findViewById(R.id.rivhTvHora);
            total = itemView.findViewById(R.id.rivhTvTotal);
        }

        void bind(final VentaCabecera ventaCabecera, final OnItemClickListener clickListener) {
            nombre.setText(ventaCabecera.getClie_nombre());
            fecha.setText(ventaCabecera.getVc_fecha());
            hora.setText(ventaCabecera.getVc_hora());
            total.setText(String.valueOf(ventaCabecera.getVc_monto()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(ventaCabecera, getAdapterPosition());
                }
            });

        }
    }
}
