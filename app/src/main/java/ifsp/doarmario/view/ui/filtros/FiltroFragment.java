package ifsp.doarmario.view.ui.filtros;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.CategoriaDAO;
import ifsp.doarmario.model.dao.CorDAO;
import ifsp.doarmario.model.dao.MarcadorDAO;
import ifsp.doarmario.model.vo.Categoria;
import ifsp.doarmario.model.vo.Cor;
import ifsp.doarmario.model.vo.Marcador;
import ifsp.doarmario.view.adapter.filtros.ThreeLevelListAdapter;
import ifsp.doarmario.view.ui.monta_looks.MontaLooksFragment;

public class FiltroFragment extends Fragment {
    private ExpandableListView expandableListView;

    String[] parent = new String[]{"Cor", "Categoria", "Marcadores"};

    LinkedHashMap<String, String[]> terceiroNivelCategoria = new LinkedHashMap<>();

    HashMap<String, ArrayList<String>> categorias = new HashMap<>();

    ArrayList<Cor> corLista;
    String[] categoriaTipoLista;
    ArrayList<Categoria> categoriaLista;
    ArrayList<Marcador> marcadorLista;

    List<String[]> secondLevel = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();
    View view;

    private Button btn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filtro, container, false);

        //botão
        super.onCreate(savedInstanceState);

        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MontaLooksFragment montaLooksFragment = new MontaLooksFragment ();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, montaLooksFragment);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            }
        });

        //referencia pra expandableListView no filtros.xml
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);

        setUpAdapter();
        //setupReferences();

        return view;
    }

    private void setUpAdapter() {
        //obter lista do banco
        CorDAO corDAO = new CorDAO(getActivity());
        CategoriaDAO categoriaDAO = new CategoriaDAO(getActivity());
        MarcadorDAO marcadorDAO = new MarcadorDAO(getActivity());

        corLista = corDAO.listar();
        ArrayList<String> categoriaTipoListaArray = categoriaDAO.listarTipoCategoria();
        categoriaLista = categoriaDAO.listar();
        marcadorLista = marcadorDAO.listar();

        //setando as informações da lista de cores
        //para um array somente com a descrição de cada cor
        String[] descricaoCores = new String[corLista.size()];
        for(int i=0; i < corLista.size(); i++){
            descricaoCores[i] = corLista.get(i).getDescricao_cor();
        }

        //setando as informações da lista de marcadores
        //para um array somente com a descrição de cada marcador
        String[] descricaoMarcadores = new String[marcadorLista.size()];
        for(int j = 0; j < marcadorLista.size(); j++) {
            descricaoMarcadores[j] = marcadorLista.get(j).getDescricao_marcador();
        }

        //obtendo categorias que tem um mesmo tipo_categoria
        categoriaTipoLista = new String[categoriaTipoListaArray.size()];
        for(int i = 0; i < categoriaTipoListaArray.size(); i++) {
            categoriaTipoLista[i] = categoriaTipoListaArray.get(i).replace("_", " ");
        }

        //adiciona em uma lista
        //os itens(categoria) de cada tipo_categoria
        for(String tipo_categoria: categoriaTipoLista) {
            ArrayList<String> itens = new ArrayList<>();
            for(Categoria categoria: categoriaLista) {
                if(categoria.getTipo_categoria().equals(tipo_categoria.replace(" ", "_"))) {
                    itens.add(categoria.getDescricao_categoria());
                }
            }

            categorias.put(tipo_categoria, itens);
        }

        secondLevel.add(descricaoCores);
        secondLevel.add(categoriaTipoLista);
        secondLevel.add(descricaoMarcadores);

        //seta os itens(categoria) de cada tipo_categoria no layout
        for(String tipo_categoria: categoriaTipoLista) {
            int tamanho = categorias.get(tipo_categoria).size();
            String[] itens = new String[tamanho];
            for (int i = 0; i < tamanho; i++){
                itens[i] = categorias.get(tipo_categoria).get(i);
            }
            terceiroNivelCategoria.put(tipo_categoria, itens);
        }

        LinkedHashMap<String, String[]> thirdLevelEmpty = new LinkedHashMap<>();
        data.add(thirdLevelEmpty);
        data.add(terceiroNivelCategoria);
        data.add(thirdLevelEmpty);

        //passing three level of information to constructor
        ThreeLevelListAdapter threeLevelListAdapterAdapter = new ThreeLevelListAdapter(getContext(), parent, secondLevel, data);
        expandableListView.setAdapter(threeLevelListAdapterAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }
}
