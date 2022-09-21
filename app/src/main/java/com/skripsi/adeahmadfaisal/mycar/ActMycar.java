package com.skripsi.adeahmadfaisal.mycar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.adapter.ActmycarAdapter;
import com.skripsi.adeahmadfaisal.adapter.PilihMobilAdapter;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.model.mycar.AcmycarItem;
import com.skripsi.adeahmadfaisal.model.mycar.ResponseMycar;
import com.skripsi.adeahmadfaisal.model.pilihMobil.PilihmobilItem;
import com.skripsi.adeahmadfaisal.model.pilihMobil.ResponsePilihmobil;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ActMycar extends AppCompatActivity {

    RecyclerView rvViewmyCar;
    List<AcmycarItem> semuamycarlist = new ArrayList<>();
    Context mContext;
    ActmycarAdapter actmycarAdapter;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    TextView errormsgcar;
    ImageView logohondacar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPrefManager = new SharedPrefManager(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_mycar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent   = new Intent(ActMycar.this, ActSemuamobil.class);
                startActivity(intent);
            }
        });

        mApiService         = UtilsApi.getAPIService();
        rvViewmyCar         = findViewById(R.id.rvdaftarMobil);
        mContext            = this;
        actmycarAdapter     = new ActmycarAdapter(mContext, semuamycarlist);
        errormsgcar         = findViewById(R.id.errormsgmycar);
        logohondacar        = findViewById(R.id.hondamycar);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvViewmyCar.setLayoutManager(mLayoutManager);

        getResultMycar();
    }

    private void getResultMycar() {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        String uid1 = sharedPrefManager.getSPUid();
        mApiService.getAcmycar(uid1).enqueue(new Callback<ResponseMycar>() {

            @Override
            public void onResponse(Call<ResponseMycar> call, Response<ResponseMycar> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    if (error == false) {
                        final List<AcmycarItem> MobilItem = response.body().getAcmycar();
                        rvViewmyCar.setAdapter(new ActmycarAdapter(mContext, MobilItem));
                        actmycarAdapter.notifyDataSetChanged();
                        errormsgcar.setVisibility(View.GONE);
                        logohondacar.setVisibility(View.GONE);
                        rvViewmyCar.setVisibility(View.VISIBLE);
                    } else {
                        String error_message = response.body().getErrorMsg();
                        errormsgcar.setVisibility(View.VISIBLE);
                        logohondacar.setVisibility(View.VISIBLE);
                        rvViewmyCar.setVisibility(View.GONE);

                    }


                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mobil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMycar> call, Throwable t) {

                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(mContext, "Data Kosong!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
