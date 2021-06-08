package com.example.flixterfbu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixterfbu.adapters.MovieAdapter;
import com.example.flixterfbu.databinding.ActivityMainBinding;
import com.example.flixterfbu.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a84a9bec1794546328acf362d75d590c";
    public static final String TAG = "MainActivity";

    List<Movie> movies;
    MovieAdapter adapter;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        // layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        movies = new ArrayList<>();

        // Create the adapter & Recycler view
        adapter = new MovieAdapter(this, movies);
        rvMovies = findViewById(R.id.rvMovies);

        // Set the adapter on the recycler view
        rvMovies.setAdapter(adapter);

        // Set a layout Manager
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client  = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results" + results);
                    movies.addAll(Movie.fromJsonArray(results));
                    Log.i(TAG, "movies" + movies.size());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure", throwable);


            }
        });



    }





}