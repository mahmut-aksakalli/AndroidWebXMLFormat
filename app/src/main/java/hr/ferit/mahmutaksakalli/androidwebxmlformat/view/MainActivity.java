package hr.ferit.mahmutaksakalli.androidwebxmlformat.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.R;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.NewsInfo;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.SearchResult;
import hr.ferit.mahmutaksakalli.androidwebxmlformat.networking.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.myText) TextView myText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RetrofitHelper
                .getsNewsApi()
                .getNews()
                .enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        SearchResult result = response.body();
                        List<NewsInfo> resultNews = result.getChannel().getNews();
                        for(NewsInfo newData : resultNews){
                            myText.setText(newData.getTitle());
                        }

                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {
                        Log.d("thereiserror" , t.getLocalizedMessage());
                    }
                });
    }
}
