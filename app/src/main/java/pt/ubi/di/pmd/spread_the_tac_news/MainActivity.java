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
// fim teste

public class MainActivity extends AppCompatActivity {

    TextView data;
    Button button;
    TextView welcome;
    String username;

    FetchData process = new FetchData();
    List<Noticia> listaNoticias = new ArrayList<>();
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
            process.execute();
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listaNoticias = process.NoticiasSucesso();
                    db.AddMultNews(listaNoticias);
                    Toast.makeText(MainActivity.this,"Sucesso "+listaNoticias.size(),Toast.LENGTH_SHORT).show();
                }
            }, 500);

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

/*
    private class FetchData extends AsyncTask<Void,Void,Void> {
        String data ="";
        String dataParsed ="";
        String singleParsed ="";


        @Override
        protected Void doInBackground(Void... voids) {

            // modificar o modo de armaznamento do json file.

            try {


                URL url = new URL(" http://10.0.2.2:80/TAC/get_product_details.php"); // equivale ao localHost/NomedaPastaNoXamp/NomedoFicheiroPhp


                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while (line != null){

                    line = bufferedReader.readLine(); // juntar estas duas linhas
                    data += line;
                }

                List<Noticia> listaNoticias = new ArrayList<>();
                String titulo;
                String contexto;

                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0 ; i < jsonArray.length(); i++ ){

                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    titulo =(String) jsonObject.get("Titulo");
                    contexto = (String) jsonObject.get("Contexto");

                    listaNoticias.add(new Noticia(titulo,contexto));

                }





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DataBaseHelper db = new DataBaseHelper(MainActivity.this);
            if (MainActivity.data.toString().isEmpty()){
                MainActivity.data.setText("VAZIO");
            }
            else {
                MainActivity.data.setText(dataParsed);
            }


        }
    }

/*
    private void getWebsite(){
        //se nao correr numa outra thread nao funciona
        //ver o porquê mais tarde !!!!!!!!!!!!!

        new Thread(new Runnable() {
            @Override
            public void run() {
                //precisa de um try catch
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect("https://www.facebook.com/ESAFETYglobal/").get();
                    String title = doc.title();
                    Elements links = doc.select("div[dir=auto]");
                    builder.append(title).append("\n");

                    // nao consigo ir buscar o texto
                    for (Element link : links) {
                        builder.append("\n").append("Texto: ").append(link.text());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Text1.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

 */
}