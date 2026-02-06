package entities.episodios;

public class AccionesEpisodio {

    private StringBuilder log = new StringBuilder();

    public void add(String texto) {
        log.append(texto).append("\n");
    }

    public String getLog() {
        return log.toString();
    }
}
