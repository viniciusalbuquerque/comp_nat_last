package pack;

import java.util.ArrayList;

public class Swarm {

	private ArrayList<Peixe> peixes;
	private Peixe melhorPeixe;
	private Peixe piorPeixe;

	public static int razaoX = 1;
	public static int razaoY = 10;
	private int numSalas;

	// private int numPeixes;

	public Swarm(int numPeixes, int numSalas, ArrayList<Disciplina> disciplinas) {
		peixes = new ArrayList<Peixe>();
		// this.numPeixes = numPeixes;
		this.numSalas = numSalas;
		inicializacaoPeixes(numPeixes, disciplinas);
	}

	private void inicializacaoPeixes(int numPeixes,
			ArrayList<Disciplina> disciplinas) {
		for (int i = 0; i < numPeixes; i++) {
			Peixe peixe = new Peixe(numSalas, disciplinas);
			peixes.add(peixe);
		}
	}

	public void start(int numIteracoes) {
		for (int i = 0; i < numIteracoes; i++) {
			checkPiorPeixe();
			checkMelhorPeixe();
			move();
		}
	}

	private void checkPiorPeixe() {
		for (Peixe peixe : peixes) {
			if (piorPeixe == null) {
				piorPeixe = peixe;
			}

			if (piorPeixe.isWorseFitness(peixe.getFitness())) {
				System.out.println("pior");
				piorPeixe = peixe;
			}
		}
	}

	private void checkMelhorPeixe() {
		for (Peixe peixe : peixes) {
			if (melhorPeixe == null) {
				melhorPeixe = peixe;
			}
			if (melhorPeixe.isBetterFitness(peixe.getFitness())) {
				System.out.println("melhor");
				melhorPeixe = peixe;
			}
		}
	}

	private void move() {
		movimentoIndividual();
		nullificarHorarios();
		movimentoMelhor();
		// movimentoColetivoDivergente();
		// movimentoColetivoConvergente();
	}

	public ArrayList<Peixe> getPeixes() {
		return peixes;
	}

	// public Swarm(){
	// this.peixes = new ArrayList<Peixe>();
	// for(int i = 0; i < numPeixes; i++){
	// Peixe peixe = new Peixe(numSalas);
	// peixes.add(peixe);
	// }
	// }

	private void movimentoIndividual() {
		for (Peixe peixe : this.peixes) {

			int qtdDisciplinasInicial = peixe.getQuantidadeDisciplinas();

			int coluna1 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha1 = Main.generator.nextInt(Main.numeroHorariosPorDia);

			int coluna2 = Main.generator.nextInt(Main.numeroDiasAula);
			int linha2 = Main.generator.nextInt(Main.numeroHorariosPorDia);

			Horario[][] gradeOriginal = peixe.getGrade();

			Horario[][] grade = gradeOriginal.clone();

			Horario horario1 = grade[linha1][coluna1];
			grade[linha1][coluna1] = grade[linha2][coluna2];
			grade[linha2][coluna2] = horario1;

			Peixe auxPeixe = new Peixe(grade);

			if (peixe.isBetterFitness(auxPeixe.getFitness())) {
				peixe = auxPeixe;
				// System.out.println("1dsa123213");
			}
			peixe = swapIndividual(peixe);

			int qtdDisciplinasFinal = peixe.getQuantidadeDisciplinas();

			if (qtdDisciplinasInicial != qtdDisciplinasFinal) {
				lancarExcecaoMudancaQtdDisciplinas(qtdDisciplinasInicial,
						qtdDisciplinasFinal, "movimento individual");
			}
		}
	}

	private void lancarExcecaoMudancaQtdDisciplinas(int qtdDisciplinasInicial,
			int qtdDisciplinasFinal, String trechoEmQueOcorreu)
			throws RuntimeException {
		String mensagem = "Quantidade de disciplinas mudou em: "
				+ trechoEmQueOcorreu;
		mensagem += "\n\nInicial: " + qtdDisciplinasInicial;
		mensagem += "\n\nFinal: " + qtdDisciplinasFinal;
		throw new RuntimeException(mensagem);
	}

	private Horario[][] deslocarTodasAsDisciplinas(Horario[][] grade,
			Horario horarioPeixe) {
		Horario[][] retGrade = grade.clone();
		boolean added = false;
		for (int i = retGrade.length - 1; i >= 0; i--) {
			for (int j = retGrade[0].length - 1; j >= 0; j--) {
				if (retGrade[i][j] != horarioPeixe) {
					if (retGrade[i][j] == null) {
						Horario horario = new Horario();
						horario.setDisciplinas(horarioPeixe.getDisciplinas());
						retGrade[i][j] = horario;
						added = true;
						break;
					} else {
						if(retGrade[i][j].getDisciplinas().size() + horarioPeixe.getDisciplinas().size() <= this.numSalas) {
							retGrade[i][j].addDisciplinas(horarioPeixe.getDisciplinas());	
							added = true;
							break;
						}
					}
				}
			}
			if(added) break;
		}
		
		if(added) {
			for (int i = 0; i < retGrade.length; i++) {
				for (int j = 0; j < retGrade[0].length; j++) {
					if(retGrade[i][j] == horarioPeixe) {
						retGrade[i][j] = null;
					}
				}
			}
		}
		
		return retGrade;
	}

