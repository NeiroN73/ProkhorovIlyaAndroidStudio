package com.example.coffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context){
        super(context, "Login.db", null, 1);   //конструктор
    }

    @Override
    public void onCreate(SQLiteDatabase myDBUser) {  //вызывается при первом создании базы данных
        myDBUser.execSQL("create Table users (username Text primary key, password Text)");  //создаем бд

    }

    @Override
    public void onUpgrade(SQLiteDatabase myDBUser, int i, int i1) {  //вызывается при модификации базы данных
        myDBUser.execSQL("drop Table if exists users");     //удаляем нашу таблицу
    }


    //метод для добавления новой записи в бд
    public Boolean insertData(String username, String password){
        SQLiteDatabase myBDUser = this.getWritableDatabase();   //получаем нашу бд
        ContentValues contentValues = new ContentValues();      // будем добавлять эту строку в нашу бд

        //Класс ContentValues используется для добавления новых строк в таблицу.
        // Каждый объект этого класса представляет собой одну строку таблицы и выглядит как ассоциативный массив с
        // именами столбцов и значениями, которые им соответствуют.
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myBDUser.insert("users", null, contentValues); //добавляем данные в таблицу

        //возращает 1 если успешно добавилось
        return result != -1;
    }

    //метод для проверки существует пользлватель с таким именем в базе или нет
    public Boolean checkUsername(String username){
        SQLiteDatabase myBDUser = this.getWritableDatabase();

        //Курсор – это особый временный объект SQL, предназначенный для использования в программах и хранимых процедурах.
        // С его помощью можно в цикле пройти по результирующему набору строк запроса,
        // по отдельности считывая и обрабатывая каждую его строку.

        Cursor cursor = myBDUser.rawQuery("select * from users where username=?", new String[] {username});

        //getCount() — возвращает количество строк в результирующем наборе данных;
        if (cursor.getCount() > 0){
            return true;
        }
        else return false;
    }


    //метод для проверки имени пользлвателя и его пароля
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase myBDUser = this.getWritableDatabase();
        Cursor cursor = myBDUser.rawQuery("select * from users where username=? and password=?", new String[] {username, password});
        if (cursor.getCount() > 0){
            return true;
        }
        else return false;
    }

}
