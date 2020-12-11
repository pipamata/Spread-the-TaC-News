package pt.ubi.di.pmd.spread_the_tac_news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    DataBaseHelper mDatabase = new DataBaseHelper(this);
    List<User> listaUsers =  new ArrayList<>();
    private EditText name;
    private EditText password;
    private Button login;
    private Button registar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText)findViewById(R.id.etName);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.btnLogin);
        registar = (Button)findViewById(R.id.lRegisto);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate(name.getText().toString() , password.getText().toString());
            }
        });

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registoIn = new Intent(Login.this, Registo.class);
                startActivity(registoIn);
            }
            });
    }

    private void Validate(String userName, String userPassword){
        listaUsers = mDatabase.getAllUsers();
        boolean userExist=false;
        Log.d("Login", listaUsers.size()+"");
        for (int i = 0; i<listaUsers.size(); i++) {
            if ((userName.equals(listaUsers.get(i).getUser())) && (userPassword.equals(listaUsers.get(i).getPassword()))) {
                Toast.makeText(this, "Dados de Login corretos", Toast.LENGTH_SHORT).show();
                userExist = true;
                break;
            }
            Log.d("Login", String.valueOf(userExist));
        }

        if (userExist){
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.putExtra("Username", userName);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Password ou Username incorretos", Toast.LENGTH_SHORT).show();
        }

    }
}