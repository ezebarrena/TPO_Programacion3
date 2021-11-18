package api;



public interface ListaHorariosTDA {
    void inicializar();
    //Inicializa la lista

    void eliminar(char cliente);
    //Elimina el horario de un cliente

    void agregarHorario(char cliente, int horario,int horarioInicio);
    //Agrega un horario a la lista

    boolean fueraDeTiempo(int tiempoActual,char cliente);
    //Devuelve true si algun cliente esta fuera de tiempo

}
