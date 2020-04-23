package com.example.sistema_ventas_simple.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.data.model.Cliente;
import com.example.sistema_ventas_simple.data.preferences.SessionPreferences;
import com.example.sistema_ventas_simple.data.util.Message;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Delete;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Insert;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Update;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ClienteTable;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClienteDetalleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.acdTietNombre)
    EditText nombre;
    @BindView(R.id.acdTietDireccion)
    EditText direccion;
    @BindView(R.id.acdTietTel)
    EditText telefono;
    @BindView(R.id.acdTietEmail)
    EditText email;

    @BindView(R.id.acdLLAgregar)
    LinearLayout agregar;

    @BindView(R.id.acdLLModificar)
    LinearLayout modificar;

    Boolean bNuevo = true, bModificado = false;

    Cliente cliente;

    Message message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        message = new Message(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")) {
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificar.setVisibility(!bNuevo ? View.VISIBLE : View.INVISIBLE);

        if (!bNuevo) {
            cliente = (Cliente) getIntent().getSerializableExtra("itemCliente");
            loadView(cliente);
        }
        nombre.requestFocus();
    }

    @OnClick(R.id.acdBAgregar)
    void clickInsert() {
        if (nombre.getText().length() > 0) {
            int codigo = SessionPreferences.get(getApplicationContext()).getCliente();
            Insert.registrar(getApplicationContext(), new Cliente(
                    codigo,
                    nombre.getText().toString(),
                    telefono.getText().toString(),
                    email.getText().toString(),
                    direccion.getText().toString()
            ), ClienteTable.TABLE);

            bModificado = true;
            message.messageToastSave();
            loadView(new Cliente(0, "", "", "", ""));

        } else {
            message.messageToast("Ingrese un nombre");
            nombre.requestFocus();
        }
    }

    @OnClick(R.id.acdBModificar)
    void clickUpdate() {

        if (nombre.getText().length() > 0) {
            Update.actualizar(getApplicationContext(), new Cliente(
                    cliente.getClie_id(),
                    nombre.getText().toString(),
                    telefono.getText().toString(),
                    email.getText().toString(),
                    direccion.getText().toString()
            ), ClienteTable.TABLE);
            bModificado = true;
            message.messageToastSave();
            exitActivity();
        } else {
            message.messageToast("Ingrese un nombre");
            nombre.requestFocus();
        }
    }

    @OnClick(R.id.acdBEliminar)
    void clickDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Cliente")
                .setMessage("¿Desea eliminar el cliente?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete.eliminar(getApplicationContext(), cliente.getClie_id(), ClienteTable.TABLE);
                        bModificado = true;
                        message.messageToastDelete();
                        exitActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void loadView(Cliente cliente) {
        nombre.setText(cliente.getClie_nombre());
        telefono.setText(cliente.getClie_num_cel());
        email.setText(cliente.getClie_email());
        direccion.setText(cliente.getClie_direccion());

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

    void exitActivity() {
        if (bModificado) {
            setResult(Activity.RESULT_OK, new Intent());
        }
        finish();
    }
}
