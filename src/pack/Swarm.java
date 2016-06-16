package pack;

import java.util.ArrayList;

public class Swarm {
	
	private ArrayList<Peixe> peixes;
	public static int razaoX = 1;
	public static int razaoY = 10;
	private int numSalas;
	
	
	public Swarm(int numPeixes, int numSalas) {
		peixes = new ArrayList<Peixe>();
		this.numSalas = numSalas;
		inicializacaoPeixes(numPeixes);
	}
	
	private void inicializacaoPeixes(int numPeixes) {
		for(int i = 0; i < numPeixes; i++) {
			Peixe peixe = new Peixe(numSalas);
			peixes.add(peixe);
		}
	}
	
	public ArrayList<Peixe> getPeixes() {
		return peixes;
	}
	
	
	

	
}
