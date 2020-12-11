package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        List<Noticia> todasNoticias;

        List<LinearLayout> listaLayouts = new ArrayList<>(); // SE NAO UTILIZAR APAGAR

        todasNoticias = db.getAll(); // metodo retorna uma List/arraylist de Noticias(neste caso so strings), que vem da base de dados.

        for (int i = todasNoticias.size()-1; i >= 0  ; i--){

            final LinearLayout linLay = (LinearLayout) getLayoutInflater().inflate(R.layout.item_news,linearLayoutActivityNew,false);
            listaLayouts.add(linLay);

            linearLayoutActivityNew.addView(linLay); // adicionamos o layout do item_news onde temos o formato das noticias guardado no Feed de noticias...

            TextView tvnoticia = linLay.findViewById(R.id.txtv_teste);
            tvnoticia.setText(todasNoticias.get(i).getContexto());

            TextView tvtitulo = linLay.findViewById(R.id.txtv_titulo);
            tvtitulo.setText(todasNoticias.get(i).getTitulo());

            TextView tvautor = linLay.findViewById(R.id.txtv_autor);
            tvautor.setText("Autor: "+todasNoticias.get(i).getAutor());

            TextView tvdata = linLay.findViewById(R.id.txtv_data);
            tvdata.setText("Data: "+ todasNoticias.get(i).getData());

            final EditText numNota = linLay.findViewById(R.id.edtxt_nota);
            final int idNoticia = i + 1;


            final Button btn = linLay.findViewById(R.id.button_news_id) ; // pode ser necessario criar um array de botoes de modo a poder saber qual a noticia que foi carregada.
            String placeHolder = getString(R.string.avaliar)+" "+i;
            btn.setText(placeHolder);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int myNum = Integer.parseInt(numNota.getText().toString());

                    if( myNum > 5 || myNum< 0){
                        Toast.makeText(News.this,"Precisa colocar um nota entre 0 e 5",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(News.this, numNota.getText().toString() + " id Noticia:" + idNoticia, Toast.LENGTH_LONG).show(); // cada botao tem um set on click listener diferente e sao criados dinamicamente ao mesmo tempos que as noticias
                    }
                }
            });
        }
        // exemplo de como aceder a um LinearLayout já impresso
        //TextView teste = listaLayouts.get(0).findViewById(R.id.txtv_teste);
        //teste.setText(getString(R.string.resultou));
    }


}