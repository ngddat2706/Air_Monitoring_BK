package com.example.air_monitoring_bk.Advice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.air_monitoring_bk.MainActivity;
import com.example.air_monitoring_bk.R;
import com.example.air_monitoring_bk.User.Parameter;
import com.example.air_monitoring_bk.User.userAdapter;

import java.util.List;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.UserViewHolder> {
    private List<AdviceItem> mListAdvice;

    public AdviceAdapter(List<AdviceItem> mListAdvice){
        this.mListAdvice = mListAdvice;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advice, parent,false);
        return new AdviceAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        AdviceItem user = mListAdvice.get(position);
        if (user == null){
            return;
        }

        holder.imgAdvice.setImageResource(user.getAdvice_icon());
        holder.tvAdvice.setText(user.getAdvice_tv());
        Integer AQI = MainActivity.AQI.get(MainActivity.AQI.size()-1);
        if(AQI < 51){
            holder.imgAdvice.setBackgroundResource(R.drawable.bg_green_comer_16);
        }else {
            holder.imgAdvice.setBackgroundResource(R.drawable.bg_greyish_comer_16);
        }
    }

    @Override
    public int getItemCount() {
        if(mListAdvice != null){
            return mListAdvice.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAdvice;
        private TextView tvAdvice;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAdvice = itemView.findViewById(R.id.icon_advice);
            tvAdvice = itemView.findViewById(R.id.tv_advice);
        }
    }
}
