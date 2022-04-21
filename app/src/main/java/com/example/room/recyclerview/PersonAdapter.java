package com.example.room.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.database.PersonneEntity;
import com.example.room.database.TestData;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<PersonneEntity> personList;
    private PersonAdapterListener adapterListener;

    public PersonAdapter(List<PersonneEntity> personList,
                         PersonAdapterListener adapterListener) {
        this.personList = personList;
        this.adapterListener = adapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.iconImageView.setImageResource(android.R.drawable.ic_menu_delete);
        PersonneEntity person = personList.get(position);
        holder.idTextView.setText(String.valueOf(person.getId()));
        holder.nomTextView.setText(person.getNom());
        holder.dateTextView.setText(TestData.formatter.format(person.getDate()));
        holder.iconImageView.setTag(Boolean.FALSE);
        holder.iconImageView.setOnClickListener(view -> {
            // change to check image
            if (holder.iconImageView.getTag() == Boolean.TRUE) {
                holder.iconImageView.setImageResource(android.R.drawable.radiobutton_off_background);
                holder.iconImageView.setTag(Boolean.FALSE);
            }
            else {
                holder.iconImageView.setImageResource(android.R.drawable.radiobutton_on_background);
                holder.iconImageView.setTag(Boolean.TRUE);
            }

            // notify to up level
            adapterListener.onClick(view, person);
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView idTextView;
        TextView nomTextView;
        TextView dateTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            idTextView = itemView.findViewById(R.id.idTextView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }

    public interface PersonAdapterListener {
        void onClick(View view, PersonneEntity person);
    }
}
