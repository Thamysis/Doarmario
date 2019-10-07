package ifsp.doarmario.view.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;

public class NUtilizadasAdapter extends RecyclerView.Adapter<NUtilizadasAdapter.MyViewHolder> {

    private List<Vestuario> listaVestuarios;
    private Context context;
    private VestuarioDAO vestuarioDAO;
    //private String


    public NUtilizadasAdapter(List<Vestuario> lista, Context c) {
        this.listaVestuarios = lista;
        context = c;
        vestuarioDAO = new VestuarioDAO(c);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_listagem_n_utilizadas, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Vestuario vestuario = listaVestuarios.get(position);
        holder.bttUsei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Mudar peça pra peça não utilizada" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.bttDoei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vestuario.setStatus_doacao("d");
                vestuarioDAO.atualizarDoada(vestuario);
                listaVestuarios.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listaVestuarios.size());
                notifyDataSetChanged();

            }
        });


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
        Button bttUsei;
        Button bttDoei;
        //TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView10);
            bttDoei = itemView.findViewById(R.id.button_doei);
            bttUsei = itemView.findViewById(R.id.button_usei);

        }
    }
}
