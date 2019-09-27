package ifsp.doarmario.menu.model.vo;

import java.io.Serializable;

public class Vestuario implements Serializable {
    private Long id_vestuario;
    private String descricao_vestuario;
    private String imagem_vestuario;
    private String status_doacao;
    private Long id_categoria;
    private Long id_cor;
    private Long id_marcador;
    private String nome_usuario;
    public Vestuario() {
    }

    public Vestuario( String descricao_vestuario, String imagem_vestuario, String status_doacao) {
        this.descricao_vestuario = descricao_vestuario;
        this.imagem_vestuario = imagem_vestuario;
        this.status_doacao = status_doacao;
    }
    public Vestuario( String descricao_vestuario, String imagem_vestuario, String status_doacao, Long id_cor, Long id_categoria, Long id_marcador, String nome_usuario ) {
        this.descricao_vestuario = descricao_vestuario;
        this.imagem_vestuario = imagem_vestuario;
        this.status_doacao = status_doacao;
        this.id_cor = id_cor;
        this.id_categoria = id_categoria;
        this.id_marcador = id_marcador;
        this.nome_usuario = nome_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }
    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Long getId_cor() {
        return id_cor;
    }

    public void setId_cor(Long id_cor) {
        this.id_cor = id_cor;
    }

    public Long getId_marcador() {
        return id_marcador;
    }

    public void setId_marcador(Long id_marcador) {
        this.id_marcador = id_marcador;
    }

    public Long getId_vestuario() {
        return id_vestuario;
    }

    public void setId_vestuario(Long id_vestuario) {
        this.id_vestuario = id_vestuario;
    }

    public String getDescricao_vestuario() {
        return descricao_vestuario;
    }

    public void setDescricao_vestuario(String descricao_vestuario) {
        this.descricao_vestuario = descricao_vestuario;
    }

    public String getImagem_vestuario() {
        return imagem_vestuario;
    }

    public void setImagem_vestuario(String imagem_vestuario) {
        this.imagem_vestuario = imagem_vestuario;
    }

    public String getStatus_doacao() {
        return status_doacao;
    }

    public void setStatus_doacao(String status_doacao) {
        this.status_doacao = status_doacao;
    }
}
