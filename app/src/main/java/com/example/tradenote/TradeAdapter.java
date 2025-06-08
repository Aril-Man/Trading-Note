package com.example.tradenote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeViewHolder> {

    private List<Trade> tradeList;
    private OnItemClickListener listener;
    private OnEditClickListener editListener;
    private OnDeleteClickListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(Trade trade);
    }

    public interface OnEditClickListener {
        void onEditClick(Trade trade);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Trade trade);
    }

    public TradeAdapter(List<Trade> tradeList) {
        this.tradeList = tradeList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnEditClickListener(OnEditClickListener editListener) {
        this.editListener = editListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trade, parent, false);
        return new TradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeViewHolder holder, int position) {
        Trade currentTrade = tradeList.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(new Date(currentTrade.getTradeDateMillis()));

        holder.tvTradeDate.setText("Tanggal Trading: " + formattedDate);
        holder.tvTradeAmount.setText(String.format(Locale.getDefault(), "Jumlah Trading: Rp %,.0f", currentTrade.getAmount()));
        holder.tvTradeProfit.setText(String.format(Locale.getDefault(), "Profit: Rp %,.0f", currentTrade.getProfit()));
        holder.tvTradeLoss.setText(String.format(Locale.getDefault(), "Loss: Rp %,.0f", currentTrade.getLoss()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentTrade);
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEditClick(currentTrade);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteClick(currentTrade);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tradeList.size();
    }

    public void updateTrades(List<Trade> newTradeList) {
        this.tradeList.clear();
        this.tradeList.addAll(newTradeList);
        notifyDataSetChanged();
    }

    public static class TradeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTradeDate, tvTradeAmount, tvTradeProfit, tvTradeLoss;
        ImageButton btnEdit, btnDelete;

        public TradeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTradeDate = itemView.findViewById(R.id.tvTradeDate);
            tvTradeAmount = itemView.findViewById(R.id.tvTradeAmount);
            tvTradeProfit = itemView.findViewById(R.id.tvTradeProfit);
            tvTradeLoss = itemView.findViewById(R.id.tvTradeLoss);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
