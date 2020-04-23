package com.example.sistema_ventas_simple.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import androidx.appcompat.widget.Toolbar;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.adapter.ProductoItemRecycler;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProductoActivity extends AppCompatActivity {

    @BindView(R.id.apRvProducto)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.apEtBuscarProducto)
    EditText buscador;

    RecyclerView.LayoutManager layoutManager;
    ProductoItemRecycler adapter;

    List<Producto> listProducto = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        cargarLista();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cargarLista();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void cargarLista() {
        Select.seleccionarProductos(getApplicationContext(), listProducto, buscador.getText().toString());
        cargarRecycler(listProducto);
    }

    private void cargarRecycler(List<Producto> listProducto) {

        adapter = new ProductoItemRecycler(listProducto, new ProductoItemRecycler.OnItemClickListener() {
            @Override
            public void OnClickItem(Producto producto, int position) {
                goActivity(false, producto);
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.apFabNuevoProducto)
    public void clickNuevoProducto() {
        goActivity(true, new Producto());
    }

    void goActivity(boolean bNuevo, Producto itemProducto) {
        Intent intent = new Intent(getApplicationContext(), ProductoDetalleActivity.class);

        intent.putExtra("bNuevo", bNuevo);
        intent.putExtra("itemProducto", itemProducto);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) cargarLista();
    }
}
