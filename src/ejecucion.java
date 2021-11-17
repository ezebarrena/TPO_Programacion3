import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import impl.ListaHorarios;


public class ejecucion {
    
    static class Camino{
        char origentxt;
        char destinotxt;
        int minutos;
        float kilometros;
    }

    public class NodoCosto {
        private int tiempoActual;
        private int [][] matrizReducida;
        private char nodoActual;
        private char[] camino;
        private float kilometros;
        private int nivel;
    }

    public static Object[] generarMatrizCosto(File Caminos,int n){
        Camino[][][] listaCalidadCamino = new Camino[n][n][14];
        Camino[][] matrizCosto = new Camino[n][n];
        float inf=Float.MAX_VALUE;        

        //ArrayList<int[][]> resultado = new ArrayList(2)<[][]>; no sabemos para que es 
        
        try (BufferedReader br = new BufferedReader(new FileReader(Caminos))) {
            String linea;
            Object[] lista = new Object[4];
            Camino[] comparacion = new Camino[14];
            linea=br.readLine();
            linea=br.readLine();
            lista=linea.split(";");
            lista[3]=lista[3].toString().replace(",",".");
            int elementos=0;
            int costoCamino;
            int tiempoCamino;
            String origenAuxiliar = (String)lista[0]; //Si se rompe puede ser esto
            String destinoAuxiliar = (String)lista[1];
            String origenActual = (String)lista[0];
            String destinoActual = (String)lista[1];
            agregarElementoMatriz(lista,comparacion,elementos);
            elementos++;

            while ((linea = br.readLine()) != null) {
               linea.replace("\n","");
               lista=linea.split(";");
               lista[3]=lista[3].toString().replace(",",".");
               origenActual = (String)lista[0];
               destinoActual = (String)lista[1];
               if(origenAuxiliar.equals(origenActual)&&destinoAuxiliar.equals(destinoActual)){
                   agregarElementoMatriz(lista,comparacion,elementos);
                   elementos++;
               }
               else{
                    seleccionarCamino(comparacion,listaCalidadCamino,matrizCosto,elementos);
                    elementos=0;
                    origenAuxiliar=origenActual;
                    destinoAuxiliar=destinoActual;
                    agregarElementoMatriz(lista,comparacion,elementos);
                    elementos=1;
               }
            }
            seleccionarCamino(comparacion,listaCalidadCamino,matrizCosto,elementos);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        for(int i=0;i<n;i++){
            matrizCosto[i][i]=new Camino();
            matrizCosto[i][i].kilometros=inf;
        }
        Object[] resultado= new Object[3];
        resultado[0]=listaCalidadCamino;
        resultado[1]=matrizCosto;

        return resultado;
    }

    public static void seleccionarCamino(Camino[] comparacion, Camino[][][] listaCalidadCamino, Camino[][] matrizCosto,int elementos){
        //Obtenemos los indices del origen y destino
        char o=comparacion[0].origentxt;
        char d=comparacion[0].destinotxt;
        int origen=o-65;
        int destino=d-65;
        //restamos 65 ya que A es = a 65 en ASCII

        if(elementos>1){ //Si hay mas de un elemento, hay que elegir cual usar
            boolean mejor=true;
            for(int i=0;i<elementos;i++){
                int mejorTiempo=comparacion[i].minutos;//Si se rompe puede ser esto
                float mejorDistancia=comparacion[i].kilometros;
                mejor=true;
                for(int j=0;j<elementos;j++){
                    if(comparacion[j].minutos<mejorTiempo||comparacion[j].kilometros<mejorDistancia){
                        //uno de los dos es peor que el que tenemos
                        mejor=false;
                        break;
                    }
                }
                if(mejor==true){ //significa que ningun camino lo supera en nada, por tanto, es el mejor
                    matrizCosto[origen][destino]=new Camino();
                    matrizCosto[origen][destino].origentxt=o;
                    matrizCosto[origen][destino].destinotxt=d;
                    matrizCosto[origen][destino].minutos=mejorTiempo;
                    matrizCosto[origen][destino].kilometros=mejorDistancia;
                    matrizCosto[destino][origen]=new Camino();
                    matrizCosto[destino][origen].origentxt=d;
                    matrizCosto[destino][origen].destinotxt=o;
                    matrizCosto[destino][origen].minutos=mejorTiempo;
                    matrizCosto[destino][origen].kilometros=mejorDistancia;
                    break;
                }
            }
            if(!mejor){ 
                //si ninguno fue superior
                //hay que seleccionar el mas corto y 
                //el resto (los que son mas rapidos que el mas corto)
                //hay que agregarlos a las posibilidades
                float mejorDistancia=Long.MAX_VALUE;
                int tiempoMejor=Integer.MAX_VALUE;
                for(int i=0;i<elementos;i++){//buscamos el mas corto
                    if(comparacion[i].kilometros<mejorDistancia){
                        mejorDistancia=comparacion[i].kilometros;
                        tiempoMejor=comparacion[i].minutos;
                    }
                }
                //Agregamos el mas corto a la matriz costo
                matrizCosto[origen][destino]=new Camino();
                matrizCosto[destino][origen]=new Camino();
                matrizCosto[origen][destino].origentxt=o;
                matrizCosto[origen][destino].destinotxt=d;
                matrizCosto[origen][destino].minutos=tiempoMejor;
                matrizCosto[origen][destino].kilometros=mejorDistancia;
                matrizCosto[destino][origen].destinotxt=o;
                matrizCosto[destino][origen].origentxt=d;
                matrizCosto[destino][origen].minutos=tiempoMejor;
                matrizCosto[destino][origen].kilometros=mejorDistancia;

                for(int i=0; i<elementos;i++){ //buscamos aquellos mas rapidos que el mas corto y los agregamos para considerarlos
                    if(comparacion[i].minutos<tiempoMejor){
                        //Encontramos una posibilidad
                        Camino agregar=new Camino();
                        agregar.origentxt=comparacion[i].origentxt;
                        agregar.destinotxt=comparacion[i].destinotxt;
                        agregar.minutos=comparacion[i].minutos;
                        agregar.kilometros=comparacion[i].kilometros;
                        listaCalidadCamino[origen][destino][0]=agregar;
                        
                    }
                }
            }
        }
        else{ //sino, hay un solo elemento y lo usamos
            int mejorTiempo=comparacion[0].minutos;
            float mejorDistancia=comparacion[0].kilometros;

            matrizCosto[origen][destino]=new Camino();
            matrizCosto[destino][origen]=new Camino();
            matrizCosto[origen][destino].origentxt=o;
            matrizCosto[origen][destino].destinotxt=d;
            matrizCosto[origen][destino].minutos=mejorTiempo;
            matrizCosto[origen][destino].kilometros=mejorDistancia;
            matrizCosto[destino][origen].origentxt=d;
            matrizCosto[destino][origen].destinotxt=o;
            matrizCosto[destino][origen].minutos=mejorTiempo;
            matrizCosto[destino][origen].kilometros=mejorDistancia;
        }
        //Al ya haber agregado las cosas seteamos los elementos a 0 nuevamente
        elementos=0;
    }

    public static void agregarElementoMatriz(Object[] lista,ejecucion.Camino[] comparacion,int elementos){
        //Agregamos los elementos de la lista para la comparacion
        comparacion[elementos]=new Camino();
        comparacion[elementos].origentxt=((String)lista[0]).charAt(0);
        comparacion[elementos].destinotxt=((String)lista[1]).charAt(0);
        comparacion[elementos].minutos=Integer.parseInt((String)lista[2]);
        comparacion[elementos].kilometros=Float.parseFloat((String)lista[3]);
    }


    
    public static void rutaPedidos(File DatosClientes, File Caminos){
        int n=15;
        Object[] resultado=generarMatrizCosto(Caminos, n); //fijarse que los indices se correspondan
        Camino[][] matrizCostos=((Camino[][])resultado[1]);
        Camino[][][] listaCalidad=(Camino[][][]) resultado[0];

        
        
        System.out.print(matrizCostos.getClass());
        for (int row = 0; row < n; row++)//Cycles through rows
        {
            for (int col = 0; col < n; col++)//Cycles through columns
            {
                System.out.print(matrizCostos[row][col].kilometros+" "); //change the %5d to however much space you want
            }
            System.out.println(); //Makes a new row
        }
        System.out.print("");
        
        /*int[][] listaCalidadCamino=(int[][]) resultado[0];
        int[][] matrizCosto=(int[][]) resultado[1];

        int inf=Integer.MAX_VALUE;

        int restriccion=inf;

        int mejorCosto=inf;
        String[] mejorCamino=new String[15];
        String mejorTiempo="";

        ListaHorarios listaHorarios=new ListaHorarios();
        //inicializar bla bla bla

        char[] recorrido=new char[n];

        //mejorCosto,mejorCamino,mejorTiempo <- buscarCamino(listaCalidadCamino,listaHorarios,matrizCosto,restriccion,recorrido)

        //matrizClientes<-generarMatrizClientes(DatosClientes)

        /*
        para cada Char paso mejorCamino
	    indiceCliente<- paso.minúscula.int-97
	    si char.esElUltimo
		    imprimir(matrizClientes[indiceCliente][1]+" "matrizClientes[indiceCliente][2]
	    sino
		    imprimir(matrizClientes[indiceCliente][1]+" "matrizClientes[indiceCliente][2]+ "->"
	    fin si
        fin para

        imprimir("El costo de este camino es "+mejorCosto+" y termina la jornada a las "+mejorTiempo+" horas")
        */


    }
    public static void main(String[] args){

        File DatosClientes=new File("DatosClientes.txt");
        File Caminos=new File("Caminos.txt");
        rutaPedidos(DatosClientes, Caminos);


    }
    
}
