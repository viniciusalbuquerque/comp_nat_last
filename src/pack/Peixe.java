package pack;

import java.util.ArrayList;

public class Peixe {

	private Horario[][] grade;
	private double fitness;
	private static final double CONS = 10;
	private int numSalasDisponiveis;
	// private static int punicao = 1000;
	private ArrayList<Disciplina> disciplinas;

	public Peixe(int numSalasDisponiveis, ArrayList<Disciplina> disciplinas) {
		fitness = Double.MAX_VALUE;
		this.disciplinas = disciplinas;
		this.grade = new Horario[Main.numeroHorariosPorDia][Main.numeroDiasAula];
		this.numSalasDisponiveis = numSalasDisponiveis;
		this.gerarGradeHorarioAleatoria();
		this.calcularFitness();
	}

	public Peixe(Horario[][] grade) {
		this.grade = grade;
		this.calcularFitness();
	}

	private void gerarGradeHorarioAleatoria() {
		// ArrayList<Disciplina> disciplinas = (ArrayList<Disciplina>)
		// Main.disciplinas.clone();
		for (Disciplina disciplina : disciplinas) {
			boolean addedDisciplina = false;
			while (!addedDisciplina) {
				int linha = Main.generator.nextInt(Main.numeroHorariosPorDia);
				int coluna = Main.generator.nextInt(Main.numeroDiasAula);

				if (this.grade[linha][coluna] == null) {
					this.grade[linha][coluna] = new Horario();
				}

				if (this.grade[linha][coluna].getDisciplinas().size() < numSalasDisponiveis) {
					this.grade[linha][coluna].addDisciplina(disciplina);
					addedDisciplina = true;
				}

			}
		}
	}

	// private void calcularFitness(){
	// fitness = 0;
	// boolean conflito = false;
	// for(int i = 0; i < grade.length; i++) {
	// fitness += (i*Swarm.razaoX) + (grade[i].length - 1) * Swarm.razaoY;
	// if(!conflito) {
	// for(int j = 0; j < grade[i].length; j++) {
	// if(grade[i][j].checkConflitoProfessor()) {
	// conflito = true;
	// }
	// }
	// }
	// }
	// fitness = cons / fitness;
	//
	// if(conflito) {
	// fitness *= punicao;
	// }
	//
	// }

	public static Horario[] getColumn2(Horario[][] array, int index) {
		if(array[index] != null) {
			Horario[] column = new Horario[array[index].length];
			for (int i = 0; i < column.length; i++) {
				column[i] = array[index][i];
//				System.out.println(column[i].getDisciplinas().get(i).getNome());
			}
			return column;	
		}
		return null;
	}
	
	public static Horario[] getColumn(Horario[][] array, int index) {
		if(array[index] != null) {
			Horario[] column = new Horario[array.length];
			for (int i = 0; i < column.length; i++) {
				column[i] = array[i][index];
//				System.out.println(column[i].getDisciplinas().get(i).getNome());
			}
			return column;	
		}
		return null;
	}

	private boolean checarConflito() {
		for(int i = 0; i < grade.length; i++) {
			for(int j = 0; j < grade[0].length; j++) {
				if(grade[i][j] != null) {
					if(grade[i][j].checkConflitoProfessor()) {
						return true;
					}	
				}
			}
		}
		return false;
	}
	
	public void calcularFitness() {
		boolean conflito = checarConflito();
		
		if(!conflito) {
			for(int i = 0; i < grade[0].length; i++) {
				Horario[] horarios = getColumn(grade, i);
				if(horarios != null) {
					int indexOfLastClass = getLastClassIndex(horarios);
					if(indexOfLastClass >= 0) {
						fitness += (i * Swarm.razaoX) + (grade[i].length - 1)
								* Math.pow(Swarm.razaoY, indexOfLastClass);
					}
				}
			}
			fitness = CONS / fitness;
		} else {
			fitness = Double.MAX_VALUE;
		}
		
	}
	
	//Esse é o calculo antigo do fitness.. Troquei porque achei que não estava na ideia que a gente queria...
	public void calcularFitness2() {
		boolean conflito = false;
		for (int i = 0; i < grade.length; i++) {
			Horario[] horarios = getColumn(grade, i);
			if(horarios != null) {
				int indexOfLastClass = getLastClassIndex(horarios);
//				System.out.println(indexOfLastClass);
				if (indexOfLastClass >= 0) {
					fitness += (i * Swarm.razaoX) + (grade[i].length - 1)
							* Math.pow(Swarm.razaoY, indexOfLastClass);
					for (int j = 0; j < grade[i].length; j++) {
						if (grade[i][j] != null) {
							if (grade[i][j].checkConflitoProfessor()) {
								conflito = true;
							}
						}
					}
				} else {
//					System.out.println("test");
				}	
			}
		}
		if (conflito) {
			fitness = Double.MAX_VALUE;
		} else {
			fitness = CONS / fitness;
		}
	}

	private int getLastClassIndex(Horario[] horarios) {
//		System.out.println(horarios.length);
		for (int i = horarios.length - 1; i >= 0; i--) {
			if (horarios[i] != null) {
//				System.out.println(i);
				return i;
			}
		}
		return -1;
	}

	public boolean isBetterFitness(double fitness) {
		return fitness < this.fitness;
	}

	public boolean isWorseFitness(double fitness) {
		return fitness > this.fitness;
	}

	public Horario[][] getGrade() {
		return this.grade;
	}

	public double getFitness() {
		return fitness;
	}
}
