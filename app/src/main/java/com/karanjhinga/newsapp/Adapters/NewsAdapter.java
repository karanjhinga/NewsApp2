package com.karanjhinga.newsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karanjhinga.newsapp.Models.Article;
import com.karanjhinga.newsapp.Others.GlideApp;
import com.karanjhinga.newsapp.Others.Helper;
import com.karanjhinga.newsapp.R;
import com.karanjhinga.newsapp.WebViewActivity;

import java.text.ParseException;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class NewsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Article> list;  //LIST OF NEWS POSTS

    public NewsAdapter(Context context, List<Article> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i==0){ // TYPE 0 IS FOR NEWS POST

            View v = LayoutInflater.from(context).inflate(R.layout.single_news_layout,viewGroup,false);
            return new NewsHolder(v);

        }else { // TYPE 1 IS FOR LOADING PROGRESS BAR

            View v= LayoutInflater.from(context).inflate(R.layout.progress_bar_layout,viewGroup,false);
            return new ProgressHolder(v);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final Article news = list.get(i);

        if(viewHolder instanceof NewsHolder){      /* SETTING UP NEWS POST*/

            NewsHolder holder = (NewsHolder) viewHolder;
            holder.title.setText(news.title);
            holder.description.setText(news.description);

            try {
                holder.time.setText(Helper.convertTimeStamp(news.publishedAt));
            } catch (ParseException e) {
                Log.e("Tag","Error date conversion");
                holder.time.setText(news.publishedAt);
            }

            GlideApp.with(context)                      /* EXTERNAL GLIDE LIBRARY TO LOAD IMAGES */
                    .load(news.urlToImage)
                    .centerCrop()
                    .transition(withCrossFade())
                    .into(holder.image);

            // LISTENER FOR NEWS POST SELECTION
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,WebViewActivity.class);
                    i.putExtra("url",news.url);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override  // METHOD TO GET THE TYPE OF VIEW THAT RECYCLER VIEW IS GOING TO DISPLAY
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    // NEWS POST VIEW HOLDER
    class NewsHolder extends RecyclerView.ViewHolder{
        TextView title,description,time;
        ImageView image;
        NewsHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.NewsImage);
            title= itemView.findViewById(R.id.NewsTitle);
            description= itemView.findViewById(R.id.NewsDescription);
            time = itemView.findViewById(R.id.NewsPublishTime);
        }
    }
    // PROGRESS BAR VIEW HOLDER
    class ProgressHolder extends RecyclerView.ViewHolder{
        ProgressHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // FUNCTION CREATED TO ADD LIST OF NEWS TO OUR RECYCLER VIEW
    public void addAll(List<Article> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    // FUNCTION CREATED TO ADD A SINGLE NEWS TO OUR RECYCLER VIEW
    public void add(Article article){
        list.add(article);
        notifyDataSetChanged();
    }

    // FUNCTION CREATED TO DELETE LAST ITEM OF RECYCLER VIEW
    public void deleteLastItem(){
        int position = list.size()-1;
        list.remove(position);
        notifyDataSetChanged();
    }
}
