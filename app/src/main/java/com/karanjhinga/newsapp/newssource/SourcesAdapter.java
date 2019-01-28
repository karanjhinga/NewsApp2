package com.karanjhinga.newsapp.newssource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karanjhinga.newsapp.data.models.Source;
import com.karanjhinga.newsapp.R;

import java.util.List;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.Holder> {

    private Context context;
    private List<Source> list; //LIST OF SOURCES BEING DISPLAYED
    private SourceSelectedListener listener;

    public SourcesAdapter(Context context, List<Source> list, SourceSelectedListener listener){
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.single_source_layout,viewGroup,false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {

        final Source source = list.get(i);

        holder.name.setText(source.name);

        holder.description.setText(source.description);
        /* LISTENER FOR SOURCE SELECTED*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSourceSelected(source);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // SINGLE SOURCE VIEW HOLDER
    class Holder extends RecyclerView.ViewHolder{
        private  TextView name,description;
        Holder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sourceName);
            description = itemView.findViewById(R.id.sourceDescription);

        }
    }

    // FUNCTION CREATED TO ADD LIST OF SOURCES TO OUR RECYCLER VIEW
    public void updateData(List<Source> list){
        this.list=list;
        notifyDataSetChanged();
    }
}
