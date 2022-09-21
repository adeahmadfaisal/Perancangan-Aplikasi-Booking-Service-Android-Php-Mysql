package com.skripsi.adeahmadfaisal.BookingService;

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
import android.text.TextUtils;
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
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class form_BookingService extends AppCompatActivity {

    BaseApiService      mApiService;
    SharedPrefManager   sharedPrefManager;
    Context             mContext;

    TextView            etNamaMatkul;
    TextView            tvestimasi;
    TextView            etKeluhan;
    TextView            etdate;
    TextView            tvTypecar;
    TextView            idmycar;
    Button              btnSimpanMatkul;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__booking_service);
        Toolbar toolbar = findViewById(R.id.settoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking Service");

        sharedPrefManager = new SharedPrefManager(this);
        mContext        = this;
        mApiService     = UtilsApi.getAPIService();

       // spinnerDosen    = findViewById(R.id.spinner);
        sTypeservice    = findViewById(R.id.etTypeService);
        Jamservice      = findViewById(R.id.etTime);
        etKeluhan       = findViewById(R.id.etKeluhan);
        btnSimpanMatkul = findViewById(R.id.btnSimpanMatkul);
        datepicker      = findViewById(R.id.bt_datepicker);
        etdate          = findViewById(R.id.etDate);
        tvTypecar       = findViewById(R.id.tvTypeCar);
        idmycar         = findViewById(R.id.idmycar);
        dateFormatter   = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        uid1            = sharedPrefManager.getSPUid();
        tvestimasi      = findViewById(R.id.tvestimasi);

        tvTypecar.setEnabled(false);
        tvTypecar.setFocusable(false);
        etdate.setEnabled(false);
        etdate.setFocusable(false);



        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });

        if (getIntent().getExtras() != null) {
            Bundle  bundle  = new Bundle();
            tvTypecar.setText(getIntent().getStringExtra("typecar"));
            idmycar.setText(getIntent().getStringExtra("Carid"));
            idmobil         = (String) idmycar.getText();
        }
        else {
            System.out.println("Gagal");
            String hasil = (String) idmycar.getText();
            Toast.makeText(mContext, "Gagal Mengambil data"+hasil , Toast.LENGTH_SHORT).show();
        }



        String[]    jam     = {"08:00","09:00","10:00","11:00","13:00","14:00","15:00","16:00"};
        ArrayAdapter<String> itemjam    = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jam);
        itemjam.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item );
        Jamservice.setAdapter(itemjam);
        Jamservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jamservicecust = parent.getItemAtPosition(position).toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss");
                try {
                    Date jamservice = dateFormat.parse(jamservicecust);
                    Log.e("Time", String.valueOf(jamservice));
                } catch (ParseException e) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnertypeservice();
    //    initSpinnerDosen();
    //    hasilspinnerDosen();


        btnSimpanMatkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etdate.length()== 0) {
                    etdate.setError("Tidak Boleh Kosong");
                } else if (etKeluhan.length()==0) {
                    etKeluhan.setError("Tidak Boleh Kosong");
                } else {
                    etdate.setError(null);
                    etKeluhan.setError(null);
                    requestSimpan();

                }


            }
        });
    }

    private void requestSimpan() {

            String status = "Pending";
            mApiService.registBooking(uid1, idmobil,
                    tanggalbooking,
                    jamservicecust,
                    typeservice, etKeluhan.getText().toString(), status)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                            if (response.isSuccessful()) {
                                Log.i("debug", "onResponse: BERHASIL");
                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("error").equals("false")) {
                                        Toast.makeText(mContext, "BERHASIL MENDAFTAR", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(mContext, ActBookingService.class));

                                    } else {
                                        String error_message = jsonRESULTS.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.i("debug", "onResponse: GA BERHASIL");

                            }

                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                        }
            });



    }

    private void spinnertypeservice() {
        String[]    item    = {"Berkala","1.000 KM","10.000 KM", "20.000 KM", "Lainnya"};
        ArrayAdapter<String> itemservice    = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, item);
        itemservice.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item );
        sTypeservice.setAdapter(itemservice);
        sTypeservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeservice = parent.getItemAtPosition(position).toString();
                switch (typeservice) {
                    case "1.000 KM":
                        tvestimasi.setVisibility(View.VISIBLE);
                        tvestimasi.setText("Estimasi Biaya: Rp. 1.000.000");
                        break;
                    case "10.000 KM":
                        tvestimasi.setVisibility(View.VISIBLE);
                        tvestimasi.setText("Estimasi Biaya: Rp. 5.000.000");
                        break;
                    case "20.000 KM":
                        tvestimasi.setVisibility(View.VISIBLE);
                        tvestimasi.setText("Estimasi Biaya: Rp. 10.000.000");
                        break;
                    default: tvestimasi.setVisibility(View.GONE);
                        break;
                }
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
                etdate.setText(tanggalbooking);
               // System.out.println(tanggalbooking);
               // System.out.println(getCurrentDate());

              //  etdate.setEnabled(false);
                try {
                    tanggalsekarang         =   dateFormatter.parse(getCurrentDate());
                    validasitanggalbooking  =   dateFormatter.parse(tanggalbooking);
                    if (validasitanggalbooking.before(tanggalsekarang)) {
                        etdate.setError("Pilih Tanggal Setelah Tanggal Sekarang");
                        Toast.makeText(mContext, "Tanggal Booking Telah Berlalu, Silahkan Pilih Tanggal Berikutnya", Toast.LENGTH_SHORT).show();

                    } else {
                        etdate.setError(null);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }

   /* private void hasilspinnerDosen() {

         spinnerDosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedName = parent.getItemAtPosition(position).toString();
              //   switch (selectedName) {
               //      case "Honda Civic Turbo":
                 //        idmobil = "CVC00";
                 //        break;
                 //    case "Honda Jazz RS":
                 //        idmobil = "Jazz";
                 //      break;
                 //  case "Honda Mobilio":
                 //      idmobil = "Mobil";
                 //      break;
                 //  case "Honda Jazz":
                 //      idmobil = "Jazz";
                 //      break;
                 //  case "Honda HR-V":
                 //      idmobil = "Hrv";
                 //      break;
                 //  case "Honda Accord":
                 //      idmobil = "Accor";
                 //      break;
                 //  case "Honda BR-V":
                 //      idmobil = "Brv";
                 //      break;
                 //  case "Honda CR-V":
                 //      idmobil = "Crv";
                 //      break;
                 //  case "Honda City":
                 //idmobil = "City";
                 //      break;
                 //  case "Honda Brio":
                 //      idmobil = "Brio";
                 //      break;
                 //  case "Honda Odyssey":
                 //      idmobil = "Odyss";
                 //      break;
                 //  default:
                 //      idmobil = "";
                 //      break;
                 //   }

                 //Toast.makeText(mContext, "Kamu memilih dosen " + idmobil, Toast.LENGTH_SHORT).show();
                 // String idcar = semuadosenItems.get(i).getCarid();
                 // etNamaMatkul.setText(idcar);

             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

     }

    private void initSpinnerDosen() {

         loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);
         mApiService.getMycar(uid1).enqueue(new Callback<ResponseMobil>() {
             @Override
             public void onResponse(Call<ResponseMobil> call, Response<ResponseMobil> response) {
                 if (response.isSuccessful()) {
                     loading.dismiss();
                     List<MycarItem> semuadosenItems = response.body().getMycar();
                     List<String> listSpinner   = new ArrayList<>();
                     listSpinner.add("12345678");
                     for (int i = 0; i < semuadosenItems.size(); i++){
                         listSpinner.add(semuadosenItems.get(i).getMycarid());

                     }
                     // Set hasil result json ke dalam adapter spinner
                     ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                             android.R.layout.simple_spinner_item, listSpinner);
                     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                     spinnerDosen.setAdapter(adapter);
                 } else {
                     loading.dismiss();
                     Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ResponseMobil> call, Throwable t) {
                 loading.dismiss();
                 Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
             }
         });

     } */

    private String getCurrentDate() {
        Date current = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateString = frmt.format(current);
        return dateString;
    }

 }

