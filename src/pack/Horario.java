package pack;

import java.util.ArrayList;

public class Horario {

	private ArrayList<Disciplina> disciplinas;
	
	public Horario(){
		this.disciplinas = new ArrayList<Disciplina>();
	}
	
	public ArrayList<Disciplina> getDisciplinas(){
		return this.disciplinas;
	}
}
