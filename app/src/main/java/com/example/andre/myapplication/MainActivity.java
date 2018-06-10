package com.example.andre.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andre.myapplication.db.DatabaseHelper;
import com.example.andre.myapplication.model.Tarefa;
import com.example.andre.myapplication.repo.TarefaRepo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List tarefas;
    ListView listViewTarefas;
    private ArrayAdapter<Tarefa> listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE).edit().clear().apply();
        if(contemPreferencias()){
            Intent i = new Intent(this,CadastroTarefaActivity.class);
            i.putExtra("temPreferencias", true);
            startActivity(i);
        }
        listViewTarefas = findViewById(R.id.listViewTarefas);
        popularLista();
        listViewTarefas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewTarefas.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                boolean selecionado = listViewTarefas.isItemChecked(position);

                View view = listViewTarefas.getChildAt(position);

                if (selecionado){
                    view.setBackgroundColor(Color.LTGRAY);
                }else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                int totalSelecionados = listViewTarefas.getCheckedItemCount();

                if (totalSelecionados > 0){

                    mode.setTitle("Lorem ipsum");
                }

                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_tarefa_selecionada, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                if (listViewTarefas.getCheckedItemCount() > 1){
                    menu.getItem(0).setVisible(false);
                }else{
                    menu.getItem(0).setVisible(true);
                }

                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menuItemEditar:

                        for (int posicao = listViewTarefas.getChildCount(); posicao >= 0; posicao--){

                            if (listViewTarefas.isItemChecked(posicao)){

                                Tarefa tarefa = (Tarefa)(listViewTarefas.getAdapter().getItem(posicao));
                                Intent i = new Intent(MainActivity.this,CadastroTarefaActivity.class);
                                i.putExtra("tarefa",tarefa);
                                startActivity(i);
                            }
                        }

                        mode.finish();
                        return true;

                    case R.id.menuItemRemover:
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        /*
        listViewTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa tarefa = (Tarefa)parent.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this,CadastroTarefaActivity.class);
                i.putExtra("tarefa",tarefa);
                startActivity(i);
            }
        });
        */

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
    @Override
    public void onResume(){
        super.onResume();
        popularLista();
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
        if(lista != null && lista.size() > 0){
            listaAdapter = new ArrayAdapter<Tarefa>(this,
                    android.R.layout.simple_list_item_1,
                    lista != null ? lista : new ArrayList<Tarefa>());

            listViewTarefas.setAdapter(listaAdapter);
        }

    }

    private boolean contemPreferencias(){
        SharedPreferences preferences = getSharedPreferences("MinhasPreferencias",MODE_PRIVATE);
        String nomeTarefa = preferences.getString("nomeTarefa","");
        String dataEntrega = preferences.getString("dataEntrega","");
        boolean temPreferencias = nomeTarefa.isEmpty() == false || dataEntrega.isEmpty() == false;
        return temPreferencias;
        /*
        Intent i;
        if(temPreferencias){
            i = new Intent(this,CadastroTarefaActivity.class);
            i.putExtra("temPreferencias", true);
            startActivity(i);
        }
        */
    }
}
