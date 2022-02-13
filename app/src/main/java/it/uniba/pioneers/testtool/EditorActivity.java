package it.uniba.pioneers.testtool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import it.uniba.pioneers.data.Visita;
import it.uniba.pioneers.data.users.CuratoreMuseale;
import it.uniba.pioneers.data.users.Guida;
import it.uniba.pioneers.sqlite.DbContract;
import it.uniba.pioneers.sqlite.DbHelper;
import it.uniba.pioneers.testtool.databinding.ActivityEditor2Binding;
import it.uniba.pioneers.widget.Node;

public class EditorActivity extends AppCompatActivity {
    public ArrayList<Node> opere;
    private ActivityEditor2Binding binding;

    FirstFragment f = new FirstFragment();
    GrafoFragment s = new GrafoFragment();
    EditorPercorsi e = new EditorPercorsi();

    public DialogNodeInfo d = new DialogNodeInfo();

    Integer state = 0;

    FragmentManager supportFragmentManager;
    FragmentContainerView containerView;



    private void init(){
        Button avanti = findViewById(R.id.changeFragment2);
        Button indietro = findViewById(R.id.changeFragment);

        Button test = findViewById(R.id.tst_info);

        containerView = findViewById(R.id.fragmentContainerView3);
        supportFragmentManager = getSupportFragmentManager();

        supportFragmentManager.beginTransaction()
                .replace(containerView.getId(), f)
                .commit();

        indietro.setOnClickListener(view -> {
            switch (state) {
                case 1:
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.slade_out, R.anim.fade_in, R.anim.slade_out)
                            .replace(containerView.getId(), f)
                            .commit();
                    state = 0;
                    indietro.setVisibility(View.INVISIBLE);
                    avanti.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.slade_out, R.anim.fade_in, R.anim.slade_out)
                            .replace(containerView.getId(), s)
                            .commit();
                    state = 1;
                    indietro.setVisibility(View.VISIBLE);
                    avanti.setVisibility(View.VISIBLE);
                    break;
            }
        });

        avanti.setOnClickListener(view2 -> {
            switch (state) {
                case 0:
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.slade_out, R.anim.fade_in, R.anim.slade_out)
                            .replace(containerView.getId(), s)
                            .commit();
                    state = 1;
                    indietro.setVisibility(View.VISIBLE);
                    avanti.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.slade_out, R.anim.fade_in, R.anim.slade_out)
                            .replace(containerView.getId(), e)
                            .commit();
                    state = 2;

                    indietro.setVisibility(View.VISIBLE);
                    avanti.setVisibility(View.INVISIBLE);
                    break;

            }
        });

        test.setOnClickListener(view3 ->{
            Guida g = new Guida();
            g.setId(1);
            g.setNome("Rino");
            g.setCognome("Pino");
            g.setDataNascita(1639937888767L);
            g.setEmail("pino@rino.cock");
            g.setPassword("dadasdsadasdsasadsad");
            g.setPassword("museo");
            g.setOnline(false);

            g.readDataDb(view3.getRootView().getContext());
            Toast.makeText(this, g.getEmail(), Toast.LENGTH_LONG).show();
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditor2Binding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_editor);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();

        DbHelper dbHelper = new DbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.AreaEntry.COLUMN_NOME, "GINO");
        values.put(DbContract.AreaEntry.COLUMN_ZONA, 1);

        long newRowId = db.insert(DbContract.AreaEntry.TABLE_NAME, null, values);


    }
}