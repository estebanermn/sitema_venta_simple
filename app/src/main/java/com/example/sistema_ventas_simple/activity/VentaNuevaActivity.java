package com.example.sistema_ventas_simple.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.adapter.VentaItemRecylcler;
import com.example.sistema_ventas_simple.data.model.Cliente;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.example.sistema_ventas_simple.data.model.ProductoVenta;
import com.example.sistema_ventas_simple.data.model.VentaCabecera;
import com.example.sistema_ventas_simple.data.preferences.SessionPreferences;
import com.example.sistema_ventas_simple.data.util.Message;
import com.example.sistema_ventas_simple.data.util.Methods;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Insert;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Select;
import com.example.sistema_ventas_simple.schemeSQLite.tables.VentaCabeceraTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaNuevaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avnRvProductosSeleccioandos)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    VentaItemRecylcler adapter;

    Boolean bModificado;

    @BindView(R.id.avnActvCliente)
    AutoCompleteTextView listaAutoCliente;

    @BindView(R.id.avnTietTotal)
    EditText totalVenta;


    @BindView(R.id.avnTietObservacion)
    EditText comentario;

    List<ProductoVenta> listaProductoVenta = new ArrayList<>();

    Message message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_nueva);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        message = new Message(getApplicationContext());

        loadAutoComplete();

        if (getIntent().hasExtra("listaProducto")) {
            List<Producto> tempProducto = Methods.convertProductoTextoToLista(getIntent().getStringExtra("listaProducto"));

            for (Producto item : tempProducto) {
                listaProductoVenta.add(new ProductoVenta(item, item.getProd_precio(), 1, item.getProd_precio()));
            }
            loadRecycler();
        }

    }

    @OnClick(R.id.avnBNuevo)
    void clickNuevaVenta() {
        if (listaProductoVenta.size() > 0) {
            if (listaAutoCliente.getText().length() > 0) {
                registrarVentaNueva();
            } else {
                message.messageToast("Ingrese un cliente");
            }
        } else {
            message.messageToast("No hay productos en la lista");
        }
    }

    private void registrarVentaNueva() {

        int codigoCabecera = SessionPreferences.get(getApplicationContext()).getVentaCabecera();
        Insert.registrar(getApplicationContext(), new VentaCabecera(
                codigoCabecera,
                Methods.getFecha(),
                Methods.getHora(),
                Double.parseDouble(totalVenta.getText().toString()),
                comentario.getText().toString(),
                listaAutoCliente.getText().toString()
        ), VentaCabeceraTable.TABLE);

        Insert.registrarVentaDetalle(getApplicationContext(), listaProductoVenta, codigoCabecera);

        bModificado = true;
        message.messageToast("Venta realizada");
        exitActivity();
    }

    private void loadRecycler() {
        adapter = new VentaItemRecylcler(listaProductoVenta, totalVenta);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.sumarLista();
    }

    private void loadAutoComplete() {
        List<Cliente> tempListCliente = new ArrayList<>();
        Select.seleccionarClientes(getApplicationContext(), tempListCliente, "");

        String[] nombre = new String[tempListCliente.size()];

        int i = 0;
        for (Cliente item : tempListCliente) {
            nombre[i] = item.getClie_nombre();
            i++;
        }

        ArrayAdapter<String> adapterCliente = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombre);
        listaAutoCliente.setAdapter(adapterCliente);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    void exitActivity() {
        if (bModificado) {
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}
