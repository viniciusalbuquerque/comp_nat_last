package pack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

//	private static final String FILENAME = "/Users/Vinicius/Documents/workspace/comp_nat/grade.txt";
	
	public static int numeroDiasAula = 3;
	public static int numeroHorariosPorDia = 5;
	public static int numeroSalasDisponiveis = 5;
	public static int numeroPeixes = 50;
	public static ArrayList<Professor> professores;
	public static ArrayList<Disciplina> disciplinas;
	
	public static final int NUM_ITERACOES = 10000;
//	private static BufferedWriter bw;
	
	public static Random generator = new Random();
	
	public static void main(String[] args) throws IOException {
		inicializarVariaveis();
		
		Swarm swarm = new Swarm(numeroPeixes, numeroSalasDisponiveis, disciplinas);
		swarm.start(NUM_ITERACOES);
		
//		File file = new File(FILENAME);
//        FileWriter fw = new FileWriter(file.getAbsolutePath());
//        bw = new BufferedWriter(fw);
        
//		ArrayList<Peixe> peixes = swarm.getPeixes();
//		
//		for(Peixe p : peixes) {
//			printPeixe(p);
//		}
		
		System.out.println("Melhor peixe");
		Peixe p = swarm.getMelhorPeixe();
		printPeixe(p);
////		writePeixe(p);
//		
		System.out.println("Pior peixe");
		p = swarm.getPiorPeixe();
		printPeixe(p);
//		writePeixe(p);
		
//		bw.close();
		
	}
	
	public static void printPeixe(Peixe p) {
		
		System.out.println(p.getFitness());
		Horario grade[][] = p.getGrade();
		
		for(int i = 0; i < grade.length; i++) {
			
			System.out.println("Dia " + i);
			
			for(int j = 0; j < grade[0].length; j++) {
				
				Horario horario = grade[i][j];
				
				System.out.print("Horário " + j + " - ");
				
				if(horario != null) {
					
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
	}
	
//	private static void writePeixe(Peixe p) throws IOException {
//		
//	}
	
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
