package ifsp.doarmario.view.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ifsp.doarmario.R;
import ifsp.doarmario.model.vo.Vestuario;

public class VestuarioAdapter extends RecyclerView.Adapter<VestuarioAdapter.MyViewHolder> {

    private List<Vestuario> listaVestuarios;
    private Context context;

    public VestuarioAdapter(List<Vestuario> lista, Context c) {
        this.listaVestuarios = lista;
        context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_listagem_vestuario, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Vestuario vestuario = listaVestuarios.get(position);
        holder.textView.setText( vestuario.getDescricao_vestuario() + "" );

        if(vestuario.getImagem_vestuario() != null){
            holder.imageView.setImageBitmap(BitmapFactory.decodeFile(vestuario.getImagem_vestuario()));
        } else{
        }
    }

    @Override
    public int getItemCount() {
        return this.listaVestuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
}
