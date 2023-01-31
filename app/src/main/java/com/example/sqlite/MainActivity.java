package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener  {
    EditText EditId, EditMarca, EditNombre, EditPrecio;
    Button Insertar, Modificar, Consultar, Borrar;
    ImageView imgLimpiar, imgCamara;
    AyudanteBD aBD;
    SQLiteDatabase db=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditId = findViewById(R.id.edittextId);
        EditMarca = findViewById(R.id.edittextMarca);
        EditNombre = findViewById(R.id.edittextNombre);
        EditPrecio = findViewById(R.id.edittextPrecio);
        Insertar = findViewById(R.id.Insertar);
        Consultar = findViewById(R.id.Consulta);
        Modificar = findViewById(R.id.Modificar);
        Borrar = findViewById(R.id.Borrar);
        imgLimpiar = findViewById(R.id.imgLimpiar);
        imgCamara = findViewById(R.id.imgcamara);
        imgLimpiar.setOnTouchListener(this);
        imgCamara.setOnTouchListener(this);
        Modificar.setEnabled(false);
        Borrar.setEnabled(false);
        aBD=new AyudanteBD(this,"JuguetesBD",null,1);

        Insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = aBD.getWritableDatabase();
                if (EditId.length() == 0 | EditMarca.length() == 0 | EditPrecio.length() == 0 | EditNombre.length() == 0){
                    Toast.makeText(getApplicationContext(), "Ingresa Todos los datos", Toast.LENGTH_LONG).show();
                }else {
                    try {
                        if (db != null) {
                            db.execSQL("INSERT INTO Juguetes values (" + EditId.getText() + ",'" + EditMarca.getText() + "','" + EditNombre.getText() + "'," + EditPrecio.getText() + ")");
                            db.close();
                            Toast.makeText(getApplicationContext(),"Inserción exitosa",Toast.LENGTH_LONG).show();
                            EditId.setText("");
                            EditMarca.setText("");
                            EditNombre.setText("");
                            EditPrecio.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "No existe la base de datos", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Id repetido, Ingresa otro por favor",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditId.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Ingresa el Id", Toast.LENGTH_LONG).show();
                }else{
                    Consultar(EditId.getText().toString());
                }
            }
        });
        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = aBD.getReadableDatabase();
                if (EditId.length() == 0 | EditMarca.length() == 0 | EditPrecio.length() == 0 | EditNombre.length() == 0){
                    Toast.makeText(getApplicationContext(), "Ingresa Todos los datos", Toast.LENGTH_LONG).show();
                }else {
                    try {
                        if (db != null) {
                            db.execSQL("UPDATE Juguetes set marca ='" + EditMarca.getText() + "', nombre='" + EditNombre.getText() + "', precio = " + EditPrecio.getText() + " WHERE id=" + EditId.getText());
                            db.close();
                            Toast.makeText(getApplicationContext(), "Modificación exitosa", Toast.LENGTH_LONG).show();
                            EditId.setText("");
                            EditMarca.setText("");
                            EditNombre.setText("");
                            EditPrecio.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "No existe la base de datos", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = aBD.getReadableDatabase();
                if (EditId.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Ingresa el Id", Toast.LENGTH_LONG).show();
                }else {
                    try {
                        if (db != null) {
                            db.execSQL("DELETE FROM Juguetes WHERE id=" + EditId.getText());
                            db.close();
                            EditId.setText("");
                            EditMarca.setText("");
                            EditNombre.setText("");
                            EditPrecio.setText("");
                            Toast.makeText(getApplicationContext(), "Eliminación exitosa", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No existe la base de datos", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Consultar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getApplicationContext(), Consulta.class));
                return false;
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            String scanContent = scanningResult.getContents();
            Consultar(scanContent);
            Toast.makeText(getApplicationContext(), scanContent, Toast.LENGTH_LONG).show();
        } else {
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.imgLimpiar:
                EditId.setText("");
                EditMarca.setText("");
                EditNombre.setText("");
                EditPrecio.setText("");
                break;
            case R.id.imgcamara:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
        return false;
    }

    public void Consultar(String id){
        db = aBD.getReadableDatabase();
        String marca = null,nombre = null,precio = null;
        try {
            if (db != null) {
                Cursor cursor = db.rawQuery("SELECT * FROM Juguetes WHERE id =" + id, null);
                if(cursor.getCount() == 0){
                    Toast.makeText(getApplicationContext(), "Producto no encontrado", Toast.LENGTH_LONG).show();
                }else {
                    while (cursor.moveToNext()) {
                        marca = cursor.getString(1);
                        nombre = cursor.getString(2);
                        precio = cursor.getString(3);
                    }
                    EditId.setText(id);
                    EditMarca.setText(marca);
                    EditNombre.setText(nombre);
                    EditPrecio.setText(precio);
                    cursor.close();
                    db.close();
                    Modificar.setEnabled(true);
                    Borrar.setEnabled(true);
                }
            } else {
                Toast.makeText(getApplicationContext(), "No existe la base de datos", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}