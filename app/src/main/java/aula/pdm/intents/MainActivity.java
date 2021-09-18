package aula.pdm.intents;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText parametro;
    private TextView retorno;

    private ActivityResultLauncher<Intent> outraActivityResultLauncher;

    public static String PARAMETRO = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parametro = findViewById(R.id.parametroEt);
        retorno = findViewById(R.id.retornoTv);

        getSupportActionBar().setTitle("Tratando intents");
        getSupportActionBar().setSubtitle("Principais tipos");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.outraActivityMi){
            //Abrir outra activity
            Intent outraActivityIntent = new Intent(this, OutraActivity.class);

            Bundle parametrosBundle = new Bundle();
            parametrosBundle.putString(PARAMETRO, String.valueOf(parametro.getText()));

            outraActivityIntent.putExtras(parametrosBundle);

            outraActivityResultLauncher.launch(outraActivityIntent);
            return true;
        }
        else if(item.getItemId() == R.id.viewMi){
            //Abrir um navegador
            return true;
        }else if (item.getItemId() == R.id.callMi){
            //Abrir uma chamada
            return true;
        }else if(item.getItemId() == R.id.dialMi){
            //Abrir uma discador
            return true;
        }else if(item.getItemId() == R.id.pickMi){
            //Abrir uma imagem
            return true;
        }
        else if(item.getItemId() == R.id.chooserMi){
            //Abrir listas de aplicativos
            return true;
        }else
            return false;
    }
}