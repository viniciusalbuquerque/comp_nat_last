package pack;

public class Professor {

	private int id;
	private String nome;
	
	Professor(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
}
