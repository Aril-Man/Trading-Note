package com.example.tradenote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditTradeActivity extends AppCompatActivity {

    private EditText etTradeDate, etTradeAmount, etTradeProfit, etTradeLoss;
    private Button btnSaveTrade;
    private TextView tvAddEditTitle;

    private TradeDatabaseHelper dbHelper;
    private Trade currentTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trade);

        tvAddEditTitle = findViewById(R.id.tvAddEditTitle);
        etTradeDate = findViewById(R.id.etTradeDate);
        etTradeAmount = findViewById(R.id.etTradeAmount);
        etTradeProfit = findViewById(R.id.etTradeProfit);
        etTradeLoss = findViewById(R.id.etTradeLoss);
        btnSaveTrade = findViewById(R.id.btnSaveTrade);

        dbHelper = new TradeDatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("trade_id")) {
            // Mode edit
            tvAddEditTitle.setText("Edit Trading");
            btnSaveTrade.setText("Perbarui Trading");

            int tradeId = intent.getIntExtra("trade_id", -1);
            long tradeDateMillis = intent.getLongExtra("trade_date_millis", 0);
            double tradeAmount = intent.getDoubleExtra("trade_amount", 0.0);
            double tradeProfit = intent.getDoubleExtra("trade_profit", 0.0);
            double tradeLoss = intent.getDoubleExtra("trade_loss", 0.0);

            currentTrade = new Trade(tradeId, tradeDateMillis, tradeAmount, tradeProfit, tradeLoss);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            etTradeDate.setText(sdf.format(new Date(tradeDateMillis)));
            etTradeAmount.setText(String.valueOf(tradeAmount));
            etTradeProfit.setText(String.valueOf(tradeProfit));
            etTradeLoss.setText(String.valueOf(tradeLoss));
        } else {
            tvAddEditTitle.setText("Tambah Trading Baru");
            btnSaveTrade.setText("Simpan Trading");
            currentTrade = null;
        }

        etTradeDate.setOnClickListener(v -> showDatePickerDialog());

        btnSaveTrade.setOnClickListener(v -> saveTrade());
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        if (currentTrade != null) {
            c.setTimeInMillis(currentTrade.getTradeDateMillis());
        } else {
            c.setTimeInMillis(System.currentTimeMillis());
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    etTradeDate.setText(sdf.format(selectedDate.getTime()));
                }, year, month, day);
        datePickerDialog.show();
    }

    private void saveTrade() {
        String dateString = etTradeDate.getText().toString().trim();
        String amountString = etTradeAmount.getText().toString().trim();
        String profitString = etTradeProfit.getText().toString().trim();
        String lossString = etTradeLoss.getText().toString().trim();

        // Validasi input
        if (dateString.isEmpty() || amountString.isEmpty() || profitString.isEmpty() || lossString.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
            return;
        }

        long tradeDateMillis;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = sdf.parse(dateString);
            tradeDateMillis = date.getTime();
        } catch (ParseException e) {
            Toast.makeText(this, "Format tanggal tidak valid. Gunakan YYYY-MM-DD", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountString);
        double profit = Double.parseDouble(profitString);
        double loss = Double.parseDouble(lossString);

        if (currentTrade == null) {
            Trade newTrade = new Trade(0, tradeDateMillis, amount, profit, loss);
            long id = dbHelper.addTrade(newTrade);
            if (id != -1) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Gagal menambahkan trading", Toast.LENGTH_SHORT).show();
            }
        } else {
            currentTrade.setTradeDateMillis(tradeDateMillis);
            currentTrade.setAmount(amount);
            currentTrade.setProfit(profit);
            currentTrade.setLoss(loss);

            int rowsAffected = dbHelper.updateTrade(currentTrade);
            if (rowsAffected > 0) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui trading", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
