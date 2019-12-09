package ifsp.doarmario.view.ui.sobre_mim;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.UsuarioDao;
import ifsp.doarmario.model.vo.Usuario;
import ifsp.doarmario.view.ui.sair.SairFragment;

public class SobreMimFragment extends Fragment {
    private TextView txtNomeUsuario;
    private EditText txtEmail;
    private EditText txtSenha;
    private Button bttSalvar;
    private ImageButton bttRemover;

    private String nomeUsuarioAtual;
    private Usuario usuarioRecuperado;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sobre_mim, container, false);

        //referenciar componentes da tela
        txtNomeUsuario = view.findViewById(R.id.txtNomeUsuario);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtSenha = view.findViewById(R.id.txtSenha);
        bttSalvar = view.findViewById(R.id.bttSalvar);
        bttRemover = view.findViewById(R.id.bttRemover);

        //atualizar dados do usuário
        bttSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperar dados atualizados e setar p/ objeto
                Usuario usuarioAtualizado = new Usuario();
                usuarioAtualizado.setNome_usuario(txtNomeUsuario.getText().toString());
                usuarioAtualizado.setEmail(txtEmail.getText().toString());
                usuarioAtualizado.setSenha(txtSenha.getText().toString());

                //enviar dados p/ banco
                boolean atualizar = UsuarioDao.usuarioDao.atualizar(usuarioAtualizado);

                if(atualizar) {
                    Toast.makeText(getActivity(), R.string.usuario_upd_sucesso, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.usuario_upd_erro, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //remover conta do usuário
        bttRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog para confirmar exclusão da conta
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                //configurar título e mensagem da dialog
                dialog.setTitle(R.string.usuario_del_dialog_titulo);
                dialog.setMessage(R.string.usuario_del_dialog_conteudo);

                //recuperar usuário p/ excluir
                usuarioRecuperado =  UsuarioDao.usuarioDao.detalhar(nomeUsuarioAtual);

                //configurar botões
                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //enviando dados p/ banco
                        boolean deletar = UsuarioDao.usuarioDao.deletar(usuarioRecuperado);

                        if(deletar) {
                            Toast.makeText(getActivity(), R.string.usuario_del_sucesso, Toast.LENGTH_SHORT).show();
                            SairFragment sairFragment = new SairFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.nav_host_fragment, sairFragment);
                            fragmentManager.popBackStack();
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(getActivity(), R.string.usuario_del_erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.setNegativeButton("Não", null );

                //criar e exibir dialog
                dialog.create();
                dialog.show();
            }
        });

        //recuperar nome do usuário atual
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");

        //método p/ recuperar Usuario e exibir dados
        recuperarUsuario();

        return view;
    }

    //recuperar dados do usuário para listagem
    private void recuperarUsuario() {
        Usuario usuarioRecuperado = UsuarioDao.usuarioDao.detalhar(nomeUsuarioAtual);

        if(usuarioRecuperado != null) {
            txtNomeUsuario.setText(usuarioRecuperado.getNome_usuario());
            txtEmail.setText(usuarioRecuperado.getEmail());
            txtSenha.setText(usuarioRecuperado.getSenha());
        } else {
            Toast.makeText(getActivity(), R.string.erro_listagem, Toast.LENGTH_SHORT).show();
        }
    }
}