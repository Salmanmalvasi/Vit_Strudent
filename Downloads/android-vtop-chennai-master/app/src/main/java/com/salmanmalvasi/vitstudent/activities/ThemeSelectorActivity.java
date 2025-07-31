package com.salmanmalvasi.vitstudent.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.salmanmalvasi.vitstudent.R;

public class ThemeSelectorActivity extends AppCompatActivity {

    private static final String PREF_NAME = "theme_preferences";
    private static final String KEY_SELECTED_THEME = "selected_theme";

    private ImageView checkBlack, checkRed, checkGreen, checkBlue, checkPurple;
    private String currentTheme = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_selector);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Choose Theme");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeViews();
        loadCurrentTheme();
        setupClickListeners();
    }

    private void initializeViews() {
        checkBlack = findViewById(R.id.check_black);
        checkRed = findViewById(R.id.check_red);
        checkGreen = findViewById(R.id.check_green);
        checkBlue = findViewById(R.id.check_blue);
        checkPurple = findViewById(R.id.check_purple);
    }

    private void loadCurrentTheme() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        currentTheme = prefs.getString(KEY_SELECTED_THEME, "default");
        updateCheckMarks();
    }

    private void setupClickListeners() {
        setupThemeCards();
    }

    private void setupThemeCards() {
        // Black theme
        findViewById(R.id.theme_black).setOnClickListener(v -> selectTheme("black"));

        // Red theme
        findViewById(R.id.theme_red).setOnClickListener(v -> selectTheme("red"));

        // Blue theme
        findViewById(R.id.theme_blue).setOnClickListener(v -> selectTheme("blue"));

        // Purple theme
        findViewById(R.id.theme_purple).setOnClickListener(v -> selectTheme("purple"));

        // Green theme
        findViewById(R.id.theme_green).setOnClickListener(v -> selectTheme("green"));
    }

    private void selectTheme(String theme) {
        // Save the selected theme
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_SELECTED_THEME, theme);
        editor.apply();

        currentTheme = theme;
        updateCheckMarks();

        // Apply the theme
        applyTheme(theme);

        // Return to previous activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("theme_changed", true);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void updateCheckMarks() {
        // Hide all checkmarks first
        findViewById(R.id.check_black).setVisibility(View.GONE);
        findViewById(R.id.check_red).setVisibility(View.GONE);
        findViewById(R.id.check_blue).setVisibility(View.GONE);
        findViewById(R.id.check_purple).setVisibility(View.GONE);
        findViewById(R.id.check_green).setVisibility(View.GONE);

        // Show checkmark for current theme
        switch (currentTheme) {
            case "black":
                findViewById(R.id.check_black).setVisibility(View.VISIBLE);
                break;
            case "red":
                findViewById(R.id.check_red).setVisibility(View.VISIBLE);
                break;
            case "blue":
                findViewById(R.id.check_blue).setVisibility(View.VISIBLE);
                break;
            case "purple":
                findViewById(R.id.check_purple).setVisibility(View.VISIBLE);
                break;
            case "green":
                findViewById(R.id.check_green).setVisibility(View.VISIBLE);
                break;
            default:
                findViewById(R.id.check_black).setVisibility(View.VISIBLE);
                break;
        }
    }

    private void applyTheme(String theme) {
        // This will be handled by the MainActivity when it resumes
        // The theme will be applied by setting the appropriate theme style
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
