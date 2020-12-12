package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;
/*
import android.os.Bundle;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
*/

// teste
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
// fim teste

public class MainActivity extends AppCompatActivity {

    public TextView data; //
    EditText editText;
    Button button;
    TextView welcome;
    String username;

   // FetchData process = new FetchData();
    List<Noticia> listaNoticias = new ArrayList<>();
    List<User> listaUsers = new ArrayList<>();
    DataBaseHelper db  = new DataBaseHelper(MainActivity.this);






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        welcome= findViewById(R.id.txtv_welcome);
        username = getIntent().getStringExtra("Username");

        welcome.setText("Welcome "+username);

        if (isNetWorkAvaible()) {
            Log.d("TemNet", "Temo net");

            GetNoticias();


                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        db.AddMultNews(listaNoticias);

                        listaUsers = db.getAllUsers();
                        listaNoticias = db.getAll();
                        Log.d("USERNAMEPA","SizeNoticias:  "+listaNoticias.size());
                        Log.d("USERNAMEPA","SizeUser:  "+listaUsers.size());

                        db.AddNewsCheck(listaUsers,listaNoticias);


                        Toast.makeText(MainActivity.this,"Sucesso "+listaNoticias.size(),Toast.LENGTH_SHORT).show();
                    }
                }, 500);











        } else {
            Log.d("TemNet", "Nao temo net");


        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verificar se a base de dados local tem noticias
                // se sim entao envia para a pagina
                Intent intent = new Intent(MainActivity.this, News.class);
                intent.putExtra("Username",username);
                startActivity(intent);
            }
        });





    }

    public boolean isNetWorkAvaible(){

        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null){
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }


    }

    public  boolean GetNoticias(){
        final List<Noticia> teste = new ArrayList<>();
        String url = "http://10.0.2.2:80/TAC/get_product_details.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i =0 ; i< response.length();i++){
                                JSONObject noticia = response.getJSONObject(i);

                                String titulo = noticia.getString("titulo");
                                Log.d("PORRA",titulo);
                                String descricao = noticia.getString("descricao");
                                String data = noticia.getString("data");
                                String autor = noticia.getString("autor");
                                teste.add(new Noticia(titulo,descricao,autor,data));
                            }
                            Log.d("PORRA","teste: "+teste.size());
                            listaNoticias = teste;
                            Log.d("PORRA","ListaNoticias: "+teste.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // Access the RequestQueue through your singleton class.
        // MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        RequestQueue mQueue  = Volley.newRequestQueue(MainActivity.this);
        mQueue.add(jsonArrayRequest);

        return  true;
    }
}