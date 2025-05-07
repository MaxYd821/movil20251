package com.example.helloandroid.adpaters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloandroid.FormColorActivity;
import com.example.helloandroid.R;
import com.example.helloandroid.entities.Color;
import com.example.helloandroid.entities.Contacto;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{
    private List<Contacto> data;
    private Activity activity;
    public ContactAdapter(List<Contacto> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        Contacto contacto = data.get(position);

        TextView tvName = holder.itemView.findViewById(R.id.tvName);
        TextView tvTelefono = holder.itemView.findViewById(R.id.tvTelefono);
        TextView tvGenero = holder.itemView.findViewById(R.id.tvTelefono);

        //tvColorName.setText(color.nombre);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Color: " + color.nombre, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), FormColorActivity.class);

                // los extras me permiten enviar información entre actividades
                //intent.putExtra("colorId", color.id);
                //intent.putExtra("colorName", color.nombre);
                //intent.putExtra("colorHex", color.colorHex);

                activity.startActivityForResult(intent, 123);
            }
        });

        try {
            //String hex = color.colorHex;
            //tvColorHex.setText(hex);
            //vColorBg.setBackgroundColor(android.graphics.Color.parseColor(hex));
        } catch(Exception ex) {
            Log.d("MAIN_APP", "Usando color por defecto");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ContactViewHolder extends  RecyclerView.ViewHolder {

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
