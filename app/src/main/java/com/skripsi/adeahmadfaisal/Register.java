package com.skripsi.adeahmadfaisal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    EditText etNama;
    EditText etEmail;
    EditText etPassword;
    EditText ettelepon;
    EditText matchpassword;
    Button bregister;
    Button blogin;

    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrasi");


        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();


    }

    private void initComponents() {


        etNama = findViewById(R.id.txt_username);
        etEmail =  findViewById(R.id.txtemail);
        etPassword =  findViewById(R.id.txt_password);
        ettelepon  = findViewById(R.id.phonenumber);
        matchpassword = findViewById(R.id.txt_confirm_password);
        bregister = findViewById(R.id.btnRegister);
        blogin = findViewById(R.id.btn_login);


        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmail.getText().toString().length()==0){
                    etEmail.setError("Field email tidak boleh kosong");

                }else if (etNama.getText().toString().length()==0){
                    etNama.setError("Field nama tidak boleh kosong");

                }else if (etPassword.getText().toString().length()==0){
                    etPassword.setError("Field password tidak boleh kosong");

                }else if (ettelepon.getText().toString().length()==0){
                    ettelepon.setError("Field telepon tidak boleh kosong");


                } else {
                    String password = etPassword.getText().toString();
                    if (matchpassword.getText().toString().equals(password)) {
                        matchpassword.setBackgroundResource(R.color.colorPrimary);
                        matchpassword.setError("Yups, password sama");
                        loading = ProgressDialog.show(mContext, null, "Harap Tunggu ...", true, false);

                        requestRegister();
                    } else {
                        matchpassword.setError("Password tidak sama");
                    }

                }
            }
        });

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }


    private void requestRegister(){
        mApiService.registRequest(etNama.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                ettelepon.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, LoginActivity.class));
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