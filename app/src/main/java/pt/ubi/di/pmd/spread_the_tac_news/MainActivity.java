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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
// fim teste

public class MainActivity extends AppCompatActivity {

    public static TextView data; // é mesmo preciso ser estatico?
    EditText editText;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = findViewById(R.id.textView);
        button = findViewById(R.id.button);



          button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getWebsite();
                FetchData process = new FetchData();
                process.execute();
            }
        });


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