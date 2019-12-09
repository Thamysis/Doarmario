package ifsp.doarmario.view.ui.monta_looks;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.MontagemDao;
import ifsp.doarmario.model.dao.Montagem_VestuarioDao;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Montagem;
import ifsp.doarmario.model.vo.Montagem_Vestuario;
import ifsp.doarmario.model.vo.Vestuario;
import ifsp.doarmario.view.ui.pagina_inicial.PaginaInicialFragment;
import java.util.Date;

public class MontaLooksFragment extends Fragment {
    private String nomeUsuarioAtual;
    private ImageView imagem_aleatorio;
    private ImageView imagem_parte_de_cima, imagem_parte_de_baixo, imagem_sapato;
    private ImageView seta_1_esquerda, seta_1_direita;
    private ImageView seta_2_esquerda, seta_2_direita;
    private ImageView seta_3_esquerda, seta_3_direita;
    private List<Vestuario> listaParteDeCima = new ArrayList<>();
    private List<Vestuario> listaParteDeBaixo = new ArrayList<>();
    private List<Vestuario> listaCalcado = new ArrayList<>();
    private int posicao_parte_de_cima;
    private int posicao_parte_de_baixo;
    private int posicao_sapato;
    private List<Long> id_listaParteDeCima = new ArrayList<>();
    private List<Long> id_listaParteDeBaixo = new ArrayList<>();
    private List<Long> id_listaCalcado = new ArrayList<>();
    private Vestuario vestuarioSelecionado;
    private Button bttSalvar;

