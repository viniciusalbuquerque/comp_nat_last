package pack;

import java.util.ArrayList;
import java.util.Collections;

public class Horario {

	private ArrayList<Disciplina> disciplinas;
	private ArrayList<Professor> professores;
	private ArrayList<Disciplina> disciplinasParaOMelhor;
	
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
	
	public void removeDisciplina(Disciplina disciplina) {
		disciplinas.remove(disciplina);
		professores.remove(disciplina.getProfessor());
	}
	
	public boolean checkConflitoProfessor() {
		for(Professor prof : this.professores) {
//			System.out.println(Collections.frequency(this.professores, prof));
			if(Collections.frequency(this.professores, prof) > 1) {
				return true;
			}
		}
		return false;
	}
	
	public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	public int distancia(ArrayList<Disciplina> disc) {
		this.disciplinasParaOMelhor = new ArrayList<>();
		int dist = 0;
		for(Disciplina disciplina : disc) {
			if(!this.disciplinas.contains(disciplina)) {
				this.disciplinasParaOMelhor.add(disciplina);
				dist++;
			}
		}
		return dist;
	}
	
	public ArrayList<Disciplina> getDisciplinasParaOMelhor() {
		return disciplinasParaOMelhor;
	}
	
}
