package com.zakiyahhamidah.fruitinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zakiyahhamidah.fruitinapp.Model.Data;
import com.zakiyahhamidah.fruitinapp.RESTAPI.ApiClient;
import com.zakiyahhamidah.fruitinapp.Model.DataResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<DataResponse> dataResponseList = new ArrayList<>();
    private List<Data> dataList = new ArrayList<>();

    GridView gridView;
    //SearchView searchView;
    FruitAdapter fruitAdapter;

    int images[] = {R.drawable.a,R.drawable.b, R.drawable.c,R.drawable.d,
            R.drawable.e,R.drawable.f, R.drawable.g,R.drawable.h,
            R.drawable.i,R.drawable.j, R.drawable.k,R.drawable.l,
            R.drawable.m,R.drawable.n, R.drawable.o,R.drawable.p,
            R.drawable.q,R.drawable.r, R.drawable.s,R.drawable.t,
            R.drawable.u,R.drawable.v, R.drawable.w, R.drawable.x,
            R.drawable.y,R.drawable.z, R.drawable.zz};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        fruitAdapter = new FruitAdapter(dataResponseList, dataList, this);
        gridView.setAdapter(fruitAdapter);

        getAllData();

        for(int i=0; i< images.length; i++){
            Data data = new Data(images[i]);
            dataList.add(data);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(new Intent(MainActivity.this,DetailsFruit.class)
                        .putExtra("data", dataResponseList.get(i))
                        .putExtra("image", dataList.get(i))
                );

                String message = dataResponseList.get(i).getName();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_navbar, menu);

        MenuItem menuItem = menu.findItem(R.id.search_fruit);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("TAG", "search ==>" + newText);

                fruitAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.search_fruit){
            return true;
        }

        if(id == R.id.bookmarks){
            Intent launchNewIntent = new Intent(MainActivity.this,Bookmarks.class);
            startActivityForResult(launchNewIntent, 0);
            return true;
        }
        if (item.getItemId() == R.id.menu_switch) {
            gridView.setNumColumns(1);
            item.setIcon(R.drawable.ic_grid_white);
            switchcolumn(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   private void switchcolumn(MenuItem item) {
        if(gridView.getNumColumns() == 1){
            item.setIcon(R.drawable.ic_view_list);
            gridView.setNumColumns(2);
        }
    }


    public void getAllData(){
        Call<List<DataResponse>> DataResponse = ApiClient.getInterface().getAllData();

        DataResponse.enqueue(new Callback<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>>() {
            @Override
            public void onResponse(Call<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>> call, Response<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>> response) {

                if(response.isSuccessful()) {
                    //String message = "Loading";
                    //Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

                    dataResponseList = response.body();

                    FruitAdapter fruitAdapter = new FruitAdapter(dataResponseList, dataList,MainActivity.this);
                    gridView.setAdapter(fruitAdapter);

                }else{
                    String message = "An Error....";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class FruitAdapter extends BaseAdapter implements Filterable {

        private List<DataResponse> dataResponseList;
        private List<DataResponse> dataResponseListFiltered;
        private List<Data> dataList;
        private Context context;
        private LayoutInflater layoutInflater;

        public FruitAdapter(List<DataResponse> dataResponseList, List<Data> dataList, Context context){
            this.dataResponseList = dataResponseList;
            this.dataResponseListFiltered = dataResponseList;
            this.dataList = dataList;
            this.context = context;
            this.layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return dataResponseListFiltered.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = layoutInflater.inflate(R.layout.row_items, viewGroup, false);
            }

            TextView textView = view.findViewById(R.id.nama);

            textView.setText(dataResponseListFiltered.get(i).getName());
            ImageView imageView = view.findViewById(R.id.img_fruit);
            imageView.setImageResource(dataList.get(i).getImage());

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();

                    if(charSequence == null ||charSequence.length() == 0){
                        filterResults.count = dataResponseListFiltered.size();
                        filterResults.values = dataResponseListFiltered;
                    }else{
                        String searchStr = charSequence.toString().toLowerCase();
                        List<DataResponse> resultData = new ArrayList<>();

                        for(DataResponse dataResponse: dataResponseListFiltered){

                            if(dataResponse.getName().toLowerCase().contains(searchStr)){
                                resultData.add(dataResponse);
                            }
                        }

                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                    dataResponseList = (List<DataResponse>) filterResults.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }
}
