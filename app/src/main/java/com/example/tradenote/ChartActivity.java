package com.example.tradenote;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ChartActivity extends AppCompatActivity {

    private WebView webViewChart;
    private ProgressBar progressBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        webViewChart = findViewById(R.id.webViewChart);
        progressBarChart = findViewById(R.id.progressBarChart);

        WebSettings webSettings = webViewChart.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webViewChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBarChart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBarChart.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                progressBarChart.setVisibility(View.GONE);
                Toast.makeText(ChartActivity.this, "Gagal memuat grafik: " + description + " (Kode: " + errorCode + ")", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onReceivedHttpError(WebView view, android.webkit.WebResourceRequest request, android.webkit.WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                if (request.isForMainFrame()) {
                    progressBarChart.setVisibility(View.GONE);
                    Toast.makeText(ChartActivity.this, "Kesalahan HTTP: " + errorResponse.getStatusCode(), Toast.LENGTH_LONG).show();
                }
            }
        });

        String htmlContent = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no'>" +
                "<style>" +
                "body { margin: 0; padding: 0; overflow: hidden; }" +
                "html, body, .tradingview-widget-container { height: 100%; width: 100%; }" +
                ".tradingview-widget-container__widget { height: calc(100% - 32px) !important; width: 100%; }" +
                ".tradingview-widget-copyright { display: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"tradingview-widget-container\" style=\"height:100%;width:100%\">" +
                "  <div class=\"tradingview-widget-container__widget\" style=\"height:calc(100% - 32px);width:100%\"></div>" +
                "  <div class=\"tradingview-widget-copyright\"><a href=\"https://www.tradingview.com/\" rel=\"noopener nofollow\" target=\"_blank\"><span class=\"blue-text\">Track all markets on TradingView</span></a></div>" +
                "  <script type=\"text/javascript\" src=\"https://s3.tradingview.com/external-embedding/embed-widget-advanced-chart.js\" async>" +
                "  {" +
                "  \"allow_symbol_change\": true," +
                "  \"calendar\": false," +
                "  \"details\": false," +
                "  \"hide_side_toolbar\": true," +
                "  \"hide_top_toolbar\": false," +
                "  \"hide_legend\": false," +
                "  \"hide_volume\": false," +
                "  \"hotlist\": false," +
                "  \"interval\": \"D\"," +
                "  \"locale\": \"en\"," +
                "  \"save_image\": true," +
                "  \"style\": \"1\"," +
                "  \"symbol\": \"NASDAQ:AAPL\"," +
                "  \"theme\": \"dark\"," +
                "  \"timezone\": \"Etc/UTC\"," +
                "  \"backgroundColor\": \"#0F0F0F\"," +
                "  \"gridColor\": \"rgba(242, 242, 242, 0.06)\"," +
                "  \"watchlist\": []," +
                "  \"withdateranges\": false," +
                "  \"compareSymbols\": []," +
                "  \"studies\": []," +
                "  \"autosize\": true" +
                "}" +
                "  </script>" +
                "</div>" +
                "</body>" +
                "</html>";

        webViewChart.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
