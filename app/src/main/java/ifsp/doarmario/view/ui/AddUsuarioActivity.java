package ifsp.doarmario.view.ui;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);

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
                    txtNome.setError("Erro, nome de usuário já existe!");
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
                    txtEmail.setError("Erro, e-mail já cadastrado!");
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
            Toast.makeText(AddUsuarioActivity.this, R.string.senhas_incompativeis, Toast.LENGTH_SHORT).show();
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
           txtNome.setError("Campo vazio!");
            return;
        }
        if(email.isEmpty()) {
            txtEmail.setError("Campo vazio!");
            return;
        }
        if(senha.isEmpty()) {
            txtSenha.setError("Campo vazio!");
            return;
        }

        boolean valida = validarSenha();

        if(valida) {
            //Toast.makeText(AddUsuarioActivity.this,senha + "\n"+ nome + email  , Toast.LENGTH_SHORT).show();

            Usuario usuariovo = new Usuario(nome_usuario, email, senha);
           // UsuarioDao usuarioDAO = new UsuarioDao(getApplicationContext());

            boolean resposta = UsuarioDao.usuarioDao.salvar(usuariovo);

            if(resposta) {
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
