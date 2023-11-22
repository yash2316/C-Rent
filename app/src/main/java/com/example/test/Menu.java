package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass> carList;
    RecyclerViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);

                return true;
            }
        });

        ImageView User_profile = findViewById(R.id.user_profile);
        User_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = getIntent();
                String _USERNAME= intent1.getStringExtra("username");
                String _NAME= intent1.getStringExtra("name");
                String _EMAIL = intent1.getStringExtra("email");
                String _PHONENO = intent1.getStringExtra("phoneNo");
                String _PASSWORD = intent1.getStringExtra("password");


                Intent intent = new Intent(Menu.this, UserProfile.class);

                intent.putExtra("name",_NAME);
                intent.putExtra("username",_USERNAME);
                intent.putExtra("email",_EMAIL);
                intent.putExtra("phoneNo",_PHONENO);
                intent.putExtra("password",_PASSWORD);
                startActivity(intent);

                

            }
        });

        initData();
        initRecyclerView();

    }

    private void filterList(String Text) {
        List<ModelClass> filteredList = new ArrayList<>();

        for (ModelClass modelClass : carList) {
            if (modelClass.getCarName().toLowerCase().contains(Text.toLowerCase())) {
                filteredList.add(modelClass);
            }
        }

        if (filteredList.isEmpty()) {

        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    private void initData() {

        carList = new ArrayList<>();

        carList.add(new ModelClass(R.drawable.fiat, "Fiat Argo", "4.7", "4200"));
        carList.add(new ModelClass(R.drawable.honda, "Honda City", "4.9", "3800"));
        carList.add(new ModelClass(R.drawable.hyundai, "Hyundai Tucson", "4.5", "3000"));
        carList.add(new ModelClass(R.drawable.hyundai_thirty, "Hyundai i30", "4.7", "3900"));
        carList.add(new ModelClass(R.drawable.mercedes, "Mercedes Benz", "4.5", "5500"));
        carList.add(new ModelClass(R.drawable.tata, "Tata Nexon", "4.6", "3500"));
        carList.add(new ModelClass(R.drawable.toyota_fortuner, "Toyota Fortuner", "4.9", "4000"));

    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(carList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {

        ModelClass clickedItem = carList.get(position);
        String carName = clickedItem.getCarName();
        String price = clickedItem.getPrice();
        String rating = clickedItem.getRating();
        int picture = clickedItem.getImage();

        // Open a new activity
        Intent intent = new Intent(this, DisplayCarDetails.class);
        intent.putExtra("carName", carName);
        intent.putExtra("price", price);
        intent.putExtra("rating", rating);
        intent.putExtra("picture", picture);

        startActivity(intent);
    }

}
