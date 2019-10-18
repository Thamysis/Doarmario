package ifsp.doarmario.view.ui.monta_looks;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349
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
<<<<<<< HEAD
import java.util.Random;
import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;
import ifsp.doarmario.view.ui.MainActivity;
import ifsp.doarmario.view.ui.detalhamento_pecas.DetalhamentoPecasFragment;
import ifsp.doarmario.view.ui.filtros.FiltroFragment;
import ifsp.doarmario.view.ui.pagina_inicial.PaginaInicialFragment;
=======

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;
import ifsp.doarmario.view.ui.detalhamento_pecas.DetalhamentoPecasFragment;
import ifsp.doarmario.view.ui.filtros.FiltroFragment;
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349

public class MontaLooksFragment extends Fragment {
    private String nomeUsuarioAtual;
    private ImageView imagem_aleatorio;
    private ImageView  imagem_parte_de_cima, imagem_parte_de_baixo, imagem_sapato;
    private ImageView seta_1_esquerda,  seta_1_direita;
    private ImageView seta_2_esquerda, seta_2_direita;
    private ImageView seta_3_esquerda, seta_3_direita;

    private List<Vestuario> listaParteDeCima = new ArrayList<>();
    private List<Vestuario> listaParteDeBaixo = new ArrayList<>();
    private List<Vestuario> listaCalcado = new ArrayList<>();

<<<<<<< HEAD
    private List<Long> id_listaParteDeCima = new ArrayList<>();
    private List<Long> id_listaParteDeBaixo = new ArrayList<>();
    private List<Long> id_listaCalcado = new ArrayList<>();
=======

>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349

    private int posicao_parte_de_cima;
    private int posicao_parte_de_baixo;
    private int posicao_sapato;
<<<<<<< HEAD

    private Vestuario vestuarioSelecionado;

=======
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349
    //filtro
    private TextView filtro;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monta_looks, container, false);

        //recuperar nome do usuário atual
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");
        //instanciar classe VestuarioDAO
        final VestuarioDAO vestuarioDAO = new VestuarioDAO(getActivity().getApplicationContext());

        //pegar lista de peças baseada nas categorias dos filtros
        listaParteDeCima = vestuarioDAO.listarParteDeCima(nomeUsuarioAtual);
        listaParteDeBaixo = vestuarioDAO.listarParteDeBaixo(nomeUsuarioAtual);
        listaCalcado = vestuarioDAO.listarSapato(nomeUsuarioAtual);
        imagem_aleatorio = view.findViewById(R.id.imageView_aleatorio);
        //Padrão é ter parte_de_cima, parte_de_baixo e calcado
        {
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

            //filtro
            filtro= view.findViewById(R.id.textView_filtro);

            if (listaCalcado.isEmpty() || listaParteDeBaixo.isEmpty() || listaParteDeCima.isEmpty()) {
                AlertDialog alerta; //Cria o gerador do AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Erro");
                builder.setMessage("Você deve cadastrar ao menos uma peça em cada categoria antes de realizar uma montagem!!!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(MainActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();

                        //Recuperar vestuario para edicao

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

                filtro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FiltroFragment filtroFragment = new FiltroFragment();

                        //Bundle bundle = new Bundle();
                        //bundle.putSerializable("vestuarioSelecionado", vestuarioSelecionado);
                        //detalhamentoPecasFragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.nav_host_fragment, filtroFragment);
                        fragmentManager.popBackStack();
                        fragmentTransaction.commit();
                    }
                });

                imagem_aleatorio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        geraAleatorio();
                    }
                });

<<<<<<< HEAD
=======
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

        posicao_parte_de_cima = 0;
        posicao_parte_de_baixo = 0;
        posicao_sapato = 0;
        //filtro
        filtro= view.findViewById(R.id.textView_filtro);

        seta_1_esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicao_parte_de_cima --;
                carregaImagens();
            }
        });
        seta_2_esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicao_parte_de_baixo --;
                carregaImagens();
            }
        });
        seta_3_esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicao_sapato --;
                carregaImagens();
            }
        });
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349

                carregaImagens();
            }
<<<<<<< HEAD
        }

=======
        });
        seta_3_direita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicao_sapato ++;
                carregaImagens();
            }
        });
        filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               FiltroFragment filtroFragment = new FiltroFragment();

                //Bundle bundle = new Bundle();
                //bundle.putSerializable("vestuarioSelecionado", vestuarioSelecionado);
                //detalhamentoPecasFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.nav_host_fragment, filtroFragment);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            }
        });

        //carregaImagens();
>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349
        return view;
    }
    public void geraAleatorio(){
        Random geradorAleatorio = new Random();
        posicao_parte_de_cima = geradorAleatorio.nextInt(listaParteDeCima.size());
        posicao_parte_de_baixo = geradorAleatorio.nextInt(listaParteDeBaixo.size());
        posicao_sapato = geradorAleatorio.nextInt(listaCalcado.size());
        carregaImagens();
    }


    public void carregaImagens(){
        int ultimoCalcadoPosicao, ultimaParteDeCimaPosicao, ultimaParteDeBaixoPosicao;
        ultimoCalcadoPosicao = listaCalcado.size() - 1;
        ultimaParteDeCimaPosicao = listaParteDeCima.size() - 1;
        ultimaParteDeBaixoPosicao = listaParteDeBaixo.size() - 1;

        if(posicao_sapato < 0){
            posicao_sapato = ultimoCalcadoPosicao;
        } else if(posicao_sapato > ultimoCalcadoPosicao){
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

        Vestuario vestuarioCalcado = listaCalcado.get(posicao_sapato);
        Vestuario vestuarioParteDeCima = listaParteDeCima.get(posicao_parte_de_cima);
        Vestuario vestuarioParteDeBaixo = listaParteDeBaixo.get(posicao_parte_de_baixo);

        imagem_parte_de_cima.setImageBitmap(BitmapFactory.decodeFile(vestuarioParteDeCima.getImagem_vestuario()));
        imagem_parte_de_baixo.setImageBitmap(BitmapFactory.decodeFile(vestuarioParteDeBaixo.getImagem_vestuario()));
        imagem_sapato.setImageBitmap(BitmapFactory.decodeFile(vestuarioCalcado.getImagem_vestuario()));

    }
<<<<<<< HEAD
=======
    /*public void filtrar()
       {

       }*/


>>>>>>> d1a6c93abaad85441e0290c3d412e4659c110349

}