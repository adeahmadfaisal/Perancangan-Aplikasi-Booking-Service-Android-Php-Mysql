package com.skripsi.adeahmadfaisal.BookingService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.adapter.BookingAdapter;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.model.Booking;
import com.skripsi.adeahmadfaisal.model.ResponseBooking;
import com.skripsi.adeahmadfaisal.sharedPrefManager.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActBookingService extends AppCompatActivity {

    RecyclerView rvView;
    List<Booking> semuabookinglist = new ArrayList<>();
    Context mContext;
    BookingAdapter bookingadapter;
    BaseApiService mApiService;
    ProgressDialog loading;
    SharedPrefManager sharedPrefManager;
    ImageButton imgdelete;
    TextView msgerror;
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPrefManager = new SharedPrefManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_booking_service);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking Service");

        mApiService         = UtilsApi.getAPIService();
        rvView              = findViewById(R.id.rvlistbooking);
        mContext            = this;
        bookingadapter      = new BookingAdapter(mContext, semuabookinglist);
        logo                = findViewById(R.id.BOhonda);
        msgerror            = findViewById(R.id.BOerrormsg);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvView.setLayoutManager(mLayoutManager);
        rvView.setItemAnimator(new DefaultItemAnimator());

        getResultBooking();
    }

    private void getResultBooking() {

        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);

        String uid1 = sharedPrefManager.getSPUid();
        mApiService.getBooking(uid1).enqueue(new Callback<ResponseBooking>() {

            @Override
            public void onResponse(Call<ResponseBooking> call, Response<ResponseBooking> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    if (error == false) {

                        final List<Booking> BookingItem = response.body().getBooking();
                        rvView.setAdapter(new BookingAdapter(mContext, BookingItem));
                        bookingadapter.notifyDataSetChanged();
                        msgerror.setVisibility(View.GONE);
                        logo.setVisibility(View.GONE);

                        rvView.setVisibility(View.VISIBLE);
                    }

                } else {
                    loading.dismiss();

                    msgerror.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.VISIBLE);
                    rvView.setVisibility(View.GONE);


                }
            }

            @Override
            public void onFailure(Call<ResponseBooking> call, Throwable t) {

                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void buttonFormBooking(View view) {

        Intent intent = new Intent(ActBookingService.this, PilihMobil.class);
        startActivity(intent);

    }
}
