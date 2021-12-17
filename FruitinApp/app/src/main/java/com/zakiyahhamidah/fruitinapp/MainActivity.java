package com.zakiyahhamidah.fruitinapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
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
    FruitAdapter fruitAdapter;
    ImageAdapter imageAdapter;

    int images[] = {R.drawable.a,R.drawable.b, R.drawable.c,R.drawable.d,
            R.drawable.e,R.drawable.f, R.drawable.g,R.drawable.h,
            R.drawable.i,R.drawable.j, R.drawable.k,R.drawable.l,
            R.drawable.m,R.drawable.n, R.drawable.o,R.drawable.p,
            R.drawable.q,R.drawable.r, R.drawable.s,R.drawable.t,
            R.drawable.u,R.drawable.v, R.drawable.w,R.drawable.x,
            R.drawable.y,R.drawable.z, R.drawable.zz};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        fruitAdapter = new FruitAdapter(dataResponseList, this);
        gridView.setAdapter(fruitAdapter);

        getAllData();

        for(int i=0; i< images.length; i++){
            Data data = new Data(images[i]);
            dataList.add(data);
        }

        imageAdapter = new ImageAdapter(dataList, this);
        gridView.setAdapter(imageAdapter);

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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

        return super.onOptionsItemSelected(item);
    }

    public void getAllData(){
        Call<List<DataResponse>> DataResponse = ApiClient.getInterface().getAllData();

        DataResponse.enqueue(new Callback<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>>() {
            @Override
            public void onResponse(Call<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>> call, Response<List<com.zakiyahhamidah.fruitinapp.Model.DataResponse>> response) {

                if(response.isSuccessful()) {
                    String message = "Request Successfull";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();

                    dataResponseList = response.body();

                    FruitAdapter fruitAdapter = new FruitAdapter(dataResponseList,MainActivity.this);
                   // ImageAdapter imageAdapter = new ImageAdapter(dataResponseList,MainActivity.this);

                    gridView.setAdapter(fruitAdapter);
                    //gridView.setAdapter(imageAdapter);

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
        //private List<Data> dataList;
        private Context context;
        private LayoutInflater layoutInflater;

        public FruitAdapter(List<DataResponse> dataResponseList, Context context){
            this.dataResponseList = dataResponseList;
            this.dataResponseListFiltered = dataResponseList;
            //this.dataList = dataList;
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
            //ImageView imageView = view.findViewById(R.id.img_fruit);
            //imageView.setImageResource(dataList.get(i).getImage());

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = dataResponseList.size();
                        filterResults.values = dataResponseList;
                    }else{
                        String searchStr = constraint.toString().toLowerCase();
                        List<DataResponse> resultData = new ArrayList<>();

                        for(DataResponse dataResponse: dataResponseList){

                            if(dataResponse.getName().contains(searchStr)){
                                resultData.add(dataResponse);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    dataResponseListFiltered = (List<DataResponse>) filterResults.values;
                    notifyDataSetChanged();

                }
            };

            return filter;
        }
    }

    public class ImageAdapter extends BaseAdapter{

        private List<Data> dataList;
        private List<Data> dataListFiltered;
        private Context context;

        public ImageAdapter(List<Data> dataModelList, Context context){
            this.dataList = dataModelList;
            this.dataListFiltered = dataModelList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return dataListFiltered.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items, null);

            ImageView imageView = view.findViewById(R.id.img_fruit);
            imageView.setImageResource(dataListFiltered.get(i).getImage());

            return view;
        }
    }

}