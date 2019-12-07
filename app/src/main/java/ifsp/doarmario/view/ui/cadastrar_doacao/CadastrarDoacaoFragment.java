package ifsp.doarmario.view.ui.cadastrar_doacao;

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
import ifsp.doarmario.view.adapter.NDoadasAdapter;

public class CadastrarDoacaoFragment extends Fragment {
    private RecyclerView recyclerView;
    private String usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cadastrar_doacao, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewN);

        //recuperar nome do usuário atual
        usuario = (String) getActivity().getIntent().getSerializableExtra("usuario");

        carregarListaVestuario();
        return root;
    }

    public void carregarListaVestuario(){
        VestuarioDAO vestuarioDAO = new VestuarioDAO();
        List<Vestuario> listaVestuario = vestuarioDAO.listarNaoUtilizadas(usuario);

        NDoadasAdapter vestuarioAdapter = new NDoadasAdapter(listaVestuario);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2 );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( vestuarioAdapter );

    }
}