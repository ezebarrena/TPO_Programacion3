import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import impl.ListaHorarios;

public class ejecucion {

    public static ArrayList<int[][]> generarMatrizCosto(File Caminos,int n){
        int[][] listaCalidadCamino= new int[n][2];
        int[][] matrizCosto=new int[n][n];
        ArrayList<int[][]> resultado=new ArrayList(2);
        
        try (BufferedReader br = new BufferedReader(new FileReader(Caminos))) {
            String linea;
            Object[] lista= new Object[5];
            Object[][] matriz=new Object[10][5];
            String origenAuxiliar="";
            String destinoAuxiliar="";
            String origenActual="";
            String destinoActual="";

            while ((linea = br.readLine()) != null) {
               linea.replace("\n","");
               lista=linea.split(";");
               
               if(origenAuxiliar.equals(origenActual)&&destinoAuxiliar.equals(destinoActual)){
                   //agrego
               }
               else{
                   if(matriz[1][0]!=null){
                       //comparo
                       //busqueda y comparacion
                    /*  si hay un camino definitivamente mejor
					        //agregarlo a matrizCosto y setear listaCalidadCamino como 0
				        sino
					        //buscar el mas rapido de los mas cortos y lo agregamos a matrizCosto
					        //seteamos listaCalidadCamino como 1 y le ponemos el camino mas corto de los mas rapidos
				        fin si*/
                        origenAuxiliar=origenActual;
                        destinoAuxiliar=destinoActual;
                   }
                   else{
                       if(!matriz[0][0].equals("origen")){
                           //es real, agrego
                       }
                       else{
                           //borro y agrego a matriz la lista
                           origenAuxiliar=origenActual;
                           destinoAuxiliar=destinoActual;
                       }
                   }
               }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return resultado;
    }

    public static void rutaPedidos(File DatosClientes, File Caminos){
        int n=15;
        ArrayList<int[][]> resultado=generarMatrizCosto(Caminos, n);

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
