package com.velichkov.kaloyan.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MovieDetailActivity extends AppCompatActivity {

    TextView titleTextView, genreTextView, artistsTextView, descriptionTextView;
    Button editButton, deleteButton, backButton;
    MovieRepo repo = new MovieRepo(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        genreTextView = (TextView) findViewById(R.id.genreTextView);
        artistsTextView = (TextView) findViewById(R.id.artistsTextView);
        descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        backButton = (Button) findViewById(R.id.backButton);

        editButton.setOnClickListener(onClick);
        deleteButton.setOnClickListener(onClick);
        backButton.setOnClickListener(onClick);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Movie movie = new Movie();
        movie = repo.getMovieById(MainActivity.id);

        titleTextView.setText(movie.title);
        genreTextView.setText(movie.genre);
        artistsTextView.setText(movie.artists);
        descriptionTextView.setText(movie.description);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.editButton :
                    Intent indent = new Intent(getApplicationContext(),MovieDetailEditActivity.class);
                    indent.putExtra("movie_Id", MainActivity.id);
                    startActivity(indent);
                    break;
                case R.id.deleteButton :
                    repo.delete(MainActivity.id);
                    Toast.makeText(getBaseContext(),"Филма е изтрит", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case R.id.backButton : finish(); break;
            }
        }
    };
}
