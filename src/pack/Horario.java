package pack;

import java.util.ArrayList;
import java.util.Collections;

public class Horario {

	private ArrayList<Disciplina> disciplinas;
	private ArrayList<Professor> professores;
	
	public Horario(){
		this.disciplinas = new ArrayList<Disciplina>();
		this.professores = new ArrayList<Professor>();
	}
	
	public ArrayList<Disciplina> getDisciplinas(){
		return this.disciplinas;
	}
	
	public void addDisciplina(Disciplina disciplina) {
		disciplinas.add(disciplina);
		professores.add(disciplina.getProfessor());
	}
	
	public boolean checkConflitoProfessor() {
		for(Professor prof : this.professores) {
			if(Collections.frequency(this.professores, prof) > 1) {
				return true;
			}
		}
		return false;
	}
}
