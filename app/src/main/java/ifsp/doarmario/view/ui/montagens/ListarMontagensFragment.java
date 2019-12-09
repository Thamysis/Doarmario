package ifsp.doarmario.view.ui.montagens;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.CategoriaDAO;
import ifsp.doarmario.model.dao.MontagemDao;
import ifsp.doarmario.model.dao.Montagem_VestuarioDao;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Categoria;
import ifsp.doarmario.model.vo.Montagem;
import ifsp.doarmario.model.vo.Vestuario;

public class ListarMontagensFragment extends Fragment {
    private CalendarView calendarView;
    private ImageView imagem_parte_de_cima;
    private ImageView imagem_parte_de_baixo;
    private ImageView imagem_sapato;
    private ImageView setaDireita;
    private TextView txtData;
    private TextView dataTitulo;
    private int contador_montagem;
    private Montagem montagem;
    private MontagemDao montagemDao;
    private Montagem_VestuarioDao montagem_vestuarioDao;
    private VestuarioDAO vestuarioDAO;
    private CategoriaDAO categoriaDAO;
    private String usuario;

    private ArrayList<Montagem> montagens;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendario,  container, false);

        //recuperar usuário
        usuario = (String) getActivity().getIntent().getSerializableExtra("usuario");

        //conexão com banco
        montagem_vestuarioDao = new Montagem_VestuarioDao();
        vestuarioDAO = new VestuarioDAO();
        categoriaDAO = new CategoriaDAO();
        montagemDao = new MontagemDao();

        //elementos do layout
        calendarView = view.findViewById(R.id.calendarView);
        dataTitulo = view.findViewById(R.id.dataTitulo);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int ano, int mes, int dia) {
                mes = mes + 1;

                String data_formatada = "";
                String data = dia + "/" + mes + "/" + ano;

                if(dia < 10) //se o dia está entre 1 e 10, deve-se adicionar um 0 à esquerda
                    data_formatada = ano + "-" + mes + "-"  + "0" + dia + "";
                else
                    data_formatada = ano + "-" + mes + "-"  + dia + "";

                //obter lista de montagens com data específica
                ArrayList<Montagem> lista_montagens = montagemDao.listar_data(data_formatada, usuario);

                //exibe lista de montagens
                if(!lista_montagens.isEmpty() && lista_montagens != null){
                   montagem_detalhar(lista_montagens, data);

                } else { //exibe mensagem de nenhuma montagem
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.nenhuma_montagem), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void montagem_detalhar(ArrayList<Montagem> montagens_lista, String data) {
        montagens = montagens_lista;
        contador_montagem = 0;

        //dialog para detalhar montagem
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        //configurar layout da dialog
        View viewDialog = getLayoutInflater().inflate(R.layout.fragment_detalhamento_montagem, null);
        dialog.setView(viewDialog);

        //recuperar elementos do layout
        imagem_parte_de_cima = viewDialog.findViewById(R.id.img_parte_de_cima);
        imagem_parte_de_baixo = viewDialog.findViewById(R.id.img_parte_de_baixo);
        imagem_sapato = viewDialog.findViewById(R.id.img_sapato);
        txtData = viewDialog.findViewById(R.id.txtData);
        setaDireita = viewDialog.findViewById(R.id.setaDireita);

        //alterar data no topo do layout
        txtData.setText(data);

        //recuperar id da montagem
        montagem = montagens.get(contador_montagem);
        carregar_pecas(montagem.getId_montagem());

        //Toast.makeText(getActivity().getApplicationContext(), montagem.getData(), Toast.LENGTH_SHORT).show();

        //criar e exibir dialog
        dialog.create();
        dialog.show();

        if(montagens.size() < 1){
        }else {
            setaDireita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (contador_montagem != montagens.size() - 1) {
                        //Montagem montagem2 = new Montagem();
                        contador_montagem += 1;

                        montagem = montagens.get(contador_montagem);
                        carregar_pecas(montagem.getId_montagem());
                    } else {
                        contador_montagem = 0;
                        //Montagem montagem3;
                        montagem = montagens.get(contador_montagem);
                        carregar_pecas(montagem.getId_montagem());

                    }

                }
            });
        }
    }

    public void carregar_pecas(Long id_montagem) {
        Vestuario parte_cima = new Vestuario();
        Vestuario parte_baixo = new Vestuario();
        Vestuario sapato = new Vestuario();

        //obter ids dos vestuários da montagem
        ArrayList<Long> id_vestuarios_montagem = montagem_vestuarioDao.listar(id_montagem);

        if(!id_vestuarios_montagem.isEmpty() && id_vestuarios_montagem != null) {
            for(Long id_vestuario: id_vestuarios_montagem) {
                //obter vestuário
                Vestuario vestuario = vestuarioDAO.detalhar(id_vestuario);

                //obter categoria
                Categoria categoria_vestuario = categoriaDAO.detalhar(vestuario.getId_categoria());

                if(categoria_vestuario.getTipo_categoria().equals("roupa_de_cima")) {
                    parte_cima = vestuario;
                } else if(categoria_vestuario.getTipo_categoria().equals("roupa_de_baixo")) {
                    parte_baixo = vestuario;
                } else if(categoria_vestuario.getTipo_categoria().equals("calcado")) {
                    sapato = vestuario;
                } else {

                }
            }
            //exibir imagens
            imagem_parte_de_cima.setImageBitmap(BitmapFactory.decodeFile(parte_cima.getImagem_vestuario()));
            imagem_parte_de_baixo.setImageBitmap(BitmapFactory.decodeFile(parte_baixo.getImagem_vestuario()));
            imagem_sapato.setImageBitmap(BitmapFactory.decodeFile(sapato.getImagem_vestuario()));
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.montagem_erro_carregar, Toast.LENGTH_LONG).show();
        }
    }
}
