package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    public static void setCustomTheme(Context context, int theme) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(Constants.KEY_THEME, theme).apply();
    }

    public static int getSelectedTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(Constants.KEY_THEME, Constants.THEME_LIGHT);
    }

    public static void applyTheme(Context context) {
        int theme = getSelectedTheme(context);
        switch (theme) {
            case Constants.THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Constants.THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Constants.THEME_CUSTOM:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    public static int getThemeResourceId(int theme) {
        if (theme == Constants.THEME_CUSTOM) {
            return R.style.Theme_MyApplication_Custom;
        }
        return R.style.Theme_MyApplication;
    }
}
