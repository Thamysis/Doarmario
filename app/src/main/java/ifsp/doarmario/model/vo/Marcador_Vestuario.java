package ifsp.doarmario.model.vo;

public class Marcador_Vestuario {
    private Long id_marcador;
    private Long id_vestuario;

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

    public Marcador_Vestuario( Long id_marcador, Long id_vestuario)
    {
        this.id_marcador = id_marcador;
        this.id_vestuario = id_vestuario;
    }
    public Marcador_Vestuario()
    {
    }
}
