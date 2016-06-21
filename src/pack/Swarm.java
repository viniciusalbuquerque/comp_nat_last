package pack;

import java.util.ArrayList;

public class Swarm {
	
	private ArrayList<Peixe> peixes;
	private Peixe melhorPeixe;
	private Peixe piorPeixe;
	
	
	public static int razaoX = 1;
	public static int razaoY = 10;
	private int numSalas;
//	private int numPeixes;
	
	public Swarm(int numPeixes, int numSalas, ArrayList<Disciplina> disciplinas) {
		peixes = new ArrayList<Peixe>();
//		this.numPeixes = numPeixes;
		this.numSalas = numSalas;
		inicializacaoPeixes(numPeixes, disciplinas);
	}
	
	private void inicializacaoPeixes(int numPeixes, ArrayList<Disciplina> disciplinas) {
		for(int i = 0; i < numPeixes; i++) {
			Peixe peixe = new Peixe(numSalas, disciplinas);
			peixes.add(peixe);
		}
	}
	
	public void start(int numIteracoes) {
		for(int i = 0; i < numIteracoes; i++) {
			checkPiorPeixe();
			checkMelhorPeixe();
			move();
		}
	}
	
	private void checkPiorPeixe() {
		for(Peixe peixe : peixes) {
			if(piorPeixe == null) {
				piorPeixe = peixe;
			}
			
			if(piorPeixe.isWorseFitness(peixe.getFitness())) {
				System.out.println("pior");
				piorPeixe = peixe;
			}
		}
	}
	private void checkMelhorPeixe() {
		for(Peixe peixe : peixes) {
			if(melhorPeixe == null) {
				melhorPeixe = peixe;
			}
			if(melhorPeixe.isBetterFitness(peixe.getFitness())) {
				System.out.println("melhor");
				melhorPeixe = peixe;
			}
		}
	}
	
	private void move() {
		movimentoIndividual();
		movimentoPior();
		movimentoMelhor();
	}
	
	public ArrayList<Peixe> getPeixes() {
		return peixes;
	}

//	public Swarm(){
//		this.peixes = new ArrayList<Peixe>();
//		for(int i = 0; i < numPeixes; i++){
//			Peixe peixe = new Peixe(numSalas);
//			peixes.add(peixe);
//		}
//	}
	
	private void movimentoIndividual() {
		for(Peixe peixe : this.peixes) {
			int coluna1 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha1 = Main.generator.nextInt(Main.numeroHorariosPorDia); 
			
			int coluna2 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha2 = Main.generator.nextInt(Main.numeroHorariosPorDia);
			
			Horario[][] grade = peixe.getGrade().clone();
			
//			if(grade[linha1][coluna1] == null) {
//				System.out.println("null");
//			} else {
//				System.out.println(grade[linha1][coluna1].getDisciplinas().size());
//			}
//			if(grade[linha2][coluna2] == null) {
//				System.out.println("null");
//			} else {
//				System.out.println(grade[linha2][coluna2].getDisciplinas().size());
//			}
//			
			
			Horario horario1 = grade[linha1][coluna1];
			grade[linha1][coluna1] = grade[linha2][coluna2];
			grade[linha2][coluna2] = horario1;

			Peixe auxPeixe = new Peixe(grade);
			
			if(peixe.isBetterFitness(auxPeixe.getFitness())) {
				peixe = auxPeixe;
			}
		}
	}
	
	private void movimentoMelhor() {
		for(Peixe peixe : this.peixes) {
			if(peixe != melhorPeixe) {
				
			}
		}
	}
	
	private void movimentoPior() {
		for(Peixe peixe : this.peixes) {
			if(peixe != melhorPeixe) {
				
			}
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
	
	public Peixe getMelhorPeixe() {
		checkMelhorPeixe();
		return melhorPeixe;
	}
	
	public Peixe getPiorPeixe() {
		return piorPeixe;
	}
}
