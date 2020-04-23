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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.adapter.ClienteItemRecycler;
import com.example.sistema_ventas_simple.data.model.Cliente;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.apRvCliente)
    RecyclerView recyclerView;

    @BindView(R.id.acEtBuscarCliente)
    TextView buscador;

    RecyclerView.LayoutManager layoutManager;

    ClienteItemRecycler adapter;

    List<Cliente> listCliente = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        loadView();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadView();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick(R.id.apFabNuevoCliente)
    public void clickNuevoCliente() {
        goActivity(true, new Cliente());
    }

    private void loadView() {
        Select.seleccionarClientes(getApplicationContext(), listCliente, buscador.getText().toString());

        loadRecycler(listCliente);
    }

    private void loadRecycler(List<Cliente> listCliente) {
        adapter = new ClienteItemRecycler(listCliente, new ClienteItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Cliente cliente, int position) {
                goActivity(false, cliente);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void goActivity(boolean bNuevo, Cliente cliente) {
        Intent intent = new Intent(getApplicationContext(), ClienteDetalleActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemCliente", cliente);

        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            loadView();
        }
    }
}
