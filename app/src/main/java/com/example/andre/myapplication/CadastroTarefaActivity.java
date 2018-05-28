package com.example.andre.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.andre.myapplication.model.Tarefa;
import com.example.andre.myapplication.repo.TarefaRepo;

public class CadastroTarefaActivity extends AppCompatActivity {
    EditText editTextNomeTarefa;
    Button btnSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tarefa);
        editTextNomeTarefa = findViewById(R.id.editTextNomeTarefa);
        btnSalvar = findViewById(R.id.buttonSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }


    public void cadastrar(){
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(editTextNomeTarefa.getText().toString());
        TarefaRepo repo = new TarefaRepo(getApplicationContext());
        repo.create(tarefa);
    }
}
