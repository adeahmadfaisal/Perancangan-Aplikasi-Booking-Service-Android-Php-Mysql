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
import com.skripsi.adeahmadfaisal.model.mycar.AcmycarItem;
import com.skripsi.adeahmadfaisal.model.pilihMobil.PilihmobilItem;
import com.skripsi.adeahmadfaisal.mycar.editMycar;
import com.skripsi.adeahmadfaisal.mycar.formMycar;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ActmycarAdapter extends RecyclerView.Adapter<ActmycarAdapter.MobilHolder> {
    
    List<AcmycarItem> pilihmobilItemList;
    Context mContext;

    public ActmycarAdapter(Context context, List<AcmycarItem> daftarMobil) {

        this.mContext           =   context;
        pilihmobilItemList      = daftarMobil;
    }

    public class MobilHolder extends RecyclerView.ViewHolder{

        public TextView myTypemobil;
        public TextView myPlat;
        public TextView tvnomesin;
        public TextView tvCustomer;
        public CardView cv_mycar;


        public MobilHolder(View itemView) {
            super(itemView);
            myTypemobil  = itemView.findViewById(R.id.myTypemobil);
            myPlat       = itemView.findViewById(R.id.myPlat);
            tvnomesin    = itemView.findViewById(R.id.tvnomesin);
            cv_mycar       = itemView.findViewById(R.id.cv_mycar);
        }
    }


    @Override
    public MobilHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemmycar, parent, false);

        return new MobilHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobilHolder holder, int position) {


       final AcmycarItem semuamobilitem     =   pilihmobilItemList.get(position);

       holder.myTypemobil.setText(semuamobilitem.getTypecar());
       holder.myPlat.setText(semuamobilitem.getNoPlat());
       holder.tvnomesin.setText((semuamobilitem.getNoMachine()));

       final String mycarid     = semuamobilitem.getMycarid();
       final String typecar     = semuamobilitem.getTypecar();
       final String noplat      = semuamobilitem.getNoPlat();
       final String nobody      = semuamobilitem.getNoBody();
       final String nomechine   = semuamobilitem.getNoMachine();
       //  final String customerid  = semuamobilitem.getCutomerId();

        holder.cv_mycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Intent intent    =   new Intent(mContext, editMycar.class);
                bundle.putString("Carid",mycarid);
                bundle.putString("Typecar",typecar);
                bundle.putString("Noplat",noplat);
                bundle.putString("Nobody",nobody);
                bundle.putString("Nomechine",nomechine);
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
