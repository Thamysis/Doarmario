package ifsp.doarmario.menu.model.vo;

public class Usuario {
    private String nome_usuario;
    private String email;
    private String senha;

    public Usuario(String nome_usuario, String email, String senha) {
        this.nome_usuario = nome_usuario;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome_usuario, String email){
        this.nome_usuario = nome_usuario;
        this.email = email;
    }
    public Usuario(){    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
