package impl;

public class NodoCosto {
    private int tiempoActual;
    private int [][] matrizReducida;
    private char nodoActual;
    private char[] camino;
    private float kilometros;
    private int nivel;

    public void setTiempoActual(int x){
        this.tiempoActual = x;
    }

    public int getTiempoActual(){
        return tiempoActual;
    }

    public void setMatrizReducida(int [][] x){
        this.matrizReducida = x;
    }

    public int [][] getMatrizReducida(){
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

    public void setKilometros(float x){
        this.kilometros = x;
    }

    public float getKilometros(){
        return kilometros;
    }

    public void setNivel(int x){
        this.nivel = x;
    }

    public int getNivel(){
        return nivel;
    }

}


