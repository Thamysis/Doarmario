package ifsp.doarmario.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Montagem implements Serializable {
    private Long id_montagem;
    private String data_montagem;

    public Montagem() {
    }

    public Montagem(Long id_montagem, String data_montagem) {
        this.id_montagem = id_montagem;
        this.data_montagem = data_montagem;
    }
    public Montagem( String data_montagem) {
        this.data_montagem = data_montagem;
    }

    public Montagem( Date dataUtil) {
        this.data_montagem = converteDeDateParaString(dataUtil);
    }

    public Long getId_montagem() {
        return id_montagem;
    }

    public void setId_montagem(Long id_montagem) {
        this.id_montagem = id_montagem;
    }

    public String getData() {
        return data_montagem;
    }

    public void setData(Date dataUtil) {
        this.data_montagem = converteDeDateParaString(dataUtil);
    }

    public void setData_montagem(String data_montagem){
        this.data_montagem = data_montagem;
    }

    public String converteDeDateParaString(Date datautil){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataconvertida = dateFormat.format(datautil);
        return dataconvertida;
    }

}
