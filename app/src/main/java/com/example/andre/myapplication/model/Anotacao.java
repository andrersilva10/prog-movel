package com.example.andre.myapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Anotacao implements Serializable {
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField
    private String texto;

    @DatabaseField(foreign=true)
    private Tarefa tarefa;

    //region GETS e SETS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa){
        this.tarefa = tarefa;
    }
    //endregion

    @Override
    public String toString() {
        return getTexto();
    }
}
