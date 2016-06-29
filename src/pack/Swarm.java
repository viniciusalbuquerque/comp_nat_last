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
		movimentoColetivoDivergente();
		movimentoColetivoConvergente();
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
			
			int qtdDisciplinasInicial = peixe.getQuantidadeDisciplinas();
			
			int coluna1 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha1 = Main.generator.nextInt(Main.numeroHorariosPorDia); 
			
			int coluna2 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha2 = Main.generator.nextInt(Main.numeroHorariosPorDia);
			
			Horario[][] gradeOriginal = peixe.getGrade();
			
			Horario[][] grade = gradeOriginal.clone();
			
			Horario horario1 = grade[linha1][coluna1];
			grade[linha1][coluna1] = grade[linha2][coluna2];
			grade[linha2][coluna2] = horario1;

			Peixe auxPeixe = new Peixe(grade);
			
			if(peixe.isBetterFitness(auxPeixe.getFitness())) {
				peixe = auxPeixe;
				System.out.println("1dsa123213");
			}	
			peixe = swapIndividual(peixe);
			
			int qtdDisciplinasFinal = peixe.getQuantidadeDisciplinas();
			
			if(qtdDisciplinasInicial != qtdDisciplinasFinal){
				lancarExcecaoMudancaQtdDisciplinas(qtdDisciplinasInicial,
						qtdDisciplinasFinal, "movimento individual");
			}
		}
	}

	private void lancarExcecaoMudancaQtdDisciplinas(int qtdDisciplinasInicial,
			int qtdDisciplinasFinal, String trechoEmQueOcorreu) throws RuntimeException {
		String mensagem = "Quantidade de disciplinas mudou em: " + trechoEmQueOcorreu;
		mensagem += "\n\nInicial: " + qtdDisciplinasInicial;
		mensagem += "\n\nFinal: " + qtdDisciplinasFinal;
		throw new RuntimeException(mensagem);
	}
	
	
	
	private void movimentoColetivoConvergente() {
		for(Peixe peixe : this.peixes) {
			
			int qtdDisciplinasInicial = peixe.getQuantidadeDisciplinas();
			Horario[][] gradeInicial = peixe.getGrade().clone();
			
			if(peixe != melhorPeixe) {
				
				int distanciaParaMelhorPeixe = peixe.distanciaParaMelhorPeixe(this.melhorPeixe);
				int countDeslocamentoDisciplinas = 0;
				Horario[][] gradePeixe = peixe.getGrade();
//				Horario[][] gradeMelhorPeixe = this.melhorPeixe.getGrade();
				
				for(int i = 0; i < gradePeixe.length && countDeslocamentoDisciplinas < 2*distanciaParaMelhorPeixe/3; i++) {
					
					for(int j = 0; j < gradePeixe[0].length; j++) {
						
						Horario horarioPeixe = gradePeixe[i][j];
						ArrayList<Disciplina> disciplinasParaOMelhor = new ArrayList<Disciplina>();
						
						if(horarioPeixe != null) {							
							disciplinasParaOMelhor = horarioPeixe.getDisciplinasParaOMelhor();							
						} 
						
						else {
							Horario horarioMelhorPeixe = this.melhorPeixe.getGrade()[i][j];
							
							if(horarioMelhorPeixe != null) {
								disciplinasParaOMelhor = horarioMelhorPeixe.getDisciplinas();	
							}
						}
						
						swapDisciplinasParaOMelhor(disciplinasParaOMelhor, gradePeixe, horarioPeixe);
						
						if(disciplinasParaOMelhor != null && disciplinasParaOMelhor.size() > 0) {
							countDeslocamentoDisciplinas++;	
						}

						int qtdDisciplinasFinal = peixe.getQuantidadeDisciplinas();
						Horario[][] gradeFinal = peixe.getGrade().clone();
						
						if(qtdDisciplinasInicial != qtdDisciplinasFinal){
							System.out.println("\n\n\n\n########################################");
							System.out.println("\n\n\n\nERRO!!!");
							System.out.println("########################################");
							System.out.println("\nPeixe inicial:\n");
							Main.printPeixe(new Peixe(gradeInicial));

							System.out.println("\nPeixe final:\n");
							Main.printPeixe(new Peixe(gradeFinal));
							
							lancarExcecaoMudancaQtdDisciplinas(qtdDisciplinasInicial,
									qtdDisciplinasFinal, "movimento coletivo convergente");
						}
					}
				}
			}

		}
	}
	
	private void swapDisciplinasParaOMelhor(
			ArrayList<Disciplina> disciplinasParaOMelhor,
			Horario[][] gradePeixe, Horario horarioPeixe) {

		if(disciplinasParaOMelhor != null && disciplinasParaOMelhor.size() > 0) {
			
			if(horarioPeixe == null) {
				horarioPeixe = new Horario();
			}
			for(Disciplina disciplina : disciplinasParaOMelhor) {
				
				boolean added = false;
				
				for(int i = 0; i < gradePeixe.length; i++) {
					
					for(int j = 0; j < gradePeixe[0].length; j++) {
						
						Horario tempHorario = gradePeixe[i][j];
						
						if(tempHorario != null) {
							
							if(tempHorario.getDisciplinas().contains(disciplina)) {
								
								tempHorario.removeDisciplina(disciplina);
								horarioPeixe.addDisciplina(disciplina);
								added = true;
								break;
							}		
						}				
					}
					if(added) {
						break;
					}
				}
			}
		}
	}

	private void movimentoColetivoDivergente() {
//		for(Peixe peixe : this.peixes) {
//			if(peixe != melhorPeixe) {
//				
//			}
//		}
	}
	
	
	private Peixe swapIndividual(Peixe peixe){
//		for(Peixe peixe : this.peixes){
			int coluna1 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha1 = Main.generator.nextInt(Main.numeroHorariosPorDia); 
			
			int coluna2 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha2 = Main.generator.nextInt(Main.numeroHorariosPorDia); 
			
			Horario horario = peixe.getGrade()[linha1][coluna1];
			if(horario == null) {
				horario = new Horario();
			}
			ArrayList<Disciplina> disciplinas1 = horario.getDisciplinas();
			Horario horario2 = peixe.getGrade()[linha2][coluna2];
			if(horario2 == null) {
				horario2 = new Horario();
			}
			ArrayList<Disciplina> disciplinas2 = horario2.getDisciplinas();
			
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
			
			
			Peixe peixe2 = new Peixe(peixe.getGrade().clone());
//			Peixe peixe2 = peixe;
			if(disciplinas1.isEmpty()) {
				peixe2.getGrade()[linha1][coluna1] = null;
			} else {
				horario.setDisciplinas(disciplinas1);
				peixe2.getGrade()[linha1][coluna1] = horario;
			}
			
			if(disciplinas2.isEmpty()) {
				peixe2.getGrade()[linha2][coluna2] = null;
			} else {
				horario2.setDisciplinas(disciplinas2);
				peixe2.getGrade()[linha2][coluna2] = horario2;
			}
			
			peixe2.calcularFitness();
			
			if(peixe.isBetterFitness(peixe2.getFitness())) {
				System.out.println("testetestesteste");
				peixe = peixe2;
			}
//		}
			return peixe;
	}
	
	public Peixe getMelhorPeixe() {
		checkMelhorPeixe();
		return melhorPeixe;
	}
	
	public Peixe getPiorPeixe() {
		checkPiorPeixe();
		return piorPeixe;
	}
}
