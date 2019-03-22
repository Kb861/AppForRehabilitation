package com.example.badanie;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by patry on 22.03.2019.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> stList;

    public ItemAdapter(List<Item> stList) {
        this.stList = stList;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_text_item, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int pos = position;
        viewHolder.tvName.setText(stList.get(position).getName());
        viewHolder.chkSelected.setTag(stList.get(position));
        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Item contact = (Item) cb.getTag();
                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return stList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvName;
        public final CheckBox chkSelected;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);


            chkSelected = itemView
                    .findViewById(R.id.chkSelected);
        }
    }
    public List<Item> getitemList() {
        return stList;
    }
}
