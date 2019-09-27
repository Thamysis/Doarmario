package ifsp.doarmario.menu.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

import ifsp.doarmario.menu.R;
import ifsp.doarmario.menu.model.dao.DbHelper;
import ifsp.doarmario.menu.model.dao.UsuarioDao;
import ifsp.doarmario.menu.model.vo.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtSenha;
    private TextView txtRedefinirSenha;
    private EditText txtRecEmail;

    private String emailDigitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //referenciar componentes da tela
        txtEmail = findViewById(R.id.editEmail);
        txtSenha = findViewById(R.id.editSenha);
        txtRedefinirSenha = findViewById(R.id.txtRedefinirSenha);

        //inicializar variaveis estáticas usadas p/ conexão c/ banco
        DbHelper.database = new DbHelper(getApplicationContext());
        UsuarioDao.usuarioDao = new UsuarioDao(getApplicationContext());

        //evento de ação ao clicar p/ o TextView Esqueceu a senha
        txtRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redefinirSenha(view);
            }
        });
    }

    //executado ao clicar no botão de login
    public void login(View view) {
        boolean verifica = false;
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        //KeyStore.PasswordProtection senha2 =

        verifica = UsuarioDao.usuarioDao.login(email);

        if(verifica){
            String nomeUsuario = UsuarioDao.usuarioDao.verificaSenha(email,senha);

            if(nomeUsuario != null) {
                Toast.makeText(this, "bem-vindo ao sistema" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("usuario", nomeUsuario);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "senha errada" , Toast.LENGTH_SHORT).show();
            }
        }else {
            txtEmail.setError("Email inexistente");
            Toast.makeText(this, R.string.usuario_nao_existe, Toast.LENGTH_SHORT).show();
        }
    }

    //executado ao clicar no botão de redefinir senha
    public void redefinirSenha(View view) {
        //dialog para obter email p/ recuperação da senha
        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);

        //configurar layout da dialog
        View viewDialog = getLayoutInflater().inflate(R.layout.redefinir_senha, null);

        dialog.setView(viewDialog);

        //recuperar email digitado
        txtRecEmail = viewDialog.findViewById(R.id.txtRecEmail);

        //criar e exibir dialog
        dialog.create();
        dialog.show();
    }

    // executado ao clicar no botão enviar e-mail p/ recuperar senha
    public void enviarEmail(View view) {
        emailDigitado = txtRecEmail.getText().toString();

        //verificar se e-mail está cadastrado
        boolean verificaEmail = false;
        verificaEmail = UsuarioDao.usuarioDao.login(emailDigitado);

        /*************************
        //CODIGO PARA ENVIAR EMAIL
        *************************/

        boolean envio = true;
        if(verificaEmail) {
            if(envio) {
                Toast.makeText(LoginActivity.this, "email enviado para" + emailDigitado, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "E-mail não cadastrado!", Toast.LENGTH_SHORT).show();
        }
    }

    //executado ao clicar no botão nova conta
    public void novaConta(View view) {
        //iniciar activity cadastro
        Intent i = new Intent(LoginActivity.this, AddUsuarioActivity.class);
        startActivity(i);
        finish();
    }
}
