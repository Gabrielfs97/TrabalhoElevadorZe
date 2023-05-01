

public class Passageiros extends Thread implements Runnable {
    public boolean Proximo;
    public int AndarDestino;
    public int AndarCorreto;

    private Predio predio;
    private Elevador elevador;

    public boolean Chegou;
   
    public Passageiros(int Destino, int andarAtual, Predio predio) {
        this.Proximo = false;
        this.AndarDestino = Destino;
        this.AndarCorreto = andarAtual;
        this.predio = predio;
        this.elevador = predio.elevador;
    }
    public void init() {
        this.start();
        this.Chegou = false;
    }
    public void FimDathread() {
        this.Chegou = true;
    }
    @Override
    public void run() {

        super.run();
        while(!Chegou) {
            EsperaroElevador();
            ChamarElevador();
            EntrarnoElevador();
            elevador.IrparaOandar(AndarDestino);
            
            SairdoElevador();
            
            SairdaFila();
            
            FimDathread();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevador.EmUso=false;
        }
    }
 
   public void EsperaroElevador() {
        while (this.Proximo==false || elevador.EstaSeMovendo==false){
                try {
                    Thread.sleep(200);
                 //   System.out.println(String.valueOf(this.threadId()+" is waiting"));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {	
            e.printStackTrace();
        }
    }

	public void ChamarElevador() {
        try {
            elevador.sinal.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        elevador.IrparaOandar(this.AndarCorreto);

    }

    public void EntrarnoElevador() {
        elevador.ReceberPassageiros(this);
    }

    public void SairdoElevador() {
        elevador.SoltarPassageiros(this);
        elevador.sinal.release();
    }

    public void SairdaFila() {
        this.predio.next=null;
    }


}