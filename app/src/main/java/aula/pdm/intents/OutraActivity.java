package aula.pdm.intents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OutraActivity extends AppCompatActivity {
    private TextView recebido;
    private EditText retorno;
    private Button retornar;

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outra);

        recebido = findViewById(R.id.recebidoTv);
        retorno = findViewById(R.id.retornoEt);
        retornar = findViewById(R.id.retornarBt);

        getSupportActionBar().setTitle("Outra Activity");
        getSupportActionBar().setSubtitle("Recebe e retorna valor");

        Bundle parametrosBundle = getIntent().getExtras();
        if(parametrosBundle != null){
           String textoRecebido = parametrosBundle.getString(MainActivity.PARAMETRO, "");
           recebido.setText(textoRecebido);
        }



    }
}