package pt.ubi.di.pmd.spread_the_tac_news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String NOTICIA = "NOTICIA";
    public static final String NEWS = "NEWS";

    public DataBaseHelper(@Nullable Context context){
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatTable = "CREATE TABLE " + NEWS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTICIA + " TEXT )";
        db.execSQL(creatTable);

        PrePopulate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void PrePopulate(SQLiteDatabase db){

        ContentValues cv = new ContentValues();

        cv.put(NOTICIA, "Teste123");

          db.insert(NEWS, null , cv);





    }
    public void addOne(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NOTICIA, s);

        db.insert(NEWS, null , cv);



    }
    public List<String> getAll(){ // Mudar List<String> assim que a base de dados estiver definida.
        List<String> noticias = new ArrayList<>();
        String query = "SELECT * FROM "+ NEWS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){ // cursos.moveToFirst move o ponteiro para a primeira linha da base de dados
            do {
                String noticia = cursor.getString(1);
                noticias.add(noticia);
            }while(cursor.moveToNext()); // cursor.MoveToNext move o ponteiro para a proxima linha da base de dados se existir
        }

        cursor.close();
        return noticias;

    }
}
