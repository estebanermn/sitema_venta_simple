package com.example.sistema_ventas_simple.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.adapter.VentaHistorialItemProductoRecycler;
import com.example.sistema_ventas_simple.data.model.VentaCabecera;
import com.example.sistema_ventas_simple.data.model.VentaDetalle;
import com.example.sistema_ventas_simple.data.util.Message;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Delete;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Select;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaHistorialDetalleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avhdActvCliente)
    TextInputEditText cliente;

    @BindView(R.id.avhdActvFecha)
    TextInputEditText fecha;

    @BindView(R.id.avhdTietObservacion)
    TextInputEditText observacion;

    @BindView(R.id.avhdRvProductosSeleccionados)
    RecyclerView recyclerView;

    @BindView(R.id.avhdTietTotal)
    TextInputEditText total;

    RecyclerView.LayoutManager layoutManager;
    VentaHistorialItemProductoRecycler adaptador;

    List<VentaDetalle> listaDetalle = new ArrayList<>();

    boolean bCancelado = false;

    VentaCabecera ventaCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_historial_detalle);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        if (getIntent().hasExtra("itemVentaCabecera")) {
            ventaCabecera = (VentaCabecera) getIntent().getSerializableExtra("itemVentaCabecera");
            cliente.setText(ventaCabecera.getClie_nombre());
            fecha.setText(ventaCabecera.getVc_fecha().concat(" ").concat(ventaCabecera.getVc_hora()));
            observacion.setText(ventaCabecera.getVc_comentario());
        }

        loadDetails();
    }

    @OnClick(R.id.avhdBCancelar)
    void onClickCancel() {
        new AlertDialog.Builder(this)
                .setTitle("Ventas realizadas")
                .setMessage("¿Desea cancelar esta venta?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete.eliminarVenta(getApplicationContext(), listaDetalle, ventaCabecera.getVc_id());
                        new Message(getApplicationContext()).messageToast("Venta Cancelada");
                        bCancelado = true;
                        exitActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void loadDetails() {

        Select.seleccionarVentaDetalle(getApplicationContext(), listaDetalle, ventaCabecera.getVc_id());
        loadRecycler();
    }

    private void loadRecycler() {
        adaptador = new VentaHistorialItemProductoRecycler(listaDetalle, total);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptador);
        adaptador.sumarItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    private void exitActivity() {
        if (bCancelado) {
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}
