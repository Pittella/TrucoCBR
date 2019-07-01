//TODO: 
//Apresentar


import cbr.cbrDescriptions.TrucoDescription;
import consultas.ExemploConsultas;
import cbr.Adaptacoes.RespondeRobo;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcolibri.exception.ExecutionException;

public class PlayingTruco {

	public TrucoDescription gameState = new TrucoDescription();
	//public RespondeRobo bot = new RespondeRobo();

	Deck MatchDeck = new Deck();
	//public Card[] MesaA = new Card[3];
	//public Card[] MesaB = new Card[3];

	public boolean RodadaEmAndamento;       //Comentar seria legal
	int PesoDaMao;
	int PesoDeInvido;
	int CountRodada;
	boolean ChamouFaltaInvido = false; //preciso disso pra ver se chamou falta
	boolean JaChamaraminvido = false;
	boolean AlguemNaoAceitouTruco = false;
	boolean EmpardouPrimeira, EmpardouSegunda, EmpardouTerceira;

	//Para Realizar Consultas
	public ExemploConsultas botResposta = new ExemploConsultas(); 

	public void DealCards(Hand A, Hand B) {
		MatchDeck.ShuffleDeckArray();
		A.SetCards(MatchDeck.DeckArray[0], MatchDeck.DeckArray[2], MatchDeck.DeckArray[4]);
		B.SetCards(MatchDeck.DeckArray[1], MatchDeck.DeckArray[3], MatchDeck.DeckArray[5]);

	}

	public void Match(Player A, Player B) {

		//System.out.println("Consulta primeira carta "+ new ExemploConsultas().respostaPrimeiraCarta());

		//CBR stuff
		A.souBot = false;
		B.souBot = true;

		System.out.println("Iniciado Partida");
		System.out.println("Setando pontos " + A.getNome() + " e " + B.getNome() + " Zero");
		A.setPontos(0);
		B.setPontos(0);
		A.setJogadorMao(true);
		B.setJogadorMao(false);
		System.out.println("Construindo deck");
		MatchDeck.BuildDeckArray();
		boolean loop = true;
		while (loop) {
			DealCards(A.getPlayerHand(), B.getPlayerHand());
			A.Mesa[0]=A.PHand.CartaVazia;
			A.Mesa[1]=A.PHand.CartaVazia;
			A.Mesa[2]=A.PHand.CartaVazia;
			B.Mesa[0]=A.PHand.CartaVazia;
			B.Mesa[1]=A.PHand.CartaVazia;
			B.Mesa[2]=A.PHand.CartaVazia;      
			System.out.println("Inicio da Mao");
			if (A.JogadorMao == true) {
				System.out.println(A.getNome() + " Primeiro Jogador na mao");
				MaoDoJogo(A, B);
				if (AlguemNaoAceitouTruco == false) {
					B.setJogadorMao(true);
					A.setJogadorMao(false);
				} else {
					this.AlguemNaoAceitouTruco = false;
				}
			} else {
				System.out.println(B.getNome() + " Primeiro Jogador na mao");
				MaoDoJogo(B, A);
				if (AlguemNaoAceitouTruco == false) {
					A.setJogadorMao(true);
					B.setJogadorMao(false);
				} else {
					this.AlguemNaoAceitouTruco = false;
				}
			}
			System.out.println("Placar " + A.getNome() + "->" + A.getPontos() + " X " + B.getPontos() + "<-" + B.getNome());
			if (A.getPontos() >= 24) {
				break;
			}
			if (B.getPontos() >= 24) {
				break;
			}
		}

		//Fim da Partida
	}

