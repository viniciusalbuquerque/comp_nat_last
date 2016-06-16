package pack;

import java.util.ArrayList;

public class Swarm {
	
	private ArrayList<Peixe> peixes;
	public static int razaoX = 1;
	public static int razaoY = 10;
	private int numSalas;
	private int numPeixes;
	
	public Swarm(int numPeixes, int numSalas) {
		peixes = new ArrayList<Peixe>();
		this.numPeixes = numPeixes;
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
	
	
	

	public Swarm(){
		this.peixes = new ArrayList<Peixe>();
		
		for(int i = 0; i < numPeixes; i++){
			Peixe peixe = new Peixe(numSalas);
			peixes.add(peixe);
		}
	}
	
	private void swapIndividual(){
		for(Peixe peixe : this.peixes){
			int coluna1 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha1 = Main.generator.nextInt(Main.numeroHorariosPorDia); 
			
			int coluna2 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha2 = Main.generator.nextInt(Main.numeroHorariosPorDia); 
			
			ArrayList<Disciplina> disciplinas1 = peixe.getGrade()[coluna1][linha1].getDisciplinas();
			ArrayList<Disciplina> disciplinas2 = peixe.getGrade()[coluna2][linha2].getDisciplinas();
			
			Disciplina disciplina1 = null;
			Disciplina disciplina2 = null;
			
			if(!disciplinas1.isEmpty()){
				int index = Main.generator.nextInt(disciplinas1.size());
				disciplina1 = disciplinas1.get(index);
				
				disciplinas1.remove(disciplina1);
			}
			
			if(!disciplinas2.isEmpty()){
				int index = Main.generator.nextInt(disciplinas2.size());
				disciplina2 = disciplinas2.get(index);
				
				disciplinas2.remove(disciplina2);
			}
			
			if(disciplina2 != null){
				disciplinas1.add(disciplina2);
			}
			
			if(disciplina1 != null){
				disciplinas2.add(disciplina1);
			}
		}
	}
}
