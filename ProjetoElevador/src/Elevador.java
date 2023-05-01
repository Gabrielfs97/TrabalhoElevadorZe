import java.util.*;
import java.util.concurrent.Semaphore;

public class Elevador extends Thread{
	
	public boolean EstaSeMovendo;
    public boolean EmUso;
    public boolean Parar;
    public int AndarCorreto;
    boolean portasAbertas;
    List<Passageiros> PassageiroEntrou = new ArrayList<Passageiros>();
    public Semaphore sinal = new Semaphore(1);
    private Predio predio;
    
    public Elevador(int F, Predio predio) {
        this.predio = predio;
        this.EstaSeMovendo = false;
        this.AndarCorreto=F;
        this.portasAbertas=false;
    }
    private void Abrirporta(){
        this.portasAbertas=true;
    }
    private void FecharPortaa(){
        this.portasAbertas=false;
    }
    public void IrparaOandar(int andar){
        while (AndarCorreto!=andar) {
            if (AndarCorreto>andar) {
                AndarCorreto-=1;
                if (PassageiroEntrou.size()>0) {
                    PassageiroEntrou.get(0).AndarCorreto-=1;
               }
            }else {
                AndarCorreto+=1;
                if (PassageiroEntrou.size()>0) {
                    PassageiroEntrou.get(0).AndarCorreto+=1;
               }
            }
        }
    }
    public void ReceberPassageiros(Passageiros passageiro) {
        Abrirporta();
        PassageiroEntrou.add(passageiro);
        
        predio.Andares.get(this.AndarCorreto).remove(passageiro);
        FecharPortaa();
    }
    public void SoltarPassageiros(Passageiros passageiro) {
        Abrirporta();
        PassageiroEntrou.remove(passageiro);
        passageiro.Chegou=true;
        predio.Andares.get(this.AndarCorreto).add(passageiro);
        FecharPortaa();
    }
    @Override
    public void run() {
        super.run();
        this.Parar=false;
        this.EstaSeMovendo=true;
        while(!Parar)
        {
      System.out.println("Elevador está funcionando");
      
      break; //break para quebrar o loop
        }
       this.EstaSeMovendo=false;
    }
} 
