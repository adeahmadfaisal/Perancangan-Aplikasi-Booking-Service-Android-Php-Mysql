package com.skripsi.adeahmadfaisal.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.BookingService.ActBookingService;
import com.skripsi.adeahmadfaisal.BookingService.edit_BookingService;
import com.skripsi.adeahmadfaisal.MainActivity;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.adapter.BookingAdapter;
import com.skripsi.adeahmadfaisal.adapter.SparePartAdapter;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.model.Booking;
import com.skripsi.adeahmadfaisal.model.ResponseBooking;
import com.skripsi.adeahmadfaisal.model.Userprofile.ResponseUser;
import com.skripsi.adeahmadfaisal.model.Userprofile.UserprofileItem;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class userprofile extends AppCompatActivity {
    List<UserprofileItem> userprofile = new ArrayList<UserprofileItem>();

    Context mContext;
    BaseApiService mApiService;
    SharedPrefManager sharedPrefManager;
    String uid;
    TextView nama;
    ProgressDialog      loading;
    TextView edemail;
    TextView edpass;
    TextView edpassconfirm;
    RadioGroup radiogroup;
    RadioButton laki;
    RadioButton perempuan;
    TextView edphone;
    TextView tgllahir;
    TextView alamat;
    Button btnupdate;
    ImageButton datepicker2;
    DatePickerDialog    datePickerDialog;
    SimpleDateFormat dateFormatter;

    String name;
    String email;
    String pass;
    String gender;
    String phone;
    String addres;
    String Birtday;
    String selectedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        Toolbar toolbar = findViewById(R.id.setuserprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Profile");

        mApiService         = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(this);

        mContext        = this;
        uid         = sharedPrefManager.getSPUid();
        nama        = findViewById(R.id.ednama);
        edemail     = findViewById(R.id.edemail);
        edpass      = findViewById(R.id.edpass);
        edpassconfirm = findViewById(R.id.edpassconfirm);
        radiogroup  = findViewById(R.id.radiogroup);
        laki        = findViewById(R.id.laki);
        perempuan   = findViewById(R.id.perempuan);
        edphone     = findViewById(R.id.edphone);
        tgllahir    = findViewById(R.id.tgllahir);
        alamat      = findViewById(R.id.alamat);
        btnupdate   = findViewById(R.id.btnupdate);
        datepicker2 = findViewById(R.id.bt_datepicker2);
        dateFormatter   = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        getResultUser();

        datepicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (radiogroup.getCheckedRadioButtonId())
                {
                    case R.id.laki:
                        selectedValue = "L";

                        break;
                    case R.id.perempuan:
                        selectedValue = "P";

                        break;
                }
                String password = edpass.getText().toString();
                if (edpassconfirm.getText().toString().equals(password)) {
                    edpassconfirm.setBackgroundResource(R.color.colorPrimary);
                    edpassconfirm.setError(null);
                    requestUpdate();
                } else {
                    edpassconfirm.setError("Password tidak sama");
                }

            }
        });

    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tgllahir.setText(dateFormatter.format(newDate.getTime()));



                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }

    private void getResultUser() {

        mApiService.getUserprofile(uid).enqueue(new Callback<ResponseUser>() {

            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()){


                    final List<UserprofileItem> profile = response.body().getUserprofile();
                    for (UserprofileItem profilitem: profile) {
                        name    = profilitem.getName();
                        email   = profilitem.getEmail();
                        pass    = profilitem.getPassword();
                        gender  = profilitem.getGender();
                        phone   = profilitem.getPhone();
                        addres  = profilitem.getAddres();
                        Birtday = profilitem.getBirthday();


                        nama.setText(name);
                        edemail.setText(email);
                        edpass.setText(pass);
                        edpassconfirm.setText(pass);
                        edphone.setText(phone);
                        tgllahir.setText(Birtday);
                        alamat.setText(addres);


                        if (gender.equals("L")) {
                            radiogroup.check(R.id.laki);
                            System.out.println("Woooooooy");
                        } else if (gender.equals("R")) {
                            radiogroup.check(R.id.perempuan);
                        } else {
                            System.out.println("Tidak ada kelamin");
                        }





                        System.out.println(name+"  "+email+" "+pass+" "+gender+" "+phone+" "+addres+" "+Birtday);


                    }



                } else {

                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

                t.printStackTrace();

                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void requestUpdate() {

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        mApiService.registUserProfile(uid,
                nama.getText().toString(),
                edemail.getText().toString(),
                edpass.getText().toString(),
                selectedValue,
                edphone.getText().toString(),
                alamat.getText().toString(),
                tgllahir.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        if (response.isSuccessful()) {
                            loading.dismiss();

                            Toast.makeText(userprofile.this, "Berhasil Mengupdate Profile", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(userprofile.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(userprofile.this, "Tidak Berhasil Mengupdate Profile", Toast.LENGTH_SHORT).show();
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
