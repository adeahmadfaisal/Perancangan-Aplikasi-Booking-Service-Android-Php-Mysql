package com.skripsi.adeahmadfaisal.mycar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.BookingService.ActBookingService;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class formMycar extends AppCompatActivity {

    BaseApiService mApiService;
    TextView    etypemobil;
    TextView    enoplat;
    TextView    enobody;
    TextView    enorangka;
    Button      BtnSimpan;
    String      Carid;
    Context     mContext;
    String      uid1;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mycar);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Kendaraan");

    sharedPrefManager = new SharedPrefManager(this);
    etypemobil  = findViewById(R.id.etypemobil);
    enoplat     = findViewById(R.id.enoplat);
    enobody     = findViewById(R.id.enobody);
    enorangka   = findViewById(R.id.enorangka);
    BtnSimpan   = findViewById(R.id.BtnSimpan);
    uid1        = sharedPrefManager.getSPUid();
    mApiService     = UtilsApi.getAPIService();

        if (getIntent().getExtras() != null) {
            Bundle  bundle  = new Bundle();
            etypemobil.setText(getIntent().getStringExtra("Typecar"));
            String Type = getIntent().getStringExtra("Typecar");
            Carid   = getIntent().getStringExtra("Carid");
        }
        else {
            System.out.println("Gagal");
           }

        BtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enoplat.getText().toString().length() == 0) {
                    enoplat.setError("Field tidak boleh kosong!");
                } else if (enobody.getText().toString().length() == 0){
                    enoplat.setError("Field tidak boleh kosong!");
                }else if (enorangka.getText().toString().length() == 0){
                    enoplat.setError("Field tidak boleh kosong!");
                }else {
                    enoplat.setError(null);
                    enobody.setError(null);
                    enorangka.setError(null);
                    requestSimpan();
                }

            }
        });


    }

    private void requestSimpan() {


        mApiService.registMycar(Carid,
                uid1,
                enoplat.getText().toString(),
                enobody.getText().toString(),
                enorangka.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        if (response.isSuccessful()) {
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")) {
                                    Toast.makeText(mContext, "Berhasil Menambahkan Mobil Baru", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, ActMycar.class));
                                } else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }

                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
