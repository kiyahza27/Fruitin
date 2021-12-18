package com.zakiyahhamidah.fruitinapp;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.zakiyahhamidah.fruitinapp.Model.Data;
import com.zakiyahhamidah.fruitinapp.Model.DataResponse;

public class DetailsFruit extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton btn;

    DataResponse dataResponse;
    Data dataList;

    TextView tvFruitName;
    TextView tvFamily;
    TextView tvGenus;
    TextView tvCarbo;
    TextView tvProtein;
    TextView tvCalories;
    TextView tvSugar;
    ImageView ivImgdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_fruit);

        getSupportActionBar().setTitle("Detail Fruit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn = (MaterialButton) findViewById(R.id.btn_bookmarks);
        btn.setOnClickListener(this);

        tvFruitName = findViewById(R.id.fruitname);
        tvFamily = findViewById(R.id.family);
        tvGenus = findViewById(R.id.genus);
        tvCarbo = findViewById(R.id.carbo);
        tvProtein = findViewById(R.id.protein);
        tvCalories = findViewById(R.id.calories);
        tvSugar = findViewById(R.id.sugar);
        ivImgdetail = findViewById(R.id.imgdetail);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            dataResponse = (DataResponse) intent.getSerializableExtra("data");
            dataList = (Data) intent.getSerializableExtra("image");

            tvFruitName.setText(dataResponse.getName());
            tvFamily.setText("Family: " + dataResponse.getFamily());
            tvGenus.setText("Genus: " + dataResponse.getGenus());
            tvCarbo.setText(String.valueOf(dataResponse.getNutritions().getCarbohydrates()) + " gr");
            tvProtein.setText(String.valueOf(dataResponse.getNutritions().getProtein()) + " gr");
            tvCalories.setText(String.valueOf(dataResponse.getNutritions().getCalories()) + " gr");
            tvSugar.setText(String.valueOf(dataResponse.getNutritions().getSugar()) + " gr");
            ivImgdetail.setImageResource(dataList.getImage());
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        btn.setText("Already in Bookmarks");
        btn.setBackgroundColor(R.color.purple_200);
    }
}