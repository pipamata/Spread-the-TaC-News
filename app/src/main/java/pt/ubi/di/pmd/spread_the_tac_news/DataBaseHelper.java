package pt.ubi.di.pmd.spread_the_tac_news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String NEWS = "NEWS";
    public static final String TITULO = "TITULO";
    public static final String CONTEXTO = "CONTEXTO";
    public static final String AUTOR = "AUTOR";
    public static final String DATA = "DATA";
    public static final String USERS = "USERS";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String CHECKED = "CHECKED";
    public static final String NEWSID = "NEWSID";
    public static final String STATUS = "STATUS";
    public static final String NOME = "NOME";

    public DataBaseHelper(@Nullable Context context){
        super(context, "news.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableNews = "CREATE TABLE " + NEWS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TITULO + " TEXT, " + CONTEXTO + " TEXT, " + AUTOR + " VARCHAR(30), " + DATA + " VARCHAR(30) )";
        String createTableUsers = "CREATE TABLE " + USERS + " (" + USERNAME + " VARCHAR(15) PRIMARY KEY, " + PASSWORD + " VARCHAR(30))";
        String createTableCheckedNews = "CREATE TABLE " + CHECKED + " (" + NOME + " VARCHAR(15), " + NEWSID + " INTEGER, " + STATUS + " boolean  default 0 )";

        db.execSQL(createTableNews);
        db.execSQL(createTableUsers);
        db.execSQL(createTableCheckedNews);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddNewsCheck(List<User> listaUsers , List<Noticia> listaNoticias){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        // falta impedir que sejam inseridas  coisas repetidas
        for (int i = 0; i < listaUsers.size(); i++){

            String query = "SELECT * FROM "+CHECKED+ " WHERE "+NOME+" == '"+listaUsers.get(i).getUser()+"';";

            Cursor cursor = db.rawQuery(query,null);
            Log.d("NewCh","CURSOR: "+cursor.getCount()+"");
            if(cursor.getCount()<=0){

                Log.d("NewCh", "USER: "+listaUsers.get(i).getUser());
                Log.d("NewCh", "NOTSIZE: "+listaNoticias.size());

                for (int k =0 ; k < listaNoticias.size(); k++){
                    Log.d("NewCh", "Titulo: "+listaNoticias.get(k).getTitulo());
                    cv.put(NOME, listaUsers.get(i).getUser());

                    int z = k+1;
                    Log.d("NewCh", "ID: "+z);
                    cv.put(NEWSID, z);
                    cv.put(STATUS, 0);

                    db.insert(CHECKED, null , cv);

                }
            }
            cursor.close();

            Log.d("NewCh","I: "+i+"");
        }
        Log.d("NewCh","FUNCIONOU PA");
        db.close();
    }

    public  boolean isNewCheck(String nomeUtilizador, int id){

        SQLiteDatabase db = this.getWritableDatabase();


        String query = "SELECT * FROM "+CHECKED+ " WHERE "+NOME+" == '"+nomeUtilizador+"' AND "+NEWSID+" == "+id+";";

        Cursor cursor = db.rawQuery(query,null);


        if (cursor.moveToFirst()){
            if (cursor.getInt(2) == 0){
                Log.d("BaseDD","NAO VOTOU ESSA NOTICIA");
                db.close();
                return  false;
            }else {
                Log.d("BaseDD"," VOTOU ESSA NOTICIA");
                db.close();
                return  true;
            }

        }
        else{
            Log.d("USERNAMEPA"," Nao tem nada la dentro");
            return false;
        }


    }
    public  void UpdateNewsStatus(String testeUtilizador, int id){
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "UPDATE "+CHECKED+" SET "+STATUS+" = 1 WHERE "+NOME+" == '"+testeUtilizador+"' AND NEWSID == "+id+";";

        db.execSQL(query);
        db.close();



    }
    public void PrePopulate(SQLiteDatabase db){ // apagar no fim

        ContentValues cv = new ContentValues();
        cv.put(TITULO, "Titulo");
        cv.put(CONTEXTO, "Contexto");

        db.insert(NEWS, null , cv);

    }

    public void addOne(Noticia noticia){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TITULO, noticia.getTitulo());
        cv.put(CONTEXTO, noticia.getContexto());
        cv.put(AUTOR, noticia.getAutor());
        cv.put(DATA, noticia.getData());

        db.insert(NEWS, null , cv);
        db.close();

    }
    public  void AddMultNews(List<Noticia> listaNoticias){
        int idUltimaNoticia;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String query = "SELECT * FROM "+ NEWS;


        Cursor cursor = db.rawQuery(query,null);
        Log.d("BaseD",cursor.getCount()+"");

        if(cursor.getCount()> 0){
            cursor.moveToLast();
            idUltimaNoticia = cursor.getInt(0); // id da ultima noticia.
            Log.d("BaseD",idUltimaNoticia+"");
        }
        else {
            idUltimaNoticia = 0;
            Log.d("BaseD",idUltimaNoticia+"");
        }


        for (int i=idUltimaNoticia; i < listaNoticias.size() ; i++){
            cv.put(TITULO, listaNoticias.get(i).getTitulo());
            cv.put(CONTEXTO, listaNoticias.get(i).getContexto());
            cv.put(AUTOR, listaNoticias.get(i).getAutor());
            cv.put(DATA, listaNoticias.get(i).getData());

            db.insert(NEWS, null , cv);
        }


        db.close();
    }
    public List<Noticia> getAll(){
        List<Noticia> listaNoticias = new ArrayList<>();
        String query = "SELECT * FROM "+ NEWS;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){ // cursos.moveToFirst move o ponteiro para a primeira linha da base de dados
            do {

                String titulo = cursor.getString(1);
                String contexto = cursor.getString(2);
                String autor = cursor.getString(3);
                String data = cursor.getString(4);
                listaNoticias.add(new Noticia(titulo,contexto,autor,data));

            }while(cursor.moveToNext()); // cursor.MoveToNext move o ponteiro para a proxima linha da base de dados se existir
        }

        cursor.close();
        db.close();
        return listaNoticias;
    }

    //USERS

    public void adicionarUser(String nome, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(USERNAME, nome);
        valores.put(PASSWORD, password);

        db.insert(USERS,null, valores);
        db.close();

    }

    public List<User> getAllUsers(){
        List<User> listaUsers= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ USERS;

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){ // cursos.moveToFirst move o ponteiro para a primeira linha da base de dados
            do {

                String user = cursor.getString(0);
                String password = cursor.getString(1);

                listaUsers.add(new User(user, password));

            }while(cursor.moveToNext()); // cursor.MoveToNext move o ponteiro para a proxima linha da base de dados se existir
        }

        cursor.close();
        db.close();
        return listaUsers;
    }
}
