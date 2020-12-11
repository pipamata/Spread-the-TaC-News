package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registo extends AppCompatActivity {
    DataBaseHelper mDatabase = new DataBaseHelper(this);
    private EditText name;
    private EditText password;
    private EditText confpassword;
    private Button registo;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        name=(EditText)findViewById(R.id.rNome);
        password=(EditText)findViewById(R.id.rPass);
        confpassword=(EditText)findViewById(R.id.rConfPass);
        registo=(Button)findViewById(R.id.rRegisto);
        login=(Button)findViewById(R.id.rLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIn = new Intent(Registo.this, Login.class);
                startActivity(loginIn);
            }
        });
        registo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = name.getText().toString();
                String pass = password.getText().toString();
                String confpass = confpassword.getText().toString();

                if (pass.equals(confpass)){
                    Toast.makeText(Registo.this, "Registo Concluido", Toast.LENGTH_SHORT).show();
                    mDatabase.adicionarUser(user, pass);
                    Intent loginIn = new Intent(Registo.this, Login.class);
                    startActivity(loginIn);
                }else{
                    Toast.makeText(Registo.this, "As passwords não combinam", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}