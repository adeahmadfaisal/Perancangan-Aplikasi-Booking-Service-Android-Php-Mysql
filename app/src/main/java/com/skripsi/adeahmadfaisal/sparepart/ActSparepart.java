package com.skripsi.adeahmadfaisal.sparepart;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.skripsi.adeahmadfaisal.R;
import com.skripsi.adeahmadfaisal.adapter.SparePartAdapter;
import com.skripsi.adeahmadfaisal.apihelper.BaseApiService;
import com.skripsi.adeahmadfaisal.apihelper.UtilsApi;
import com.skripsi.adeahmadfaisal.model.sparepart.ResponseSparePart;
import com.skripsi.adeahmadfaisal.model.sparepart.SparepartItem;
import java.util.ArrayList;
import java.util.List;

public class ActSparepart extends AppCompatActivity implements SearchView.OnQueryTextListener  {

    RecyclerView rvsparepart;
    List<SparepartItem> semuasparepartlist = new ArrayList<>();
    Context mContext;
    SparePartAdapter sparePartAdapter;
    BaseApiService mApiService;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sparepart);
        Toolbar toolbar = findViewById(R.id.settoolbarsparepart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Spare Part");


        mApiService         = UtilsApi.getAPIService();
        rvsparepart         = findViewById(R.id.rvsparepart);
        mContext            = this;
        sparePartAdapter    = new SparePartAdapter(mContext, semuasparepartlist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvsparepart.setLayoutManager(mLayoutManager);
        getSparepart();

    }

    private void getSparepart() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu...", true, false);
        mApiService.getSparepart().enqueue(new Callback<ResponseSparePart>() {

            @Override
            public void onResponse(Call<ResponseSparePart> call, Response<ResponseSparePart> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    final List<SparepartItem> MobilItem = response.body().getSparepart();

                    rvsparepart.setAdapter(new SparePartAdapter(mContext, MobilItem));
                    sparePartAdapter.notifyDataSetChanged();
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mobil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSparePart> call, Throwable t) {

                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setQueryHint("Cari Nama Sparepart");

        searchView.setIconified(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        rvsparepart.setVisibility(View.GONE);
        mApiService.search(newText).enqueue(new Callback<ResponseSparePart>() {

            @Override
            public void onResponse(Call<ResponseSparePart> call, Response<ResponseSparePart> response) {
                if (response.isSuccessful()){

                    boolean value = response.body().isError();
                    rvsparepart.setVisibility(View.VISIBLE);

                    if (value == false) {
                        semuasparepartlist = response.body().getSparepart();
                        sparePartAdapter = new SparePartAdapter(mContext, semuasparepartlist);
                        rvsparepart.setAdapter(sparePartAdapter);
                        sparePartAdapter.notifyDataSetChanged();


                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data mobil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSparePart> call, Throwable t) {

                t.printStackTrace();
                loading.dismiss();
                Toast.makeText(mContext, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();


            }


        });
        return true;
    }
}
