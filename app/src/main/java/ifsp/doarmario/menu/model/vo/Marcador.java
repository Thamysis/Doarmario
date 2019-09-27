package ifsp.doarmario.menu.model.vo;

public class Marcador {
    private Long id_marcador;
    private String descricao_marcador;

    public Marcador( String descricao_marcador) {
        this.descricao_marcador = descricao_marcador;
    }
    public Marcador(){    }
    public Long getId_marcador() {
        return id_marcador;
    }

    public void setId_marcador(Long id_marcador) {
        this.id_marcador = id_marcador;
    }

    public String getDescricao_marcador() {
        return descricao_marcador;
    }

    public void setDescricao_marcador(String descricao_marcador) {
        this.descricao_marcador = descricao_marcador;
    }
    @Override
    public String toString(){
        return descricao_marcador;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof  Marcador){
            Marcador marcador = (Marcador) obj;
            if(marcador.getDescricao_marcador().equals(descricao_marcador) && marcador.getId_marcador() == id_marcador)
                return true;
        }
        return false;
    }
}
