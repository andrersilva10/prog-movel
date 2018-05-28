package com.example.andre.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andre.myapplication.db.DatabaseHelper;
import com.example.andre.myapplication.model.Tarefa;
import com.example.andre.myapplication.repo.TarefaRepo;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List tarefas;
    ListView listViewTarefas;
    private ArrayAdapter<Tarefa> listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewTarefas = findViewById(R.id.listViewTarefas);

        listViewTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = (Tarefa)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, tarefa.getNome(),Toast.LENGTH_LONG).show();
            }
        });

        TarefaRepo repo = new TarefaRepo(getApplicationContext());
        List tarefas = repo.findAll();

        popularLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.menuItemNovo:
                i = new Intent(this,CadastroTarefaActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    private void popularLista(){

        List<Tarefa> lista = null;

        try {
            DatabaseHelper conexao = new DatabaseHelper(this);

            lista = conexao.getTarefaDao()
                    .queryBuilder()
                    .orderBy("nome", true)
                    .query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        listaAdapter = new ArrayAdapter<Tarefa>(this,
                android.R.layout.simple_list_item_1,
                lista);

        listViewTarefas.setAdapter(listaAdapter);
    }
}
