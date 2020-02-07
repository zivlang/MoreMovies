package com.example.moremovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class Movie implements Parcelable {

    private String title;
    private int id;
    private int year;
    private String image;
    private String description;
    private String category;

    Movie(){}

    private Movie(Parcel in) {
        title = in.readString();
        id = in.readInt();
        year = in.readInt();
        image = in.readString();
        description = in.readString();
        category = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", year=" + year +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    String getCategory() {
        return category;
    }

    void setCategory(String category) {
        this.category = category;
    }

    int getYear() {
        return year;
    }

    void setYear(int year) {
        this.year = year;
    }

    void setTitle(String title) {
        this.title = title;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String summary) {
        this.description = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeInt(id);
        dest.writeInt(year);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(category);
    }
}
