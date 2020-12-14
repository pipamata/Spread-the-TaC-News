package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class News extends AppCompatActivity {

    LinearLayout linearLayoutActivityNew;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        username = getIntent().getStringExtra("Username");
        final DataBaseHelper db = new DataBaseHelper(News.this);
        linearLayoutActivityNew = findViewById(R.id.ll_activity_news);
        List<Noticia> todasNoticias;

        todasNoticias = db.getAllNoticias();

        for (int i = todasNoticias.size()-1; i >= 0  ; i--){
            final LinearLayout linLay = (LinearLayout) getLayoutInflater().inflate(R.layout.item_news,linearLayoutActivityNew,false);
            linearLayoutActivityNew.addView(linLay);

            TextView tvnoticia = linLay.findViewById(R.id.txtv_teste);
            tvnoticia.setText(todasNoticias.get(i).getContexto());

            TextView tvtitulo = linLay.findViewById(R.id.txtv_titulo);
            tvtitulo.setText(todasNoticias.get(i).getTitulo());

            TextView tvautor = linLay.findViewById(R.id.txtv_autor);
            tvautor.append(" "+todasNoticias.get(i).getAutor());

            TextView tvdata = linLay.findViewById(R.id.txtv_data);
            tvdata.append(" "+ todasNoticias.get(i).getData());

            final EditText numNota = linLay.findViewById(R.id.edtxt_nota);
            final int idNoticia = i + 1;

            final Button share = linLay.findViewById(R.id.button_share);

            // botao para fazer share das noticias
            // -- criar a string com toda a info a ser enviada
            final String titulo = todasNoticias.get(i).getTitulo();
            final String noticia = todasNoticias.get(i).getContexto();
             share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharing = new Intent(Intent.ACTION_SEND);
                    sharing.setType("text/plain");
                    sharing.putExtra(Intent.EXTRA_SUBJECT, titulo);
                    sharing.putExtra(Intent.EXTRA_TEXT, noticia);
                    // texto que aparece no menu de escolha da aplicação
                    startActivity(Intent.createChooser(sharing, "Compartilhar notícia via:"));
                }
            });

            final Button btn = linLay.findViewById(R.id.button_news_id);
            if (db.isNewCheck(username,idNoticia)){

                btn.setVisibility(View.GONE);
                numNota.setVisibility(View.GONE);
            }
            else
            {
                String buttonName = getString(R.string.avaliar);
                btn.setText(buttonName);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!numNota.getText().toString().isEmpty()){
                            int myNum = Integer.parseInt(numNota.getText().toString());

                            if( myNum > 5 || myNum< 0){
                                Toast.makeText(News.this,"Precisa colocar um nota entre 0 e 5",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(News.this, "Avaliaste esta noticia com :"+numNota.getText().toString(), Toast.LENGTH_LONG).show(); // cada botao tem um set on click listener diferente e sao criados dinamicamente ao mesmo tempos que as noticias
                                SendToRemoteDBNoticias(username,idNoticia, numNota.getText().toString()); // em vez de renato username
                                db.UpdateNewsStatus(username,idNoticia);
                                btn.setVisibility(View.GONE);
                                numNota.setVisibility(View.GONE);
                            }
                        }
                        else {
                            Toast.makeText(News.this,"Selecione uma nota de 0 a 5",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    public  void SendToRemoteDBNoticias(final String user, int id, final String nota ){
        final String noticiaID = ""+id;
        String url = "http://10.0.2.2:80/TAC/add_note.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(News.this, response.trim(),Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(News.this, error.toString(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("user",user);
                params.put("id",noticiaID);
                params.put("nota",nota);
                return params;
            }
        };

        RequestQueue requestQueue  = Volley.newRequestQueue(News.this);
        requestQueue.add(stringRequest);
    }
}