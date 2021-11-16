package api;

import impl.NodoCosto;

public interface ColaPrioridadNodosTDA {
    
    public void inicializarCola();
    public void añadir(int x);
    public void eliminar();
    public NodoCosto primero();

}