	public void MaoDoJogo(Player A, Player B) {
		//Imicializando Flags de nova rodada
		this.PesoDaMao = 1;
		this.PesoDeInvido = 0;
		this.CountRodada = 1;
		int PontosDaMaoA = 0;
		int PontosDaMaoB = 0;
		A.PrimeiroDaRodada = true;
		B.PrimeiroDaRodada = false;
		this.RodadaEmAndamento = true;
		A.JogouUmaCarta = false;
		B.JogouUmaCarta = false;
		this.JaChamaraminvido = false;
		A.PodeChamarOuAumentarTruco = true;
		B.PodeChamarOuAumentarTruco = true;
		A.PodeChamarInvido = true;
		B.PodeChamarInvido = true;

		A.VenceuPrimeiraRodada = false;
		A.VenceuSegundaRodada = false;
		A.VenceuTerceiraRodada = false;
		B.VenceuPrimeiraRodada = false;
		B.VenceuSegundaRodada = false;
		B.VenceuTerceiraRodada = false;

		this.EmpardouPrimeira = false;
		this.EmpardouSegunda = false;
		this.EmpardouTerceira = false;



		while (this.RodadaEmAndamento == true) {
			if (this.CountRodada >= 4) {
				this.RodadaEmAndamento = false;
				break;
			}

			if (A.PrimeiroDaRodada == true && B.PrimeiroDaRodada == false) {
				//A.setPontos(A.getPontos() + this.PesoDaMao);
				RodadaDaMao(A, B);
				//this.RodadaEmAndamento = false;
			} else if (B.PrimeiroDaRodada == true && A.PrimeiroDaRodada == false) {
				//B.setPontos(B.getPontos() + this.PesoDaMao);
				RodadaDaMao(B, A);
				//this.RodadaEmAndamento = false;
			} else {
				System.out.println("Error MaoDoJogo() ");
			}

			if (A.JogouUmaCarta == true && B.JogouUmaCarta == true) {
				System.out.println("Ambos jogadores jogaram uma carta na rodada #" + CountRodada);
				if (ContabilizaCartasDaRodada(A.Mesa[CountRodada - 1], B.Mesa[CountRodada - 1], CountRodada) == 1) {
					switch (CountRodada) {
					case 1:
						System.out.println(A.getNome() + " vence a rodada " + CountRodada);
						A.VenceuPrimeiraRodada = true;
						B.VenceuPrimeiraRodada = false;
						break;
					case 2:
						System.out.println(A.getNome() + " vence a rodada " + CountRodada);
						A.VenceuSegundaRodada = true;
						B.VenceuSegundaRodada = false;
						break;
					case 3:
						System.out.println(A.getNome() + " vence a rodada " + CountRodada);
						A.VenceuTerceiraRodada = true;
						B.VenceuTerceiraRodada = false;
						break;
					default:
						System.out.println("Error!!!!");
						break;
					}
					PontosDaMaoA = PontosDaMaoA + 1;
					A.PrimeiroDaRodada = true;
					B.PrimeiroDaRodada = false;
				} else if (ContabilizaCartasDaRodada(A.Mesa[CountRodada - 1], B.Mesa[CountRodada - 1], CountRodada) == 2) {

					switch (CountRodada) {
					case 1:
						System.out.println(B.getNome() + " vence a rodada " + CountRodada);
						B.VenceuPrimeiraRodada = true;
						A.VenceuPrimeiraRodada = false;
						break;
					case 2:
						System.out.println(B.getNome() + " vence a rodada " + CountRodada);
						B.VenceuSegundaRodada = true;
						A.VenceuSegundaRodada = false;
						break;
					case 3:
						System.out.println(B.getNome() + " vence a rodada " + CountRodada);
						B.VenceuTerceiraRodada = true;
						A.VenceuTerceiraRodada = false;
						break;
					default:
						System.out.println("Error!!!!");
						break;
					}

					PontosDaMaoB = PontosDaMaoB + 1;
					B.PrimeiroDaRodada = true;
					A.PrimeiroDaRodada = false;

				} else {
					System.out.println("Empardou a rodada #" + CountRodada);
					switch (CountRodada) {
					case 1:
						System.out.println("Empardou a primeira vale a segunda");
						PontosDaMaoA = 0;
						PontosDaMaoB = 0;
						this.EmpardouPrimeira = true;
						break;
					case 2:
						if (this.EmpardouPrimeira = true) {
							System.out.println("Empardou a primeira e segunda vale a terceira");
							PontosDaMaoA = 0;
							PontosDaMaoB = 0;
							this.EmpardouSegunda = true;
						} else {
							System.out.println("Empardou a segunda vale a primeira");
							this.EmpardouSegunda = true;
							PontosDaMaoA = 0;
							PontosDaMaoB = 0;
						}
						break;
					case 3:
						System.out.println("Empardou primeira, segunda e terceira o mao vence");
						break;
					default:
						break;
					}

				}

				if (this.EmpardouPrimeira == true && this.EmpardouSegunda == false && this.EmpardouTerceira == false) {
					if (CountRodada == 2) {
						System.out.println("Se empardou a primeira logo vale a segunda");
						if (A.VenceuSegundaRodada == true && B.VenceuSegundaRodada == false) {
							System.out.println(A.getNome() + "Venceu a segunda");
							PontosDaMaoA = 2;
							PontosDaMaoB = 0;
						} else if (B.VenceuSegundaRodada == true && A.VenceuSegundaRodada == false) {
							System.out.println(B.getNome() + "Venceu a segunda");
							PontosDaMaoA = 0;
							PontosDaMaoB = 2;
						}
					}
				}

				if (this.EmpardouPrimeira == false && this.EmpardouSegunda == true && this.EmpardouTerceira == false) {
					if (CountRodada == 2) {
						System.out.println("Se empardou a segunda logo vale a primeira");
						if (A.VenceuPrimeiraRodada == true) {
							System.out.println(A.getNome() + "Havia vencido a primeira rodada");
							PontosDaMaoA = 2;
							PontosDaMaoB = 0;
						} else if (B.VenceuPrimeiraRodada == true) {
							System.out.println(B.getNome() + "Havia vencido a primeira rodada");
							PontosDaMaoA = 0;
							PontosDaMaoB = 2;
						}
					}
				}

				if (PontosDaMaoA == 2) {
					break;
				}
				if (PontosDaMaoB == 2) {
					break;
				}

				this.CountRodada = this.CountRodada + 1;
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				System.out.printf("\n");
				if (this.CountRodada > 1) {
					A.PodeChamarInvido = false;
					B.PodeChamarInvido = false;
					A.PodeChamarFlor = false;
					B.PodeChamarFlor = false;
				}
				A.JogouUmaCarta = false;
				B.JogouUmaCarta = false;
			}
		}

		if (AlguemNaoAceitouTruco == false) {
			if (PontosDaMaoA > PontosDaMaoB) {
				System.out.println(A.getNome() + " fez pontos!");
				A.setPontos(A.getPontos() + this.PesoDaMao);
			} else if (PontosDaMaoB > PontosDaMaoA) {
				System.out.println(B.getNome() + " fez pontos!");
				B.setPontos(B.getPontos() + this.PesoDaMao);
			}
		} else {
			//this.AlguemNaoAceitouTruco = false;
		}

	}

	public void RodadaDaMao(Player A, Player B) {

		System.out.println("Rodada #" + this.CountRodada + " PesoDaMao:" + this.PesoDaMao);

		A.VezDeJogar = true;
		A.ChamouTruco = false;

		while (A.VezDeJogar == true) {

			OpcoesDeRodada(A, B);

		}

		//A.setPrimeiroDaRodada(false); 
		//B.setPrimeiroDaRodada(true);
		A.PrimeiroDaRodada = false;
		B.PrimeiroDaRodada = true;

	}

