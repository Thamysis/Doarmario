package ifsp.doarmario.view.ui.lista_pecas_doadas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.RecyclerItemClickListener;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;
import ifsp.doarmario.view.adapter.DoadasAdapter;

public class ListaPecasDoadasFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoadasAdapter vestuarioAdapter;
    private List<Vestuario> listaVestuario = new ArrayList<>();
    private Vestuario vestuarioSelecionado;
    private String nomeUsuarioAtual;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_pecas_doadas, container, false);
        recyclerView =  view.findViewById(R.id.recyclerViewD);

        //recuperar nome do usuário atual
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");

        carregarListaVestuario();
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity().getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                vestuarioSelecionado = listaVestuario.get( position );
//                                DetalhamentoPecasFragment detalhamentoPecasFragment = new DetalhamentoPecasFragment();
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("vestuarioSelecionado", vestuarioSelecionado);
//                                detalhamentoPecasFragment.setArguments(bundle);
//                                getActivity().getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.nav_host_fragment, detalhamentoPecasFragment).commit();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
//                                vestuarioSelecionado = listaVestuario.get( position );
//                                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//                                dialog.setTitle("Confirmar exclusão");
//                                dialog.setMessage("Deseja excluir o vestuario: " + vestuarioSelecionado.getDescricao_vestuario() + " ?" );
//                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                        VestuarioDAO vestuarioDAO = new VestuarioDAO(getActivity().getApplicationContext());
//                                        if ( vestuarioDAO.deletar(vestuarioSelecionado) ){
//
//                                            carregarListaVestuario();
//                                            Toast.makeText(getActivity().getApplicationContext(),
//                                                    "Sucesso ao excluir vestuário!",
//                                                    Toast.LENGTH_SHORT).show();
//
//                                        }else {
//                                            Toast.makeText(getActivity().getApplicationContext(),
//                                                    "Erro ao excluir vestuário!",
//                                                    Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                });
//
//                                dialog.setNegativeButton("Não", null );
//                                dialog.create();
//                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
        return view;
    }

    public void carregarListaVestuario(){
        VestuarioDAO vestuarioDAO = new VestuarioDAO(getActivity().getApplicationContext() );
        listaVestuario = vestuarioDAO.listarDoadas(nomeUsuarioAtual);
        vestuarioAdapter = new DoadasAdapter( listaVestuario, getActivity().getApplicationContext() );
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3 );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( vestuarioAdapter );
    }

}