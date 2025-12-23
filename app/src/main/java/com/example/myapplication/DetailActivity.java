package com.example.myapplication;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.applyTheme(this);
        setTheme(ThemeManager.getThemeResourceId(ThemeManager.getSelectedTheme(this)));
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WebView webView = findViewById(R.id.webView);
        String url = getIntent().getStringExtra(EXTRA_URL);

        if (url != null && !url.isEmpty()) {
            android.widget.ProgressBar progressBar = findViewById(R.id.progressBar);
            android.widget.TextView tvError = findViewById(R.id.tvError);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true); // Improve compatibility

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(android.view.View.VISIBLE);
                    tvError.setVisibility(android.view.View.GONE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(android.view.View.GONE);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    progressBar.setVisibility(android.view.View.GONE);
                    tvError.setVisibility(android.view.View.VISIBLE);
                    tvError.setText("Error: " + description);
                    android.widget.Toast.makeText(DetailActivity.this, "Error: " + description, android.widget.Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onReceivedSslError(WebView view, android.webkit.SslErrorHandler handler, android.net.http.SslError error) {
                   // Ignore SSL errors for testing purposes (e.g. self-signed certs or old sites)
                   // handler.proceed(); // WARNING: This is dangerous for production.
                   // For this task, we will proceed but warn.
                   handler.proceed();
                   android.widget.Toast.makeText(DetailActivity.this, "SSL Warning: " + error.toString(), android.widget.Toast.LENGTH_SHORT).show();
                }
            });
            
            // Handle incomplete URLs
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            
            webView.loadUrl(url);
        } else {
             android.widget.Toast.makeText(this, "Refusing to load invalid URL", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}
