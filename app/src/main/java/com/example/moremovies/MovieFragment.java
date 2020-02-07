package com.example.moremovies;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class MovieFragment extends Fragment {

    private Movie currentMovie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        final Bundle bundle = getArguments();

        if (bundle != null) {
            currentMovie = bundle.getParcelable("currentMovie");
        }

        TextView titleView = rootView.findViewById(R.id.movieTitleId);
        TextView descriptionView = rootView.findViewById(R.id.descriptionId);
        TextView categoryView = rootView.findViewById(R.id.categoryId);
        TextView yearView = rootView.findViewById(R.id.yearId);
        ImageView imageView = rootView.findViewById(R.id.movieImageId);

        titleView.setText(currentMovie.getTitle());
        yearView.setText(String.valueOf(currentMovie.getYear()));
        categoryView.setText(currentMovie.getCategory());
        descriptionView.setText(currentMovie.getDescription());

        Glide.with(MovieFragment.this)
                .load(currentMovie.getImage())
                .apply(new RequestOptions())
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        Toast.makeText(getActivity(), "Error: image URL issue", Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

        return rootView;
    }
}
