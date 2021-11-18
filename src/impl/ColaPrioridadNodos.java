package impl;

import api.ColaPrioridadNodosTDA;

class Nodo{
	NodoCosto nodo;
	Nodo sig;
}

public class ColaPrioridadNodos implements ColaPrioridadNodosTDA {
	
	Nodo raiz;

	
	public void inicializar() {
		raiz=null;
	}
	


	public void append(NodoCosto x) {
		Nodo aux=new Nodo();
		aux.nodo=x;
		if(raiz==null){
			raiz=aux;
		}
		else{
			float cActual=x.getCota();
			if(raiz.nodo.getCota()>cActual){
				aux.sig=raiz;
				raiz=aux;
			}
			else{
				Nodo recon=new Nodo();
				recon=raiz;
				
				while(recon.sig!=null && recon.nodo.getCota()<cActual){
					recon=recon.sig;
				}
				if(recon.sig==null){
					recon.sig=aux;
					aux.sig=null;
				}
				else{
					aux.sig=recon.sig.sig;
					recon.sig=aux;
				}
			}
		}
	}
	
	public NodoCosto pop() {
		NodoCosto resultado=raiz.nodo;
		raiz=raiz.sig;
		return resultado;
	}

	public int nivelPrimero(){
		return raiz.nodo.getNivel();
	}

	public void filtrar(float x) {
		if(raiz!=null){
			if(raiz.nodo.getCota()>x){
				//significa que todos son mayores
				raiz=null;
			}
			else{
				Nodo recon=new Nodo();
				while(recon!=null && recon.nodo.getCota()<x){
					recon=recon.sig;
				}
				recon=null;
			}
		}
	}
	
	public boolean vacia(){
		return raiz==null;
	}

}

