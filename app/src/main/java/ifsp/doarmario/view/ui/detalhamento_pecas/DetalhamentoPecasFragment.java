package ifsp.doarmario.view.ui.detalhamento_pecas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.CategoriaDAO;
import ifsp.doarmario.model.dao.CorDAO;
import ifsp.doarmario.model.dao.MarcadorDAO;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Categoria;
import ifsp.doarmario.model.vo.Cor;
import ifsp.doarmario.model.vo.Marcador;
import ifsp.doarmario.model.vo.Vestuario;

public class DetalhamentoPecasFragment extends Fragment {
    private EditText edit_descricao_vestuario, edit_cor_vestuario, edit_categoria_vestuario, edit_marcador_vestuario;
    private Button btt_alterar;
    private ImageButton btt_edit_marcador, btt_edit_cor, btt_edit_categoria;
    private ImageView imagem_vestuario;
    private Vestuario vestuarioAtual;
    private AlertDialog pickerCor, pickerCategoria, pickerMarcador;
    private Long id_categoria_alterada, id_cor_alterada, id_marcador_alterado ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhamento_pecas, container, false);

        final VestuarioDAO vestuarioDAO = new VestuarioDAO(getActivity().getApplicationContext());

        //recupera o vestuário
        Bundle bundle = getArguments();
        vestuarioAtual = (Vestuario) bundle.getSerializable("vestuarioSelecionado");

        edit_descricao_vestuario = view.findViewById(R.id.edit_descricao_vestuario);
        edit_cor_vestuario = view.findViewById(R.id.edit_cor_vestuario);
        edit_categoria_vestuario = view.findViewById(R.id.edit_categoria_vestuario);
        edit_marcador_vestuario = view.findViewById(R.id.edit_marcador_vestuario);

        btt_alterar = (Button) view.findViewById(R.id.bttAlterar);

        btt_edit_marcador = (ImageButton) view.findViewById(R.id.btt_edit_marcador);
        btt_edit_categoria = (ImageButton) view.findViewById(R.id.btt_edit_categoria);
        btt_edit_cor = (ImageButton) view.findViewById(R.id.btt_edit_cor);

        imagem_vestuario = (ImageView) view.findViewById(R.id.img_vestuario);

        if(vestuarioAtual.getImagem_vestuario() != null){
            imagem_vestuario.setImageBitmap(BitmapFactory.decodeFile(vestuarioAtual.getImagem_vestuario()));
        }
        edit_descricao_vestuario.setText( vestuarioAtual.getDescricao_vestuario() );

        //nota: implementar ação de clique em editDescricao
         edit_descricao_vestuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicou", Toast.LENGTH_SHORT);
                edit_categoria_vestuario.setEnabled(true);
            }
        });

        CorDAO corDAO = new CorDAO(getActivity());
        CategoriaDAO categoriaDAO = new CategoriaDAO(getActivity());
        MarcadorDAO marcadorDAO = new MarcadorDAO(getActivity());

        Cor corVestuario = corDAO.detalhar(vestuarioAtual.getId_cor());
        Categoria categoriaVestuario = categoriaDAO.detalhar(vestuarioAtual.getId_categoria());
        Marcador marcadorVestuario = marcadorDAO.detalhar(vestuarioAtual.getId_marcador());

        edit_categoria_vestuario.setText(categoriaVestuario.getDescricao_categoria());
        edit_cor_vestuario.setText(corVestuario.getDescricao_cor());
        edit_marcador_vestuario.setText(marcadorVestuario.getDescricao_marcador());

        edit_cor_vestuario.setEnabled(false);
        edit_categoria_vestuario.setEnabled(false);
        edit_marcador_vestuario.setEnabled(false);

        final ArrayList<Cor> corLista = corDAO.listar();
        final ArrayList<Categoria> categoriaLista = categoriaDAO.listar();
        final ArrayList<Marcador> marcadorLista = marcadorDAO.listar();
        final ArrayAdapter<Cor> adapterCor = new ArrayAdapter<Cor>(getActivity(),R.layout.item_picker, corLista);
        final ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(getActivity(),R.layout.item_picker, categoriaLista);
        final ArrayAdapter<Marcador> adapterMarcador = new ArrayAdapter<Marcador>(getActivity(),R.layout.item_picker, marcadorLista);
        final AlertDialog.Builder builderCor = new AlertDialog.Builder(getActivity());
        final AlertDialog.Builder builderCategoria = new AlertDialog.Builder(getActivity());
        final AlertDialog.Builder builderMarcador = new AlertDialog.Builder(getActivity());
        builderCor.setTitle("Selecione a cor para alterar.");
        builderCategoria.setTitle("Selecione a categoria para alterar.");
        builderMarcador.setTitle("Selecione o marcador para alterar.");

        id_categoria_alterada = Long.valueOf(0);
        id_cor_alterada = Long.valueOf(0);
        id_marcador_alterado = Long.valueOf(0);
        //define o diálogo como uma lista, passa o adapter.
        btt_edit_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderCategoria.setSingleChoiceItems(adapterCategoria, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        edit_categoria_vestuario.setEnabled(true);
                        edit_categoria_vestuario.setText(categoriaLista.get(arg1).getDescricao_categoria());
                        edit_categoria_vestuario.setEnabled(false);
                        id_categoria_alterada = categoriaLista.get(arg1).getId_categoria();
                        pickerCategoria.dismiss();
                    }
                });
                pickerCategoria = builderCategoria.create();
                pickerCategoria.show();
            }
        });
        btt_edit_cor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderCor.setSingleChoiceItems(adapterCor, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        edit_cor_vestuario.setEnabled(true);
                        edit_cor_vestuario.setText(corLista.get(arg1).getDescricao_cor());
                        edit_cor_vestuario.setEnabled(false);
                        id_cor_alterada = corLista.get(arg1).getId_cor();
                        pickerCor.dismiss();
                    }
                });
                pickerCor = builderCor.create();
                pickerCor.show();
            }
        });
        btt_edit_marcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderMarcador.setSingleChoiceItems(adapterMarcador, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        edit_marcador_vestuario.setEnabled(true);
                        edit_marcador_vestuario.setText(marcadorLista.get(arg1).getDescricao_marcador());
                        edit_marcador_vestuario.setEnabled(false);
                        id_marcador_alterado = marcadorLista.get(arg1).getId_marcador();
                        pickerMarcador.dismiss();
                    }
                });
                pickerMarcador = builderMarcador.create();
                pickerMarcador.show();
            }
        });

        btt_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descricao_vestuario = edit_descricao_vestuario.getText().toString();
                if (!descricao_vestuario.isEmpty()) {
                    Vestuario vestuario = new Vestuario();
                    vestuario.setDescricao_vestuario(descricao_vestuario);
                    if(id_categoria_alterada == 0)
                        vestuario.setId_categoria(vestuarioAtual.getId_categoria());
                    else
                        vestuario.setId_categoria(id_categoria_alterada);
                    if(id_cor_alterada == 0)
                        vestuario.setId_cor(vestuarioAtual.getId_cor());
                    else
                        vestuario.setId_cor(id_cor_alterada);
                    if(id_marcador_alterado == 0)
                        vestuario.setId_marcador(vestuarioAtual.getId_marcador());
                    else
                        vestuario.setId_marcador(id_marcador_alterado);

                    vestuario.setId_vestuario(vestuarioAtual.getId_vestuario());

                    if (vestuarioDAO.atualizar(vestuario)) {
                        //finish();
                        Toast.makeText(getActivity().getApplicationContext(), "Sucesso ao atualizar Vestuário!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Erro ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        return view;
    }
}