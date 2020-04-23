package com.example.sistema_ventas_simple.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sistema_ventas_simple.R;
import com.example.sistema_ventas_simple.data.model.Producto;
import com.example.sistema_ventas_simple.data.preferences.SessionPreferences;
import com.example.sistema_ventas_simple.data.util.Message;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Delete;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Insert;
import com.example.sistema_ventas_simple.schemeSQLite.crud.Update;
import com.example.sistema_ventas_simple.schemeSQLite.tables.ProductoTable;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoDetalleActivity extends AppCompatActivity {

    @BindView(R.id.apdTietNombreProducto)
    TextInputEditText nombre;

    @BindView(R.id.apdTietPrecioProducto)
    TextInputEditText precio;

    @BindView(R.id.apdIvImageProducto)
    ImageView image;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.apdLlAgregar)
    LinearLayout agregar;

    @BindView(R.id.apdLlModificar)
    LinearLayout modificarEliminar;

    boolean bNuevo = true;
    Producto producto;

    String pathUri = "";
    final int SELECT_PICTURE = 200;

    boolean bModificado = false;

    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        message = new Message(getApplicationContext());

        if (getIntent().hasExtra("bNuevo")) {
            bNuevo = getIntent().getBooleanExtra("bNuevo", true);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        agregar.setVisibility(bNuevo ? View.VISIBLE : View.INVISIBLE);
        modificarEliminar.setVisibility(bNuevo ? View.INVISIBLE : View.VISIBLE);


        if (!bNuevo) {
            producto = (Producto)getIntent().getSerializableExtra("itemProducto");
            loadView(producto);
        }
        nombre.requestFocus();
    }

    @OnClick(R.id.apdBBuscarImagen)
    public void searchImage() {
        Intent selectIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(selectIntent, "selecciona app de imagen"), SELECT_PICTURE);
    }

    @OnClick(R.id.apdBQuitarImagen)
    public void removeImage() {
        Picasso.get().load(R.drawable.caja_producto)
                .resize(180, 160)
                .centerCrop()
                .into(image);
        pathUri = "";
    }

    @OnClick(R.id.apdBAgregar)
    public void addProducto() {
        int codigo = SessionPreferences.get(getApplicationContext()).getProducto();

        Insert.registrar(getApplicationContext(), new Producto(
                codigo,
                nombre.getText().toString(),
                Double.parseDouble(precio.getText().toString()),
                pathUri, false), ProductoTable.TABLE);

        message.messageToastSave();
        bModificado = true;

        nombre.setText("");
        precio.setText("");
        pathUri = "";
        Picasso.get().load(R.drawable.caja_producto)
                .resize(180, 160)
                .centerCrop()
                .into(image);

        nombre.requestFocus();
    }

    @OnClick(R.id.apdBModificar)
    public void updateProducto() {
        Update.actualizar(getApplicationContext(),
                new Producto(
                        producto.getProd_id(),
                        nombre.getText().toString(),
                        Double.parseDouble(precio.getText().toString()),
                        pathUri,
                        false
                ), ProductoTable.TABLE);

        message.messageToastSave();
        bModificado = true;
        exitActivity();
    }

    @OnClick(R.id.apdBEliminar)
    public void deleteProducto() {
        new AlertDialog.Builder(this).setTitle("Productos")
                .setMessage("¿Deseas eliminar el producto?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete.eliminar(getApplicationContext(), producto.getProd_id(),
                                ProductoTable.TABLE);
                        message.messageToastDelete();
                        bModificado = true;
                        exitActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PICTURE:
                if (requestCode == RESULT_OK) {
                    Uri path = data.getData();
                    assert path != null;
                    pathUri = path.toString();

                    Picasso.get().load(pathUri)
                            .resize(180, 160).centerCrop().into(image);
                }
        }
    }

    private void loadView(Producto producto) {
        nombre.setText(producto.getProd_nombre());
        precio.setText(String.valueOf(producto.getProd_precio()));

        if (producto.getProd_ruta_foto().length() <= 1 || producto.getProd_ruta_foto().isEmpty()) {

            Picasso.get().load(R.drawable.caja_producto).into(image);
        } else {

            Picasso.get().load(producto.getProd_ruta_foto())
                    .resize(180, 160)
                    .error(R.drawable.caja_producto_error)
                    .centerCrop()
                    .into(image);
        }

        pathUri = producto.getProd_ruta_foto();

    }

    void exitActivity() {
        if (bModificado) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        exitActivity();
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
}
