package impl;

public class NodoCosto {

    private int tiempoActual;
    private Camino [][] matrizReducida;
    private char nodoActual;
    private char[] camino;
    private int nivel;
    private float cota;
    char[] pendientes;
    ListaHorarios listaHorarios;

    public void setListaHorarios(ListaHorarios listaHorarios){
        this.listaHorarios=listaHorarios;
    }

    public ListaHorarios getListaHorarios(){
        return this.listaHorarios;
    }

	public void setPendientes(char[] pendientes){
		this.pendientes=pendientes;
	}

	public char[] getPendientes(){
		return this.pendientes;
	}

    public void setTiempoActual(int x){
        this.tiempoActual = x;
    }

    public int getTiempoActual(){
        return tiempoActual;
    }

    public void setMatrizReducida(Camino [][] x){
        this.matrizReducida = x;
    }

    public Camino[][] getMatrizReducida(){
        return matrizReducida;
    }

    public void setNodoActual(char x){
        this.nodoActual = x;
    }

    public char getNodoActual(){
        return nodoActual;
    }

    public void setCamino(char [] x){
        this.camino = x;
    }

    public char [] getCamino(){
        return camino;
    }

    public void setNivel(int x){
        this.nivel = x;
    }

    public int getNivel(){
        return nivel;
    }

    public float getCota(){
        return cota;
    }

    public void setCota(float x){
        cota=x;
    }

}