	private Horario[][] deslocarDisciplinas(Horario[][] grade,
			Horario horarioPeixe, ArrayList<Disciplina> melhoresDisciplinas) {
		Horario[][] retGrade = grade.clone();
		boolean euBrakei = false;
		for (Disciplina disciplina : melhoresDisciplinas) {
			for (int i = 0; i < retGrade.length; i++) {
				for (int j = 0; j < retGrade[0].length; j++) {
					Horario tempHorario = retGrade[i][j];
					if (tempHorario != null) {
						if(horarioPeixe.getDisciplinas().size() < numSalas) {
							if (tempHorario != horarioPeixe) {
								if (tempHorario.getDisciplinas().contains(
										disciplina)) {
									tempHorario.removeDisciplina(disciplina);
								}
							} else {
								tempHorario.addDisciplina(disciplina);
							}	
						} else {
							euBrakei = true;
							break;
						}
					}
				}
				if(euBrakei) break;
			}
		}

		return retGrade;
	}

	private void movimentoMelhor() {
		// System.out.println("1234567890");
		for (Peixe peixe : this.peixes) {
			if (peixe != this.melhorPeixe) {
				int qtdDisciplinasInicial = peixe.getQuantidadeDisciplinas();
				Horario[][] gradePeixe = peixe.getGrade();
				Horario[][] gradeMelhorPeixe = this.melhorPeixe.getGrade();

				int distancia = peixe.distanciaProMelhor(melhorPeixe);
				int fatorDistancia = 3 * distancia / 2;
				int countDeslocamentos = 0;
				boolean deiBreak = false;
				for (int i = 0; i < gradePeixe.length; i++) {
					for (int j = 0; j < gradePeixe[0].length; j++) {
						Horario horario = gradePeixe[i][j];
						if (gradeMelhorPeixe[i][j] == null
								&& gradePeixe[i][j] != null) {
							int discInit = gradePeixe[i][j].getDisciplinas().size();
							gradePeixe = deslocarTodasAsDisciplinas(gradePeixe,
									horario);
							if(gradePeixe[i][j] == null) {
								countDeslocamentos += discInit;
							}
							
						} else {
							if (horario != null) {
								horario.disciplinasDiferentesDoMelhor(gradeMelhorPeixe[i][j]
										.getDisciplinas());
								ArrayList<Disciplina> disciplinasDiferentesDoMelhor = horario
										.getDisciplinasParaOMelhor();
								gradePeixe = deslocarDisciplinas(gradePeixe,
										horario, disciplinasDiferentesDoMelhor);
								countDeslocamentos += disciplinasDiferentesDoMelhor
										.size();
							}
						}

						if (countDeslocamentos >= fatorDistancia) {
							deiBreak = true;
							break;
						}
					}
					if (deiBreak)
						break;
				}
				int qtdDisciplinasFinal = peixe.getQuantidadeDisciplinas();
				if (qtdDisciplinasInicial != qtdDisciplinasFinal) {
					lancarExcecaoMudancaQtdDisciplinas(qtdDisciplinasInicial,
							qtdDisciplinasFinal,
							"movimento coletivo convergente");
				}

			}

		}

	}

	private void nullificarHorarios() {
		for(Peixe peixe : this.peixes) {
			Horario[][] grade = peixe.getGrade();
			
			for (int i = 0; i < grade.length; i++) {
				for (int j = 0; j < grade[0].length; j++) {
					if(grade[i][j] != null) {
						if(grade[i][j].getDisciplinas() == null || grade[i][j].getDisciplinas().size() == 0) {
							grade[i][j] = null;
						}
					}
				}
			}
		}
	}
	
