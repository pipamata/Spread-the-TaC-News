package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView Text1;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Text1 = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });
    }

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
}