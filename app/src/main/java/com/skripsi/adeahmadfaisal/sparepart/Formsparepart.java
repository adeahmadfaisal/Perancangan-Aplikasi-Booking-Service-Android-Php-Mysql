package com.skripsi.adeahmadfaisal.sparepart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.skripsi.adeahmadfaisal.R;

public class Formsparepart extends AppCompatActivity {

    TextView tvNamaSparePart;
    TextView tvIDSparePart;
    TextView tvHsparepart;
    TextView tvDeskripsiSparePart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formsparepart);
        Toolbar toolbar = findViewById(R.id.setformparepart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Deskripsi Sparepart");

        tvNamaSparePart     = findViewById(R.id.tvNamaSparePart);
        tvIDSparePart       = findViewById(R.id.tvIDSparePart);
        tvHsparepart        = findViewById(R.id.tvHsparepart);
        tvDeskripsiSparePart= findViewById(R.id.tvDeskripsiSparePart);

        if (getIntent().getExtras() != null) {
            Bundle  bundle  = new Bundle();
            tvNamaSparePart.setText(getIntent().getStringExtra("Namasparepart"));
            tvIDSparePart.setText(getIntent().getStringExtra("Idsparepart"));
            tvHsparepart.setText(getIntent().getStringExtra("Harga"));
            tvDeskripsiSparePart.setText(getIntent().getStringExtra("Deskripsi"));

        }
        else {
            System.out.println("Gagal");
        }

    }
}
