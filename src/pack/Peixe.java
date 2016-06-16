package pack;

import java.util.ArrayList;

public class Peixe {

	private ArrayList<ArrayList<Horario>> grade;
	private double fitness;	
	private double cons = 10;
	private int numSalasDisponiveis;
	
	public Peixe(int numSalasDisponiveis) {
		this.grade = new ArrayList<ArrayList<Horario>>();
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
				
				if(this.grade.get(linha).get(coluna).getDisciplinas().size() < numSalasDisponiveis) {
					this.grade.get(linha).get(coluna).getDisciplinas().add(disciplina);
					addedDisciplina = true;
				}
				
			}
			
		}
	}

	private void calcularFitness(){
		fitness = 0;
		for(int i = 0; i < grade.size(); i++) {
			fitness += (i*Swarm.razaoX) + (grade.get(i).size() - 1) * Swarm.razaoY;
		}
		fitness = cons / fitness;
	}
}
