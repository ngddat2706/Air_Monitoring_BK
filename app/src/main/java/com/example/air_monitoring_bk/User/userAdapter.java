package com.example.air_monitoring_bk.User;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.example.air_monitoring_bk.R;

import java.text.DecimalFormat;
import java.util.List;

public class userAdapter extends RecyclerView.Adapter<userAdapter.UserViewHolder> {

    private List<Parameter> mListUsers;
    private float progress;

    public userAdapter(List<Parameter> mListUsers){
        this.mListUsers = mListUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parameter, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Parameter user = mListUsers.get(position);
        if (user == null){
            return;
        }

        holder.tvName.setText(user.getName());
        holder.tvValue.setText(user.getValue());
        progress = Float.parseFloat(holder.tvValue.getText().toString());
        holder.seekBar.setProgress(progress);
        if(progress<51)
            holder.seekBar.setRingColor(Color.GREEN);
        else if(progress <101)
            holder.seekBar.setRingColor(Color.YELLOW);
        else if(progress <151)
            holder.seekBar.setRingColor(Color.rgb(255,126,0));
        else if(progress <201)
            holder.seekBar.setRingColor(Color.RED);
        else if(progress <301)
            holder.seekBar.setRingColor(Color.rgb(143,63,151));
        else if(progress <501)
            holder.seekBar.setRingColor(Color.rgb(126,0,35));

    }

    @Override
    public int getItemCount() {
        if(mListUsers != null){
            return mListUsers.size();
        }
        return 0;
    }

    public static  class  UserViewHolder extends RecyclerView.ViewHolder {

        private CircularSeekBar seekBar;
        private TextView tvName;
        private TextView tvValue;
        private float progress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
//            imgParameter = itemView.findViewById(R.id.img_parameter);
            tvName = itemView.findViewById(R.id.tv_name);
            tvValue = itemView.findViewById(R.id.tv_value);
            progress = Float.parseFloat(tvValue.getText().toString());
            seekBar = itemView.findViewById(R.id.seekbar);
            //seekBar.setProgressTextFormat(new DecimalFormat("0,0,0"));
        }
    }

}

