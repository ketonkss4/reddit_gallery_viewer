package com.imgur.buzzfeeddemo.network;

import com.imgur.buzzfeeddemo.AppConfig;
import com.imgur.buzzfeeddemo.models.Gallery;
import com.imgur.buzzfeeddemo.models.GalleryItem;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Kevin Moturi on 6/3/2016.
 *
 */
public class MainService {

    private NetworkService networkService;

    private OkHttpClient getAuthClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Interceptor authInterceptor = chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", "Client-ID ceb06e455ea5d99")
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        };
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(authInterceptor);
        return httpClient.build();
    }

    public MainService() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getAuthClient())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }

    private interface NetworkService {
        @GET("gallery/r/{subreddit}")
        Observable<Gallery> getSubredditGallery(@Path("subreddit") String subreddit);
    }

    public Observable<Gallery> getSubredditGallery(String subreddit){
        return networkService.getSubredditGallery(subreddit);
    }

}
