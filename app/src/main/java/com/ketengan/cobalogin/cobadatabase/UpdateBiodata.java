package com.ketengan.cobalogin.cobadatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateBiodata extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dataHelperDatabase;
    EditText editTextNim, editTextNamaMahasiswa, editTextNamaKampus;
    Button buttonUpdateBiodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        dataHelperDatabase = new DataHelper(this);
        editTextNim = findViewById(R.id.e_text_ubah_nim_mahasiswa);
        editTextNamaKampus = findViewById(R.id.e_text_ubah_nama_kampus);
        editTextNamaMahasiswa = findViewById(R.id.e_text_ubah_nama_mahasiswa);

        SQLiteDatabase database = dataHelperDatabase.getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM biodatamahasiswa WHERE nama = '" + getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            cursor.moveToPosition(0);
            editTextNim.setText(cursor.getString(0).toString());
            editTextNamaMahasiswa.setText(cursor.getString(1).toString());
            editTextNamaKampus.setText(cursor.getString(2).toString());
        }

        buttonUpdateBiodata = findViewById(R.id.button_update_biodata);
        buttonUpdateBiodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = dataHelperDatabase.getWritableDatabase();
                database.execSQL("UPDATE biodatamahasiswa SET nama = '" +
                        editTextNamaMahasiswa.getText().toString() +"', namakampus = '" +
                        editTextNamaKampus.getText().toString() +"' WHERE nim = '" + editTextNim.getText().toString()+"'");
                Toast.makeText(getApplicationContext(), "Data berhasil diubah menjadi " + editTextNamaMahasiswa.getText().toString(), Toast.LENGTH_SHORT).show();
                MainActivity.mainActivity.refreshList();
                finish();
            }
        });

    }
}
