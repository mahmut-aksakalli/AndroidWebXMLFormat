package hr.ferit.mahmutaksakalli.androidwebxmlformat.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.R;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.NewsInfo;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.SearchResult;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.networking.RetrofitHelper;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.viewmodel.NewsListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvNews) RecyclerView rvNews;
    @BindView(R.id.select) Spinner categorySpinner;

    private NewsListViewModel mViewModel;
    public  ArrayAdapter<String> categoryAdapter;
    private List<String> cats = new ArrayList<>();

    private NewsClickCallback mClickCallback = new NewsClickCallback() {
        @Override
        public void onClick(NewsInfo news) {

            Uri link = Uri.parse(news.getLink());
            Intent webIntent = new Intent(Intent.ACTION_VIEW, link);
            startActivity(webIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this)
                .get(NewsListViewModel.class);

        loadData("");
        setUpRecyclerView();
        setUpSpinner();

    }

    void loadData(String category) {

        RetrofitHelper
                .getsNewsApi()
                .getNewsByCategory(category)
                .enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call,
                                           Response<SearchResult> response) {

                        SearchResult result = response.body();

                        mViewModel.setData(result.getChannel().getNews());
                        Log.d("allthingok", "data loaded");

                        for(NewsInfo data : result.getChannel().getNews()){
                            if(!cats.contains(data.getCategory())){
                                cats.add(data.getCategory());
                            }
                        }

                        mViewModel.setCategories(cats);

                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {
                        Log.d("thereiserror" , t.getLocalizedMessage());
                    }
                });
        }

    void setUpRecyclerView() {
        LinearLayoutManager linearLayout =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration divider =
                new DividerItemDecoration(this, linearLayout.getOrientation());

        NewsAdapter adapter = new NewsAdapter(
                new ArrayList<NewsInfo>(), mClickCallback);

        rvNews.setLayoutManager(linearLayout);
        rvNews.addItemDecoration(divider);
        rvNews.setAdapter(adapter);

        mViewModel.getData().observe(this, new Observer<List<NewsInfo>>() {
            @Override
            public void onChanged(@Nullable List<NewsInfo> news) {
                ((NewsAdapter)(rvNews.getAdapter())).refreshData(news);

            }
        });
    }

    private void setUpSpinner() {
        categoryAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>());
        categorySpinner.setAdapter(categoryAdapter);

        mViewModel.getCategories().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> data) {
                categoryAdapter.clear();
                categoryAdapter.add("All Categories");
                categoryAdapter.addAll(data);
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnItemSelected(R.id.select)
    void onItemSelected(int position) {
        final String ITEM = categorySpinner.getItemAtPosition(position).toString();
        if (ITEM.equals("All Categories")) {
            loadData("");
        } else {
            loadData(ITEM);
        }
    }
}