	private void movimentoColetivoConvergente() {
		for (Peixe peixe : this.peixes) {

			int qtdDisciplinasInicial = peixe.getQuantidadeDisciplinas();

			if (peixe != melhorPeixe) {

				int dist = peixe.distanciaProMelhor(this.melhorPeixe);
				int countDeslocamentoDisciplinas = 0;
				Horario[][] gradePeixe = peixe.getGrade();
				// Horario[][] gradeMelhorPeixe = this.melhorPeixe.getGrade();

				for (int i = 0; i < gradePeixe.length
						&& countDeslocamentoDisciplinas < 2 * dist / 3; i++) {
					for (int j = 0; j < gradePeixe[0].length
							&& countDeslocamentoDisciplinas < 2 * dist / 3; j++) {

						Horario horarioPeixe = gradePeixe[i][j];
						Horario horarioMelhorPeixe = this.melhorPeixe
								.getGrade()[i][j];
						ArrayList<Disciplina> disciplinasParaOMelhor = new ArrayList<Disciplina>();
						if (horarioMelhorPeixe == null) {
							if (horarioPeixe != null) {

							}
						} else {
							if (horarioPeixe != null) {
								disciplinasParaOMelhor = horarioPeixe
										.getDisciplinasParaOMelhor();
							}

							else {
								if (horarioMelhorPeixe != null) {
									disciplinasParaOMelhor = horarioMelhorPeixe
											.getDisciplinas();
								}
							}

							horarioPeixe = swapDisciplinasParaOMelhor(
									disciplinasParaOMelhor, gradePeixe,
									horarioPeixe);

							if (disciplinasParaOMelhor != null
									&& disciplinasParaOMelhor.size() > 0) {
								countDeslocamentoDisciplinas++;
							}
						}

					}
				}
			}

			int qtdDisciplinasFinal = peixe.getQuantidadeDisciplinas();

			if (qtdDisciplinasInicial != qtdDisciplinasFinal) {
				lancarExcecaoMudancaQtdDisciplinas(qtdDisciplinasInicial,
						qtdDisciplinasFinal, "movimento coletivo convergente");
			}
		}
	}

	private Horario swapDisciplinasParaOMelhor(
			ArrayList<Disciplina> disciplinasParaOMelhor,
			Horario[][] gradePeixe, Horario horarioPeixe) {

		if (disciplinasParaOMelhor != null && disciplinasParaOMelhor.size() > 0) {

			if (horarioPeixe == null) {
				horarioPeixe = new Horario();
			}
			for (Disciplina disciplina : disciplinasParaOMelhor) {

				boolean added = false;

				for (int i = 0; i < gradePeixe.length; i++) {

					for (int j = 0; j < gradePeixe[0].length; j++) {

						Horario tempHorario = gradePeixe[i][j];

						if (tempHorario != null) {

							if (tempHorario.getDisciplinas().contains(
									disciplina)) {

								tempHorario.removeDisciplina(disciplina);
								horarioPeixe.addDisciplina(disciplina);
								added = true;
								break;
							}
						}
					}
					if (added) {
						break;
					}
				}
			}
			return horarioPeixe;
		}
		return null;
	}

	private void movimentoColetivoDivergente() {
		// for(Peixe peixe : this.peixes) {
		// if(peixe != melhorPeixe) {
		//
		// }
		// }
	}

	private Peixe swapIndividual(Peixe peixe) {
		// for(Peixe peixe : this.peixes){
		int coluna1 = Main.generator.nextInt(Main.numeroDiasAula);
		int linha1 = Main.generator.nextInt(Main.numeroHorariosPorDia);

		int coluna2 = Main.generator.nextInt(Main.numeroDiasAula);
		int linha2 = Main.generator.nextInt(Main.numeroHorariosPorDia);

		Horario horario = peixe.getGrade()[linha1][coluna1];
		if (horario == null) {
			horario = new Horario();
		}
		ArrayList<Disciplina> disciplinas1 = horario.getDisciplinas();
		Horario horario2 = peixe.getGrade()[linha2][coluna2];
		if (horario2 == null) {
			horario2 = new Horario();
		}
		ArrayList<Disciplina> disciplinas2 = horario2.getDisciplinas();

		Disciplina disciplina1 = null;
		Disciplina disciplina2 = null;

		if (!disciplinas1.isEmpty()) {
			int index = Main.generator.nextInt(disciplinas1.size());
			disciplina1 = disciplinas1.get(index);

			disciplinas1.remove(disciplina1);

		}

		if (!disciplinas2.isEmpty()) {
			int index = Main.generator.nextInt(disciplinas2.size());
			disciplina2 = disciplinas2.get(index);

			disciplinas2.remove(disciplina2);
		}

		if (disciplina2 != null) {
			disciplinas1.add(disciplina2);
		}

		if (disciplina1 != null) {
			disciplinas2.add(disciplina1);
		}

		Peixe peixe2 = new Peixe(peixe.getGrade().clone());
		// Peixe peixe2 = peixe;
		if (disciplinas1.isEmpty()) {
			peixe2.getGrade()[linha1][coluna1] = null;
		} else {
			horario.setDisciplinas(disciplinas1);
			peixe2.getGrade()[linha1][coluna1] = horario;
		}

		if (disciplinas2.isEmpty()) {
			peixe2.getGrade()[linha2][coluna2] = null;
		} else {
			horario2.setDisciplinas(disciplinas2);
			peixe2.getGrade()[linha2][coluna2] = horario2;
		}

		peixe2.calcularFitness();

		if (peixe.isBetterFitness(peixe2.getFitness())) {
			System.out.println("testetestesteste");
			peixe = peixe2;
		}
		// }
		return peixe;
	}

	public Peixe getMelhorPeixe() {
		checkMelhorPeixe();
		return melhorPeixe;
	}

	public Peixe getPiorPeixe() {
		checkPiorPeixe();
		return piorPeixe;
	}
}
