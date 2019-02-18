package com.android.alpages.videothequeedacy.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.android.alpages.videothequeedacy.MainActivity;
import com.android.alpages.videothequeedacy.Model.Film;
import com.android.alpages.videothequeedacy.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private Context context;
    private MainActivity activity;

    private List<Film> films;

    public FilmAdapter(MainActivity activity, OrderedRealmCollection<Film> data) {
        this.films = data;
        this.activity = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_row, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Film talent = films.get(i);
        viewHolder.txt_nomFilm.setText(talent.getNom());
        viewHolder.txt_typeFilm.setText(talent.getType());
        viewHolder.txt_anneeFilm.setText(talent.getAnnee());
        viewHolder.txt_prixFilm.setText(talent.getPrix());
        viewHolder.txt_synopsis.setText(talent.getSynopsis());



    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.txt_nomFilm) TextView txt_nomFilm;
        @BindView(R.id.txt_typeFilm) TextView txt_typeFilm;
        @BindView(R.id.txt_anneeFilm) TextView txt_anneeFilm;
        @BindView(R.id.txt_prixFilm) TextView txt_prixFilm;
        @BindView(R.id.txt_synopsis) TextView txt_synopsis;






        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


}
