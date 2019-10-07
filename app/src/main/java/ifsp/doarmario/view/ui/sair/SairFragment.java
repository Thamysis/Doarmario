package ifsp.doarmario.view.ui.sair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ifsp.doarmario.R;
import ifsp.doarmario.view.ui.LoginActivity;

public class SairFragment extends Fragment {
    private SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pagina_inicial, container, false);

        //obter preferências do usuário
        preferences = getActivity().getSharedPreferences("usuario_detalhes", Context.MODE_PRIVATE);

        //mudar preferências do usuário
        if(preferences.getString("nome_usuario", null) != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

            Toast.makeText(getContext(), getString(R.string.logoff_sucesso), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        return view;
    }
}
