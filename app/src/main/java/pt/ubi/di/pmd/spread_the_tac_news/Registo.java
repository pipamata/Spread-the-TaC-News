package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Registo extends AppCompatActivity {
    DataBaseHelper mDatabase = new DataBaseHelper(this);
    EditText name;
    EditText password;
    EditText confpassword;
    Button registo;
    Button login;
    List<User> listaUsers =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);

        name=findViewById(R.id.rNome);
        password=findViewById(R.id.rPass);
        confpassword=findViewById(R.id.rConfPass);
        registo=findViewById(R.id.rRegisto);
        login=findViewById(R.id.rLogin);

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
                boolean existeUser = false;
                listaUsers = mDatabase.getAllUsers();

                for (int i = 0; i<listaUsers.size(); i++) {
                    if ((user.equals(listaUsers.get(i).getUser()))) {
                        existeUser = true;
                        break;
                    }
                }

                if (existeUser == false) {
                    if (pass.equals(confpass)) {
                        Toast.makeText(Registo.this, "Registo Concluido", Toast.LENGTH_SHORT).show();
                        mDatabase.adicionarUser(user, pass);
                        Intent loginIn = new Intent(Registo.this, Login.class);
                        startActivity(loginIn);
                    }else{
                        Toast.makeText(Registo.this, "As passwords não combinam", Toast.LENGTH_SHORT).show();
                        password.setText("");
                        confpassword.setText("");
                    }
                }else{
                    Toast.makeText(Registo.this, "Já existe um utilizador com esse Username", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    password.setText("");
                    confpassword.setText("");
                }
            }
        });
    }
}