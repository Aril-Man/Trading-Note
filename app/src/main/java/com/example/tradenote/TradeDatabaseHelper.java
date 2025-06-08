package com.example.tradenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class TradeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trading.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TRADES = "trades";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRADE_DATE_MILLIS = "trade_date_millis";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_PROFIT = "profit";
    public static final String COLUMN_LOSS = "loss";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TRADES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TRADE_DATE_MILLIS + " INTEGER, " +
                    COLUMN_AMOUNT + " REAL, " +
                    COLUMN_PROFIT + " REAL, " +
                    COLUMN_LOSS + " REAL);";

    public TradeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRADES);
        onCreate(db);
    }

    public long addTrade(Trade trade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRADE_DATE_MILLIS, trade.getTradeDateMillis());
        values.put(COLUMN_AMOUNT, trade.getAmount());
        values.put(COLUMN_PROFIT, trade.getProfit());
        values.put(COLUMN_LOSS, trade.getLoss());

        long newRowId = db.insert(TABLE_TRADES, null, values);
        db.close();
        return newRowId;
    }

    public List<Trade> getAllTrades() {
        List<Trade> tradeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRADES + " ORDER BY " + COLUMN_TRADE_DATE_MILLIS + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trade trade = new Trade();
                trade.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                trade.setTradeDateMillis(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_TRADE_DATE_MILLIS)));
                trade.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)));
                trade.setProfit(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PROFIT)));
                trade.setLoss(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LOSS)));
                tradeList.add(trade);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tradeList;
    }

    public int updateTrade(Trade trade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRADE_DATE_MILLIS, trade.getTradeDateMillis());
        values.put(COLUMN_AMOUNT, trade.getAmount());
        values.put(COLUMN_PROFIT, trade.getProfit());
        values.put(COLUMN_LOSS, trade.getLoss());

        int rowsAffected = db.update(TABLE_TRADES, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(trade.getId())});
        db.close();
        return rowsAffected;
    }

    public int deleteTrade(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TRADES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }
}
