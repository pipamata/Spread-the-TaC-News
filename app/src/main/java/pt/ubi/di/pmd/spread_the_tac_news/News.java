package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class News extends AppCompatActivity {

    LinearLayout linearLayoutActivityNew;
    LinearLayout linearLayoutItemNews;
    LinearLayout linearLayoutItemNews2;
    TextView textViewItemNews;
    TextView textViewItemNews2;
    ImageView newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        DataBaseHelper db = new DataBaseHelper(News.this);

        // (activity news )Atividade principal onde vamos ver as noticias
        linearLayoutActivityNew = findViewById(R.id.ll_activity_news);

        List<String> todasNoticias;
        List<LinearLayout> listaLayouts = new ArrayList<>(); // lista onde guardamos todos os layouts de modo a poder aceder mais tarde se necessario.

        todasNoticias = db.getAll(); // metodo retorna uma List/arraylist de Noticias(neste caso so strings), que vem da base de dados.

        for (int i = todasNoticias.size()-1; i >= 0  ; i--){

            LinearLayout linLay = (LinearLayout) getLayoutInflater().inflate(R.layout.item_news,linearLayoutActivityNew,false);
            listaLayouts.add(linLay);

            linearLayoutActivityNew.addView(linLay); // adicionamos o layout do item_news onde temos o formato das noticias guardado no Feed de noticias...

            TextView tv = linLay.findViewById(R.id.txtv_teste);
            tv.setText(todasNoticias.get(i));

            final Button btn = linLay.findViewById(R.id.button_news_id) ; // pode ser necessario criar um array de botoes de modo a poder saber qual a noticia que foi carregada.
           String placeHolder = getString(R.string.avaliar)+" "+i;
            btn.setText(placeHolder);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(News.this,btn.getText().toString(),Toast.LENGTH_LONG).show(); // cada botao tem um set on click listener diferente e sao criados dinamicamente ao mesmo tempos que as noticias

                }
            });
        }
        // exemplo de como aceder a um LinearLayout já impresso
        TextView teste = listaLayouts.get(0).findViewById(R.id.txtv_teste);
        teste.setText(getString(R.string.resultou));
    }
}