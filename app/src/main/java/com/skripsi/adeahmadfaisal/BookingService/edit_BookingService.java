package com.skripsi.adeahmadfaisal.BookingService;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.skripsi.adeahmadfaisal.MainActivity;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.mycar.ActMycar;
import com.skripsi.adeahmadfaisal.mycar.editMycar;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class edit_BookingService extends AppCompatActivity {
    private CoordinatorLayout constraintLayout;
    BaseApiService      mApiService;
    SharedPrefManager   sharedPrefManager;
    Context             mContext;

    TextView            edNamaMatkul;
    TextView            edKeluhan;
    TextView            eddate;
    TextView            edTypecar;
    TextView            edmycar;
    Button              btnupdate;
    Button              btndelete;
    Spinner             spinnerDosen;
    Spinner             sTypeservice;
    Spinner             Jamservice;
    ProgressDialog      loading;

    DatePickerDialog    datePickerDialog;
    SimpleDateFormat    dateFormatter;
    SimpleDateFormat    timeFormatter;

    ImageButton         datepicker;
    Date                tanggalsekarang;
    Date                validasitanggalbooking;
    Date                jamservice;

    String              typeservice;
    String              jamservicecust;
    String              selectedName;
    String              idmobil;
    String              tanggalbooking;
    String              uid1;
    String              status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__booking_service);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking Service");

        sharedPrefManager = new SharedPrefManager(this);
        mContext        = this;
        mApiService     = UtilsApi.getAPIService();



        sTypeservice    = findViewById(R.id.edTypeService);
        Jamservice      = findViewById(R.id.edTime);
        edKeluhan       = findViewById(R.id.edKeluhan);
        btnupdate    = findViewById(R.id.btnupdate);
        datepicker      = findViewById(R.id.bt_datepicker);
        eddate          = findViewById(R.id.edDate);
        edTypecar       = findViewById(R.id.edTypeCar);
        edmycar         = findViewById(R.id.edmycar);
        btndelete       = findViewById(R.id.btndelete);
        dateFormatter   = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        uid1            = sharedPrefManager.getSPUid();
        constraintLayout = findViewById(R.id.Coordinatoract);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });

        if (getIntent().getExtras() != null) {
            Bundle  bundle  = new Bundle();
            edTypecar.setText(getIntent().getStringExtra("Typecar"));
            edmycar.setText(getIntent().getStringExtra("Idbooking"));
            eddate.setText(getIntent().getStringExtra("Date"));
            sTypeservice.setSelected(Boolean.parseBoolean(getIntent().getStringExtra("Typeservice")));
            edKeluhan.setText(getIntent().getStringExtra("Problem"));
            status = getIntent().getStringExtra("Status");

            idmobil         = (String) edmycar.getText();

            } else {
            System.out.println("Gagal");
        }


        String[]    jam     = {"08:00","09:00","10:00","11:00","13:00","14:00","15:00","16:00"};
        ArrayAdapter<String> itemjam    = new ArrayAdapter<>(this, R.layout.fontspinner, jam);
        itemjam.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item );
        Jamservice.setAdapter(itemjam);
        Jamservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jamservicecust = parent.getItemAtPosition(position).toString();
                SimpleDateFormat dateFormat  = new SimpleDateFormat("hh:mm");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
                try {
                    jamservice = dateFormat.parse(jamservicecust);
                    //System.out.println("Format tanpa second "+ jamservice);
                    //System.out.println("Format tanpa second "+ jamservicecust);

                } catch (ParseException e) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnertypeservice();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (eddate.getText().toString() == "") {
                    eddate.setError("Tidak boleh Kosong");
                    btnupdate.setEnabled(false);
                } else
                {
                    eddate.setError(null);
                    requestUpdate();

                }
                   // eddate.setText(tanggalbooking);
                  //  System.out.println("Tanggal "+eddate.getText().toString());

            }
        });

        if (status.equals("Approve")) {
            btndelete.setBackgroundResource(R.drawable.button_disable);
            btnupdate.setBackgroundResource(R.drawable.button_disable);
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Jika status sudah Approve, tidak bisa menghapus booking service. Silahkan Hubungi Customer Service", Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
            btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Jika status sudah Approve, tidak bisa mengupdate booking service. Silahkan Hubungi Customer Service", Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
        } else if (status.equals("Pending")) {
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }
            });
        } else if (status.equals("Finish")){
            btndelete.setBackgroundResource(R.drawable.button_disable);
            btnupdate.setBackgroundResource(R.drawable.button_disable);
            btndelete.setEnabled(false);
            btnupdate.setEnabled(false);

        } else {
            btnupdate.setBackgroundResource(R.drawable.button_disable);
            btnupdate.setEnabled(false);
            btndelete.setEnabled(true);
            btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }
            });
        }


    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_delete_forever_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                        mApiService.deletebookingRequest(idmobil).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    loading.dismiss();

                                    Toast.makeText(edit_BookingService.this, "Berhasil mengapus Booking Service "+idmobil, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(mContext, ActBookingService.class)
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


    private void spinnertypeservice() {
        String[]    item    = {"Berkala","1.000","6.000 KM","10.000 KM"};
        ArrayAdapter<String> itemservice    = new ArrayAdapter<>(this, R.layout.fontspinner, item);
        itemservice.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item );
        sTypeservice.setAdapter(itemservice);
        sTypeservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeservice = parent.getItemAtPosition(position).toString();



               // Toast.makeText(mContext, "Kamu memilih Type Service " + typeservice, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                //String tglsekranga = dateFormatter.format(tanggalsekarang);

                //System.out.println(tglsekranga);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tanggalbooking = dateFormatter.format(newDate.getTime());
               // System.out.println(tanggalbooking);
               // System.out.println(getCurrentDate());

              // eddate.setEnabled(false);
                try {
                    tanggalsekarang         =   dateFormatter.parse(getCurrentDate());
                    validasitanggalbooking  =   dateFormatter.parse(tanggalbooking);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (validasitanggalbooking.before(tanggalsekarang)){
                    Toast.makeText(mContext, "Tanggal Booking Telah Berlalu, Silahkan Pilih Tanggal Berikutnya" , Toast.LENGTH_SHORT).show();
                    eddate.setError("Silahkan Pilih Tanggal Berikutnya");
                        System.out.println(validasitanggalbooking +" dan " +tanggalsekarang);
               btnupdate.setEnabled(false);

                } else {
                   eddate.setText(dateFormatter.format(newDate.getTime()));
                   eddate.setError(null);
                   btnupdate.setEnabled(true);
                }

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }

    private String getCurrentDate() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateString = frmt.format(current);
        return dateString;
    }

    private void requestUpdate() {

        loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
        mApiService.registEditBooking(idmobil,
                eddate.getText().toString(),
                jamservicecust,
                typeservice, edKeluhan.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                        if (response.isSuccessful()) {
                            loading.dismiss();

                            Toast.makeText(edit_BookingService.this, "Berhasil Mengupdate Booking Service "+idmobil+
                                    tanggalbooking+
                                    jamservicecust+
                                    typeservice+ edKeluhan.getText().toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(edit_BookingService.this, ActBookingService.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(edit_BookingService.this, "Tidak Berhasil Mengupdate Booking Service "+idmobil, Toast.LENGTH_SHORT).show();
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

