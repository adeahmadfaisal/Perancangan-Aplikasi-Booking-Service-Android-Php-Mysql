package com.skripsi.adeahmadfaisal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.model.sparepart.SparepartItem;
import com.skripsi.adeahmadfaisal.mycar.editMycar;
import com.skripsi.adeahmadfaisal.sparepart.Formsparepart;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SparePartAdapter extends RecyclerView.Adapter<SparePartAdapter.MobilHolder> {
    
    List<SparepartItem> pilihmobilItemList;
    Context mContext;


    public SparePartAdapter(Context context, List<SparepartItem> daftarMobil) {

        this.mContext           =   context;
        pilihmobilItemList      = daftarMobil;


    }

    public class MobilHolder extends RecyclerView.ViewHolder{

        public TextView namasparepart;
        public TextView idsparepart;
        public TextView hargasparepart;
        public CardView cv_sparepart;



        public MobilHolder(View itemView) {
            super(itemView);
            namasparepart     = itemView.findViewById(R.id.namasparepart);
            idsparepart       = itemView.findViewById(R.id.idsparepart);
            hargasparepart    = itemView.findViewById(R.id.hargasparepart);
            cv_sparepart      = itemView.findViewById(R.id.cv_sparepart);

        }
    }


    @Override
    public MobilHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsparepart, parent, false);

        return new MobilHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobilHolder holder, int position) {


       final SparepartItem semuamobilitem     =   pilihmobilItemList.get(position);

       holder.namasparepart.setText(semuamobilitem.getNameSparepart());
       holder.idsparepart.setText(semuamobilitem.getIdSparepart());


       Integer hargaIDR;
       final String namasparepart     = semuamobilitem.getNameSparepart();
       final String idsparepart       = semuamobilitem.getIdSparepart();
       final String carid             = semuamobilitem.getCarId();
       final String harga             = semuamobilitem.getHarga();
       final String deskripsi         = semuamobilitem.getDeskripsi();
       hargaIDR                       = Integer.parseInt(harga);
       Locale localeID = new Locale("in", "ID");
       NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
       // detailHarga.setText(formatRupiah.format((double)hargarumah));
       holder.hargasparepart.setText(formatRupiah.format((double)hargaIDR));
       final String rupiah = formatRupiah.format((double)hargaIDR);


        holder.cv_sparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Intent intent    =   new Intent(mContext, Formsparepart.class);
                bundle.putString("Namasparepart",namasparepart);
                bundle.putString("Idsparepart",idsparepart);
                bundle.putString("Carid",carid);
                bundle.putString("Harga",rupiah);
                bundle.putString("Deskripsi",deskripsi);
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
