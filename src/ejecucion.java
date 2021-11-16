import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import impl.ListaHorarios;

public class ejecucion {

    public static Object[] generarMatrizCosto(File Caminos,int n){
        ArrayList<Object>[][] listaCalidadCamino = new ArrayList[n][n];
        float[][][] matrizCosto = new float[n][n][4];
        float inf=Float.MAX_VALUE;        

        //ArrayList<int[][]> resultado = new ArrayList(2)<[][]>; no sabemos para que es 
        
        try (BufferedReader br = new BufferedReader(new FileReader(Caminos))) {
            String linea;
            Object[] lista = new Object[4];
            Object[][] comparacion = new Object[14][4];
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
        }
        catch(IOException e){
            e.printStackTrace();
        }
        for(int i=0;i<n;i++){
            matrizCosto[i][i][3]=inf;
        }
        Object[] resultado= new Object[3];
        resultado[0]=listaCalidadCamino;
        resultado[1]=matrizCosto;

        return resultado;
    }

    public static void seleccionarCamino(Object[][] comparacion, ArrayList<Object>[][] listaCalidadCamino, float[][][] matrizCosto,int elementos){
        //Obtenemos los indices del origen y destino
        char o=(((String)comparacion[0][0])).charAt(0);
        char d=(((String)comparacion[0][1])).charAt(0);
        int origen=(int)o-65;
        int destino=(int)d-65;
        Long origenf=Long.valueOf((int)o-65);
        Long destinof=Long.valueOf((int)d-65);
        float origend=Float.parseFloat(destinof.toString());
        float destinod=Float.parseFloat(origenf.toString());
        //restamos 65 ya que A es = a 65 en ASCII

        if(elementos>1){ //Si hay mas de un elemento, hay que elegir cual usar
            boolean mejor=true;
            for(int i=0;i<elementos;i++){
                float mejorTiempo=Float.parseFloat(comparacion[i][2].toString());//Si se rompe puede ser esto
                float mejorDistancia=Float.parseFloat(comparacion[i][3].toString());
                mejor=true;
                for(int j=0;j<elementos;j++){
                    if(Float.parseFloat(comparacion[j][2].toString())<mejorTiempo||Float.parseFloat(comparacion[j][3].toString())<mejorDistancia){
                        //uno de los dos es peor que el que tenemos
                        mejor=false;
                        break;
                    }
                }
                if(mejor==true){ //significa que ningun camino lo supera en nada, por tanto, es el mejor
                    matrizCosto[origen][destino][0]=origend;
                    matrizCosto[origen][destino][1]=destinod;
                    matrizCosto[origen][destino][2]=mejorTiempo;
                    matrizCosto[origen][destino][3]=mejorDistancia;
                    matrizCosto[destino][origen][0]=origend;
                    matrizCosto[destino][origen][1]=destinod;
                    matrizCosto[destino][origen][2]=mejorTiempo;
                    matrizCosto[destino][origen][3]=mejorDistancia;
                    break;
                }
            }
            if(!mejor){ //si ninguno fue superior
                //hay que seleccionar el mas corto y 
                //el resto (los que son mas rapidos que el mas corto)
                //hay que agregarlos a las posibilidades
                float mejorDistancia=Long.MAX_VALUE;
                float tiempoMejor=Long.MAX_VALUE;
                for(int i=0;i<elementos;i++){//buscamos el mas corto
                    if(Float.parseFloat(comparacion[i][3].toString())<mejorDistancia){
                        mejorDistancia=Float.parseFloat(comparacion[i][3].toString());
                        tiempoMejor=Float.parseFloat(comparacion[i][2].toString());
                    }
                }
                //Agregamos el mas corto a la matriz costo
                matrizCosto[origen][destino][0]=origend;
                matrizCosto[origen][destino][1]=destinod;
                matrizCosto[origen][destino][2]=tiempoMejor;
                matrizCosto[origen][destino][3]=mejorDistancia;
                matrizCosto[destino][origen][0]=origend;
                matrizCosto[destino][origen][1]=destinod;
                matrizCosto[destino][origen][2]=tiempoMejor;
                matrizCosto[destino][origen][3]=mejorDistancia;

                for(int i=0; i<elementos;i++){ //buscamos aquellos mas rapidos que el mas corto y los agregamos para considerarlos
                    if(Float.parseFloat(comparacion[i][2].toString())<tiempoMejor){
                        //Encontramos una posibilidad
                        if(listaCalidadCamino[origen][destino]==null){
                            listaCalidadCamino[origen][destino]=new ArrayList<>();
                        }
                        Object[] agregar=new Object[4];
                        agregar[0]=comparacion[i][0];
                        agregar[1]=comparacion[i][1];
                        agregar[2]=comparacion[i][2];
                        agregar[3]=comparacion[i][3];
                        listaCalidadCamino[origen][destino].add(agregar);
                        
                    }
                }
            }
        }
        else{ //sino, hay un solo elemento y lo usamos
            String mejorTiempo=comparacion[0][2].toString();
            String mejorDistancia=comparacion[0][3].toString();

            float mejorTiempoN=Float.parseFloat(mejorTiempo);
            float mejorDistanciaN=Float.parseFloat(mejorDistancia);


            matrizCosto[origen][destino][0]=origend;
            matrizCosto[origen][destino][1]=destinod;
            matrizCosto[origen][destino][2]=mejorTiempoN;
            matrizCosto[origen][destino][3]=mejorDistanciaN;
            matrizCosto[destino][origen][0]=origend;
            matrizCosto[destino][origen][1]=destinod;
            matrizCosto[destino][origen][2]=mejorTiempoN;
            matrizCosto[destino][origen][3]=mejorDistanciaN;
        }
        //Al ya haber agregado las cosas seteamos los elementos a 0 nuevamente
        elementos=0;
    }

    public static void agregarElementoMatriz(Object[] lista,Object[][] comparacion,int elementos){
        //Agregamos los elementos de la lista para la comparacion
        comparacion[elementos][0]=lista[0];
        comparacion[elementos][1]=lista[1];
        comparacion[elementos][2]=lista[2];
        comparacion[elementos][3]=lista[3];
    }


    
    public static void rutaPedidos(File DatosClientes, File Caminos){
        int n=15;
        Object[] resultado=generarMatrizCosto(Caminos, n); //fijarse que los indices se correspondan
        float[][][] matrizCostos=((float[][][])resultado[1]);
        ArrayList<Object>[][] listaCalidad=(ArrayList<Object>[][]) resultado[0];

        
        
        System.out.print(matrizCostos.getClass());
        for (int row = 0; row < n; row++)//Cycles through rows
        {
            for (int col = 0; col < n; col++)//Cycles through columns
            {
                System.out.print(matrizCostos[row][col][3]+" "); //change the %5d to however much space you want
            }
            System.out.println(); //Makes a new row
        }
        
        int[][] listaCalidadCamino=(int[][]) resultado[0];
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
