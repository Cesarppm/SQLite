package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AyudanteBD extends SQLiteOpenHelper {
    //Sentencia para crear la tabla de Usuarios
    String sentenciaCreacionSQL="CREATE TABLE Juguetes (id INTEGER primary key,marca TEXT NOT NULL, nombre TEXT NOT NULL, precio DOUBLE NOT NULL)";
    public AyudanteBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sentenciaCreacionSQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