	public void OpcoesDeRodada(Player A, Player B) {
		int EscolhaDaJogada = 0;

		//System.out.println("Mao de " + A.getNome() + ":");
		System.out.printf("Vez: Mao de %s:", A.getNome());
		A.PHand.PrintHand();
		//System.out.println("Mao de " + B.getNome() + ":");
		System.out.printf("Outro: Mao de %s:", B.getNome());
		B.PHand.PrintHand();

		System.out.println("Jogador a jogar " + A.getNome() + ", selecione sua jogada:");

		if (A.PodeChamarOuAumentarTruco == true && A.PodeChamarInvido == true && A.PHand.ChecarFlor() == true) { //Adioonar flor && A.PodeChamarFlor == true
			System.out.println("(1)(2)(3)Cartas (4)Chamar/Aumentar o Jogo (5)Envido (6)Real Envido (7)Falta Envido (8)Flor (9)Ir ao Baralho ");
		} else if (A.PodeChamarOuAumentarTruco == true && A.PodeChamarInvido == true && A.PHand.ChecarFlor() == false) {
			System.out.println("(1)(2)(3)Cartas (4)Chamar/Aumentar o Jogo (5)Envido (6)Real Envido (7)Falta Envido (9)Ir ao Baralho ");
		} else if (A.PodeChamarOuAumentarTruco == true && A.PodeChamarInvido == false && A.PHand.ChecarFlor() == true) {
			System.out.println("(1)(2)(3)Cartas (4)Chamar/Aumentar o Jogo (8)Flor (9)Ir ao Baralho ");
		} else if (A.PodeChamarOuAumentarTruco == false && A.PodeChamarInvido == true && A.PHand.ChecarFlor() == false) {
			System.out.println("(1)(2)(3)Cartas (5)Envido (6)Real Envido (7)Falta Envido (9)Ir ao Baralho ");
		} else if (A.PodeChamarOuAumentarTruco == false && A.PodeChamarInvido == false && A.PHand.ChecarFlor() == true) {
			System.out.println("(1)(2)(3)Cartas (8)Flor (9)Ir ao Baralho ");
		} else if (A.PodeChamarOuAumentarTruco == true && A.PodeChamarInvido == false && A.PHand.ChecarFlor() == false) {
			System.out.println("(1)(2)(3)Cartas (4)Chamar/Aumentar (9)Ir ao Baralho ");
		} else if (A.PodeChamarOuAumentarTruco == false && A.PodeChamarInvido == false && A.PHand.ChecarFlor() == false) {
			System.out.println("(1)(2)(3)Cartas (9)Ir ao Baralho ");
		}

		//BOT_CBR stuff        
		if (A.souBot == true) {
			//Hierarquia das decisoes do bot
			//Chamar flor?        	
			//Chamar invido?        	
			//chamar truco?        	
			//jogar carta

			int intJogadorMao=0;
			if(A.JogadorMao==true) {
				intJogadorMao=2;
			} else{
				intJogadorMao=1;
			}

			System.out.println("Sort Mao");
			int[] pesos = SortHandToBot(A.PHand);        	


			if(A.PHand.ChecarFlor()==true && A.PodeChamarFlor == true) {
				//Chama flor se tiver flor
				System.out.println("Bot Chamour Flor");
				EscolhaDaJogada = 8;
				A.PodeChamarFlor = false;
			} else {
				//Chamar invido
				//Exemplo:
				//System.out.println("Consulta chamou envido "+ new ExemploConsultas().respostaChamarEnvido(1, 33));
				if((botResposta.respostaChamarEnvido(intJogadorMao, A.PHand.CalcEnvido())==5 && this.CountRodada ==1) && A.PodeChamarInvido == true) {
					System.out.println("Bot Chamou Invido");
					A.ChamouEnvido = true;
					A.PodeChamarInvido = false;
					EscolhaDaJogada = 5; 
				} else if((botResposta.respostaChamarTruco(pesos[0], pesos[1], pesos[2], intJogadorMao, B.Mesa[0].peso, B.Mesa[1].peso, B.Mesa[2].peso, this.CountRodada)==4) && A.ChamouTruco == false) {
					System.out.println("Bot Chamou Truco");
					A.ChamouTruco = true;
					EscolhaDaJogada = 4; 
				} else {        		
					//Isso aqui eh so para o bot jogar uma carta
					System.out.println("Bot Escolheu Jogar Uma Carta");
					int peso = botResposta.respostaPrimeiraCartaTeste(pesos[0],pesos[1], pesos[2]);
					int inputDoBot = verificaPosicaoDaCarta(A.PHand,peso);
					System.out.println("Escolha do bot");
					EscolhaDaJogada = inputDoBot;
				}
				/*try {
                    //codigo do input do bot kkk
                    //EscolhaDaJogada = bot.jogaCarta(gameState, CountRodada); //Cometar essa linha pq sem ela da erro por enquanto

               		} catch (ExecutionException ex) {
                    Logger.getLogger(PlayingTruco.class.getName()).log(Level.SEVERE, null, ex);
                }*/	        		
			}


		} else {
			//codigo do input player
			EscolhaDaJogada = A.GetPlayerInput();

		}

		switch (EscolhaDaJogada) {
		case 1:
		case 2:
		case 3:
			if (A.PHand.HandArray[EscolhaDaJogada - 1].peso == -1) {

				System.out.println("Carta Vazia Escolha Invalida");

			} else {

				A.Mesa[CountRodada - 1] = A.PHand.HandArray[EscolhaDaJogada - 1];

				A.PHand.HandArray[EscolhaDaJogada - 1] = A.PHand.CartaVazia;

				A.JogouUmaCarta = true;

				System.out.println(A.getNome() + " jogou " + A.Mesa[CountRodada - 1].Nome);

				System.out.println("Passando a vez para outro jogador");

				A.VezDeJogar = false;
			}
			break;
		case 4:
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println(A.getNome() + " chamou o jogo");
			A.ChamouTruco = true;
			B.ChamouTruco = false;
			ChamouTruco(A, B);
			break;
		case 5:
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println(A.getNome() + " chamou invido");
			this.PesoDeInvido = 1;
			A.ChamouEnvido = true;
			B.ChamouEnvido = false;
			ChamouEnvido(A, B);
			break;
		case 6:
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println(A.getNome() + " chamou real invido");
			this.PesoDeInvido = 3;
			A.ChamouEnvido = true;
			B.ChamouEnvido = false;
			ChamouEnvido(A, B);
			break;
		case 7:
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println(A.getNome() + " chamou Falta invido");
			this.ChamouFaltaInvido = true;
			int ValorFalta = CalculoFalta(A.getPontos(), B.getPontos());
			System.out.println("Valor Falta:" + ValorFalta);
			this.PesoDeInvido = ValorFalta;
			A.ChamouEnvido = true;
			B.ChamouEnvido = false;
			ChamouEnvido(A, B);
			break;
		case 8:
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println(A.getNome() + " chamou Flor");
			A.PodeChamarInvido = false;
			B.PodeChamarInvido = false;
			if (B.PHand.ChecarFlor() == true) {
				System.out.println("Contra Flor � Pro兊ido!");
				System.out.println(A.getNome() + " cantou " + A.PHand.CalcEnvido());

				System.out.println(B.getNome() + " cantou " + B.PHand.CalcEnvido());

				if (A.PHand.CalcEnvido() > B.PHand.CalcEnvido()) {

					System.out.println(A.getNome() + " venceu disputa de flor");

					A.setPontos(A.getPontos() + this.PesoDeInvido);

				} else if (A.PHand.CalcEnvido() < B.PHand.CalcEnvido()) {

					System.out.println(B.getNome() + " venceu disputa de flor");

					B.setPontos(B.getPontos() + this.PesoDeInvido);

				} else if (A.PHand.CalcEnvido() == B.PHand.CalcEnvido()) {

					System.out.println("Envido empatou");

				}
			} else {
				A.setPontos(A.getPontos() + 3);
			}
			break;
		case 9:
			System.out.println(A.getNome() + " Foi ao baralho");
			System.out.println("Causando o fim da rodada");
			B.setPontos(B.getPontos() + this.PesoDaMao);
			this.RodadaEmAndamento = false;
			this.AlguemNaoAceitouTruco = true; //Aqui estou usando um artif兤io para ignorar setJogadorMao na Funcao Match
			B.setJogadorMao(true);
			A.setJogadorMao(false);
			B.VezDeJogar = false;
			A.VezDeJogar = false;
			break;
		default:
			System.out.println("Escolha invalida");
			break;
		}

	}

