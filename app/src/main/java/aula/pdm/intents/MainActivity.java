package aula.pdm.intents;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Intent.ACTION_CHOOSER;
import static android.content.Intent.ACTION_PICK;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_TITLE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText parametro;
    private TextView retorno;

    private ActivityResultLauncher<Intent> outraActivityResultLauncher;
    private ActivityResultLauncher<String> requisicaoPermissaoActivityResultLauncher;
    private ActivityResultLauncher<Intent> selecionarImagemActivityResultLauncher;
    private ActivityResultLauncher<Intent> escolherAplicativoActivityResultLauncher;

    public static String PARAMETRO = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parametro = findViewById(R.id.parametroEt);
        retorno = findViewById(R.id.retornoTv);

        getSupportActionBar().setTitle("Tratando intents");
        getSupportActionBar().setSubtitle("Principais tipos");


        outraActivityResultLauncher = registerForActivityResult(
                 new ActivityResultContracts.StartActivityForResult(),
                 new ActivityResultCallback<ActivityResult>() {

                     public void onActivityResult(ActivityResult result){
                         if(result.getResultCode() == Activity.RESULT_OK){
                             result.getData().getStringExtra(PARAMETRO).contains(retorno.getText());
                         }
                    }
                }
        );


       requisicaoPermissaoActivityResultLauncher = registerForActivityResult(
               new ActivityResultContracts.RequestPermission(),
               new ActivityResultCallback<Boolean>() {
                   @Override
                   public void onActivityResult(Boolean concedida) {
                       if(!concedida){
                           //requisitar permissão para o usuário
                           requisitarPermissaoLigacao();
                       }else{
                           chamarTelefone();
                       }
                   }
               }
       );


        selecionarImagemActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        visualizarImagem(result);
                    }
                }
        );


        escolherAplicativoActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        visualizarImagem(result);
                    }
                }
        );

    }

    public void visualizarImagem(ActivityResult resultado){
        if(resultado.getResultCode() == RESULT_OK){
            Intent visualizarImagemIntent = new Intent(ACTION_VIEW);
            visualizarImagemIntent.setData(resultado.getData().getData());
            startActivity(visualizarImagemIntent);
        }
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
            String url;
            String it = parametro.getText().toString();
            if(!it.toLowerCase().contains("http[s]?")){
                url = "http://"+it;
            }else{
                url = it;
            }

            Intent siteIntent = new Intent(ACTION_VIEW, Uri.parse(url));
            startActivity(siteIntent);
            return true;

        }else if (item.getItemId() == R.id.callMi){
            //Abrir uma chamada
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    //requisitar a permissão para o usuário
                    requisitarPermissaoLigacao();
                }else{
                    //chamar o telefone
                    chamarTelefone();
                }
            }else{
                //chamar o telefone direto pois, permissão foi dada durante processo de instalação.
                chamarTelefone();
            }
            return true;
        }else if(item.getItemId() == R.id.dialMi){
            //Abrir uma discador
            Intent discadorIntent = new Intent(Intent.ACTION_DIAL);
            discadorIntent.setData(Uri.parse("tel: " + parametro.getText()));
            startActivity(discadorIntent);

            return true;
        }else if(item.getItemId() == R.id.pickMi){
            //Abrir uma imagem
            selecionarImagemActivityResultLauncher.launch(prepararImagemIntent());

            return true;
        }
        else if(item.getItemId() == R.id.chooserMi){
            //Abrir listas de aplicativos
            Intent escolherActivityIntent = new Intent(ACTION_CHOOSER);
            escolherActivityIntent.putExtra(Intent.EXTRA_INTENT, prepararImagemIntent());
            escolherActivityIntent.putExtra(EXTRA_TITLE, "Escolha um aplicativo");
            escolherAplicativoActivityResultLauncher.launch(escolherActivityIntent);

            return true;
        }

        return false;
    }

    public Intent prepararImagemIntent(){
        Intent pegarImagemIntent = new Intent(ACTION_PICK);
        String diretorio = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        pegarImagemIntent.setDataAndType(Uri.parse(diretorio), "image/*");

        return pegarImagemIntent;
    }

    public void requisitarPermissaoLigacao(){
        requisicaoPermissaoActivityResultLauncher.launch(CALL_PHONE);
    }

    public void chamarTelefone(){
        Intent chamarIntent = new Intent();
        chamarIntent.setAction(Intent.ACTION_CALL);
        chamarIntent.setData(Uri.parse("tel: " + parametro.getText()));
        startActivity(chamarIntent);
    }

}