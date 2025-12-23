package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        setTheme(ThemeManager.getThemeResourceId(ThemeManager.getSelectedTheme(this)));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Mock Authentication
                SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
                prefs.edit().putBoolean(Constants.KEY_IS_LOGGED_IN, true).apply();

                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        // Hide logout since we are not logged in
        menu.findItem(R.id.action_logout).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_theme_light) {
            ThemeManager.setCustomTheme(this, Constants.THEME_LIGHT);
            recreate();
            return true;
        } else if (id == R.id.action_theme_dark) {
            ThemeManager.setCustomTheme(this, Constants.THEME_DARK);
            recreate();
            return true;
        } else if (id == R.id.action_theme_custom) {
            ThemeManager.setCustomTheme(this, Constants.THEME_CUSTOM);
            recreate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
