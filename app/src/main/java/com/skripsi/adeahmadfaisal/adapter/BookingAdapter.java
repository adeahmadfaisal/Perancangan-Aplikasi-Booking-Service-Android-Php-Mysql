package com.skripsi.adeahmadfaisal.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.skripsi.adeahmadfaisal.BookingService.edit_BookingService;
import com.skripsi.adeahmadfaisal.BookingService.form_BookingService;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.model.Booking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {
    List<Booking> semuabookingItemList;
    Context mContext;


    public BookingAdapter(Context context, List<Booking> daftarBooking) {

        this.mContext           =   context;
        semuabookingItemList    = daftarBooking;

    }

    public class BookingHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle;
        public TextView tvSubtitle;
        public TextView tvId;
        public CardView cv_main;
        public TextView tvjam;
        public TextView status;
        public ImageView imgstatus;


        public BookingHolder(View itemView) {
            super(itemView);
            tvId        = itemView.findViewById(R.id.tv_id);
            tvTitle     = itemView.findViewById(R.id.tv_title);
            tvSubtitle  = itemView.findViewById(R.id.tv_subtitle);
            cv_main     = itemView.findViewById(R.id.cv_main);
            tvjam       = itemView.findViewById(R.id.tv_jam);
            status      = itemView.findViewById(R.id.status);
            imgstatus   = itemView.findViewById(R.id.imgstatus);



        }
    }


    @Override
    public BookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView   = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_booking_service, parent, false);

        return new BookingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookingHolder holder, int position) {

       final Booking semuadosenitem     =   semuabookingItemList.get(position);
       holder.tvTitle.setText(semuadosenitem.getTypeCar());
       holder.tvSubtitle.setText(semuadosenitem.getDate());
       holder.tvId.setText((semuadosenitem.getIdbooking()));
       holder.status.setText(semuadosenitem.getStatus());
       final String statusS = semuadosenitem.getStatus();

        switch (statusS){
            case "Pending":
                holder.imgstatus.setImageResource(R.drawable.ic_assignment_late_black_24dp);
                holder.status.setText(semuadosenitem.getStatus());
                break;
            case "Finish":
                holder.imgstatus.setImageResource(R.drawable.ic_assignment_turned_in_black_24dp);
                holder.status.setText(semuadosenitem.getStatus());
                break;
            case "Cancel":
                holder.imgstatus.setImageResource(R.drawable.ic_delete_forever_black_24dp);
                holder.status.setText(semuadosenitem.getStatus());
                break;
            case "Approve":
                holder.imgstatus.setImageResource(R.drawable.ic_assignment_black_24dp);
                holder.status.setText(semuadosenitem.getStatus());
                break;
        }


        final Date   jamservice;
        final String idbooking   = semuadosenitem.getIdbooking();
        final String idmobil     = semuadosenitem.getIdmobil();
        final String typecar     = semuadosenitem.getTypeCar();
        final String date        = semuadosenitem.getDate();
        final String hour        = semuadosenitem.getHour();
        final String typeservice = semuadosenitem.getTypeService();
        final String problem     = semuadosenitem.getProblem();
        final String WIB         = " WIB";


        SimpleDateFormat dateFormat  = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        try {
            jamservice = dateFormat.parse(hour);
            String out = dateFormat2.format(jamservice);
            holder.tvjam.setText(out+WIB);
            //System.out.println("Format tanpa second "+ jamservice);
            //System.out.println("Format tanpa second "+ jamservicecust);

        } catch (ParseException e) {
        }




        holder.cv_main.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle bundle = new Bundle();
               Intent intent    =   new Intent(mContext, edit_BookingService.class);
               bundle.putString("Idbooking",idbooking);
               bundle.putString("Idmobil",idmobil);
               bundle.putString("Typecar",typecar);
               bundle.putString("Date",date);
               bundle.putString("Hour",hour);
               bundle.putString("Typeservice",typeservice);
               bundle.putString("Problem",problem);
               bundle.putString("Status",statusS);

               intent.putExtras(bundle);
               mContext.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return semuabookingItemList.size();
    }
}
