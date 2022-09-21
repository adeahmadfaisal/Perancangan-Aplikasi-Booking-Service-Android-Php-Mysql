package com.skripsi.adeahmadfaisal.BookingService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.MainActivity;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.adapter.BookingAdapter;
import com.skripsi.adeahmadfaisal.adapter.PilihMobilAdapter;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.model.pilihMobil.PilihmobilItem;
import com.skripsi.adeahmadfaisal.model.pilihMobil.ResponsePilihmobil;
import com.skripsi.adeahmadfaisal.mycar.ActSemuamobil;
import com.skripsi.adeahmadfaisal.mycar.formMycar;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;
import com.skripsi.adeahmadfaisal.sparepart.ActSparepart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PilihMobil extends AppCompatActivity {

    RecyclerView rvViewmobil;
    List<PilihmobilItem> semuamobillist = new ArrayList<>();
    Context mContext;
    PilihMobilAdapter pilihmobiladapter;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    TextView errormsg;
    ImageView logo;
    Button resgitermobil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPrefManager = new SharedPrefManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_mobil);

            Toolbar toolbar = findViewById(R.id.settopilihmobil);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Pilih Mobil");

        mApiService         = UtilsApi.getAPIService();
        rvViewmobil         = findViewById(R.id.rv_daftarMobil);
        mContext            = this;
        pilihmobiladapter   = new PilihMobilAdapter(mContext, semuamobillist);
        errormsg            = findViewById(R.id.errormsg);
        logo                = findViewById(R.id.logohonda);
        resgitermobil       = findViewById(R.id.registermobil);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvViewmobil.setLayoutManager(mLayoutManager);

        getResultMobil();

        resgitermobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PilihMobil.this, ActSemuamobil.class);
                startActivity(intent);
            }
        });



    }

    private void getResultMobil() {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        String uid1 = sharedPrefManager.getSPUid();
        mApiService.getPilihmobil(uid1).enqueue(new Callback<ResponsePilihmobil>() {

            @Override
            public void onResponse(Call<ResponsePilihmobil> call, Response<ResponsePilihmobil> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    Boolean error = response.body().isError();
                        if (error == false) {
                            final List<PilihmobilItem> MobilItem = response.body().getPilihmobil();

                            rvViewmobil.setAdapter(new PilihMobilAdapter(mContext, MobilItem));
                            pilihmobiladapter.notifyDataSetChanged();
                            errormsg.setVisibility(View.GONE);
                            logo.setVisibility(View.GONE);
                            resgitermobil.setVisibility(View.GONE);
                            rvViewmobil.setVisibility(View.VISIBLE);


                        } else {
                            String error_message = response.body().getErrorMsg();
                            errormsg.setVisibility(View.VISIBLE);
                            logo.setVisibility(View.VISIBLE);
                            resgitermobil.setVisibility(View.VISIBLE);
                            rvViewmobil.setVisibility(View.GONE);
                            errormsg.setText(error_message);

                        }

                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mobil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePilihmobil> call, Throwable t) {

                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
