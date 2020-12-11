package pt.ubi.di.pmd.spread_the_tac_news;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed ="";
    String singleParsed ="";
    List<Noticia> listaNoticias = new ArrayList<>();


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

            String titulo;
            String contexto;
            String date;
            String autor;

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                titulo =(String) jsonObject.get("titulo");
                contexto = (String) jsonObject.get("descricao");
                date =(String) jsonObject.get("data");
                autor = (String) jsonObject.get("autor");

                listaNoticias.add(new Noticia(titulo,contexto,autor,date));

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



    }

    public List<Noticia> NoticiasSucesso(){


        return listaNoticias;
    }
}
