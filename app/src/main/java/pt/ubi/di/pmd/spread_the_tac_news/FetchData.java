package pt.ubi.di.pmd.spread_the_tac_news;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void,Void,Void> {
    String data ="";

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





        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (MainActivity.data.toString().isEmpty()){
            MainActivity.data.setText("VAZIO");
        }
        else {
            MainActivity.data.setText(data);
        }

    }

    // criar um metodo que devolve um array com as noticias para a pagina onde vao ser impressas e guardadas na base de dados local.
}
