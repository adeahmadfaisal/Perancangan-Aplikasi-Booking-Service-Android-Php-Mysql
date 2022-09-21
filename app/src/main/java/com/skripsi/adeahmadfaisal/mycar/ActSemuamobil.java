package com.skripsi.adeahmadfaisal.mycar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.adapter.ActmycarAdapter;
import com.skripsi.adeahmadfaisal.adapter.SemuaMobilAdapter;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.model.allcar.AllcarItem;
import com.skripsi.adeahmadfaisal.model.allcar.ResponseAllCar;
import com.skripsi.adeahmadfaisal.model.mycar.AcmycarItem;
import com.skripsi.adeahmadfaisal.model.mycar.ResponseMycar;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ActSemuamobil extends AppCompatActivity {

    RecyclerView rvsemuaMobil;
    List<AllcarItem> semuamycarlist = new ArrayList<>();
    Context mContext;
    SemuaMobilAdapter semuaMobilAdapter;
    BaseApiService mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_semuamobil);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pilih Type Kendaraan");

        mApiService         = UtilsApi.getAPIService();
        rvsemuaMobil        = findViewById(R.id.rvsemuaMobil);
        mContext            = this;
        semuaMobilAdapter   = new SemuaMobilAdapter(mContext, semuamycarlist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvsemuaMobil.setLayoutManager(mLayoutManager);

        getSemuacar();
    }

    private void getSemuacar() {


        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        mApiService.getAllcar().enqueue(new Callback<ResponseAllCar>() {

            @Override
            public void onResponse(Call<ResponseAllCar> call, Response<ResponseAllCar> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    final List<AllcarItem> MobilItem = response.body().getAllcar();

                    rvsemuaMobil.setAdapter(new SemuaMobilAdapter(mContext, MobilItem));
                    semuaMobilAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mobil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAllCar> call, Throwable t) {

                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
