package com.example.inmotestapi;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private List<User> saveUser;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveUser = new ArrayList<>();
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading");
        pd.show();

        GetData service = RetorifitClient.getRetrofitInstance().create(GetData.class);
        Call<List<User>> call = service.getAllUser();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                pd.dismiss();
                loadDatalist(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadDatalist(List<User> body) {
        recyclerView = findViewById(R.id.recycleid);
        adapter = new CustomAdapter(body,MainActivity.this);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

    }

    private void loadMore(int index) {
        saveUser.add(new User("load","load"));
        adapter.notifyItemInserted(saveUser.size()-1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
