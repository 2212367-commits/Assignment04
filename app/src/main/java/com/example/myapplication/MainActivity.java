package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        setTheme(ThemeManager.getThemeResourceId(ThemeManager.getSelectedTheme(this)));

        super.onCreate(savedInstanceState);
        
        // 2.3 Check Authentication
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
        if (!isLoggedIn) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(userList, new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                // 7.2 Load Data URL
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_URL, user.getWebsite());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(User user, View view) {
                // 6.3 Popup Menu
                showPopupMenu(view, user);
            }
        });
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        // 4.3 Retrieve from SQLite
        List<User> localData = databaseHelper.getAllUsers();
        if (!localData.isEmpty()) {
            userList.clear();
            userList.addAll(localData);
            adapter.notifyDataSetChanged();
        } else {
            // 3.1 Fetch from API
            fetchFromApi();
        }
    }

    private void fetchFromApi() {
        RetrofitClient.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    
                    // 4.2 Store in SQLite
                    for (User user : users) {
                        databaseHelper.insertUser(user);
                    }
                    
                    userList.clear();
                    userList.addAll(users);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(View view, User user) {
        PopupMenu popup = new PopupMenu(this, view);
        // 6.3 Quick Actions
        popup.getMenu().add("Edit (Simulated)");
        popup.getMenu().add("Delete (Simulated)");
        popup.setOnMenuItemClickListener(item -> {
            Toast.makeText(MainActivity.this, item.getTitle() + ": " + user.getName(), Toast.LENGTH_SHORT).show();
            return true;
        });
        popup.show();
    }

    // 6.1 Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_theme_light) {
            setThemeAndRecreate(Constants.THEME_LIGHT);
            return true;
        } else if (id == R.id.action_theme_dark) {
            setThemeAndRecreate(Constants.THEME_DARK);
            return true;
        } else if (id == R.id.action_theme_custom) {
            setThemeAndRecreate(Constants.THEME_CUSTOM);
            return true;
        } else if (id == R.id.action_logout) {
            // Logout
            getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)
                    .edit().remove(Constants.KEY_IS_LOGGED_IN).apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void setThemeAndRecreate(int theme) {
        ThemeManager.setCustomTheme(this, theme);
        recreate();
    }
}