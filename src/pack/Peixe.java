package pack;

import java.util.ArrayList;

public class Peixe {

	private Horario[][] grade;
	private double fitness;	
	private double cons = 10;
	private int numSalasDisponiveis;
	
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
					this.grade[linha][coluna].getDisciplinas().add(disciplina);
					addedDisciplina = true;
				}
				
			}
		}
	}

	private void calcularFitness(){
		fitness = 0;
		for(int i = 0; i < grade.length; i++) {
			fitness += (i*Swarm.razaoX) + (grade[i].length - 1) * Swarm.razaoY;
		}
		fitness = cons / fitness;
		this.fitness = 0;
	}
	
	public Horario[][] getGrade(){
		return this.grade;
	}
}
