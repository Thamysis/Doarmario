package ifsp.doarmario.view.ui.lista_pecas_naoutilizadas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;
import ifsp.doarmario.view.adapter.NUtilizadasAdapter;

public class ListaPecasNaoUtilizadasFragment extends Fragment {
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lista_pecas_nao_utilizadas, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewN);
        carregarListaVestuario();
        return root;
    }

    public void carregarListaVestuario(){
        VestuarioDAO vestuarioDAO = new VestuarioDAO( getActivity().getApplicationContext() );
        List<Vestuario> listaVestuario = vestuarioDAO.listarNaoUtilizadas();
        NUtilizadasAdapter vestuarioAdapter = new NUtilizadasAdapter(listaVestuario, getActivity().getApplicationContext() );

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2 );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( vestuarioAdapter );
    }
}