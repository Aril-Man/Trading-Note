package com.example.tradenote;

public class Trade {
    private int id;
    private long tradeDateMillis;
    private double amount;
    private double profit;
    private double loss;

    public Trade() {
    }

    public Trade(int id, long tradeDateMillis, double amount, double profit, double loss) {
        this.id = id;
        this.tradeDateMillis = tradeDateMillis;
        this.amount = amount;
        this.profit = profit;
        this.loss = loss;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTradeDateMillis() {
        return tradeDateMillis;
    }

    public void setTradeDateMillis(long tradeDateMillis) {
        this.tradeDateMillis = tradeDateMillis;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }
}
