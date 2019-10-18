package ifsp.doarmario.view.ui.pagina_inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;
import ifsp.doarmario.view.ui.MainActivity;
import ifsp.doarmario.view.ui.cadastra_pecas.CadastroPecasFragment;
import ifsp.doarmario.view.ui.detalhamento_pecas.DetalhamentoPecasFragment;

public class PaginaInicialFragment extends Fragment {
    private FloatingActionButton btt_camera;
    private TextView contDoadas;
    private VestuarioDAO vestuarioDAO;
    private String nomeUsuarioAtual;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pagina_inicial, container, false);
        ((MainActivity) getActivity()).setToolbarTitle("Página inicial");
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");
        contDoadas = view.findViewById(R.id.txt_doadas);
        vestuarioDAO = new VestuarioDAO(getContext());
        List<Vestuario> vestuarioList = vestuarioDAO.listarDoadas(nomeUsuarioAtual);
        int contagem = vestuarioList.size();
        contDoadas.setText(contagem + "");

        btt_camera = view.findViewById(R.id.btt_camera);
        btt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CadastroPecasFragment cadastroPecasFragment = new CadastroPecasFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, cadastroPecasFragment);
                fragmentManager.popBackStack();
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}