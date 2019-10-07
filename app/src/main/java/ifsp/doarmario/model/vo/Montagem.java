package ifsp.doarmario.model.vo;

import java.io.Serializable;
import java.util.Date;

public class Montagem implements Serializable {
    private Long id_montagem;
    private Date data_montagem;
    private String data;

    public Montagem() {
    }

    public Montagem(Long id_montagem, String data) {
        this.id_montagem = id_montagem;
        this.data = data;
    }

    public Long getId_montagem() {
        return id_montagem;
    }

    public void setId_montagem(Long id_montagem) {
        this.id_montagem = id_montagem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static Long converterToLong(Date data_date) {
        Long data_long = new Long(data_date.getTime());
        return data_long;
    }

    public static Date converterToDate(Long data_long) {
        Date data = new Date(data_long);
        return data;
    }
}
