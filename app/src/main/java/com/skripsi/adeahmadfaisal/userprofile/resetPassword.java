package com.skripsi.adeahmadfaisal.userprofile;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.LoginActivity;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class resetPassword extends AppCompatActivity {
    TextView email;
    TextView pass;
    TextView repass;
    Button   btnreset;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.setreset);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");

        email   = findViewById(R.id.email);
        pass    = findViewById(R.id.password);
        repass  = findViewById(R.id.repassword);
        btnreset= findViewById(R.id.btnreset);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        repass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (pass.getText().toString().equals(repass.getText().toString())) {
                    repass.setError(null);
                } else {
                    repass.setError("Password tidak cocok");

                }
                return false;
            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())) {
                    email.setError("Tidak boleh kosong!");
                } else if (TextUtils.isEmpty(pass.getText())) {
                    pass.setError("Tidak boleh kosong!");
                } else if (TextUtils.isEmpty(repass.getText())) {
                    repass.setError("Tidak boleh kosong!");
                } else {
                    email.setError(null);
                    pass.setError(null);
                    repass.setError(null);
                    resetpassword();
                }

            }
        });


    }

    private void resetpassword() {

        mApiService.resetpassword(email.getText().toString(),
                pass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    Toast.makeText(mContext, "Berhasil Reset Password", Toast.LENGTH_SHORT).show();
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
