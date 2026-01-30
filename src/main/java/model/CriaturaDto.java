package model;

public class CriaturaDto {

    private Long id;
    private String tipo;       // CONEJO / GUSANO...
    private String nombre;
    private String alias;
    private int nivel;
    private int experiencia;
    private int puntosVida;
    private int puntosAtaque;
    private String tipoAtaque;
    private Long personajeId;

    public CriaturaDto(Long id, String tipo, String nombre, String alias, int nivel, int experiencia,
                       int puntosVida, int puntosAtaque, String tipoAtaque, Long personajeId) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.alias = alias;
        this.nivel = nivel;
        this.experiencia = experiencia;
        this.puntosVida = puntosVida;
        this.puntosAtaque = puntosAtaque;
        this.tipoAtaque = tipoAtaque;
        this.personajeId = personajeId;
    }

    public Long getId() { return id; }
    public String getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public String getAlias() { return alias; }
    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
    public int getPuntosVida() { return puntosVida; }
    public int getPuntosAtaque() { return puntosAtaque; }
    public String getTipoAtaque() { return tipoAtaque; }
    public Long getPersonajeId() { return personajeId; }

    @Override
    public String toString() {
        return "CriaturaDto [id=" + id + ", tipo=" + tipo + ", nombre=" + nombre
                + ", alias=" + alias + ", nivel=" + nivel + ", pv=" + puntosVida
                + ", atk=" + puntosAtaque + ", personajeId=" + personajeId + "]";
    }
}
