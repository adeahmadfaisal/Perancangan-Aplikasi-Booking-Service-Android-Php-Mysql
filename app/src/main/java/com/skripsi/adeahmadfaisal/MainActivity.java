package com.skripsi.adeahmadfaisal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skripsi.adeahmadfaisal.BookingService.ActBookingService;
import com.skripsi.adeahmadfaisal.Lokasi.ActLokasi;
import com.skripsi.adeahmadfaisal.Lokasi.LokasiDealer;
import com.skripsi.adeahmadfaisal.mycar.ActMycar;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;
import com.skripsi.adeahmadfaisal.sparepart.ActSparepart;
import com.skripsi.adeahmadfaisal.userprofile.userprofile;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    TextView tvResultNama;
    TextView getTvResultEmail;
  //  String resultNama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SudahLogin, false)

        if (sharedPrefManager.getSPSudahLogin() == false) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }

        initComponents();
        tvResultNama.setText(sharedPrefManager.getSPNama());
        getTvResultEmail.setText(sharedPrefManager.getSPEmail());



    }

    private void initComponents() {

        tvResultNama        = findViewById(R.id.nameapp);
        getTvResultEmail    = findViewById(R.id.emailapp);
    }

    public void logout(View view) {

        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SudahLogin, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    public void bookingserviceclick(View view) {
        Intent intent = new Intent(MainActivity.this, ActBookingService.class);
        startActivity(intent);
    }

    public void buttonmycar(View view) {
        Intent intent = new Intent(MainActivity.this, ActMycar.class);
        startActivity(intent);
    }


    public void Sparepart(View view) {
        Intent intent = new Intent(MainActivity.this, ActSparepart.class);
        startActivity(intent);
    }

    public void lokasidealer(View view) {
        Intent intent = new Intent(MainActivity.this, ActLokasi.class);
        startActivity(intent);
    }

    public void userprofile(View view) {
        Intent intent = new Intent(MainActivity.this, userprofile.class);
        startActivity(intent);
    }
}
