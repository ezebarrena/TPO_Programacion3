package impl;

import api.ListaHorariosTDA;

class Nodoh{
    int horario;
    int horarioInicio;
    char cliente;
    Nodoh sig;
}
public class ListaHorarios implements ListaHorariosTDA {

    Nodoh raiz;

    public void inicializar() {
        raiz=null;
    }
    
    public void agregarHorario(char cliente, int horario,int horarioInicio) {
        Nodoh aux=new Nodoh();
        aux.cliente=cliente;
        aux.horarioInicio=horarioInicio;
        aux.horario=horario;
        if(raiz==null){
            raiz=aux;
        }
        else{
            if(raiz.horario>=horario){
                aux.sig=raiz;
                raiz=aux;
            }
            else{
                Nodoh recon=new Nodoh();
                recon=raiz;
                while(recon.sig!=null && recon.sig.horario<horario){
                    recon=recon.sig;
                }
                if(recon.sig==null){
                    recon.sig=aux;
                }
                else{
                    aux.sig=recon.sig;
                    recon.sig=aux;
                }
            }
        }
    }

    
    public boolean fueraDeTiempo(int tiempoActual, char cliente) {
        boolean respuesta=false;
        Nodoh recon=new Nodoh();
        recon=raiz;
        if(recon.horario<=tiempoActual){
            while(recon.cliente!=cliente){
                recon=recon.sig;
            }
            if(recon.horarioInicio>tiempoActual){
                respuesta=true;
            }
        }
        else{
            respuesta=true;
        }
        return respuesta;
    }
    
    
    public void eliminar(char cliente) {
        if(raiz!=null){
            if(raiz.cliente==cliente){
                raiz.sig=raiz;
            }
            else{
                Nodoh recon=new Nodoh();
                recon=raiz;
                while(recon.sig!=null && recon.sig.cliente!=cliente){
                    recon=recon.sig;
                }
                if(recon.sig.cliente==cliente){
                    recon.sig.sig=recon.sig;
                    }
                }
            }
        }
    }

