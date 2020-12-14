package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    TextView welcome, noNet;
    Button button;
    String username;
    List<Noticia> listaNoticias = new ArrayList<>();
    List<User> listaUsers = new ArrayList<>();
    DataBaseHelper db  = new DataBaseHelper(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        welcome= findViewById(R.id.txtv_welcome);
        username = getIntent().getStringExtra("Username");
        noNet = findViewById(R.id.txtv_noInternet);

        welcome.setText("Bem-Vindo "+username);

        if (isNetWorkAvaible()) {
            GetNoticias();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    db.AddMultNews(listaNoticias);
                    listaUsers = db.getAllUsers();
                    listaNoticias = db.getAllNoticias();
                    db.AddNewsCheck(listaUsers,listaNoticias);

                }
            }, 500);

        } else {
            noNet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public  void GetNoticias(){
        final List<Noticia> noticias = new ArrayList<>();
        String url = "http://10.0.2.2:80/TAC/get_news.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i =0 ; i< response.length();i++){
                                JSONObject noticia = response.getJSONObject(i);
                                String titulo = noticia.getString("titulo");
                                String descricao = noticia.getString("descricao");
                                String data = noticia.getString("data");
                                String autor = noticia.getString("autor");
                                noticias.add(new Noticia(titulo,descricao,autor,data));
                            }
                            listaNoticias = noticias;
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

        RequestQueue mQueue  = Volley.newRequestQueue(MainActivity.this);
        mQueue.add(jsonArrayRequest);

    }
}