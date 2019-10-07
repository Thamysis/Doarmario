package ifsp.doarmario.menu.view.ui.desenvolvedoras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ifsp.doarmario.menu.R;

public class DesenvolvedorasFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sobre_desenvolvedoras, container, false);
        return root;
    }
}