package com.example.sistema_ventas_simple.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.adapter.HistorialItemRecycler;
import com.example.sistema_ventas_simple.data.model.VentaCabecera;
import com.example.sistema_ventas_simple.data.util.Methods;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Select;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistorialActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.avhRvProducto)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;

    HistorialItemRecycler adapter;

    @BindView(R.id.avhEtBuscarCliente)
    EditText buscador;

    List<VentaCabecera> ventaCabeceraList = new ArrayList<>();

    String fecha = "";
    int anio, mes, dia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        layoutManager = new LinearLayoutManager(getApplicationContext());

        calendarLoadVariable();

        filterVentas();

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterVentas();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick(R.id.avhFabBuscarVenta)
    void clickBuscarVenta() {
        new DatePickerDialog(this, calendarioFlotante, anio, mes, dia).show();
    }

    private void calendarLoadVariable() {
        Calendar calendar = Calendar.getInstance();
        anio = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        fecha = Methods.getFecha();
    }

    private DatePickerDialog.OnDateSetListener calendarioFlotante = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            anio = year;
            mes = month;
            dia = dayOfMonth;
            fecha = Methods.concat(new Object[]{anio, "-", String.format("%02d", (mes + 1)), "-", String.format("%02d", dia)});
            filterVentas();
        }
    };

    private void filterVentas() {
        Select.seleccionarVentaCabecera(getApplicationContext(), ventaCabeceraList, fecha, buscador.getText().toString());
        loadRecycler(ventaCabeceraList);
    }

    private void loadRecycler(List<VentaCabecera> ventaCabeceraList) {
        adapter = new HistorialItemRecycler(ventaCabeceraList, new HistorialItemRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(VentaCabecera ventaCabecera, int position) {
                goActivity(ventaCabecera);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void goActivity(VentaCabecera ventaCabecera) {
        Intent intent = new Intent(getApplicationContext(), VentaHistorialDetalleActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("itemVentaCabecera", ventaCabecera);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            filterVentas();
        }
    }
}
