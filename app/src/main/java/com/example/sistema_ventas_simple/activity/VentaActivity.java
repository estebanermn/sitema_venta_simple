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
import android.widget.EditText;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.adapter.ProductoItemRecycler;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.example.sistema_ventas_simple.data.util.Message;
import com.example.sistema_ventas_simple.data.util.Methods;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VentaActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avRvProducto)
    RecyclerView recyclerView;

    @BindView(R.id.avEtBuscarProducto)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;

    ProductoItemRecycler adapter;

    List<Producto> listaProducto = new ArrayList<>();
    List<Producto> listaProductoSeleccionados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        ButterKnife.bind(this);

        //ocultar el teclado al iniciar el activity
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        loadList();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadList();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick(R.id.avFabNuevoProducto)
    void clickVentaNueva() {
        listaProductoSeleccionados.clear();
        for (Producto item : listaProducto) {
            if (item.getProd_seleccionado()) listaProductoSeleccionados.add(item);
        }
        if (listaProductoSeleccionados.size() > 0) {
            goActivity();
        } else {
            new Message(getApplicationContext()).messageToast("No ha seleccionado productos");
        }

    }

    private void loadList() {
        Select.seleccionarProductos(getApplicationContext(), listaProducto, buscador.getText().toString());
        loadRecycler(listaProducto);
    }

    private void loadRecycler(final List<Producto> listaProducto) {
        adapter = new ProductoItemRecycler(listaProducto, new ProductoItemRecycler.OnItemClickListener() {
            @Override
            public void OnClickItem(Producto producto, int position) {
                listaProducto.get(position).setProd_seleccionado(!producto.getProd_seleccionado());
                loadRecycler(listaProducto);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void goActivity() {
        Intent intent = new Intent(getApplicationContext(), VentaNuevaActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("listaProducto", Methods.convertProductoListToText(listaProductoSeleccionados));

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            loadList();
        }
    }
}
