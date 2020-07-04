package com.yassinetaboubi.android.magazines;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yassinetaboubi.android.magazines.R;

import java.util.List;

public class List1Adapter extends RecyclerView.Adapter<List1Adapter.ViewHolder> {
    List<OurData> list;
    private Context context;
    public List1Adapter(List<OurData>list,Context context){
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OurData ourData=list.get(position);
        holder.itemTxt.setText(ourData.getUsername());
        holder.itemTxt1.setText(ourData.getScore());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTxt;
        private TextView itemTxt1;
        public ViewHolder(View view){
            super(view);
            itemTxt=view.findViewById(R.id.itemtxtt);
            itemTxt1=view.findViewById(R.id.itemtxtt1);

        }
    }
}
