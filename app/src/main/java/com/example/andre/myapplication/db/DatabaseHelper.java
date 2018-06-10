package com.example.andre.myapplication.db;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andre.myapplication.model.Anotacao;
import com.example.andre.myapplication.model.Tarefa;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.dao.Dao;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "teste.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION =13;

    // the DAO object we use to access the SimpleData table
    //pressure
    private Dao<Tarefa, Integer> tarefaDao = null;
    private Dao<Anotacao, Integer> anotacaoDao = null;

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Tarefa.class);
            TableUtils.createTable(connectionSource,Anotacao.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {

            Dao<Tarefa, Integer> dao = getTarefaDao();
            TableUtils.dropTable(connectionSource,Tarefa.class,true);
            TableUtils.dropTable(connectionSource,Anotacao.class,true);
            onCreate(db,connectionSource);
        } catch (Exception e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }

    }

    public Dao<Tarefa, Integer> getTarefaDao() {
        if (null == tarefaDao) {
            try {
                tarefaDao = getDao(Tarefa.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return tarefaDao;
    }

    public Dao<Anotacao, Integer> getAnotacaoDao(){
        if(null == anotacaoDao){
            try {
                anotacaoDao = getDao(Anotacao.class);

            }catch (java.sql.SQLException e){
                e.printStackTrace();
            }
        }
        return anotacaoDao;
    }
}