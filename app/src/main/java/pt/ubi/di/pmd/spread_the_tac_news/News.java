package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class News extends AppCompatActivity {

    LinearLayout linearLayoutActivityNew;
    LinearLayout linearLayoutItemNews;
    TextView textViewItemNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // (activity news )Atividade principal onde vamos ver as noticias
        linearLayoutActivityNew = findViewById(R.id.ll_activity_news);

        // cada item é criado no item_news e fazemos o inflate paara o activity news
        linearLayoutItemNews = (LinearLayout) getLayoutInflater().inflate(R.layout.item_news,null);
        // adicionamos o item ao feed de noticias
        linearLayoutActivityNew.addView(linearLayoutItemNews);

        // aqui modificamos e mudamos o valor do texto e de todos os componentes que acharmos convenientes
        // atraves de uma base de dados
        textViewItemNews = findViewById(R.id.txtv_teste);
        textViewItemNews.setText("Teste");







    }
}