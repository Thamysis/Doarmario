package ifsp.doarmario.view.ui;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.UsuarioDao;
import ifsp.doarmario.model.vo.Usuario;
import ifsp.doarmario.view.ui.lista_pecas_naoutilizadas.ListaPecasNaoUtilizadasFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String nomeUsuarioAtual;
    private TextView txtNomeUsuario;
    private TextView txtEmail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recuperar usuário
        nomeUsuarioAtual = (String) getIntent().getSerializableExtra("usuario");

        //obter objeto banco
        Usuario usuario = UsuarioDao.usuarioDao.detalhar(nomeUsuarioAtual);

        //mensagem p/ usuário
        Toast.makeText(this, nomeUsuarioAtual, Toast.LENGTH_LONG);

        //configurando toolbar e menu
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        //obter referências elementos nav_header
        txtNomeUsuario = headerView.findViewById(R.id.txtNomeUsuario);
        txtEmail = headerView.findViewById(R.id.txtEmail);

        //setar o nome e email no header menu
        txtNomeUsuario.setText(nomeUsuarioAtual);
        txtEmail.setText(usuario.getEmail());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.pagina_inicial,
                R.id.cadastro_pecas,
                R.id.monta_pecas,
                R.id.listagem_pecas,
                R.id.listagem_doadas,
                R.id.listagem_naoutilizadas,
                R.id.listagem_montagens,
                R.id.perfil,
                R.id.sobre_desenvolvedoras
        ).setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Toast.makeText(this, getString(R.string.bem_vindo) + nomeUsuarioAtual , Toast.LENGTH_SHORT).show();


    }

    public void alerta(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Alerta");
        dialog.setMessage("Você possui peças que não estão sendo utilizadas com frequência. Sugerimos que você as doe ou as utilize mais! "  );
        dialog.setPositiveButton("VER PEÇAS NÃO UTILIZADAS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListaPecasNaoUtilizadasFragment naoUtilizadasFragment = new ListaPecasNaoUtilizadasFragment();
                //nota: abrir fragmento nao_utilizados aqui
            }
        });
        dialog.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void  setToolbarTitle(String title){
        toolbar.setTitle(title);
    }

}
