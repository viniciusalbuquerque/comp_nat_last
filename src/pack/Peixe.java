package pack;

import java.util.ArrayList;

public class Peixe {

	private ArrayList<ArrayList<Horario>> grade;
	private double peso;
	
	Peixe(){
		this.grade = new ArrayList<ArrayList<Horario>>();
		
		this.gerarGradeHorarioAleatoria();
		
		this.calcularPeso();
	}
	
	private void gerarGradeHorarioAleatoria() {
		ArrayList<Disciplina> disciplinas = (ArrayList<Disciplina>) Main.disciplinas.clone();
		
		for(Disciplina disciplina : disciplinas){
			int linha = Main.generator.nextInt(Main.numeroHorariosPorDia + 1);
			int coluna = Main.generator.nextInt(Main.numeroDiasAula);
			
			this.grade.get(linha).get(coluna).getDisciplinas().add(disciplina);
		}
	}

	private void calcularPeso(){
		// TODO Auto-generated method stub
	}
}
