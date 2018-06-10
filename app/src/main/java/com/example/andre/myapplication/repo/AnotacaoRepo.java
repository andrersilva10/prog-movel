package com.example.andre.myapplication.repo;

import android.content.Context;

import com.example.andre.myapplication.db.DatabaseHelper;
import com.example.andre.myapplication.db.DatabaseManager;
import com.example.andre.myapplication.model.Anotacao;

import java.sql.SQLException;
import java.util.List;

public class AnotacaoRepo implements  Crud {

    private DatabaseHelper helper;

    public AnotacaoRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) {
        int index = -1;

        Anotacao object = (Anotacao) item;
        try {
            index = helper.getAnotacaoDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int update(Object item) {
        int index = -1;

        Anotacao object = (Anotacao) item;

        try {
            index = helper.getAnotacaoDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;

        Anotacao object = (Anotacao) item;

        try {
            index = helper.getAnotacaoDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public Object findById(int id) {
        Anotacao wishList = null;
        try {
            wishList = helper.getAnotacaoDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishList;
    }

    @Override
    public List<?> findAll() {
        List<Anotacao> items = null;
        try {
            items =  helper.getAnotacaoDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
