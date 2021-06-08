package com.example.flixterfbu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixterfbu.databinding.ActivityMainBinding;
import com.example.flixterfbu.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsActivity";

    // the movie to display
    Movie movie;

    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView posterBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        posterBackdrop = (ImageView) findViewById(R.id.posterBackdrop);

        LayerDrawable stars = (LayerDrawable) rbVoteAverage.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);


        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        String imageUrl = movie.getBackdrop_path();
        Glide.with(this).load(imageUrl).
                transform(new CenterInside(), new RoundedCorners(24)).
                placeholder(R.drawable.movie_placeholder_vertical).
                into(posterBackdrop);

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVote_average().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        posterBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (movie.getId() == null) {
                    Toast.makeText(MovieDetailsActivity.this, "Error. Cannot find movie.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    i.putExtra("id", movie.getId());
                    startActivity(i);
                }
            }
        });


    }








}