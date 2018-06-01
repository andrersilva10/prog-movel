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
    private EditText editTextDataCriacao;
    private Button btnSalvar;
    private Button btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);
        editTextNomeTarefa = findViewById(R.id.editTextNomeTarefa);
        editTextDataCriacao = findViewById(R.id.editTextDataCriacao);

        btnSalvar = findViewById(R.id.buttonSalvar);
        btnCancelar = findViewById(R.id.buttonCancelar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastroTarefaActivity.this.finish();
            }
        });
    }


    public void cadastrar(){
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(editTextNomeTarefa.getText().toString());
        Toast.makeText(this,editTextDataCriacao.getText().toString(),Toast.LENGTH_LONG);
        Date date = new Date();
        TarefaRepo repo = new TarefaRepo(getApplicationContext());
        repo.create(tarefa);
    }
}
