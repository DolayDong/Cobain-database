package com.ketengan.cobalogin.cobadatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewData extends AppCompatActivity {
    private DataHelper dataHelperDatabase;
    private EditText editTextNamaMahasiswa, editTextNim, editTextNamaKampus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_data);

        dataHelperDatabase = new DataHelper(this);

        editTextNim = findViewById(R.id.e_text_nim);
        editTextNamaMahasiswa = findViewById(R.id.e_text_nama_mahasiswa);
        editTextNamaKampus = findViewById(R.id.e_text_nama_kampus);

        Button buttonAdd = findViewById(R.id.button_add_data);
        Button buttonBack = findViewById(R.id.button_back);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dataHelperDatabase.getWritableDatabase();
                database.execSQL("INSERT INTO biodatamahasiswa(nim, nama, namakampus) VALUES ('" +
                        editTextNim.getText().toString() + "', '" +
                        editTextNamaMahasiswa.getText().toString() + "', '" +
                        editTextNamaKampus.getText().toString() + "')");

                Toast.makeText(getApplicationContext(), editTextNamaMahasiswa.getText().toString() + " berhasil disimpan", Toast.LENGTH_SHORT).show();

                MainActivity.mainActivity.refreshList();
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

