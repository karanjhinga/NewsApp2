package com.karanjhinga.newsapp.Categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karanjhinga.newsapp.Data.Models.Category;
import com.karanjhinga.newsapp.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder>{

    private Context context;
    private CategoryListener listener;
    private List<Category> list = new ArrayList<>(); // LIST OF CATEGORIES

    public CategoryAdapter(Context context, CategoryListener listener) {
        this.context = context;
        this.listener = listener;
        createList();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(context).inflate(R.layout.single_category_layout,viewGroup,false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        final Category category = list.get(position);

        holder.txt.setText(category.name);

        holder.img.setImageResource(category.image);

        // LISTENER FOR CATEGORY SELECTION
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClicked(category.name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    class Holder extends RecyclerView.ViewHolder{

        private  ImageView img;     //CATEGORY IMAGE
        private TextView txt;       //CATEGORY TITLE

        Holder(@NonNull final View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.categoryImage);
            txt = itemView.findViewById(R.id.categoryName);

        }
    }

    private void createList() {
        /* ADDING CATEGORIES */
        list.add(new Category("Technology",R.drawable.technology));
        list.add(new Category("Sports",R.drawable.sports));
        list.add(new Category("Business",R.drawable.business));
        list.add(new Category("Entertainment",R.drawable.entertainment));
        list.add(new Category("General",R.drawable.general));
        list.add(new Category("Health",R.drawable.health));
        list.add(new Category("Science",R.drawable.science));
    }
}
