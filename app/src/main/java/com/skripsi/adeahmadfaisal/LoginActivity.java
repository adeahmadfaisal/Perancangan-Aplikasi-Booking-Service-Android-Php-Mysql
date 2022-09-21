package com.skripsi.adeahmadfaisal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;
import com.skripsi.adeahmadfaisal.sparepart.ActSparepart;
import com.skripsi.adeahmadfaisal.userprofile.resetPassword;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail;
    EditText getnama;
    EditText etPassword;
    TextView reset;
    Button btnLogin;
    Button btnRegister;
    ProgressDialog loading;


    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPrefManager = new SharedPrefManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");

        mContext = this;
            mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
            iniComponents();
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, resetPassword.class);
                    startActivity(intent);
                }
            });


        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }

    }

    private void iniComponents() {

    etEmail     = findViewById(R.id.etEmail);
    etPassword  = findViewById(R.id.etPassword);
    reset       = findViewById(R.id.reset);
    btnLogin    =  findViewById(R.id.btnLogin);
    btnRegister = findViewById(R.id.btnRegister);




    btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (etEmail.length() == 0) {
                etEmail.setError("Email tidak boleh kosong");
            } else if (etPassword.length() == 0){
                etPassword.setError("Password tidak boleh kosong");

            }
             else {
                etEmail.setError(null);
                etPassword.setError(null);

                loading = ProgressDialog.show(mContext, null, "Harap Tunggu ...",
                        true,
                        false);
                mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    loading.dismiss();
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        if (jsonRESULTS.getString("error").equals("false")){
                                            // Jika login berhasil maka data nama yang ada di response API
                                            // akan diparsing ke activity selanjutnya.

                                            Toast.makeText(mContext, "BERHASIL LOGIN", Toast.LENGTH_SHORT).show();
                                            String email    = jsonRESULTS.getJSONObject("user").getString("email");
                                            String nama     = jsonRESULTS.getJSONObject("user").getString("nama");
                                            String uid      = jsonRESULTS.getString("uid");

                                            sharedPrefManager.saveSPString(SharedPrefManager.SP_Nama, nama);
                                            sharedPrefManager.saveSPString(SharedPrefManager.SP_Email, email);
                                            sharedPrefManager.saveSPString(SharedPrefManager.SP_Uid, uid);

                                            // String nama1 = sharedPrefManager.getSPNama();
                                            // String email1 = sharedPrefManager.getSPEmail();
                                            // String uid1 = sharedPrefManager.getSPUid();

                                            // Toast.makeText(mContext, uid1, Toast.LENGTH_SHORT).show();

                                            // Shared Pref ini berfungsi untuk menjadi trigger session login
                                            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SudahLogin, true);
                                            startActivity(new Intent(mContext, MainActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        } else {
                                            // Jika login gagal
                                            String error_message = jsonRESULTS.getString("error_msg");
                                            //  Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                            Snackbar snackbar = Snackbar
                                                    .make(v, error_message, Snackbar.LENGTH_LONG);

                                            snackbar.show();
                                        }
                                    } catch (JSONException | IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    loading.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.toString());
                                loading.dismiss();
                                Snackbar snackbar = Snackbar
                                        .make(v, "Koneksi terputus. Periksa koneksi internet snda", Snackbar.LENGTH_LONG);

                                snackbar.show();

                            }
                        });
            }


        }
    });

    btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(mContext, Register.class));
        }
    });



    }



}



