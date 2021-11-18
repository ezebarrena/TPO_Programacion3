package api;

import impl.NodoCosto;

public interface ColaPrioridadNodosTDA {
    
	void inicializar();
	//Inicializa la lista.
	//La precondición es que la lista no esté inicializada.
	//Es precondición del resto de metodos.
	
	void append(NodoCosto x);
	//Agrega un elemento a la lista.

    void filtrar(float x);
    //esto no está implementado, hacer que la lista elimine todos aquellos nodos
    //cuya cota sea superior o igual a el float x
	
	NodoCosto pop();
	//Elimina y devuelve el primer elemento de la lista.

    boolean vacia(); 
    //devuelve true si la cola esta vacia

    int nivelPrimero();
}
