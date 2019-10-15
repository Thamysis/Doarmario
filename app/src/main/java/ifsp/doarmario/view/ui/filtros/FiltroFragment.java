package ifsp.doarmario.view.ui.filtros;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.CategoriaDAO;
import ifsp.doarmario.model.dao.MarcadorDAO;
import ifsp.doarmario.model.vo.Categoria;
import ifsp.doarmario.model.vo.Cor;
import ifsp.doarmario.model.vo.Marcador;
import ifsp.doarmario.view.adapter.filtros.ThreeLevelListAdapter;

public class FiltroFragment extends Fragment {
    private ExpandableListView expandableListView;

    String[] parent = new String[]{"Cor", "Categoria", "Marcadores"};
    String[] q1 = new String[]{"List View", "Grid View"};
    String[] q2 = new String[]{"Linear Layout", "Relative Layout"};
    String[] q3 = new String[]{"Recycle View"};
    String[] des1 = new String[]{"l layout that organizes its children into a single horizontal or vertical row. It creates a scrollbar if the length of the window exceeds the length of the screen."};
    String[] des2 = new String[]{"Enables you to specify the location of child objects relative to each other (child A to the left of child B) or to the parent (aligned to the top of the parent)."};
    String[] des3 = new String[]{"This list contains linear layout information"};
    String[] des4 = new String[]{"This list contains relative layout information,Displays a scrolling grid of columns and rows"};
    String[] des5 = new String[]{"Under the RecyclerView model, several different components work together to display your data. Some of these components can be used in their unmodified form; for example, your app is likely to use the RecyclerView class directly. In other cases, we provide an abstract class, and your app is expected to extend it; for example, every app that uses RecyclerView needs to define its own view holder, which it does by extending the abstract RecyclerView.ViewHolder class."};

    LinkedHashMap<String, String[]> thirdLevelq1 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelq2 = new LinkedHashMap<>();
    LinkedHashMap<String, String[]> thirdLevelq3 = new LinkedHashMap<>();

    HashMap<String, ArrayList<String>> categorias = new HashMap<>();

    ArrayList<Cor> corLista;
    String[] categoriaTipoLista;
    ArrayList<Categoria> categoriaLista;
    ArrayList<Marcador> marcadorLista;

    List<String[]> secondLevel = new ArrayList<>();
    List<LinkedHashMap<String, String[]>> data = new ArrayList<>();
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filtro, container, false);
        setUpAdapter();
        return view;
    }

    private void setUpAdapter() {
        //obter lista do banco
        //CorDAO corDAO = new CorDAO(getActivity());
        CategoriaDAO categoriaDAO = new CategoriaDAO(getActivity());
        MarcadorDAO marcadorDAO = new MarcadorDAO(getActivity());

        //corLista = corDAO.listar();
        ArrayList<String> categoriaTipoListaArray = categoriaDAO.listarTipoCategoria();
        categoriaTipoLista = new String[categoriaTipoListaArray.size()];
        for(int i = 0; i < categoriaTipoListaArray.size(); i++) {
            categoriaTipoLista[i] = categoriaTipoListaArray.get(i).replace("_", " ");
        }
        categoriaLista = categoriaDAO.listar();
        marcadorLista = marcadorDAO.listar();

        String[] categoriaMarcador = new String[marcadorLista.size()];
        for(int j = 0; j < marcadorLista.size(); j++) {
            categoriaMarcador[j] = marcadorLista.get(j).getDescricao_marcador();
        }

        //adiciona os itens de cada tipo_categoria
        for(String tipo_categoria: categoriaTipoLista) {
            ArrayList<String> itens = new ArrayList<>();
            for(Categoria categoria: categoriaLista) {
                if(categoria.getTipo_categoria().equals(tipo_categoria.replace(" ", "_"))) {
                    itens.add(categoria.getDescricao_categoria());
                }
            }
            categorias.put(tipo_categoria, itens);
        }

        for(String tipo_categoria: categoriaTipoLista) {
            int tamanho = categorias.get(tipo_categoria).size();
            String[] itens = new String[tamanho];
            for (int i = 0; i < tamanho; i++){
                itens[i] = categorias.get(tipo_categoria).get(i);
            }
            thirdLevelq2.put(tipo_categoria, itens);
        }

        secondLevel.add(q1);
        secondLevel.add(categoriaTipoLista);
        secondLevel.add(categoriaMarcador);
        thirdLevelq1.put(q1[0], des1);
        thirdLevelq1.put(q1[1], des2);

        data.add(thirdLevelq1);
        data.add(thirdLevelq2);
        data.add(thirdLevelq3);

        //referencia pra expandableListView no filtros.xml
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandible_listview);

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
