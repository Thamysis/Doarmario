package ifsp.doarmario.model.vo;

import java.io.Serializable;

public class Cor implements Serializable {
    private Long id_cor;
    private String descricao_cor;

    public Cor( String descricao_cor) {
        this.descricao_cor = descricao_cor;
    }
    public Cor(){    }
    public Long getId_cor() {
        return id_cor;
    }

    public void setId_cor(Long id_cor) {
        this.id_cor = id_cor;
    }

    public String getDescricao_cor() {
        return descricao_cor;
    }

    public void setDescricao_cor(String descricao_cor) {
        this.descricao_cor = descricao_cor;
    }

    @Override
    public String toString(){
        return descricao_cor;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof  Cor){
            Cor cor = (Cor) obj;
            if(cor.getDescricao_cor().equals(descricao_cor) && cor.getId_cor() == id_cor)
                return true;
        }
        return false;
    }
}