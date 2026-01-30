package model;

public class UsuarioDto {

	private Long id;
	private String username;
	private String email;
	private String rol;
	private String fechaAlta;
	private Boolean activo;
	
	public UsuarioDto() {
		super();
	}

	public UsuarioDto(Long id, String username, String email, String rol, String fechaAlta, Boolean activo) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.rol = rol;
		this.fechaAlta = fechaAlta;
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "UsuarioDto [id=" + id + ", username=" + username + ", email=" + email + ", rol=" + rol + ", fechaAlta="
				+ fechaAlta + ", activo=" + activo + "]";
	}
	
	
	
}
