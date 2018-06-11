package com.example.andre.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andre.myapplication.model.Tarefa;
import com.example.andre.myapplication.repo.TarefaRepo;

import java.util.Calendar;
import java.util.Date;

public class CadastroTarefaActivity extends AppCompatActivity {
    private EditText editTextNomeTarefa;
    private EditText editTextDataEntrega;
    private Button btnSalvar;
    private Button btnCancelar;
    private Tarefa tarefa;

    public SharedPreferences sharedPreferences;

    //region eventos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);

        editTextNomeTarefa = findViewById(R.id.editTextNomeTarefa);
        editTextDataEntrega = findViewById(R.id.editTextDataCriacao);
        btnSalvar = findViewById(R.id.buttonSalvar);
        btnCancelar = findViewById(R.id.buttonCancelar);
        if(getIntent().getBooleanExtra("temPreferencias",false)){
            getPreferences();
        }
        tarefa = (Tarefa)getIntent().getSerializableExtra("tarefa");
        popularCampos(tarefa);
        final boolean novaTarefa = tarefa == null;
        setTitle(novaTarefa ? (R.string.nova_tarefa) : (R.string.editar_tarefa));

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(novaTarefa)
                    salvarTarefa();
                else
                    atualizarTarefa(tarefa);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroTarefaActivity.this.finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStop(){
        super.onStop();
        setPreferences(tarefa != null);
        /*
        if(tarefa == null){
            setPreferences(false);
        }else{
            setPreferences(true);
        }
        */
    }
    //endregion eventos

    private void setPreferences(boolean ehEdicao){
        if(ehEdicao){
            SharedPreferences.Editor editor = this.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE).edit();
            editor.remove("nomeTarefa");
            editor.remove("dataEntrega");
            editor.apply();
        }else{
            this.sharedPreferences = getSharedPreferences("MinhasPreferencias",MODE_PRIVATE);
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putString("nomeTarefa",editTextNomeTarefa.getText().toString());
            editor.putString("dataEntrega",editTextDataEntrega.getText().toString());
            editor.apply();
        }
    }

    private void getPreferences(){
        this.sharedPreferences = getSharedPreferences("MinhasPreferencias",MODE_PRIVATE);
        this.editTextNomeTarefa.setText(this.sharedPreferences.getString("nomeTarefa",""));
        this.editTextDataEntrega.setText(this.sharedPreferences.getString("dataEntrega",""));
    }
    private void popularCampos(Tarefa tarefa){
        if(tarefa == null) return;
        editTextNomeTarefa.setText(tarefa.getNome());
        editTextDataEntrega.setText(tarefa.getDataEntrega());

    }

    private void salvarTarefa(){
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(editTextNomeTarefa.getText().toString());
        tarefa.setDataEntrega(editTextDataEntrega.getText().toString());
        tarefa.setDataCriacao(Calendar.getInstance().getTime().toString());
        TarefaRepo repo = new TarefaRepo(getApplicationContext());
        repo.create(tarefa);
        Toast.makeText(this, R.string.nova_tarefa_criada_com_sucesso,Toast.LENGTH_LONG).show();
        CadastroTarefaActivity.this.finish();
    }

    private void atualizarTarefa(Tarefa tarefa){
        if(tarefa.getId() != 0){
            TarefaRepo repo = new TarefaRepo(getApplicationContext());
            tarefa.setNome(editTextNomeTarefa.getText().toString());
            tarefa.setDataCriacao(editTextDataEntrega.getText().toString());
            repo.update(tarefa);
            Toast.makeText(this, R.string.tarefa_atualizada_com_sucesso,Toast.LENGTH_LONG).show();
        }
    }

}
