package com.skripsi.adeahmadfaisal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.skripsi.adeahmadfaisal.BookingService.form_BookingService;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.model.pilihMobil.PilihmobilItem;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PilihMobilAdapter extends RecyclerView.Adapter<PilihMobilAdapter.MobilHolder> {
    //List<Booking> semuabookingItemList;
    List<PilihmobilItem> pilihmobilItemList;
    Context mContext;


    public PilihMobilAdapter(Context context,  List<PilihmobilItem> daftarMobil) {

        this.mContext           =   context;
        pilihmobilItemList      = daftarMobil;


    }

    public class MobilHolder extends RecyclerView.ViewHolder{

        public TextView tvTypeMobil;
        public TextView tvNoPlat;
        public TextView tvIdCar;
        public TextView tvCustomer;
        public TextView nomachine;
        public CardView cvmain;


        public MobilHolder(View itemView) {
            super(itemView);
            tvTypeMobil  = itemView.findViewById(R.id.tvTypemobil);
            tvNoPlat     = itemView.findViewById(R.id.tvPlat);
            tvIdCar      = itemView.findViewById(R.id.tvidcar);
            tvCustomer   = itemView.findViewById(R.id.tvcustomer);
            cvmain       = itemView.findViewById(R.id.cv_main);
            nomachine    = itemView.findViewById(R.id.tnomesin);
        }
    }


    @Override
    public MobilHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempilihmobil, parent, false);

        return new MobilHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobilHolder holder, int position) {


       final PilihmobilItem semuamobilitem     =   pilihmobilItemList.get(position);

       holder.tvTypeMobil.setText(semuamobilitem.getTypeCar());
       holder.tvNoPlat.setText(semuamobilitem.getNoPlat());
       holder.tvIdCar.setText((semuamobilitem.getMycarid()));
       holder.tvCustomer.setText(semuamobilitem.getCutomerId());
        holder.nomachine.setText(semuamobilitem.getNoMachine());

       final String mycarid     = semuamobilitem.getMycarid();
       final String typecar     = semuamobilitem.getTypeCar();
       //  final String customerid  = semuamobilitem.getCutomerId();


       holder.cvmain.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // Snackbar.make( v, "Anda Memilih Mobil "+typecar, Snackbar.LENGTH_LONG).show();
               Bundle bundle = new Bundle();
               Intent intent    =   new Intent(mContext, form_BookingService.class);
               bundle.putString("Carid",mycarid);
               bundle.putString("typecar",typecar);
               intent.putExtras(bundle);
               mContext.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return pilihmobilItemList.size();
    }
}
