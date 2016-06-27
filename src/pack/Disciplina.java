package pack;

public class Disciplina {

	private String nome;
	private Professor professor;
	
	public Disciplina(String nome, Professor professor){
		this.nome = nome;
		this.professor = professor;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public String getNome() {
		return nome;
	}
	
}
