package ifsp.doarmario.view.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.UsuarioDao;
import ifsp.doarmario.model.vo.Usuario;

public class AddUsuarioActivity extends AppCompatActivity {
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtSenhaConfirm;
    private Button bttAdd;

    private String nome_usuario;
    private String email;
    private String senha;
    private String senhaConfirm;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);

        //preferências
        preferences = getSharedPreferences("usuario_detalhes", MODE_PRIVATE);

        //referenciar componentes da tela
        txtNome = findViewById(R.id.txtNome);
        txtEmail = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        txtSenhaConfirm = findViewById(R.id.txtSenhaConfirm);
        bttAdd = findViewById(R.id.bttAdd);

        txtNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                nome_usuario = txtNome.getText().toString();
                boolean verificarUsuario = UsuarioDao.usuarioDao.verificarUsuario(nome_usuario);
                if(verificarUsuario) {
                    txtNome.setError(getString(R.string.usuario_ja_existente));
                    bttAdd.setEnabled(false);
                } else {
                    bttAdd.setEnabled(true);
                }
            }
        });
        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                email = txtEmail.getText().toString();
                boolean verificarEmail = UsuarioDao.usuarioDao.login(email);

                if(verificarEmail) {
                    txtEmail.setError(getString(R.string.email_ja_existente));
                    bttAdd.setEnabled(false);
                } else {
                    bttAdd.setEnabled(true);
                }
            }
        });

    }

    public boolean validarSenha() {
        boolean valida = true;
        senha = txtSenha.getText().toString();
        senhaConfirm = txtSenhaConfirm.getText().toString();

        if (!senha.equals(senhaConfirm)) {
            txtSenhaConfirm.setError(getString(R.string.senhas_incompativeis));
            valida = false;
        }
        return valida;
    }

    public void cadastrar(View view) {
        nome_usuario = txtNome.getText().toString();
        email = txtEmail.getText().toString();
        senha = txtSenha.getText().toString();

        //validar campos
        if(nome_usuario.isEmpty()){
            txtNome.setError(getString(R.string.campo_vazio));
            return;
        }
        if(email.isEmpty()) {
            txtEmail.setError(getString(R.string.campo_vazio));
            return;
        }
        if(senha.isEmpty()) {
            txtSenha.setError(getString(R.string.campo_vazio));
            return;
        }

        boolean valida = validarSenha();

        if(valida) {
            Usuario usuariovo = new Usuario(nome_usuario, email, senha);

            boolean resposta = UsuarioDao.usuarioDao.salvar(usuariovo);

            if(resposta) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("nome_usuario", nome_usuario);
                editor.commit();

                Toast.makeText(AddUsuarioActivity.this, R.string.cadastro_sucesso , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddUsuarioActivity.this, MainActivity.class);
                intent.putExtra("usuario", nome_usuario);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AddUsuarioActivity.this, R.string.cadastro_erro , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
