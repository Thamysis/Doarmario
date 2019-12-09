package ifsp.doarmario.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ifsp.doarmario.R;
import ifsp.doarmario.model.dao.VestuarioDAO;
import ifsp.doarmario.model.vo.Vestuario;

public class NDoadasAdapter extends RecyclerView.Adapter<NDoadasAdapter.MyViewHolder> {

    private List<Vestuario> listaVestuarios;
    private Context context;
    private Vestuario vestuario;
    private VestuarioDAO vestuarioDAO;
    //private String


    public NDoadasAdapter(List<Vestuario> lista) {
        this.listaVestuarios = lista;
        vestuarioDAO = new VestuarioDAO();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_cadastrar_doacao, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        vestuario = listaVestuarios.get(position);

        if(vestuario.getImagem_vestuario() != null){
            Bitmap img = BitmapFactory.decodeFile(vestuario.getImagem_vestuario());
            holder.imageView.setImageBitmap(img);

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

        } else{
        }
    }

    @Override
    public int getItemCount() {
        return this.listaVestuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        //Button bttUsei;
        Button bttDoei;
        //TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView10);
            bttDoei = itemView.findViewById(R.id.button_doei);
            //bttUsei = itemView.findViewById(R.id.button_usei);

        }
    }
}
