package com.skripsi.adeahmadfaisal.mycar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.BookingService.ActBookingService;
import com.skripsi.adeahmadfaisal.BookingService.edit_BookingService;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editMycar extends AppCompatActivity {

    BaseApiService mApiService;
    TextView    etypemobil;
    TextView    enoplat;
    TextView    enobody;
    TextView    enorangka;
    Button      eBtnSimpan;
    Button      eBtnDelete;
    String      Carid;
    Context     mContext;
    String      uid1;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mycar);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Kendaraan");

    sharedPrefManager = new SharedPrefManager(this);
    etypemobil  = findViewById(R.id.editTypemobil);
    enoplat     = findViewById(R.id.editNoplat);
    enobody     = findViewById(R.id.editNobody);
    enorangka   = findViewById(R.id.editNorangka);
    eBtnSimpan  = findViewById(R.id.eBtnSimpan);
    eBtnDelete  = findViewById(R.id.eBtnDelete);
    uid1        = sharedPrefManager.getSPUid();
    mApiService = UtilsApi.getAPIService();

        if (getIntent().getExtras() != null) {
            Bundle  bundle  = new Bundle();
            etypemobil.setText(getIntent().getStringExtra("Typecar"));
            enoplat.setText(getIntent().getStringExtra("Noplat"));
            enobody.setText(getIntent().getStringExtra("Nobody"));
            enorangka.setText(getIntent().getStringExtra("Nomechine"));
            Carid   = getIntent().getStringExtra("Carid");
        }
        else {
            System.out.println("Gagal");
           }

        eBtnSimpan.setOnClickListener(new View.OnClickListener() {
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
                    requestUpdate();
                }

            }
        });

        eBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption();
                diaBox.show();

            }
        });




    }

    private void requestUpdate() {


        mApiService.registEditMycar(Carid,
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
                                    Toast.makeText(mContext, "Berhasil Mengupdate Mobil ", Toast.LENGTH_SHORT).show();
                                    finish();
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



    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_forever_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        mApiService.deletemycarRequest(Carid).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    loading.dismiss();

                                    Toast.makeText(editMycar.this, "Berhasil mengapus Booking Service "+Carid, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, ActMycar.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                } else {
                                    loading.dismiss();
                                    Toast.makeText(mContext, "Gagal menghapus Booking Service", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(mContext, "koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }


}
