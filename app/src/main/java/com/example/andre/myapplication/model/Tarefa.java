package com.example.andre.myapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Tarefa {
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField
    public String nome;


    @Override
    public String toString() {
        return nome;
    }
}
