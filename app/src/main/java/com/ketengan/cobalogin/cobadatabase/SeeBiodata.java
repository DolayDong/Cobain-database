package com.ketengan.cobalogin.cobadatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SeeBiodata extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dataHelperDatabase;
    TextView textViewNama, textViewNim, textViewNamaKampus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_data);

        dataHelperDatabase = new DataHelper(this);
        textViewNim = findViewById(R.id.tv_nim_mahasiswa);
        textViewNama = findViewById(R.id.tv_nama_mahasiswa);
        textViewNamaKampus = findViewById(R.id.tv_nama_kampus);

        SQLiteDatabase database = dataHelperDatabase.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM biodatamahasiswa WHERE nama = '" + getIntent().getStringExtra("nama") + "'", null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0){
            cursor.moveToPosition(0);
            textViewNim.setText(cursor.getString(0));
            textViewNama.setText(cursor.getString(1));
            textViewNamaKampus.setText(cursor.getString(2));
        }

    }
}
