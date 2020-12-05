package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // new branch
        // será que é desta?
        Toast.makeText(this, "TEste!!", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "O meu teste é mais fofo que o teu :b!!", Toast.LENGTH_SHORT).show();
    }
}