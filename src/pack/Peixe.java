package pack;

import java.util.ArrayList;

public class Peixe {

	private Horario[][] grade;
	private double fitness;	
	private double cons = 10;
	private int numSalasDisponiveis;
	private static int punicao = 100;
	
	public Peixe(int numSalasDisponiveis) {
		this.grade = new Horario[Main.numeroDiasAula][Main.numeroHorariosPorDia];
		this.numSalasDisponiveis = numSalasDisponiveis;
		this.gerarGradeHorarioAleatoria();
		this.calcularFitness();
	}
	
	
	private void gerarGradeHorarioAleatoria() {
		ArrayList<Disciplina> disciplinas = (ArrayList<Disciplina>) Main.disciplinas.clone();
		for(Disciplina disciplina : disciplinas){
			boolean addedDisciplina = false;
			while(!addedDisciplina) {
				int linha = Main.generator.nextInt(Main.numeroHorariosPorDia + 1);
				int coluna = Main.generator.nextInt(Main.numeroDiasAula);
				
				if(this.grade[linha][coluna].getDisciplinas().size() < numSalasDisponiveis) {
					this.grade[linha][coluna].addDisciplina(disciplina);
					addedDisciplina = true;
				}
				
			}
		}
	}

	private void calcularFitness(){
		fitness = 0;
		boolean conflito = false;
		for(int i = 0; i < grade.length; i++) {
			fitness += (i*Swarm.razaoX) + (grade[i].length - 1) * Swarm.razaoY;
			if(!conflito) {
				for(int j = 0; j < grade[i].length; j++) {
					if(grade[i][j].checkConflitoProfessor()) {
						conflito = true;
					}
				}
			}
		}
		fitness = cons / fitness;
		
		if(conflito) {
			fitness *= punicao;
		}
		
	}
	
	
	public Horario[][] getGrade(){
		return this.grade;
	}
}
