package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Consulta extends AppCompatActivity {
    AyudanteBD aBD;
    SQLiteDatabase db=null;
    TextView Datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        Datos = findViewById(R.id.txtResultado);
        aBD=new AyudanteBD(this,"JuguetesBD",null,1);
        db = aBD.getReadableDatabase();
        try {
            if (db != null) {
                Cursor cursor = db.rawQuery("SELECT * FROM Juguetes", null);
                while (cursor.moveToNext()) {
                    Datos.append("Id: " + cursor.getInt(0) + "\nMarca: " + cursor.getString(1) +
                            "\nNombre: " + cursor.getString(2) + "\nPrecio: " + cursor.getDouble(3) + "\n\n");
                }
                cursor.close();
                db.close();
            } else {
                Toast.makeText(getApplicationContext(), "No existe la base de datos", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}