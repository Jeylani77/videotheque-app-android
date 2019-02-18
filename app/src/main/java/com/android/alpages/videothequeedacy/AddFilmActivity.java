package com.android.alpages.videothequeedacy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.alpages.videothequeedacy.Model.Film;

import java.util.UUID;

import io.realm.Realm;

public class AddFilmActivity extends AppCompatActivity {

    private Realm realm;
    EditText edt_nom,edt_synopsis,edt_annee,edt_type,edt_prix;
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        edt_nom = (EditText) findViewById(R.id.edt_nom);
        edt_type = (EditText) findViewById(R.id.edt_type);
        edt_synopsis = (EditText) findViewById(R.id.edt_synopsis);
        edt_annee = (EditText) findViewById(R.id.edt_annee);
        edt_prix = (EditText) findViewById(R.id.edt_prix);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        realm.init(this);

        realm = Realm.getDefaultInstance();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Film film = realm.createObject(Film.class, UUID.randomUUID().toString());
                        film.setNom(String.valueOf(edt_nom.getText()));
                        film.setSynopsis(String.valueOf(edt_synopsis.getText()));
                        film.setAnnee(String.valueOf(edt_annee.getText()));
                        film.setPrix(String.valueOf(edt_prix.getText()));
                        film.setType(String.valueOf(edt_type.getText()));
                        film.setTimestamp(System.currentTimeMillis());
                    }
                });
            }
        });


    }
}
