package com.example.andre.myapplication;

import android.content.Intent;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.andre.myapplication.db.DatabaseHelper;
import com.example.andre.myapplication.model.Anotacao;
import com.example.andre.myapplication.model.Tarefa;
import com.example.andre.myapplication.repo.AnotacaoRepo;
import com.example.andre.myapplication.repo.TarefaRepo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnotacoesActivity extends AppCompatActivity {
    private Tarefa tarefa;
    List<Anotacao> anotacoes;
    private ArrayAdapter<Anotacao> listaAdapter;
    private ListView listViewAnotacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anotacoes);
        listViewAnotacoes = findViewById(R.id.listViewAnotacoes);
        tarefa = (Tarefa)getIntent().getSerializableExtra("tarefa");
        anotacoes = getAnotacoes();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewAnotacoes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listViewAnotacoes.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                boolean selecionado = listViewAnotacoes.isItemChecked(position);

                View view = listViewAnotacoes.getChildAt(position);

                if (selecionado){
                    view.setBackgroundColor(Color.LTGRAY);
                }else{
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                int totalSelecionados = listViewAnotacoes.getCheckedItemCount();

                if (totalSelecionados > 0){

                    mode.setTitle(R.string.selecionado);
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
                if (listViewAnotacoes.getCheckedItemCount() > 1){
                    menu.getItem(0).setVisible(false);
                }else{
                    menu.getItem(0).setVisible(true);
                }

                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menuItemRemover:
                        for (int posicao = listViewAnotacoes.getChildCount(); posicao >= 0; posicao--){
                            if (listViewAnotacoes.isItemChecked(posicao)){
                                Anotacao anotacao = (Anotacao) (listViewAnotacoes.getAdapter().getItem(posicao));
                                AnotacaoRepo repo = new AnotacaoRepo(AnotacoesActivity.this);
                                repo.delete(anotacao);
                                popularLista();
                                Toast.makeText(AnotacoesActivity.this, R.string.anotacao_removida_com_sucesso,Toast.LENGTH_LONG).show();
                            }
                        }

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_anotacao,menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent i;
        switch (id){
            case R.id.menuItemNovaAnotacao:
                showInputDialog();
                break;
        }
        return true;
    }

    protected void showInputDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(AnotacoesActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_anotacao, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AnotacoesActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Anotacao anotacao = new Anotacao();
                        anotacao.setTexto(editText.getText().toString());
                        anotacao.setTarefa(tarefa);
                        tarefa.addAnotacao(anotacao);
                        AnotacaoRepo repo = new AnotacaoRepo(AnotacoesActivity.this);
                        repo.insertRaw(anotacao);
                        popularLista();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void popularLista(){
        anotacoes = getAnotacoes();

        if(anotacoes != null && anotacoes.size() > 0){
            listaAdapter = new ArrayAdapter<Anotacao>(this,
                    android.R.layout.simple_list_item_1,
                    anotacoes != null ? anotacoes : new ArrayList<Anotacao>());
            listViewAnotacoes.setAdapter(listaAdapter);
        }

    }

    private List getAnotacoes(){
        return new AnotacaoRepo(this).findByTarefaId(tarefa.getId());
    }
}
