package com.skripsi.adeahmadfaisal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.model.allcar.AllcarItem;
import com.skripsi.adeahmadfaisal.model.mycar.AcmycarItem;
import com.skripsi.adeahmadfaisal.mycar.formMycar;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SemuaMobilAdapter extends RecyclerView.Adapter<SemuaMobilAdapter.MobilHolder> {

    List<AllcarItem> pilihsemuamobilItemList;
    Context mContext;


    public SemuaMobilAdapter(Context context, List<AllcarItem> daftarMobil) {

        this.mContext                =   context;
        pilihsemuamobilItemList      = daftarMobil;


    }

    public class MobilHolder extends RecyclerView.ViewHolder{

        public TextView carTypeMobil;
        public TextView carTypeEngine;
        public TextView carPrice;
        public CardView cv_allcar;


        public MobilHolder(View itemView) {
            super(itemView);
            carTypeMobil  = itemView.findViewById(R.id.carTypeMobil);
            carTypeEngine = itemView.findViewById(R.id.carTypeEngine);
            carPrice      = itemView.findViewById(R.id.carPrice);
            cv_allcar     = itemView.findViewById(R.id.cv_allcar);
        }
    }


    @Override
    public MobilHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsemuamobil, parent, false);

        return new MobilHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobilHolder holder, int position) {


       final AllcarItem semuamobilitem     =   pilihsemuamobilItemList.get(position);
       final String harga     = semuamobilitem.getPrice();
       String tipe  = "Type Mesin :";
       Integer hargaIDR;
       hargaIDR                       = Integer.parseInt(harga);
       Locale localeID = new Locale("in", "ID");
       NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

       holder.carTypeMobil.setText(semuamobilitem.getTypecar());
       holder.carTypeEngine.setText(tipe+semuamobilitem.getTypeEngine());
       holder.carPrice.setText(formatRupiah.format((double)hargaIDR));

       final String mycarid     = semuamobilitem.getCarid();
       final String typecar     = semuamobilitem.getTypecar();
       final String carid       = semuamobilitem.getCarid();

       //  final String customerid  = semuamobilitem.getCutomerId();

        holder.cv_allcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Intent intent    =   new Intent(mContext, formMycar.class);
                bundle.putString("mycarid",mycarid);
                bundle.putString("Typecar",typecar);
                bundle.putString("Carid" ,carid);

                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return pilihsemuamobilItemList.size();
    }
}
