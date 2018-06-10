package com.example.andre.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andre.myapplication.model.Tarefa;
import com.example.andre.myapplication.repo.TarefaRepo;

import java.util.Date;

public class CadastroTarefaActivity extends AppCompatActivity {
    private EditText editTextNomeTarefa;
    private EditText editTextDataEntrega;
    private Button btnSalvar;
    private Button btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);
        editTextNomeTarefa = findViewById(R.id.editTextNomeTarefa);
        editTextDataEntrega = findViewById(R.id.editTextDataCriacao);

        btnSalvar = findViewById(R.id.buttonSalvar);
        btnCancelar = findViewById(R.id.buttonCancelar);

        final Tarefa tarefa = (Tarefa)getIntent().getSerializableExtra("tarefa");
        popularCampos(tarefa);
        final boolean novaTarefa = tarefa == null;


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
        TarefaRepo repo = new TarefaRepo(getApplicationContext());
        repo.create(tarefa);
        Toast.makeText(this,"Nova tarefa criada com sucesso",Toast.LENGTH_LONG).show();
        CadastroTarefaActivity.this.finish();
    }

    private void atualizarTarefa(Tarefa tarefa){
        if(tarefa.getId() != 0){
            TarefaRepo repo = new TarefaRepo(getApplicationContext());
            tarefa.setNome(editTextNomeTarefa.getText().toString());
            tarefa.setDataCriacao(editTextDataEntrega.getText().toString());
            repo.update(tarefa);
            Toast.makeText(this,"Tarefa atualizada com sucesso",Toast.LENGTH_LONG).show();
        }
    }

}