    //montagem
    private MontagemDao montagemDao;
    private Montagem_VestuarioDao montagem_vestuarioDao;
    Vestuario vestuarioCalcado, vestuarioParteDeCima, vestuarioParteDeBaixo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monta_looks, container, false);
        //recuperar nome do usuário atual
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");
        //instanciar classe VestuarioDAO
        final VestuarioDAO vestuarioDAO = new VestuarioDAO();
        bttSalvar = view.findViewById(R.id.button_salvarMontagem);

        //pegar lista de peças baseada nas categorias dos filtros
        listaParteDeCima = vestuarioDAO.listarParteDeCima(nomeUsuarioAtual);
        listaParteDeBaixo = vestuarioDAO.listarParteDeBaixo(nomeUsuarioAtual);
        listaCalcado = vestuarioDAO.listarSapato(nomeUsuarioAtual);
        imagem_aleatorio = view.findViewById(R.id.imageView_aleatorio);

        //Padrão é ter parte_de_cima, parte_de_baixo e calcado

        //1
        seta_1_esquerda = view.findViewById(R.id.setaEsquerda_1);
        seta_1_direita = view.findViewById(R.id.setaDireita_1);
        imagem_parte_de_cima = view.findViewById(R.id.img_parte_de_cima);
        //2
        seta_2_esquerda = view.findViewById(R.id.setaEsquerda_2);
        seta_2_direita = view.findViewById(R.id.setaDireita_2);
        imagem_parte_de_baixo = view.findViewById(R.id.img_parte_de_baixo);
        //3
        seta_3_esquerda = view.findViewById(R.id.setaEsquerda_3);
        seta_3_direita = view.findViewById(R.id.setaDireita_3);
        imagem_sapato = view.findViewById(R.id.img_sapato);

        if (listaCalcado.isEmpty() || listaParteDeBaixo.isEmpty() || listaParteDeCima.isEmpty()) {
            AlertDialog alerta; //Cria o gerador do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Erro");
            builder.setMessage("Você deve cadastrar ao menos uma peça em cada categoria antes de realizar uma montagem!!!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    PaginaInicialFragment paginaInicialFragment = new PaginaInicialFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("nomeUsuarioAtual", nomeUsuarioAtual);
                    paginaInicialFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment, paginaInicialFragment);
                    fragmentManager.popBackStack();
                    fragmentTransaction.commit();
                }
            });
            //cria o AlertDialog
            alerta = builder.create();
            //Exibe
            alerta.show();
        } else {
            posicao_parte_de_cima = 0;
            posicao_parte_de_baixo = 0;
            posicao_sapato = 0;

            seta_1_esquerda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicao_parte_de_cima--;
                    carregaImagens();
                }
            });
            seta_2_esquerda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicao_parte_de_baixo--;
                    carregaImagens();
                }
            });
            seta_3_esquerda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicao_sapato--;
                    carregaImagens();
                }
            });

            seta_1_direita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicao_parte_de_cima++;
                    carregaImagens();
                }
            });
            seta_2_direita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicao_parte_de_baixo++;
                    carregaImagens();
                }
            });
            seta_3_direita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    posicao_sapato++;
                    carregaImagens();
                }
            });

            imagem_aleatorio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    geraAleatorio();
                }
            });
            carregaImagens();

        }
        bttSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarMontagem();
            }
        });

        return view;
    }

    public void salvarMontagem(){
        montagemDao = new MontagemDao();
        montagem_vestuarioDao = new Montagem_VestuarioDao();

        Date dataUtil = new Date();
        Montagem montagem1 =  new Montagem(dataUtil);

        if(montagemDao.salvar(montagem1)){
            Montagem montagem2 = montagemDao.getUltimaMontagem();

            montagem_vestuarioDao.salvar(new Montagem_Vestuario(montagem2.getId_montagem(),vestuarioCalcado.getId_vestuario() ));
            montagem_vestuarioDao.salvar(new Montagem_Vestuario(montagem2.getId_montagem(),vestuarioParteDeBaixo.getId_vestuario() ));
            montagem_vestuarioDao.salvar(new Montagem_Vestuario(montagem2.getId_montagem(),vestuarioParteDeCima.getId_vestuario() ));

            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.montagem_salvar_sucesso), Toast.LENGTH_SHORT).show();

            //voltar ao fragment inicial
            PaginaInicialFragment paginaInicialFragment = new PaginaInicialFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, paginaInicialFragment);

            fragmentTransaction.commit();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.montagem_salvar_erro), Toast.LENGTH_SHORT).show();
        }

    }

    public void geraAleatorio() {
        Random geradorAleatorio = new Random();
        posicao_parte_de_cima = geradorAleatorio.nextInt(listaParteDeCima.size());
        posicao_parte_de_baixo = geradorAleatorio.nextInt(listaParteDeBaixo.size());
        posicao_sapato = geradorAleatorio.nextInt(listaCalcado.size());
        carregaImagens();
    }

    public void carregaImagens() {
        int ultimoCalcadoPosicao, ultimaParteDeCimaPosicao, ultimaParteDeBaixoPosicao;
        ultimoCalcadoPosicao = listaCalcado.size() - 1;
        ultimaParteDeCimaPosicao = listaParteDeCima.size() - 1;
        ultimaParteDeBaixoPosicao = listaParteDeBaixo.size() - 1;

        if (posicao_sapato < 0) {
            posicao_sapato = ultimoCalcadoPosicao;
        } else if (posicao_sapato > ultimoCalcadoPosicao) {
            posicao_sapato = 0;
        }
        if(posicao_parte_de_cima < 0){
            posicao_parte_de_cima = ultimaParteDeCimaPosicao;
        } else if(posicao_parte_de_cima > ultimaParteDeCimaPosicao){
            posicao_parte_de_cima = 0;
        }
        if(posicao_parte_de_baixo < 0){
            posicao_parte_de_baixo = ultimaParteDeBaixoPosicao;
        } else if(posicao_parte_de_baixo > ultimaParteDeBaixoPosicao){
            posicao_parte_de_baixo = 0;
        }

        vestuarioCalcado = listaCalcado.get(posicao_sapato);
        vestuarioParteDeCima = listaParteDeCima.get(posicao_parte_de_cima);
        vestuarioParteDeBaixo = listaParteDeBaixo.get(posicao_parte_de_baixo);

        imagem_parte_de_cima.setImageBitmap(BitmapFactory.decodeFile(vestuarioParteDeCima.getImagem_vestuario()));
        imagem_parte_de_baixo.setImageBitmap(BitmapFactory.decodeFile(vestuarioParteDeBaixo.getImagem_vestuario()));
        imagem_sapato.setImageBitmap(BitmapFactory.decodeFile(vestuarioCalcado.getImagem_vestuario()));

    }
}