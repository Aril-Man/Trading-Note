package com.example.tradenote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TradeDatabaseHelper dbHelper;
    private List<Trade> tradeList;
    private TradeAdapter tradeAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddTrade;
    private TextView tvNoTrades;

    private static final int ADD_TRADE_REQUEST_CODE = 1;
    private static final int EDIT_TRADE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewTrades);
        fabAddTrade = findViewById(R.id.fabAddTrade);
        tvNoTrades = findViewById(R.id.tvNoTrades);

        dbHelper = new TradeDatabaseHelper(this);

        tradeList = new ArrayList<>();
        tradeAdapter = new TradeAdapter(tradeList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tradeAdapter);

        loadTrades();

        fabAddTrade.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditTradeActivity.class);
            startActivityForResult(intent, ADD_TRADE_REQUEST_CODE);
        });

        tradeAdapter.setOnEditClickListener(trade -> {
            Intent intent = new Intent(MainActivity.this, AddEditTradeActivity.class);
            intent.putExtra("trade_id", trade.getId());
            intent.putExtra("trade_date_millis", trade.getTradeDateMillis());
            intent.putExtra("trade_amount", trade.getAmount());
            intent.putExtra("trade_profit", trade.getProfit());
            intent.putExtra("trade_loss", trade.getLoss());
            startActivityForResult(intent, EDIT_TRADE_REQUEST_CODE);
        });

        tradeAdapter.setOnDeleteClickListener(trade -> {
            showDeleteConfirmationDialog(trade);
        });
    }

    private void loadTrades() {
        tradeList = dbHelper.getAllTrades();
        tradeAdapter.updateTrades(tradeList);
        checkEmptyState();
    }

    private void checkEmptyState() {
        if (tradeList.isEmpty()) {
            tvNoTrades.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoTrades.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showDeleteConfirmationDialog(Trade trade) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Catatan Trading")
                .setMessage("Apakah Anda yakin ingin menghapus catatan trading ini?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    if (dbHelper.deleteTrade(trade.getId()) > 0) {
                        Toast.makeText(MainActivity.this, "Trading berhasil dihapus", Toast.LENGTH_SHORT).show();
                        loadTrades();
                    } else {
                        Toast.makeText(MainActivity.this, "Gagal menghapus trading", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Tidak", null) // Tutup dialog jika "Tidak"
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == ADD_TRADE_REQUEST_CODE || requestCode == EDIT_TRADE_REQUEST_CODE) && resultCode == RESULT_OK) {
            loadTrades();
            Toast.makeText(this, "Trading berhasil disimpan!", Toast.LENGTH_SHORT).show();
        }
    }
}
