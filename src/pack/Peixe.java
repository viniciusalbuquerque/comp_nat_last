package pack;

import java.util.ArrayList;

public class Peixe {

	private Horario[][] grade;
	private double fitness;
	
	Peixe(){
		this.grade = new Horario[Main.numeroDiasAula][Main.numeroHorariosPorDia];
		
		this.gerarGradeHorarioAleatoria();
		
		this.calcularFitness();
	}
	
	private void gerarGradeHorarioAleatoria() {
		ArrayList<Disciplina> disciplinas = (ArrayList<Disciplina>) Main.disciplinas.clone();
		
		for(Disciplina disciplina : disciplinas){
			int coluna = Main.generator.nextInt(Main.numeroDiasAula);
			int linha = Main.generator.nextInt(Main.numeroHorariosPorDia);
			
			this.grade[coluna][linha].getDisciplinas().add(disciplina);
		}
	}

	private void calcularFitness(){
		this.fitness = 0;
	}
	
	public Horario[][] getGrade(){
		return this.grade;
	}
}
