package com.example.sistema_ventas_simple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import com.example.sistema_ventas_simple.activity.*;

import com.example.sistema_ventas_simple.schemeSQLite.ConectionSQLiteHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ConectionSQLiteHelper con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        con = new ConectionSQLiteHelper(MainActivity.this);

    }

    @OnClick(R.id.amIbProducto)
    public void clickProducto() {
        goActivity(ProductoActivity.class);
    }

    @OnClick(R.id.amIbCliente)
    public void clickCliente() {
        goActivity(ClienteActivity.class);
    }

    @OnClick(R.id.amIbVenta)
    public void clickVenta() {
        goActivity(VentaActivity.class);
    }

    @OnClick(R.id.amIbVentaHistorial)
    public void clickVentaHistorial() {
        goActivity(HistorialActivity.class);
    }

    void goActivity(Class<?> paramClass) {
        //intento para mostrar otras activities
        Intent intent = new Intent(getApplicationContext(), paramClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        con.close();
        super.onDestroy();
    }
}
