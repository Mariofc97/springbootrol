package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    // ---------- ID ----------
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    private Long id;

    // ---------- USERNAME ----------
    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(name = "username", nullable = false, unique = true, length = 30)
    private String username;

    // ---------- EMAIL ----------
    @NotNull
    @NotBlank
    @Email
    @Size(max = 120)
    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    // ---------- PASSWORD ----------
    @NotNull
    @NotBlank
    @Size(min = 4, max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;

 // ---------- ROL ----------
    @NotNull
    @NotBlank
    @Pattern(regexp = "JUGADOR|ADMINISTRADOR", message = "Rol debe ser JUGADOR o ADMINISTRADOR")
    @Column(name = "rol", nullable = false, length = 20)
    private String rol;

    // ---------- FECHA DE ALTA ----------
    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;

    // ---------- ACTIVO ----------
    @NotNull
    @Column(name = "activo", nullable = false)
    private Boolean activo = Boolean.TRUE;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Personaje> personajes = new ArrayList<>();


    // ======== CONSTRUCTORES ========
    public Usuario() {
        // Constructor vacío para JPA
    }

    public Usuario(Long id, String username, String email, String password, String rol) {
    	this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.activo = Boolean.TRUE;
    }
    
    

    public Usuario(@NotNull @NotBlank @Size(max = 30) String username,
			@NotNull @NotBlank @Email @Size(max = 120) String email,
			@NotNull @NotBlank @Size(min = 4, max = 100) String password, @NotNull String rol,
			@NotNull LocalDateTime fechaAlta, @NotNull Boolean activo) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.fechaAlta = fechaAlta;
		this.activo = activo;
	}

	// Constructor completo (sin id)
    public Usuario(String username, String email, String password, String rol,
                   Boolean activo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.activo = activo;
    }


    // ======== GETTERS Y SETTERS ========
    
    
    public List<Personaje> getPersonajes() {
    	return personajes;
    }
    
    public void setPersonajes(List<Personaje> personajes) {
    	this.personajes = personajes;
    }
    public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public LocalDateTime getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDateTime fechaAlta) { this.fechaAlta = fechaAlta; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }


    // ======== PRE-PERSIST ========
    @PrePersist
    private void prePersist() {
        if (fechaAlta == null) {
            fechaAlta = LocalDateTime.now();
        }
        if (activo == null) {
            activo = Boolean.TRUE;
        }
    }

    // ======== EQUALS & HASHCODE ========
    @Override
    public int hashCode() {
        return (username == null) ? 0 : username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Usuario other = (Usuario) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        return username != null && username.equals(other.username);
    }

    // ======== TO STRING ========
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fecha = (fechaAlta == null) ? "null" : fechaAlta.format(fmt);

        return "Usuario [id=" + id
                + ", username=" + username
                + ", email=" + email
                + ", rol=" + rol
                + ", activo=" + activo
                + ", fechaAlta=" + fecha
                + "]";
    }
}