	public void ChamouTruco(Player A, Player B) {
		if (this.PesoDaMao < 4) {
			int RespostaTruco;
			//A.PodeChamarOuAumentarTruco = false;
			//B.PodeChamarOuAumentarTruco = true;
			System.out.println(B.getNome() + " aceitas? (1)Sim (2)Nao (3)Aumentar");
			
			RespostaTruco = 1;
			
			if(B.souBot==true) {
				//Bot
				int[] pesos = SortHandToBot(A.PHand);
				int intJogadorMao=0;
				if(A.JogadorMao==true) {
					intJogadorMao=2;
				} else{
					intJogadorMao=1;
				}

				if(this.PesoDaMao == 1){
					//Esta em Truco
					if(this.botResposta.respostaChamarRetruco(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==3) {
						RespostaTruco = 3;
					} else if(this.botResposta.respostaAceitarTruco(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==1) {
						RespostaTruco = 1;
					} else {
						RespostaTruco = 2;
					}
				} else if(this.PesoDaMao == 2){
					//Esta em Retruco
					if(this.botResposta.respostaChamarVale4(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==3) {
						RespostaTruco = 3;
					} else if(this.botResposta.respostaAceitarRetruco(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==1) {
						RespostaTruco = 1;
					} else {
						RespostaTruco = 2;
					}					
				} else if(this.PesoDaMao == 3){
					//Esta em Vale 4
					if(this.botResposta.respostaAceitarVale4(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==1) {
						RespostaTruco = 1;
					} else {
						RespostaTruco = 2;
					}				
				}

			}else {         	
				//Player
				RespostaTruco = B.GetPlayerInput();
			}

			switch (RespostaTruco) {
			case 1:
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("Aceitastes");
				this.PesoDaMao = this.PesoDaMao + 1;
				System.out.println("A mao agora vale" + this.PesoDaMao + " pontos.");
				A.PodeChamarOuAumentarTruco = false;
				B.PodeChamarOuAumentarTruco = true;
				break;
			case 2:
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println(B.getNome() + "Recusastes");
				System.out.println("Fim da rodada pois arregou do truco/retruco/vale4");
				A.setPontos(A.getPontos() + this.PesoDaMao);
				System.out.println(A.getNome() + " fez " + this.PesoDaMao);
				B.VezDeJogar = false;
				A.VezDeJogar = false;
				B.setJogadorMao(false);
				A.setJogadorMao(true);
				this.AlguemNaoAceitouTruco = true;
				this.RodadaEmAndamento = false;
				break;
			case 3:
				if (this.PesoDaMao < 4) {
					this.PesoDaMao = this.PesoDaMao + 1;
					System.out.println(B.getNome() + " Aumentou o jogo para " + this.PesoDaMao);
					B.PodeChamarOuAumentarTruco = false;
					A.PodeChamarOuAumentarTruco = true;
					System.out.println(A.getNome() + " Aceitas? (1)Quero (2)Nao quero (3)Aumentar");

					//RespostaTruco = A.GetPlayerInput();

					if(A.souBot==true) {
						//Bot
						int[] pesos = SortHandToBot(A.PHand);
						int intJogadorMao=0;
						if(A.JogadorMao==true) {
							intJogadorMao=2;
						} else{
							intJogadorMao=1;
						}

						if(this.PesoDaMao == 1){
							//Esta em Truco
							if(this.botResposta.respostaChamarRetruco(pesos[0], pesos[1], pesos[2], intJogadorMao, B.Mesa[0].peso, B.Mesa[1].peso, B.Mesa[2].peso, this.CountRodada)==3) {
								RespostaTruco = 3;
							} else if(this.botResposta.respostaAceitarTruco(pesos[0], pesos[1], pesos[2], intJogadorMao, B.Mesa[0].peso, B.Mesa[1].peso, B.Mesa[2].peso, this.CountRodada)==1) {
								RespostaTruco = 1;
							} else {
								RespostaTruco = 2;
							}
						} else if(this.PesoDaMao == 2){
							//Esta em Retruco
							if(this.botResposta.respostaChamarVale4(pesos[0], pesos[1], pesos[2], intJogadorMao, B.Mesa[0].peso, B.Mesa[1].peso, B.Mesa[2].peso, this.CountRodada)==3) {
								RespostaTruco = 3;
							} else if(this.botResposta.respostaAceitarRetruco(pesos[0], pesos[1], pesos[2], intJogadorMao, B.Mesa[0].peso, B.Mesa[1].peso, B.Mesa[2].peso, this.CountRodada)==1) {
								RespostaTruco = 1;
							} else {
								RespostaTruco = 2;
							}					
						} else if(this.PesoDaMao == 3){
							//Esta em Vale 4
							if(this.botResposta.respostaAceitarVale4(pesos[0], pesos[1], pesos[2], intJogadorMao, B.Mesa[0].peso, B.Mesa[1].peso, B.Mesa[2].peso, this.CountRodada)==1) {
								RespostaTruco = 1;
							} else {
								RespostaTruco = 2;
							}				
						}

					}else {         	
						//Player
						RespostaTruco = A.GetPlayerInput();
					}

					switch (RespostaTruco) {
					case 1:
						System.out.println(A.getNome() + " Aceitou aumentar");
						this.PesoDaMao = this.PesoDaMao + 1;
						System.out.println("A mao agora vale" + this.PesoDaMao + " pontos.");
						A.PodeChamarOuAumentarTruco = true;
						B.PodeChamarOuAumentarTruco = false;

						break;
					case 2:
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println(A.getNome() + "Recusastes");
						System.out.println("Fim da rodada pois arregou do truco/retruco/vale4");
						B.setPontos(B.getPontos() + this.PesoDaMao);
						System.out.println(B.getNome() + " fez " + this.PesoDaMao);
						B.VezDeJogar = false;
						A.VezDeJogar = false;
						A.setJogadorMao(false);
						B.setJogadorMao(true);
						this.AlguemNaoAceitouTruco = true;
						this.RodadaEmAndamento = false;
						break;
					case 3:
						if (this.PesoDaMao < 4) {
							this.PesoDaMao = this.PesoDaMao + 1;
							System.out.println(A.getNome() + " Aumentou ainda mais!");
							System.out.println(A.getNome() + " Aumentou o jogo para " + this.PesoDaMao);
							System.out.println(B.getNome() + " Aceitas? (1)Quero (2)Nao quero");
							//RespostaTruco = B.GetPlayerInput();
							
							if(B.souBot==true) {
								//Bot
								int[] pesos = SortHandToBot(A.PHand);
								int intJogadorMao=0;
								if(A.JogadorMao==true) {
									intJogadorMao=2;
								} else{
									intJogadorMao=1;
								}

								if(this.PesoDaMao == 1){
									//Esta em Truco
									if(this.botResposta.respostaChamarRetruco(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==3) {
										RespostaTruco = 3;
									} else if(this.botResposta.respostaAceitarTruco(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==1) {
										RespostaTruco = 1;
									} else {
										RespostaTruco = 2;
									}
								} else if(this.PesoDaMao == 2){
									//Esta em Retruco
									if(this.botResposta.respostaChamarVale4(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==3) {
										RespostaTruco = 3;
									} else if(this.botResposta.respostaAceitarRetruco(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==1) {
										RespostaTruco = 1;
									} else {
										RespostaTruco = 2;
									}					
								} else if(this.PesoDaMao == 3){
									//Esta em Vale 4
									if(this.botResposta.respostaAceitarVale4(pesos[0], pesos[1], pesos[2], intJogadorMao, A.Mesa[0].peso, A.Mesa[1].peso, A.Mesa[2].peso, this.CountRodada)==1) {
										RespostaTruco = 1;
									} else {
										RespostaTruco = 2;
									}				
								}

							}else {         	
								//Player
								RespostaTruco = B.GetPlayerInput();
							}
							
							switch (RespostaTruco) {
							case 1:
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("Aceitastes");
								this.PesoDaMao = this.PesoDaMao + 1;
								System.out.println("A mao agora vale" + this.PesoDaMao + " pontos.");
								A.PodeChamarOuAumentarTruco = false;
								B.PodeChamarOuAumentarTruco = false;
								break;
							case 2:
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println("");
								System.out.println(B.getNome() + "Recusastes");
								System.out.println("Fim da rodada pois arregou do truco/retruco/vale4");
								A.setPontos(A.getPontos() + this.PesoDaMao);
								System.out.println(A.getNome() + " fez " + this.PesoDaMao);
								B.VezDeJogar = false;
								A.VezDeJogar = false;
								B.setJogadorMao(false);
								A.setJogadorMao(true);
								this.AlguemNaoAceitouTruco = true;
								this.RodadaEmAndamento = false;
								break;
							}

						} else {
							System.out.println("Nao podes mais aumentar");
						}
						break;
					}
				} else {
					System.out.println("Nao podes mais aumentar");
				}
				break;
			default:
				break;
			}
		} else {
			System.out.println("Nao podes mais aumentar");
		}

	}

	public void ChamouEnvido(Player A, Player B) {
		this.JaChamaraminvido = true;
		A.PodeChamarInvido = false;
		B.PodeChamarInvido = false;
		int RespostaInvido=1;
		if (this.PesoDeInvido == 1 && this.ChamouFaltaInvido == false) {
			//Escolha da jogada invido//
			System.out.println(B.getNome() + " Aceitas invido? (1)Quero (2)Nao Quero (3)Invido (4)Real Invido (5)Falta Invido");
			B.PHand.PrintHand();
			
			if(B.souBot==true){
				//Bot
				int intJogadorMao=0;
				if(B.JogadorMao==true) {
					intJogadorMao=2;
				} else{
					intJogadorMao=1;
				}
				if(botResposta.respostaAceitarEnvido(intJogadorMao, B.PHand.CalcEnvido())==1){
					RespostaInvido = 1;
				} else/* if(botResposta.respostaChamarEnvido(intJogadorMao, B.PHand.CalcEnvido())==3){
					RespostaInvido = 3;					
				} else*/ if(botResposta.respostaChamarRealEnvido(intJogadorMao, B.PHand.CalcEnvido())==4){
					RespostaInvido = 4;	
					B.PodeChamarInvido = false;
				} else if(botResposta.respostaChamarFaltaEnvido(intJogadorMao, B.PHand.CalcEnvido())==5){
					RespostaInvido = 5;
					B.PodeChamarInvido = false;
				} else {
					RespostaInvido = 2;		
					B.ChamouEnvido = true;
				}
			} else{
				//player humano
				RespostaInvido = B.GetPlayerInput();
			}
			
			//Invido
			switch (RespostaInvido) {
			case 1:
				System.out.println(B.getNome() + " Aceitou Invido");
				this.PesoDeInvido = 2;
				CantarInvido(A, B);
				//Invido    
				break;
			case 2:
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println(B.getNome() + " Arregou o Invido");
				A.setPontos(A.getPontos() + this.PesoDeInvido);
				//Invido
				break;
			case 3:
				System.out.println(B.getNome() + " Aumentou invido com invido");
				this.PesoDeInvido = 2;
				System.out.println(A.getNome() + " Aceitas invido+invido? (1)Quero (2)Nao quero (3)Real Invido (4) Falta Invido");
				
				if(A.souBot==true){
					//Bot
					int intJogadorMao=0;
					if(A.JogadorMao==true) {
						intJogadorMao=2;
					} else{
						intJogadorMao=1;
					}
					if(botResposta.respostaAceitarEnvido(intJogadorMao, A.PHand.CalcEnvido())==1){
						RespostaInvido = 1;
					} else if(botResposta.respostaChamarRealEnvido(intJogadorMao, A.PHand.CalcEnvido())==4){
						RespostaInvido = 3;					
					} else if(botResposta.respostaChamarFaltaEnvido(intJogadorMao, A.PHand.CalcEnvido())==5){
						RespostaInvido = 4;					
					} else {
						RespostaInvido = 2;
					}
				} else{
					//player humano
					RespostaInvido = A.GetPlayerInput();
				}
				
				
				
				
				//Invido+Invido
				switch (RespostaInvido) {
				case 1:
					System.out.println(A.getNome() + "Aceitou o invido+invido");
					this.PesoDeInvido = 3;
					CantarInvido(A, B);
					//Invido+Invido
					break;
				case 2:
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println(A.getNome() + " Arregou Invido+Invido");
					this.PesoDeInvido = 2;
					B.setPontos(B.getPontos() + this.PesoDeInvido);
					//Invido+Invido
					break;
				case 3:
					System.out.println(A.getNome() + " Aumentou o invido+invido com real invido");
					this.PesoDeInvido = 3;
					System.out.println(B.getNome() + " Aceitas invido+invido+Real invido? (1)Quero (2)Nao quero (3) Falta Invido");
					//RespostaInvido = B.GetPlayerInput();
					
					if(B.souBot==true){
						//Bot
						int intJogadorMao=0;
						if(B.JogadorMao==true) {
							intJogadorMao=2;
						} else{
							intJogadorMao=1;
						}
						if(botResposta.respostaAceitarRealEnvido(intJogadorMao, B.PHand.CalcEnvido())==1){
							RespostaInvido = 1;
						} else if(botResposta.respostaChamarFaltaEnvido(intJogadorMao, B.PHand.CalcEnvido())==5){
							RespostaInvido = 3;					
						} else {
							RespostaInvido = 2;			
						}
					} else{
						//player humano
						RespostaInvido = B.GetPlayerInput();
					}
					
					//Invido+Invido+Real-Invido
					switch (RespostaInvido) {
					case 1:
						System.out.println(B.getNome() + " Aceitou o invido+invido+Real Invido");
						this.PesoDeInvido = 5;
						CantarInvido(A, B);
						//Invido+Invido+Real-Invido
						break;
					case 2:
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println(B.getNome() + " Arregou o invido+invido+Real Invido");
						B.setPontos(B.getPontos() + this.PesoDeInvido);
						//Invido+Invido+Real-Invido
						break;
					case 3:
						System.out.println(B.getNome() + " Aumentou invido+invido+Real Invido com Falta Invido");
						this.PesoDeInvido = 1;
						System.out.println(A.getNome() + " Aceitas invido+invido+Real invido+Falta invido? (1)Quero (2)Nao Quero");
						
						//RespostaInvido = A.GetPlayerInput();
						if(A.souBot==true){
							//Bot
							int intJogadorMao=0;
							if(A.JogadorMao==true) {
								intJogadorMao=2;
							} else{
								intJogadorMao=1;
							}
							if(botResposta.respostaAceitarRealEnvido(intJogadorMao, A.PHand.CalcEnvido())==5){
								RespostaInvido = 1;
							} else {
								RespostaInvido = 2;
							}
						} else{
							//player humano
							RespostaInvido = A.GetPlayerInput();
						}
						
						
						//Invido+Invido+Real-Invido+Real-Invido
						if (RespostaInvido == 1) {
							System.out.println(A.getNome() + " Aceitou invido+invido+Real invido+Falta invido");
							this.PesoDeInvido = CalculoFalta(A.getPontos(), B.getPontos());
							CantarInvido(A, B);
							//Invido+Invido+Real-Invido+Real-Invido
						} else if (RespostaInvido == 2) {
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println("");
							System.out.println(A.getNome() + " Arregou invido+invido+Real invido+Falta invido");
							B.setPontos(B.getPontos() + this.PesoDeInvido);
						}
						break;
					default:
						break;
					}
					//Invido+Invido
					break;

				case 4:
					System.out.println(A.getNome() + " Aumentou o invido+invido com Falta Invido");
					this.PesoDeInvido = 1;
					System.out.println(B.getNome() + " Aceitas invido+invido+Falta Invido? (1)Quero (2)Nao Quero");
					
					//RespostaInvido = B.GetPlayerInput();
					if(B.souBot==true){
						//Bot
						int intJogadorMao=0;
						if(B.JogadorMao==true) {
							intJogadorMao=2;
						} else{
							intJogadorMao=1;
						}
						if(botResposta.respostaAceitarFaltaEnvido(intJogadorMao, B.PHand.CalcEnvido())==5){
							RespostaInvido = 1;
						} else {
							RespostaInvido = 2;
						}
					} else{
						//player humano
						RespostaInvido = B.GetPlayerInput();
					}
					
					//invido+invido+Falta Invido 1
					if (RespostaInvido == 1) {
						System.out.println(B.getNome() + " Aceitou invido+invido+Falta Invido");
						this.PesoDeInvido = CalculoFalta(A.getPontos(), B.getPontos());
						CantarInvido(A, B);
						//invido+invido+falta invido 2
					}
					if (RespostaInvido == 2) {
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println(B.getNome() + " Arregou invido+invido+Falta Invido");
						A.setPontos(A.getPontos() + this.PesoDeInvido);
					}
					//Invido+Invido
					break;
				default:
					System.out.println("Opcao invaldia");
					break;
				}
				//Invido
				break;
			case 4:
				System.out.println(B.getNome() + " aumentou invido com Real Invido");
				this.PesoDeInvido = 3;
				System.out.println(A.getNome() + " Aceitas invido+Real Invido? (1)Quero (2)Nao Quero (3)Falta Invido");
				
				//RespostaInvido = A.GetPlayerInput();
				if(A.souBot==true){
					//Bot
					int intJogadorMao=0;
					if(A.JogadorMao==true) {
						intJogadorMao=2;
					} else{
						intJogadorMao=1;
					}
					if(botResposta.respostaAceitarRealEnvido(intJogadorMao, A.PHand.CalcEnvido())==1){
						RespostaInvido = 1;
					} else if(botResposta.respostaChamarFaltaEnvido(intJogadorMao, A.PHand.CalcEnvido())==5){
						RespostaInvido = 3;					
					} else {
						RespostaInvido = 2;
					}
				} else{
					//player humano
					RespostaInvido = A.GetPlayerInput();
				}
				
				switch (RespostaInvido) {
				case 1:
					System.out.println(A.getNome() + "Aceitou invido+Real invido");
					this.PesoDeInvido = 5;
					CantarInvido(A, B);
					break;
				case 2:
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println(A.getNome() + " Arregou Invido+Real Invido");
					B.setPontos(B.getPontos() + this.PesoDeInvido);
					break;
				case 3:
					System.out.println(A.getNome() + " Aumentou Invido+Real Invido com Falta Invido");
					this.PesoDeInvido = 1;
					System.out.println(B.getNome() + " Aceitas Invido+Real Invido + Falta Invido?(1)Quero (2)Nao Quero");
					
					//RespostaInvido = B.GetPlayerInput();
					if(B.souBot==true){
						//Bot
						int intJogadorMao=0;
						if(B.JogadorMao==true) {
							intJogadorMao=2;
						} else{
							intJogadorMao=1;
						}
						if(botResposta.respostaAceitarFaltaEnvido(intJogadorMao, B.PHand.CalcEnvido())==5){
							RespostaInvido = 1;
						} else {
							RespostaInvido = 2;
						}
					} else{
						//player humano
						RespostaInvido = B.GetPlayerInput();
					}
					
					switch (RespostaInvido) {
					case 1:
						System.out.println(B.getNome() + " Aceitou invido+real invido + falta invido");
						this.PesoDeInvido = CalculoFalta(A.getPontos(), B.getPontos());
						CantarInvido(A, B);
						break;
					case 2:
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println("");
						System.out.println(B.getNome() + " Arregou invido+real invido + falta invido");
						A.setPontos(A.getPontos() + this.PesoDeInvido);
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
				break;
			case 5:
				System.out.println(B.getNome() + " aumentou invido com Falta Invido");
				this.PesoDeInvido = 1;
				System.out.println(A.getNome() + " Aceitas Invido+Falta Invido? (1)Quero (2)Nao Quero");
				//RespostaInvido = A.GetPlayerInput();
				if(A.souBot==true){
					//Bot
					int intJogadorMao=0;
					if(A.JogadorMao==true) {
						intJogadorMao=2;
					} else{
						intJogadorMao=1;
					}
					if(botResposta.respostaAceitarFaltaEnvido(intJogadorMao, A.PHand.CalcEnvido())==1){
						RespostaInvido = 1;
					} else {
						RespostaInvido = 2;
					}
				} else{
					//player humano
					RespostaInvido = A.GetPlayerInput();
				}
				
				switch (RespostaInvido) {
				case 1:
					System.out.println(A.getNome() + " Aceitou Invido+Falta Invido");
					this.PesoDeInvido = CalculoFalta(A.getPontos(), B.getPontos());
					CantarInvido(A, B);
					break;
				case 2:
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println(A.getNome() + " Arregou Invido+Falta Invido");
					B.setPontos(B.getPontos() + this.PesoDeInvido);
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		} else if (this.PesoDeInvido == 3 && this.ChamouFaltaInvido == false) {
			System.out.println(B.getNome() + "Aceitas Real Invido? (1)Quero (2)Nao quero (3)Falta Invido");
			
			//RespostaInvido = B.GetPlayerInput();
			if(B.souBot==true){
				//Bot
				int intJogadorMao=0;
				if(B.JogadorMao==true) {
					intJogadorMao=2;
				} else{
					intJogadorMao=1;
				}
				if(botResposta.respostaAceitarRealEnvido(intJogadorMao, B.PHand.CalcEnvido())==5){
					RespostaInvido = 1;
				} else if(botResposta.respostaChamarFaltaEnvido(intJogadorMao, B.PHand.CalcEnvido())==5){
					RespostaInvido = 3;
				} else {
					RespostaInvido = 2;
				}
			} else{
				//player humano
				RespostaInvido = B.GetPlayerInput();
			}
			
			switch (RespostaInvido) {
			case 1:
				System.out.println(B.getNome() + " Aceitou Real Invido");
				CantarInvido(A, B);
				break;
			case 2:
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println(B.getNome() + " Arregou Real Invido");
				this.PesoDeInvido = 2;
				A.setPontos(A.getPontos() + this.PesoDeInvido);
				break;
			case 3:
				System.out.println(B.getNome() + " Aumentou Real invido com Falta invido");
				this.PesoDeInvido = 1;
				System.out.println(A.getNome() + " Aceitas Real Invido + Falta Invido?(1)Quero (2)Nao quero");
				
				//RespostaInvido = A.GetPlayerInput();
				if(A.souBot==true){
					//Bot
					int intJogadorMao=0;
					if(A.JogadorMao==true) {
						intJogadorMao=2;
					} else{
						intJogadorMao=1;
					}
					if(botResposta.respostaAceitarFaltaEnvido(intJogadorMao, A.PHand.CalcEnvido())==1){
						RespostaInvido = 1;
					} else {
						RespostaInvido = 2;
					}
				} else{
					//player humano
					RespostaInvido = A.GetPlayerInput();
				}
				
				
				switch (RespostaInvido) {
				case 1:
					System.out.println(A.getNome() + " Aceitou Real Invido+ Falta Invido");
					this.PesoDeInvido = CalculoFalta(A.getPontos(), B.getPontos());
					CantarInvido(A, B);
					break;
				case 2:
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println("");
					System.out.println(A.getNome() + " Arregou Real Invido+ Falta Invido");
					this.PesoDeInvido = 1;
					break;
				}
				break;
			default:
				break;
			}
		} else if (this.ChamouFaltaInvido == true) {
			System.out.println(B.getNome() + " Aceitas Falta invido?(1)Quero (2)Nao quero");
			
			//RespostaInvido = B.GetPlayerInput();
			if(B.souBot==true){
				//Bot
				int intJogadorMao=0;
				if(B.JogadorMao==true) {
					intJogadorMao=2;
				} else{
					intJogadorMao=1;
				}
				if(botResposta.respostaAceitarFaltaEnvido(intJogadorMao, B.PHand.CalcEnvido())==1){
					RespostaInvido = 1;
				} else {
					RespostaInvido = 2;
				}
			} else{
				//player humano
				RespostaInvido = B.GetPlayerInput();
			}
			
			switch (RespostaInvido) {
			case 1:
				System.out.println(B.getNome() + " Aceitou Falta invido");
				this.PesoDeInvido = CalculoFalta(A.getPontos(), B.getPontos());
				CantarInvido(A, B);
				break;
			case 2:
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println(B.getNome() + " Arregou Falta Invido");
				A.setPontos(A.getPontos() + this.PesoDeInvido);
				break;
			default:
				break;
			}
		}
	}

	public int ContabilizaCartasDaRodada(Card A, Card B, int Rodada) {

		if (A.peso > B.peso) {
			//A venceu
			return 1;

		} else if (A.peso < B.peso) {
			//B venceu
			return 2;

		} else {
			//Empardou
			return 3;

		}

	}

	public int CalculoFalta(int A, int B) {

		if (A > B) {

			return A - B + 1;

		} else if (A < B) {

			return B - A + 1;

		} else {

			return 1;
		}
	}

	public void CantarInvido(Player A, Player B) {

		System.out.println(A.getNome() + " cantou " + A.PHand.CalcEnvido());

		System.out.println(B.getNome() + " cantou " + B.PHand.CalcEnvido());

		if (A.PHand.CalcEnvido() > B.PHand.CalcEnvido()) {

			System.out.println(A.getNome() + " venceu disputa de Envido");

			A.setPontos(A.getPontos() + this.PesoDeInvido);

		} else if (A.PHand.CalcEnvido() < B.PHand.CalcEnvido()) {

			System.out.println(B.getNome() + " venceu disputa de Envido");

			B.setPontos(B.getPontos() + this.PesoDeInvido);

		} else if (A.PHand.CalcEnvido() == B.PHand.CalcEnvido()) {

			System.out.println("Envido empatou");

		}
	}


	public int[] SortHandToBot(Hand Mao) {
		int[] pesos = new int[3];
		if(Mao.HandArray[0].peso > Mao.HandArray[1].peso && Mao.HandArray[0].peso > Mao.HandArray[2].peso) {
			pesos[0] = Mao.HandArray[0].peso;
			if(Mao.HandArray[1].peso > Mao.HandArray[2].peso) {
				pesos[1] = Mao.HandArray[1].peso;
				pesos[2] = Mao.HandArray[2].peso;
			} else {
				pesos[1] = Mao.HandArray[2].peso;
				pesos[2] = Mao.HandArray[1].peso;
			}
		} else if (Mao.HandArray[1].peso > Mao.HandArray[0].peso && Mao.HandArray[1].peso > Mao.HandArray[2].peso) {
			pesos[0] = Mao.HandArray[1].peso;
			if(Mao.HandArray[0].peso > Mao.HandArray[2].peso) {
				pesos[1] = Mao.HandArray[0].peso;
				pesos[2] = Mao.HandArray[2].peso;
			} else {
				pesos[1] = Mao.HandArray[2].peso;
				pesos[2] = Mao.HandArray[0].peso;
			}
		} else {
			pesos[0] = Mao.HandArray[2].peso;
			if(Mao.HandArray[0].peso > Mao.HandArray[1].peso) {
				pesos[1] = Mao.HandArray[0].peso;
				pesos[2] = Mao.HandArray[1].peso;
			} else {
				pesos[1] = Mao.HandArray[1].peso;
				pesos[2] = Mao.HandArray[0].peso;
			}
		}    	

		return pesos;
	}

	public int verificaPosicaoDaCarta(Hand Mao, int peso){
		int posicao = 0;
		if(peso == Mao.HandArray[0].peso)
			posicao = 1;
		if(peso == Mao.HandArray[1].peso)
			posicao = 2;
		if(peso == Mao.HandArray[2].peso)
			posicao = 3;

		return posicao;    	

	}
}