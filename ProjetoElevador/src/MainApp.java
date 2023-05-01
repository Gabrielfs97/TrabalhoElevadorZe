import java.util.*;

public class MainApp{
    public static Predio predio;

    public MainApp(int N, int andares) {

        int TotalAnd = andares;

        predio = new Predio(TotalAnd);
        Random rand = new Random(); 
        
        for (int i = 0; i < N;i++) {
            int AndarCorreto = rand.nextInt(TotalAnd);
            int AndarDestino = 0;
            do{
                AndarDestino = rand.nextInt(TotalAnd);
            }while(AndarDestino==AndarCorreto);

            Passageiros passageiro = new Passageiros(AndarCorreto, AndarDestino, predio);
            predio.Andares.get(AndarCorreto).add(passageiro);
            predio.passageiros.add(passageiro);
            System.out.println("Sou um passageiro que esta no andar " + AndarCorreto +  " e quero ir para o andar " + AndarDestino);
      
        }
        predio.run();


    }

} 
