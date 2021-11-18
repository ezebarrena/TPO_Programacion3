import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import impl.Camino;
import impl.ColaPrioridadNodos;
import impl.ListaHorarios;
import impl.NodoCosto;


public class ejecucion {

    static int n=15;
    
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

    public static void agregarElementoMatriz(Object[] lista,Camino[] comparacion,int elementos){
        //Agregamos los elementos de la lista para la comparacion
        comparacion[elementos]=new Camino();
        comparacion[elementos].origentxt=((String)lista[0]).charAt(0);
        comparacion[elementos].destinotxt=((String)lista[1]).charAt(0);
        comparacion[elementos].minutos=Integer.parseInt((String)lista[2]);
        comparacion[elementos].kilometros=Float.parseFloat((String)lista[3]);
    }


    public static ListaHorarios armarHorarios(File DatosClientes){
        ListaHorarios resultado=new ListaHorarios();
        resultado.inicializar();
        try (BufferedReader br = new BufferedReader(new FileReader(DatosClientes))) {
            br.readLine();
            br.readLine();
            String linea="";
            Object[] lista=new Object[6];
            while ((linea = br.readLine()) != null){
                linea.replace("\n","");
                lista=linea.split(";");
                char cliente=((String)lista[0]).charAt(0);
                int horario=Integer.parseInt((String)lista[5])*60;
                int horarioInicio=Integer.parseInt((String)lista[4])*60;
                resultado.agregarHorario(cliente, horario, horarioInicio);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return resultado;
    }

    public static NodoCosto reducir(Camino[][] matrizCostos,char nodoActual,char[] camino,int nivel,int tiempo,
    int origen,int destino,float CotaAnterior, float CostoCamino,char[] pendientes,File DatosClientes){
        NodoCosto res=new NodoCosto();

        float inf=Float.MAX_VALUE;
        char[] caminoCopia=new char[n];

        for(int q=0;q<nivel;q++){
            caminoCopia[q]=camino[q];
        }
        caminoCopia[nivel]=nodoActual;
        res.setCamino(caminoCopia);

        //El tiempo actual será la hora de inicio mas la de llegada
        res.setTiempoActual(tiempo+matrizCostos[origen][destino].minutos);

        //Seteamos el nivel como el actual+1
        res.setNivel(nivel+1);

        //Seteamos el nodo actual
        res.setNodoActual(nodoActual);

        //Copiamos la matriz para despues reducir
        Camino[][] copiaCostos=new Camino[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Camino agregar=new Camino();
                agregar.destinotxt=matrizCostos[i][j].destinotxt;
                agregar.kilometros=matrizCostos[i][j].kilometros;
                agregar.minutos=matrizCostos[i][j].minutos;
                agregar.origentxt=matrizCostos[i][j].origentxt;
                copiaCostos[i][j]=agregar;
            }
        }

        //cambiamos filas y columnas que corresponda por infinito si el nivel es 1 o +

        if(nivel>0){
            //cambiamos
            //seteamos fila
            for(int f=0;f<n;f++){
                copiaCostos[origen][f].kilometros=inf;
            }
            //seteamos columna
            for(int c=0;c<n;c++){
                copiaCostos[c][destino].kilometros=inf;
            }
            
            //seteamos punto
            copiaCostos[destino][origen].kilometros=inf;

        }

        //reducimos y seteamos

        float costoTotal=0;

        //reduzco filas
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                float num=copiaCostos[i][j].kilometros;
                //si hay algun elemento que no sea 0 o inf se puede reducir la fila
                if(num!=0 && num!=inf){
                    //busco el minimo
                    float min=inf;
                    for(int x=0; x<n;x++){
                        if(min>copiaCostos[i][x].kilometros){
                            min=copiaCostos[i][x].kilometros;
                        }
                    }
                    //Acá ya lo encontró por lo que sumo la reduccion al costo,
                    //reduzco la fila y sigo con la proxima
                    costoTotal+=min;

                    for(int y=0;y<n;y++){
                        if(copiaCostos[i][y].kilometros!=inf){
                            copiaCostos[i][y].kilometros-=min;
                        }
                        
                    }

                    break;
                }
            }
        }

