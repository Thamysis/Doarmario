package ifsp.doarmario.menu.view.ui.montagens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ifsp.doarmario.menu.R;

public class MontagensFragment extends Fragment {
    private String nomeUsuarioAtual;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_montagens, container, false);

        //recuperar nome do usuário atual
        nomeUsuarioAtual = (String) getActivity().getIntent().getSerializableExtra("usuario");


        return view;
    }
}