package it.uniba.pioneers.data.users;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.uniba.pioneers.data.serer.Server;
import it.uniba.pioneers.sqlite.DbContract;
import it.uniba.pioneers.sqlite.DbHelper;


public class CuratoreMuseale {
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'", Locale.getDefault());

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) throws ParseException {
        this.dataNascita = CuratoreMuseale.format.parse(dataNascita);
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setDataNascita(int dataNascita) {
        this.dataNascita = new Date(dataNascita);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String email;
    private String password;
    private String propic;
    private int zona;

    //ONLINE STATE
    private boolean online;

    public CuratoreMuseale(){
        setId(0);
        setNome("");
        setCognome("");
        setEmail("");
        setPassword("");
        setPropic(null);
        setZona(0);

        setOnline(true);
    }

    public CuratoreMuseale(JSONObject data) throws JSONException, ParseException {
        setId(data.getInt(DbContract.CuratoreMusealeEntry.COLUMN_ID));
        setNome(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_NOME));
        setCognome(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_COGNOME));
        setDataNascita(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_DATA_NASCITA));
        setEmail(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_EMAIL));
        setPassword(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_PASSWORD));
        setPropic(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_PROPIC));
        setZona(data.getInt(DbContract.CuratoreMusealeEntry.COLUMN_ZONA));

        setOnline(true);
    }

    public void setDataFromJSON(JSONObject data) throws JSONException, ParseException {
        setId(data.getInt(DbContract.CuratoreMusealeEntry.COLUMN_ID));
        setNome(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_NOME));
        setCognome(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_COGNOME));
        //this.setDataNascita(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_DATA_NASCITA));
        setEmail(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_EMAIL));
        setPassword(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_PASSWORD));
        setPropic(data.getString(DbContract.CuratoreMusealeEntry.COLUMN_PROPIC));
        setZona(data.getInt(DbContract.CuratoreMusealeEntry.COLUMN_ZONA));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject tmp = new JSONObject();

        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_ID, getId());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_NOME, getNome());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_COGNOME, getCognome());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_DATA_NASCITA, getDataNascita());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_EMAIL, getEmail());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_PASSWORD, getPassword());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_PROPIC, getPropic());
        tmp.put(DbContract.CuratoreMusealeEntry.COLUMN_ZONA, getZona());

        return tmp;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    //DB METHOD
    public void readDataDb(Context context){
        if(isOnline()){
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Server.getUrl() + "/curatore-museale/read/";

            JSONObject data = new JSONObject();
            try {
                data.put("id", getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CuratoreMuseale self = this;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Boolean status =  response.getBoolean("status");
                                if(status){
                                    self.setDataFromJSON(response.getJSONObject("data"));
                                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Non è avenuto nessun cambio dati, verifica che i valori siano validi", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Il server non risponde", Toast.LENGTH_SHORT).show();
                    System.out.println(error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }else{
            //TODO SQLITE3
        }
    }

    public void createDataDb(Context context){
        if(isOnline()){
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Server.getUrl() + "/curatore-museale/create/";

            JSONObject data = new JSONObject();
            try {
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_NOME, getNome());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_COGNOME, getCognome());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_DATA_NASCITA, getDataNascita());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_EMAIL, getEmail());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_PASSWORD, getPassword());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_PROPIC, getPropic().toString());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_ZONA, getZona());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CuratoreMuseale self = this;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Boolean status =  response.getBoolean("status");
                                if(status){
                                    self.setDataFromJSON(response.getJSONObject("data"));
                                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Non è avenuto nessun cambio dati, verifica che i valori siano validi", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Il server non risponde", Toast.LENGTH_SHORT).show();
                    System.out.println(error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }else{

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_NOME, getNome());
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_COGNOME, getCognome());
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_DATA_NASCITA, getDataNascita().getTime());
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_EMAIL, getEmail());
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_PASSWORD, getPassword());
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_PROPIC, getPropic().toString());
            values.put(DbContract.CuratoreMusealeEntry.COLUMN_ZONA, getZona());


            long newRowId = db.insert(DbContract.CuratoreMusealeEntry.TABLE_NAME, null, values);

        }
    }

    public void updateDataDb(Context context){
        if(isOnline()){
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Server.getUrl() + "/curatore-museale/update/";

            JSONObject data = new JSONObject();
            try {
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_ID, getId());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_NOME, getNome());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_COGNOME, getCognome());
                //data.put(DbContract.CuratoreMusealeEntry.COLUMN_DATA_NASCITA, getDataNascita());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_EMAIL, getEmail());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_PASSWORD, getPassword());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_PROPIC, getPropic());
                data.put(DbContract.CuratoreMusealeEntry.COLUMN_ZONA, getZona());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CuratoreMuseale self = this;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Boolean status =  response.getBoolean("status");
                                if(status){
                                    self.setDataFromJSON(response.getJSONObject("data"));
                                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Non è avenuto nessun cambio dati, verifica che i valori siano validi", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Il server non risponde", Toast.LENGTH_SHORT).show();
                    System.out.println(error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }else{
            //TODO SQLITE3
        }
    }

    public void deleteDataDb(Context context){
        if(isOnline()){
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = Server.getUrl() + "/curatore-museale/delete/";

            JSONObject data = new JSONObject();
            try {
                data.put("id", getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CuratoreMuseale self = this;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Boolean status =  response.getBoolean("status");
                                if(status){
                                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Non è avenuto nessun cambio dati, verifica che i valori siano validi", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Il server non risponde", Toast.LENGTH_SHORT).show();
                    System.out.println(error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        }else{
            //TODO SQLITE3
        }
    }
}
