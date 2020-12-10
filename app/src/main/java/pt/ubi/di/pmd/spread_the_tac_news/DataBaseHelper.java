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

    public DataBaseHelper(@Nullable Context context){
        super(context, "news.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable = "CREATE TABLE " + NEWS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + TITULO + " TEXT, " + CONTEXTO + " TEXT, " + AUTOR + " VARCHAR(30), " + DATA + " VARCHAR(30) )";
        db.execSQL(creatTable);

        //PrePopulate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
        return listaNoticias;
    }
}
