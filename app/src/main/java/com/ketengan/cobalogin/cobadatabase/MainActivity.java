package com.ketengan.cobalogin.cobadatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String[] dataDaftarMahasiswa;
    ListView listViewDaftarMahasiswa;
    Menu menu;
    protected Cursor cursor;
    DataHelper dataHelperCenter;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddNewData = findViewById(R.id.button_buat_data_baru);

        buttonAddNewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPindahHalamanAddData = new Intent(MainActivity.this, AddNewData.class);
                startActivity(intentPindahHalamanAddData);
            }
        });

        mainActivity = this;
        dataHelperCenter = new DataHelper(this);
        refreshList();
    }

    public void refreshList(){
        TextView textViewNotDataFound = findViewById(R.id.tv_not_data_found);

        SQLiteDatabase database = dataHelperCenter.getReadableDatabase();
            cursor = database.rawQuery("SELECT * FROM biodatamahasiswa", null);
            if (cursor.getCount() != 0){
                textViewNotDataFound.setVisibility(View.INVISIBLE);
            } else {
                textViewNotDataFound.setVisibility(View.VISIBLE);
            }
            dataDaftarMahasiswa = new String[cursor.getCount()];
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                dataDaftarMahasiswa[i] = cursor.getString(1);
            }


        listViewDaftarMahasiswa = findViewById(R.id.lv_data_mahasiswa);
        listViewDaftarMahasiswa.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataDaftarMahasiswa));
        listViewDaftarMahasiswa.setSelected(true);

        listViewDaftarMahasiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = dataDaftarMahasiswa[position];
                final CharSequence[] dialogItem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};
                final AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(MainActivity.this);
                builderAlertDialog.setTitle("Option");

                builderAlertDialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intentMoveToSeeDetail = new Intent(getApplicationContext(), SeeBiodata.class);
                                intentMoveToSeeDetail.putExtra("nama", selection);
                                startActivity(intentMoveToSeeDetail);
                                break;
                            case 1:
                                Intent intentMoveToUpdateBiodata = new Intent(getApplicationContext(), UpdateBiodata.class);
                                intentMoveToUpdateBiodata.putExtra("nama", selection);
                                startActivity(intentMoveToUpdateBiodata);
                                break;
                            case 2:
                                /*SQLiteDatabase databaseDelete = dataHelperCenter.getWritableDatabase();
                                databaseDelete.execSQL("DELETE FROM biodatamahasiswa WHERE nama = '" + selection + "'");*/
                                showDialogAlertDeleteData(selection);
                                break;
                        }
                    }
                });
                builderAlertDialog.create();
                builderAlertDialog.show();
            }
        });
        ((ArrayAdapter)listViewDaftarMahasiswa.getAdapter()).notifyDataSetInvalidated();

    }

    private void showDialogAlertDeleteData(final String nama){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //set judul
        alertDialogBuilder.setTitle("Hapus Data?");
        alertDialogBuilder.setMessage("Hapus data " + nama);
        alertDialogBuilder.setCancelable(false);

        //set kalo di klik ya
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //hapus data dari database
                SQLiteDatabase databaseDelete = dataHelperCenter.getWritableDatabase();
                databaseDelete.execSQL("DELETE FROM biodatamahasiswa WHERE nama = '" + nama + "'");

                // keluarkan pesan pemberitahuan
                Toast.makeText(getApplicationContext(), "Data berhasil dihapus ", Toast.LENGTH_SHORT).show();

                refreshList();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }
}
