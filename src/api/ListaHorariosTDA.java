package api;

public interface ListaHorariosTDA {
    
    public void inicializarLista();
    public void agregarHorario(int horario);
    public void eliminarHorario(char cliente);
    public boolean fueraDeTiempo(int horario);

}
