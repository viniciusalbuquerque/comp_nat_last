package pack;

import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static int numeroDiasAula = 6;
	public static int numeroHorariosPorDia = 6;
	public static int numeroSalas = 5;
	public static ArrayList<Professor> professores;
	public static ArrayList<Disciplina> disciplinas;
	
	public static Random generator = new Random();
	
	public static void main(String[] args){
		
	}
	
	private void inicializarVariaveis(){
		professores = new ArrayList<Professor>();
		disciplinas = new ArrayList<Disciplina>();
		
		professores.add(new Professor(1, "Jose"));
		professores.add(new Professor(2, "Maria"));
		professores.add(new Professor(3, "Joao"));
		professores.add(new Professor(4, "Pedro"));
		professores.add(new Professor(5, "Luciano"));
		
		disciplinas.add(new Disciplina("Calculo I", professores.get(0)));
		disciplinas.add(new Disciplina("Calculo II", professores.get(1)));
		disciplinas.add(new Disciplina("Calculo III", professores.get(2)));
		disciplinas.add(new Disciplina("LPI", professores.get(3)));
		disciplinas.add(new Disciplina("Java", professores.get(4)));
		disciplinas.add(new Disciplina("Fenomenos dos Transportes", professores.get(0)));
		disciplinas.add(new Disciplina("Quimica", professores.get(1)));
		disciplinas.add(new Disciplina("Estrutura de dados", professores.get(2)));
		disciplinas.add(new Disciplina("Computacao natural", professores.get(3)));
		disciplinas.add(new Disciplina("Redes neurais", professores.get(4)));
		
	}
}
