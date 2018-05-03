package hr.ferit.mahmutaksakalli.androidwebxmlformat.networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitHelper {

    private static final String BASE_URL = "https://www.bug.hr/";
    private static Retrofit sRetrofitInstance = null;
    private static NewsAPI sNewsAPI = null;

    public static NewsAPI getsNewsApi(){

        if(sNewsAPI == null){
            sRetrofitInstance = getRetrofit();
            sNewsAPI = sRetrofitInstance.create(NewsAPI.class);
        }
        return sNewsAPI;

    }

    private static Retrofit getRetrofit() {
        if(sRetrofitInstance == null) {
            sRetrofitInstance = new Retrofit.Builder()
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .build();
        }
        return sRetrofitInstance;
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }


}
