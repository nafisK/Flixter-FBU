package com.example.flixterfbu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixterfbu.databinding.ActivityMovieTrailerBinding;
import com.example.flixterfbu.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    ActivityMovieTrailerBinding binding;
    public static final String TAG = "MovieTrailerActivity";
    String videoKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieTrailerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // temporary test video id -- TODO replace with movie trailer video id
        String videoId = getIntent().getStringExtra("id");
        String URL = String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=a84a9bec1794546328acf362d75d590c", videoId);
//        String URL = String.format("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", videoId);

        AsyncHttpClient client  = new AsyncHttpClient();
        client.get(URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject object = results.getJSONObject(0);
                    videoKey = object.getString("key");
                    Log.d(TAG, object.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }
            // videoKey = iA_wCcG-dQQ
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });



        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                Log.i(TAG, "Video Key: " + videoKey);
                youTubePlayer.cueVideo(videoKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player: " + youTubeInitializationResult);
            }
        });
    }
}