package com.android.alpages.videothequeedacy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.alpages.videothequeedacy.Adapter.FilmAdapter;
import com.android.alpages.videothequeedacy.Model.Film;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    RealmResults<Film> films;
     FilmAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.film_list);

        realm.init(this);

        realm = Realm.getDefaultInstance();


        films = realm.where(Film.class).findAll();
        films = films.sort("timestamp");

        final FilmAdapter adapter = new FilmAdapter(this, films);


        listerFilm();

    }


    public void listerFilm(){
        adapter = new FilmAdapter(this, films);
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nomEditText = new EditText(MainActivity.this);
                nomEditText.setHint("Nom ");
                layout.addView(nomEditText);

                final EditText synopsisEditText = new EditText(MainActivity.this);
                synopsisEditText.setHint("Synopsis");
                layout.addView(synopsisEditText);


                final EditText anneeEditText = new EditText(MainActivity.this);
                anneeEditText.setHint("Année ");
                layout.addView(anneeEditText);

                final EditText typeEditText = new EditText(MainActivity.this);
                typeEditText.setHint("Type");
                layout.addView(typeEditText);


                final EditText prixEditText = new EditText(MainActivity.this);
                prixEditText.setHint("Prix ");
                layout.addView(prixEditText);

                nomEditText.setText(films.get(position).getNom());
                synopsisEditText.setText(films.get(position).getSynopsis());
                anneeEditText.setText(films.get(position).getAnnee());
                typeEditText.setText(films.get(position).getType());
                prixEditText.setText(films.get(position).getPrix());


                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Modifier Film")
                        .setView(layout)
                        .setPositiveButton("Sauvegarder", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 5/4/17 Save Edited Task
                                changeFilm(films.get(position).getId(),String.valueOf(nomEditText.getText()),
                                        String.valueOf(synopsisEditText.getText()),String.valueOf(anneeEditText.getText()),
                                        String.valueOf(typeEditText.getText()),String.valueOf(prixEditText.getText()));
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: 5/4/17 Delete Task
                                deleteFilm(films.get(position).getId());
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .create();
                dialog.show();


            }
        }));


    }


    private void deleteFilm(final String filmId) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Film.class).equalTo("id", filmId)
                        .findFirst()
                        .deleteFromRealm();
            }
        });
    }

    private void changeFilm(final String filmId, final String nom, final String synopsis,
                            final String annee, final String type, final String prix) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Film film = realm.where(Film.class).equalTo("id", filmId).findFirst();
                film.setNom(nom);
                film.setSynopsis(synopsis);
                film.setAnnee(annee);
                film.setType(type);
                film.setPrix(prix);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ajout) {
            Intent myIntent = new Intent(MainActivity.this, AddFilmActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        listerFilm();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