        //reduzco columnas

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                float num=copiaCostos[j][i].kilometros;
                //si hay algun elemento que no sea 0 o inf se puede reducir la columna
                if(num!=0 && num!=inf){
                    //busco el minimo
                    float min=inf;
                    for(int x=0; x<n;x++){
                        if(min>copiaCostos[x][i].kilometros){
                            min=copiaCostos[x][i].kilometros;
                        }
                    }
                    //Acá ya lo encontró por lo que sumo la reduccion al costo,
                    //reduzco la columna y sigo con la proxima
                    costoTotal+=min;

                    for(int y=0;y<n;y++){
                        if(copiaCostos[y][i].kilometros!=inf){
                            copiaCostos[y][i].kilometros-=min;
                        }
                    }
                    break;
                }
            }
        }

        //hecho esto, ya puedo asignar la matriz

        res.setMatrizReducida(copiaCostos);
        
        if(nivel==0){
            res.setCota(costoTotal);
        }
        else{
            res.setCota(costoTotal+CotaAnterior+CostoCamino);
        }

        char[] copiaPendientes=new char[15];

        for(int i=0; i<n;i++){
            copiaPendientes[i]=pendientes[i];
        }

        for(int i=0;i<15;i++){
			if(copiaPendientes[i]==nodoActual){
				copiaPendientes[i]=' ';
			}
		}

        res.setPendientes(copiaPendientes);

        res.setListaHorarios(armarHorarios(copiaPendientes,DatosClientes));
        

        return res;
    }

    public static char[] armarListaClientes(File DatosClientes){
        char[] resultado=new char[n];
        try (BufferedReader br = new BufferedReader(new FileReader(DatosClientes))) {
            br.readLine();
            String linea="";
            Object[] lista=new Object[6];
            for(int i=0;(linea = br.readLine()) != null;i++){
                lista=linea.split(";");
                resultado[i]=((String)lista[0]).charAt(0);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return resultado;
    }


    public static char[][] hijosATiempo(NodoCosto menor,Camino[][] matrizCostos,char Origen,ListaHorarios tiempos){
        char[][] hijos=new char[n][2];
        char[] hijosAComprobar=menor.getPendientes();
        int tiempoTotal;
        int hijoYendo;
        int hijoDesde=Origen-65;
        int cant=0;
        for(int i=0; i<n;i++){
            if(hijosAComprobar[i]!=' '){
                hijoYendo=hijosAComprobar[i]-65;
                tiempoTotal=menor.getTiempoActual()+matrizCostos[hijoDesde][hijoYendo].minutos;
                if(!menor.getListaHorarios().fueraDeTiempo(tiempoTotal, hijosAComprobar[i])){
                    hijos[cant][0]=hijosAComprobar[i];
                    hijos[cant][1]='Z';
                    cant++;
                }
                else{
                    //No está a tiempo
                    hijos[cant][1]=hijosAComprobar[i];
                    cant++;
                }
            }
        }
        while(cant!=15){
            hijos[cant][0]='Z';
            hijos[cant][1]='Z';
            cant++;
        }
        return hijos;
    }

    public static ListaHorarios armarHorarios(char[] copiaPendientes, File DatosClientes){
        ListaHorarios res=new ListaHorarios();
        res.inicializar();
        String linea="";
        String[] lista=new String[6];
        try (BufferedReader br = new BufferedReader(new FileReader(DatosClientes))) {
            br.readLine();
            br.readLine();
            for(int i=0;(linea = br.readLine()) != null;i++){
                lista=linea.split(";");
                for(int j=0;j<n;j++){
                    if(lista[0].charAt(0)==copiaPendientes[j]){
                        res.agregarHorario(lista[0].charAt(0),Integer.parseInt(lista[5]),Integer.parseInt(lista[4]));
                        break;
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return res;
    }


    public static Object[] buscarCamino(Camino[][] matrizCostos, Camino[][][] listaCalidadCaminos,File DatosClientes, int n){
        Object[] resultadocamino=new Object[4];
        char[] camino=new char[n];
        ArrayList<Camino> erro=new ArrayList<Camino>();
        ColaPrioridadNodos colaprioridad=new ColaPrioridadNodos();
        colaprioridad.inicializar();
        int inf=Integer.MAX_VALUE;
        int mejorTiempo=inf;
        float mejorDistancia=inf;
        int nivel=0;


        //copiamos los clientes
        char[] clientes=armarListaClientes(DatosClientes);
        
        //armamos la lista de horarios
        ListaHorarios listaHorarios=armarHorarios(DatosClientes);

        //hacer una copia de la matriz costos para no modificar la original

        Camino[][] copiaCostos=new Camino[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Camino agregar=new Camino();
                agregar.destinotxt=matrizCostos[i][j].destinotxt;
                agregar.kilometros=matrizCostos[i][j].kilometros;
                agregar.minutos=matrizCostos[i][j].minutos;
                agregar.origentxt=matrizCostos[i][j].origentxt;
                copiaCostos[i][j]=agregar;
            }
        }
        

        //calcular la matriz reducida junto con su limite inferior
        //basicamente el primer nodocosto
        NodoCosto raiz=reducir(copiaCostos,'A',camino,nivel,420,0,0,0,0,clientes,DatosClientes);

        //lo agregamos a la cola de nodos
        colaprioridad.append(raiz);

        //Ahora armamos un ciclo para chequear caminos posibles y se elige uno y se agrega
        while(!colaprioridad.vacia()){
            //primero agarramos el primer nodo de la cola
            NodoCosto menor=colaprioridad.pop();
            //ahora buscamos los hijos de ese que tienen camino posible
            char[] posibilidades=new char[n];
            char[] caminosNodosSinTiempo=new char[n];
            char[][] posibilidadesSinFiltrar=hijosATiempo(menor,matrizCostos,menor.getNodoActual(),listaHorarios);
            
            for(int a=0;a<n;a++){
                posibilidades[a]=posibilidadesSinFiltrar[a][0];
                caminosNodosSinTiempo[a]=posibilidadesSinFiltrar[a][1];
            }

            for(int e=0;e<n;e++){
                if(caminosNodosSinTiempo[e]!='Z'){
                    Camino agregar=new Camino();
                    int num1=((int)menor.getNodoActual())-65;
                    int num2=((int)(caminosNodosSinTiempo[e]))-65;
                    if(num1==-65 || num2==-65){
                        System.out.print("Hola");
                    }
                    agregar.destinotxt=caminosNodosSinTiempo[e];
                    agregar.origentxt=menor.getNodoActual();
                    agregar.minutos=matrizCostos[num1][num2].minutos;
                    agregar.kilometros=matrizCostos[num1][num2].kilometros;
                    erro.add(agregar);
                }
            }
            
            
            //calculamos todos
            for(int z=0;z<n;z++){
                //calcular todos los nodos y encolarlos
                if(posibilidades[z]!='Z'){
                    int num3=((int)menor.getNodoActual())-65;
                    int num4=(((int)posibilidades[z])-65);
                    NodoCosto agregar=reducir(menor.getMatrizReducida(),posibilidades[z],menor.getCamino(),menor.getNivel(),menor.getTiempoActual(),
                    num3,num4,menor.getCota(),menor.getMatrizReducida()[num3][num4].kilometros,menor.getPendientes(),DatosClientes);
                    colaprioridad.append(agregar);
                }
            }

            //Comprobar si es hoja y filtrar
            if(!colaprioridad.vacia() && colaprioridad.nivelPrimero()==n){
                //significa que es hoja
                NodoCosto eval=colaprioridad.pop();
                if(mejorDistancia>eval.getCota()){
                    //significa que el camino encontrado es mejor
                    mejorDistancia=eval.getCota();
                    mejorTiempo=eval.getTiempoActual();
                    camino=eval.getCamino();
                    colaprioridad.filtrar(eval.getCota());
                }
            }
        }

        //devolvemos todo: el camino, el mejor tiempo, la mejor distancia y los fallos

        //el camino va a ser 0
        resultadocamino[0]=camino;
        //el tiempo 1
        resultadocamino[1]=mejorTiempo;
        //La distancia 2
        resultadocamino[2]=mejorDistancia;
        //los fallos 3
        resultadocamino[3]=erro;
        
        return resultadocamino;
    }


    
    public static void rutaPedidos(File DatosClientes, File Caminos){
        int n=15;
        Object[] resultado=generarMatrizCosto(Caminos, n); //fijarse que los indices se correspondan
        Camino[][] matrizCostos=((Camino[][])resultado[1]);
        Camino[][][] listaCalidadCaminos=(Camino[][][]) resultado[0];
        

        int infi=Integer.MAX_VALUE;
        float inff=Float.MAX_VALUE;

        float mejorCosto=inff;
        char[] mejorCamino=new char[15];
        int mejorTiempo=infi;



        while(true){
            Object[] resultados=buscarCamino(matrizCostos,listaCalidadCaminos,DatosClientes,n);
            if(mejorCosto>(float)resultados[2]){
                mejorCosto=(float)resultados[2];
                mejorTiempo=(int)resultados[1];
                mejorCamino=(char[])resultados[0];
            }
            ArrayList<Camino> errores=(ArrayList<Camino>)resultados[3];
            System.out.print("");
            //mirar si quedaron errores, si los hubo cambio el primer error y dejo que se repita el algoritmo
            //si no hay nada, break y fin
            if(!errores.isEmpty()){
                //buscar la menor diferencia de km, cambiar lo necesario en matriz costo y dejar que se repita el algo
                while(!errores.isEmpty()){
                    Camino correcion=errores.get(0);
                    int origenc=correcion.origentxt-65;
                    int destinoc=correcion.destinotxt-65;
                    if(listaCalidadCaminos[origenc][destinoc][0]!=null){
                        Camino nuevo=listaCalidadCaminos[origenc][destinoc][0];
                        matrizCostos[origenc][destinoc]=nuevo;
                    }
                }
            }
            else{
                break;
            }
        }

        int refOr=mejorCamino[14]-65;
        mejorTiempo+=matrizCostos[refOr][0].minutos;

        int horas=mejorTiempo/60;
        int minutos=(mejorTiempo-horas*60);

        
        System.out.println("El mejor camino tiene "+mejorCosto+" kms y termina a las "+horas+":"+minutos+" hs.");
        System.out.println("El recorrido seguido es:");
        int f=0;
        while(f<n){
            System.out.print((mejorCamino[f]+" -> "));
            f++;
        }
        System.out.print("A");

    }
    public static void main(String[] args){

        File DatosClientes=new File("DatosClientes.txt");
        File Caminos=new File("Caminos.txt");
        rutaPedidos(DatosClientes, Caminos);
        
    }
    
}
