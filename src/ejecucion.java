import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import impl.ListaHorarios;

public class ejecucion {

    public static ArrayList<int[][]> generarMatrizCosto(File Caminos,int n){
        ArrayList<Object>[][] listaCalidadCamino = new ArrayList[n][n];
        int[][][] matrizCosto = new int[n][n][4];
        Vector [][][] caminos = new Vector [n][n][14];

        //ArrayList<int[][]> resultado = new ArrayList(2)<[][]>; no sabemos para que es 
        
        try (BufferedReader br = new BufferedReader(new FileReader(Caminos))) {
            String linea;
            Object[] lista = new Object[4];
            Object[][] comparacion = new Object[14][4];
            linea=br.readLine();
            linea=br.readLine();
            lista=linea.split(";");
            int elementos=0;
            int costoCamino;
            int tiempoCamino;
            String origenAuxiliar = (String)lista[0]; //Si se rompe puede ser esto
            String destinoAuxiliar = (String)lista[1];
            String origenActual = "";
            String destinoActual = "";
            agregarElementoMatriz(lista,comparacion,elementos);

            while ((linea = br.readLine()) != null) {
               linea.replace("\n","");
               lista=linea.split(";");
               if(origenAuxiliar.equals(origenActual)&&destinoAuxiliar.equals(destinoActual)){
                   agregarElementoMatriz(lista,comparacion,elementos);
               }
               else{
                   if(elementos>1){
                        seleccionarCamino(comparacion,listaCalidadCamino,matrizCosto,elementos);
                        origenAuxiliar=origenActual;
                        destinoAuxiliar=destinoActual;
                   }
                   else{
                        
                        elementos=0;
                        origenAuxiliar=origenActual;
                        destinoAuxiliar=destinoActual;
                       }
                   }
                   agregarElementoMatriz(lista,comparacion,elementos);
               }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return resultado;
    }

    public static void seleccionarCamino(Object[][] comparacion, ArrayList<Object>[][] listaCalidadCamino, int[][][] matrizCosto,int elementos){
        //Obtenemos los indices del origen y destino
        int origen=Integer.parseInt((((String)comparacion[0][0]).toLowerCase()))-97;
        int destino=Integer.parseInt((((String)comparacion[0][1]).toLowerCase()))-97;

        if(elementos>1){
            boolean mejor=true;
            for(int i=0;i<elementos;i++){
                int mejorTiempo=(Integer)comparacion[i][2];//Si se rompe puede ser esto
                int mejorDistancia=(Integer)comparacion[i][3];
                mejor=true;
                for(int j=0;j<elementos;j++){
                    if((Integer)comparacion[j][2]<mejorTiempo||(Integer)comparacion[j][3]<mejorDistancia){
                        //uno de los dos es peor que el que tenemos
                        mejor=false;
                        break;
                    }
                }
                if(mejor==true){
                    matrizCosto[origen][destino][0]=origen;
                    matrizCosto[origen][destino][1]=destino;
                    matrizCosto[origen][destino][2]=mejorTiempo;
                    matrizCosto[origen][destino][3]=mejorDistancia;
                    matrizCosto[destino][origen][0]=origen;
                    matrizCosto[destino][origen][1]=destino;
                    matrizCosto[destino][origen][2]=mejorTiempo;
                    matrizCosto[destino][origen][3]=mejorDistancia;
                    break;
                }
            }
            if(!mejor){
                //hay que seleccionar el mas corto y 
                //el resto (los que son mas rapidos que el mas corto)
                //hay que agregarlos a las posibilidades
                int mejorDistancia=Integer.MAX_VALUE;
                int tiempoMejor=Integer.MAX_VALUE;
                for(int i=0;i<elementos;i++){
                    if((Integer)comparacion[i][3]<mejorDistancia){
                        mejorDistancia=(Integer)comparacion[i][3];
                        tiempoMejor=(Integer)comparacion[i][2];
                    }
                }
                matrizCosto[origen][destino][0]=origen;
                matrizCosto[origen][destino][1]=destino;
                matrizCosto[origen][destino][2]=tiempoMejor;
                matrizCosto[origen][destino][3]=mejorDistancia;
                matrizCosto[destino][origen][0]=origen;
                matrizCosto[destino][origen][1]=destino;
                matrizCosto[destino][origen][2]=tiempoMejor;
                matrizCosto[destino][origen][3]=mejorDistancia;

                for(int i=0; i<elementos;i++){
                    if((Integer)comparacion[i][2]<tiempoMejor){
                        //Encontramos una posibilidad
                        listaCalidadCamino[origen][destino].add(comparacion[i]);
                    }
                }
            }
        }
        else{
            int mejorTiempo=(Integer)comparacion[0][2];
            int mejorDistancia=(Integer)comparacion[0][3];
            matrizCosto[origen][destino][0]=origen;
            matrizCosto[origen][destino][1]=destino;
            matrizCosto[origen][destino][2]=mejorTiempo;
            matrizCosto[origen][destino][3]=mejorDistancia;
            matrizCosto[destino][origen][0]=origen;
            matrizCosto[destino][origen][1]=destino;
            matrizCosto[destino][origen][2]=mejorTiempo;
            matrizCosto[destino][origen][3]=mejorDistancia;
        }
        elementos=0;
    }




    public static void rutaPedidos(File DatosClientes, File Caminos){
        int n=15;
        ArrayList<int[][]> resultado=generarMatrizCosto(Caminos, n); //fijarse que los indices se correspondan

        int[][] listaCalidadCamino=resultado.get(0);
        int[][] matrizCosto=resultado.get(1);

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
