package ifsp.doarmario.menu.model.vo;

import java.io.Serializable;

public class Categoria implements Serializable {
    private Long id_categoria;
    private String tipo_categoria;
    private String descricao_categoria;

    public Categoria(String tipo_categoria, String descricao_categoria) {
        this.tipo_categoria = tipo_categoria;
        this.descricao_categoria = descricao_categoria;
    }
    public Categoria(){}

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTipo_categoria() {
        return tipo_categoria;
    }

    public void setTipo_categoria(String tipo_categoria) {
        this.tipo_categoria = tipo_categoria;
    }

    public String getDescricao_categoria() {
        return descricao_categoria;
    }

    public void setDescricao_categoria(String descricao_categoria) {
        this.descricao_categoria = descricao_categoria;
    }
    @Override
    public String toString(){
        return descricao_categoria;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof  Categoria){
            Categoria categoria = (Categoria) obj;
            if(categoria.getDescricao_categoria().equals(descricao_categoria) && categoria.getId_categoria() == id_categoria && categoria.getTipo_categoria() == tipo_categoria)
                return true;
        }
        return false;
    }

}
