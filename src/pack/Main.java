package pack;

import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static int numeroDiasAula = 1;
	public static int numeroHorariosPorDia = 3;
	public static int numeroSalas = 5;
	public static int numeroPeixes = 10;
	public static ArrayList<Professor> professores;
	public static ArrayList<Disciplina> disciplinas;
	
	public static final int NUM_ITERACOES = 1000;
	
	public static Random generator = new Random();
	
	public static void main(String[] args){
		inicializarVariaveis();
		
		Swarm swarm = new Swarm(numeroPeixes, numeroSalas, disciplinas);
		swarm.start(NUM_ITERACOES);
		Peixe p = swarm.getMelhorPeixe();
		System.out.println(p.getFitness());
		Horario grade[][] = p.getGrade();
		for(int i = 0; i < grade.length; i++) {
			System.out.println("Horario " + i);
			for(int j = 0; j < grade[0].length; j++) {
				Horario horario = grade[i][j];
				if(horario != null) {
					System.out.print("Dia " + String.valueOf(j) + " - ");
					for(int k = 0; k < horario.getDisciplinas().size(); k++) {
						Disciplina d = horario.getDisciplinas().get(k);
						System.out.print(d.getNome() + " ");	
					}
					System.out.println();
				}
			}
			System.out.println();
		}
		System.out.println();
		
		p = swarm.getPiorPeixe();
		System.out.println(p.getFitness());
		grade = p.getGrade();
		for(int i = 0; i < grade.length; i++) {
			System.out.println("Horario " + i);
			for(int j = 0; j < grade[0].length; j++) {
				Horario horario = grade[i][j];
				if(horario != null) {
					System.out.print("Dia " + String.valueOf(j) + " - ");
					for(int k = 0; k < horario.getDisciplinas().size(); k++) {
						Disciplina d = horario.getDisciplinas().get(k);
						System.out.print(d.getNome() + " ");	
					}
					System.out.println();
				}
			}
			System.out.println();
		}
	}
	
	private static void inicializarVariaveis(){
		professores = new ArrayList<Professor>();
		disciplinas = new ArrayList<Disciplina>();
		
		professores.add(new Professor(1, "Jose"));
		professores.add(new Professor(2, "Maria"));
		professores.add(new Professor(3, "Joao"));
		professores.add(new Professor(4, "Pedro"));
		professores.add(new Professor(5, "Luciano"));
		
		disciplinas.add(new Disciplina("CalculoI", professores.get(0)));
		disciplinas.add(new Disciplina("CalculoII", professores.get(1)));
		disciplinas.add(new Disciplina("CalculoIII", professores.get(2)));
		disciplinas.add(new Disciplina("LPI", professores.get(3)));
		disciplinas.add(new Disciplina("Java", professores.get(4)));
		disciplinas.add(new Disciplina("FenomenosDosTransportes", professores.get(0)));
		disciplinas.add(new Disciplina("Quimica", professores.get(1)));
		disciplinas.add(new Disciplina("EstruturaDeDados", professores.get(2)));
		disciplinas.add(new Disciplina("ComputacaoNatural", professores.get(3)));
		disciplinas.add(new Disciplina("RedesNeurais", professores.get(4)));
		
		
		
	}
}
