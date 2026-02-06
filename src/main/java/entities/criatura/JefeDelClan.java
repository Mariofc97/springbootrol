package entities.criatura;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("JEFE_DEL_CLAN")
public class JefeDelClan extends Criatura {
	public JefeDelClan() {
		super("Jefe del clan", null, 50, 500, 280, 80, "Garrotazo atronador");
		// TODO Auto-generated constructor stub
	}
}
