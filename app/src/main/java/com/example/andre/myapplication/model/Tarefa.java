package com.example.andre.myapplication.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.zip.CheckedOutputStream;

@DatabaseTable
public class Tarefa implements Serializable{
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField
    private String nome;

    @DatabaseField
    private String dataCriacao;

    @DatabaseField
    private String dataEntrega;

    @DatabaseField
    private boolean terminada;

    @ForeignCollectionField(eager=false)
    private Collection<Anotacao> anotacoes;

    //region GETS e SETS
    public String getNome() {
        return nome;
    }

    public Collection<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    //endregion

    @Override
    public String toString() {
        return getNome();
    }

    public void addAnotacao(Anotacao anotacao){
        if(anotacoes == null) anotacoes = new ArrayList<>();
        anotacoes.add(anotacao);
    }
}
