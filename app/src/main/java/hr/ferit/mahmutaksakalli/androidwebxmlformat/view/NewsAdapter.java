package hr.ferit.mahmutaksakalli.androidwebxmlformat.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.R;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.NewsInfo;

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<NewsInfo> mNews;
    private NewsClickCallback mCallback;

    public NewsAdapter(List<NewsInfo> news, NewsClickCallback onNewsClickListener){
        mNews = new ArrayList<>();
        this.refreshData(news);
        mCallback = onNewsClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new, parent, false);
        return new NewsViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsInfo current = mNews.get(position);
        holder.title.setText(current.getTitle());
        holder.category.setText(current.getCategory());
        holder.description.setText(current.getDescription());
        holder.pubDate.setText(current.getPubDate());
        Picasso.get()
                .load(current.getEnclosure().getUrl())
                .centerCrop()
                .fit()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public void refreshData(List<NewsInfo> news) {
        mNews.clear();
        mNews.addAll(news);
        this.notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster) ImageView poster;
        @BindView(R.id.title)  TextView title;
        @BindView(R.id.description)  TextView description;
        @BindView(R.id.category) TextView category;
        @BindView(R.id.pubdate)  TextView pubDate;

        public NewsViewHolder(View itemView, final NewsClickCallback callback) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(mNews.get(getAdapterPosition()));
                }
            });
        }
    }
}
