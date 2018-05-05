package hr.ferit.mahmutaksakalli.androidwebxmlformat.networking;


import hr.ferit.mahmutaksakalli.androidwebxmlformat.model.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsAPI {

    @GET("rss")
    Call<SearchResult> getNews();

    @GET("rss/{category}")
    Call<SearchResult> getNewsByCategory(@Path("category") String category);
}
