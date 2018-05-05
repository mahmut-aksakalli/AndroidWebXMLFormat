package hr.ferit.mahmutaksakalli.androidwebxmlformat.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private NewsListViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mViewModel = ViewModelProviders.of(this)
                .get(NewsListViewModel.class);

        loadData();
        setUpRecyclerView();

    }

    void loadData() {

        RetrofitHelper
                .getsNewsApi()
                .getNews()
                .enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call,
                                           Response<SearchResult> response) {

                        SearchResult result = response.body();
                        mViewModel.setData(result.getChannel().getNews());

                        Log.d("allthingok", "data loaded");

                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {
                        Log.d("thereiserror" , t.getLocalizedMessage());
                    }
                });
        }

    private void setUpRecyclerView() {
        LinearLayoutManager linearLayout =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration divider =
                new DividerItemDecoration(this, linearLayout.getOrientation());

        NewsAdapter adapter = new NewsAdapter(
                new ArrayList<NewsInfo>());

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
}
