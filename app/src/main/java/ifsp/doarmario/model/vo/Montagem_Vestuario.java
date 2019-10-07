package ifsp.doarmario.model.vo;

import java.io.Serializable;

public class Montagem_Vestuario implements Serializable {
    private Long id_montagem;
    private Long id_vestuario;

    public Montagem_Vestuario(){}

    public Montagem_Vestuario(Long id_montagem, Long id_vestuario) {
        this.id_montagem = id_montagem;
        this.id_vestuario = id_vestuario;
    }

    public Long getId_montagem() {
        return id_montagem;
    }

    public void setId_montagem(Long id_montagem) {
        this.id_montagem = id_montagem;
    }

    public Long getId_vestuario() {
        return id_vestuario;
    }

    public void setId_vestuario(Long id_vestuario) {
        this.id_vestuario = id_vestuario;
    }
}
