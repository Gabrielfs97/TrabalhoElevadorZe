import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Predio {
	
	private final int NumeroAnds;
	
	public List<Passageiros> passageiros = new ArrayList<Passageiros>();
	public Passageiros next;
	public List<List<Passageiros>> Andares = new ArrayList<List<Passageiros>>();
	public final Elevador elevador;
	
	public Predio(int num_andares) {
		this.NumeroAnds=num_andares;
		
		for (int i=0; i<this.NumeroAnds;i++) {
			List<Passageiros> andar = new ArrayList<Passageiros>();
			this.Andares.add(andar);
		}
		
		this.elevador = new Elevador(ThreadLocalRandom.current().nextInt(0, Andares.size()),this);
		
	}
	
	private void Pegaroproximo() {
		
		HashMap<Passageiros, Integer> line_dict = new HashMap<Passageiros, Integer>();
		
		for (Passageiros passageiro : Andares.get(elevador.AndarCorreto)) {
			if (passageiro.Chegou==true) {
				continue;
			}
			int distance = Math.abs(passageiro.AndarDestino-passageiro.AndarCorreto);
			line_dict.put(passageiro,distance);
		}
		
		List<Map.Entry<Passageiros, Integer> > list =
	               new LinkedList<Map.Entry<Passageiros, Integer> >(line_dict.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Passageiros, Integer> >() {
            public int compare(Map.Entry<Passageiros, Integer> o1,
                               Map.Entry<Passageiros, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
		HashMap<Passageiros, Integer> temp = new LinkedHashMap<Passageiros, Integer>();
        for (Map.Entry<Passageiros, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
		this.next = temp.entrySet().iterator().next().getKey();
		elevador.EstaSeMovendo=true;
		elevador.EmUso = true;
		next.Proximo=true;
		
	}

	
	public void run() {
		
		for (Passageiros passageiro : this.passageiros) {
			passageiro.start();
		}
		List<Boolean> chegadas = new ArrayList<Boolean>();
		for (Passageiros passageiro: passageiros) {
			chegadas.add(passageiro.Chegou);
		}
		
		if (this.Andares.get(elevador.AndarCorreto).size()==0) {
			elevador.IrparaOandar(AndarCheio());
		}
		
		elevador.start();
		Pegaroproximo();
		
		while (chegadas.contains(false)) {
			if (elevador.EmUso){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				Pegaroproximo();
			}
			chegadas = new ArrayList<Boolean>();
			for (Passageiros passageiro: passageiros) {
				chegadas.add(passageiro.Chegou);
			}
			
		}
		elevador.Parar=true;
		
	}

	private int AndarCheio() {
		
		
			
			int ret_val = 0;
			int andarMaischeio = 0;
			int Contar = 0;
			//	int Tamanhodoandar = this.Andares.get(this.elevador.AndarCorreto).size();
			for (List<Passageiros> andar: this.Andares) {
				if (andar.size() > andarMaischeio) {
					andarMaischeio = andar.size();
					ret_val=Contar;
				}
				Contar+=1;
			}
			
			return ret_val;
		}
}
