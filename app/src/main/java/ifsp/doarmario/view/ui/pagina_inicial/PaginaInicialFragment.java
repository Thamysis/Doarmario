package ifsp.doarmario.view.ui.pagina_inicial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ifsp.doarmario.R;
import ifsp.doarmario.view.ui.cadastra_pecas.CadastroPecasFragment;
import ifsp.doarmario.view.ui.detalhamento_pecas.DetalhamentoPecasFragment;

public class PaginaInicialFragment extends Fragment {
    private FloatingActionButton btt_camera;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagina_inicial, container, false);

        btt_camera = view.findViewById(R.id.btt_camera);
        btt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CadastroPecasFragment cadastroPecasFragment = new CadastroPecasFragment();

                //Bundle bundle = new Bundle();

                //detalhamentoPecasFragment.setArguments(bundle);

                // getActivity().getSupportFragmentManager().beginTransaction()
                //.replace(R.id.nav_host_fragment, detalhamentoPecasFragment).commit();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //Fragment childFragment = fragmentManager.findFragmentByTag("qa_fragment");
                fragmentTransaction.replace(R.id.nav_host_fragment, cadastroPecasFragment);

                //fragmentTransaction.remove(fragmentManager.getFragment(savedInstanceState, ""));
                fragmentManager.popBackStack();
                fragmentTransaction.commit();

            }
        });
        return view;
    }
}