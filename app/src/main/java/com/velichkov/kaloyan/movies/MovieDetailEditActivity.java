package com.velichkov.kaloyan.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MovieDetailEditActivity extends AppCompatActivity implements android.view.View.OnClickListener{

    Button saveButton;
    Button closeButton;
    EditText titleEditText;
    EditText artistsEditText;
    EditText movieDescriptionEditText;
    Spinner dropDown;
    private int _Movie_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_edit);

        saveButton = (Button) findViewById(R.id.saveButton);
        closeButton = (Button) findViewById(R.id.closeButton);

        titleEditText = (EditText) findViewById(R.id.titleTextView);
        artistsEditText = (EditText) findViewById(R.id.artistsEditText);
        movieDescriptionEditText = (EditText) findViewById(R.id.movieDescriptionEditText);

        saveButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);


        _Movie_Id =0;
        Intent intent = getIntent();
        _Movie_Id =intent.getIntExtra("movie_Id", 0);
        MovieRepo repo = new MovieRepo(this);
        Movie movie = new Movie();
        movie = repo.getMovieById(_Movie_Id);

        movieDescriptionEditText.setText(movie.description);
        artistsEditText.setText(movie.artists);
        titleEditText.setText(movie.title);

        dropDown = (Spinner) findViewById(R.id.dropDown);
        String[] genres = new String[]{"", "Документален", "Екшън", "Ужаси", "Комедия", "Фантастика"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genres);
        dropDown.setAdapter(adapter);

        if(movie.genre!=null) {
            String currentGenre = movie.genre;
            if (!currentGenre.equals(null)) {
                int spinnerPosition = adapter.getPosition(currentGenre);
                dropDown.setSelection(spinnerPosition);
            }
        }
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.saveButton)){
            MovieRepo repo = new MovieRepo(this);
            Movie movie = new Movie();
            movie.description= movieDescriptionEditText.getText().toString();
            movie.artists=artistsEditText.getText().toString();
            movie.genre=dropDown.getSelectedItem().toString();
            movie.title=titleEditText.getText().toString();
            movie.movie_ID=_Movie_Id;

            if (_Movie_Id==0){
                _Movie_Id = repo.insert(movie);
                Toast.makeText(this,"Филма е добавен",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                repo.update(movie);
                Toast.makeText(this,"Редакцията е записана",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else if (view== findViewById(R.id.closeButton)){
            finish();
        }
    }

}