package com.velichkov.kaloyan.movies;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity  implements android.view.View.OnClickListener{

    Button addButton, exitButton;
    TextView movie_Id;
    static int id;

    @Override
    public void onClick(View view) {
        if (view== findViewById(R.id.addButton)){
            Intent intent = new Intent(this,MovieDetailEditActivity.class);
            intent.putExtra("movie_Id",0);
            startActivity(intent);
        } else {
            finish();
        }
    }

    public void showList(){
        MovieRepo repo = new MovieRepo(this);

        ArrayList<HashMap<String, String>> movieList =  repo.getMovieList();
        ListAdapter adapter = new SimpleAdapter( MainActivity.this,movieList, R.layout.view_movie_entry, new String[] { "id","title","genre"}, new int[] {R.id.movieId, R.id.movieTitle, R.id.movieGenre});
        setListAdapter(adapter);

        if(movieList.size()!=0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    movie_Id = (TextView) view.findViewById(R.id.movieId);
                    String movieId = movie_Id.getText().toString();
                    MainActivity.id = Integer.parseInt(movieId);
                    Intent indent = new Intent(getApplicationContext(),MovieDetailActivity.class);
                    startActivity(indent);
                }
            });
        }else{
            Toast.makeText(this,"Няма филми!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }
}
