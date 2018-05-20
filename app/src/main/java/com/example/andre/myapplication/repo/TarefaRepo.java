package com.example.andre.myapplication.repo;

import android.content.Context;
import java.util.List;
import com.example.andre.myapplication.db.DatabaseHelper;
import com.example.andre.myapplication.db.DatabaseManager;
import com.example.andre.myapplication.model.Tarefa;

import java.sql.SQLException;

public class TarefaRepo implements  Crud{
    private DatabaseHelper helper;

    public TarefaRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;

        Tarefa object = (Tarefa) item;
        try {
            index = helper.getTarefaDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {
        int index = -1;

        Tarefa object = (Tarefa) item;

        try {
            index = helper.getTarefaDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;

        Tarefa object = (Tarefa) item;

        try {
            index = helper.getTarefaDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public Object findById(int id) {

        Tarefa wishList = null;
        try {
            wishList = helper.getTarefaDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }


    @Override
    public List<?> findAll() {

        List<Tarefa> items = null;

        try {
            items =  helper.getTarefaDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
