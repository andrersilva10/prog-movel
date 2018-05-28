package com.example.andre.myapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;

@DatabaseTable
public class Tarefa {
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField
    public String nome;

    @DatabaseField
    private Date dataCriacao;

    @DatabaseField
    private Date dataEntrega;

    @DatabaseField
    private boolean terminada;
    @Override
    public String toString() {
        return nome;
    }
}
