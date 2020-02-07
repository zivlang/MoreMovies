package com.example.moremovies;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class MoviesListFragment extends Fragment implements MoviesAdapter.ItemClickListener{

    private GetJson getJson;
    private ArrayList<Movie> moviesList, searchResults;
    private String searchText;
    private Movie currentMovie;
    private EditText searchEditText;
    private String jsonString;
    private int length;
    private int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_movies, container, false);

        Context context = getActivity();

        try {
            String url = "https://x-mode.co.il/exam/allMovies/allMovies.txt";
            getJson = new GetJson(url);
            jsonString = getJson.jsonString;
            moviesList = getJSONArray();
        } catch (ExecutionException | JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        searchEditText = rootView.findViewById(R.id.searchTextId);
        Button searchButton = rootView.findViewById(R.id.searchButtonId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        final MoviesAdapter moviesAdapter = new MoviesAdapter(context, this);
        moviesAdapter.setClickListener(this);

        RecyclerView moviesRV = rootView.findViewById(R.id.moviesRVId);
        moviesRV.setLayoutManager(linearLayoutManager);
        moviesRV.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration;
        if (context != null) {
            dividerItemDecoration = new DividerItemDecoration(context, linearLayoutManager.getOrientation());
            moviesRV.addItemDecoration(dividerItemDecoration);
        }

        moviesRV.setAdapter(moviesAdapter);
        moviesAdapter.attachMoviesList(moviesList);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = searchEditText.getText().toString();
                length = searchText.length();
                if(!searchText.matches("[ A-Za-z]+") && length > 0){
                    Toast.makeText(getActivity(), "Use only letters and spaces", Toast.LENGTH_LONG).show();
                    erase();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.isEmpty()){
                    Toast.makeText(getActivity(), "The search box is empty", Toast.LENGTH_LONG).show();
                }

                searchResults = new ArrayList<>();

                for(int i = 0; i < moviesList.size(); i++){
                    if(moviesList.get(i).getTitle().toLowerCase().contains(searchText.toLowerCase())){
                        searchResults.add(moviesList.get(i));
                    }
                }
                if(searchResults.size() == 0){
                    Toast.makeText(getActivity(), "No movie title was found", Toast.LENGTH_LONG).show();
                }
                moviesAdapter.attachMoviesList(searchResults);
                moviesAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    private void erase() {
        StringBuilder sb = new StringBuilder(searchText);
        sb = sb.deleteCharAt(searchText.length() - 1);
        searchEditText.setText(sb.toString());
        searchEditText.setSelection(length);
    }

    private ArrayList<Movie> getJSONArray() throws JSONException, ExecutionException, InterruptedException {
        final ArrayList<Movie> moviesArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray resultsArray = jsonObject.getJSONArray("movies");

        for (int i = 0; i < resultsArray.length(); i++) {

            Movie movie = new Movie();

            JSONObject movieObject = resultsArray.getJSONObject(i);
            movie.setTitle(movieObject.getString("name"));
            movie.setYear(movieObject.getInt("year"));
            movie.setCategory(movieObject.getString("category"));
            movie.setId(movieObject.getInt("id"));
            movieId = movie.getId();
            getJson = new GetJson("https://x-mode.co.il/exam/descriptionMovies/" + movieId + ".txt");
            JSONObject secondMovieObject = new JSONObject(getJson.jsonString);
            movie.setDescription(secondMovieObject.getString("description"));
            movie.setImage(secondMovieObject.getString("imageUrl"));

            moviesArrayList.add(movie);
        }
        //sort movies by ascending year
        Collections.sort(moviesArrayList, new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                return o1.getYear() - o2.getYear();
            }
        });
        return moviesArrayList;
    }

    void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    @Override
    public void onItemClick(Movie movie) {
        MovieFragment movieFragment = new MovieFragment();
        if (getFragmentManager() != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putParcelable("currentMovie", currentMovie);
            movieFragment.setArguments(bundle);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(R.id.fragments_container, movieFragment);
            ft.addToBackStack(null).commit();
        }
    }
}