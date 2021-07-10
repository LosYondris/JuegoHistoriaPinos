package com.cornelio.losyondris.juegohistoriapinos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<PooUser> list;

    public MyAdapter(Context context, ArrayList<PooUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lista_vista, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PooUser p = list.get(position);
        holder.lnombre.setText(p.getNombre());
        holder.lapellido.setText(p.getApellido());
        holder.lnivel.setText(p.getNivel());
        holder.lpunto.setText(p.getPuntos());
       // holder.lfecha.setText(p.getFoto()+""+position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView limg;
        TextView lnombre,lapellido,lpunto,lnivel,lfecha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            limg = itemView.findViewById(R.id.list_img);
            lnombre = itemView.findViewById(R.id.list_nombre);
            lapellido = itemView.findViewById(R.id.list_apellido);
            lpunto = itemView.findViewById(R.id.list_punto);
            lnivel = itemView.findViewById(R.id.list_nivel);
           // lfecha = itemView.findViewById(R.id.list_fecha);

        }
    }

}