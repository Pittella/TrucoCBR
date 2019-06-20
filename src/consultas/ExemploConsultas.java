package consultas;

import cbr.Adaptacoes.RespondeRobo;
import cbr.cbrDescriptions.TrucoDescription;
import jcolibri.exception.ExecutionException;

public class ExemploConsultas {
	//RespondeRobo respondeRobo = new RespondeRobo();
	TrucoDescription description = new TrucoDescription();

	public Integer respostaPrimeiraCarta() {
		description.setCartaAltaRobo(52);
		description.setCartaMediaRobo(16);
		description.setCartaBaixaRobo(1);
		int cartaParaJogar = 0;
		RespondeRobo resposta = new RespondeRobo();

		try {
			cartaParaJogar = resposta.jogaCarta(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartaParaJogar;
	}

	public Integer respostaPrimeiraCartaTeste(int Alta, int Media, int Baixa) {
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int cartaParaJogar = 0;
		RespondeRobo resposta = new RespondeRobo();

		try {
			cartaParaJogar = resposta.jogaCarta(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartaParaJogar;
	}

	public Integer respostaSegundaCarta() {
		description.setCartaAltaRobo(52);
		description.setCartaMediaRobo(16);
		description.setCartaBaixaRobo(1);
		int cartaParaJogar = 0;
		RespondeRobo resposta = new RespondeRobo();

		try {
			cartaParaJogar = resposta.jogaCarta(description, 2);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartaParaJogar;
	}

	public Integer respostaTerceiraCarta() {
		description.setCartaAltaRobo(52);
		description.setCartaMediaRobo(16);
		description.setCartaBaixaRobo(1);
		int cartaParaJogar = 0;
		RespondeRobo resposta = new RespondeRobo();

		try {
			cartaParaJogar = resposta.jogaCarta(description, 3);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartaParaJogar;
	}

// ----------------------------------------------------------------------------------------------------------------------------//
// -------------------------------------------------------TRUCO----------------------------------------------------------------//
	
	public Integer respostaChamarTruco(		int Alta, int Media, int Baixa, 
											int jogadorMao, int primeiraCartaHumano, 
											int segundaCartaHumano, int terceiraCartaHumano,
											int rodada) {
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int chamarTruco = 0;
		RespondeRobo resposta = new RespondeRobo();

		if(rodada == 1 && jogadorMao == 1)
			description.setPrimeiraCartaHumano(primeiraCartaHumano);
		if(rodada == 2 && jogadorMao == 1)
			description.setSegundaCartaHumano(segundaCartaHumano);
		if(rodada == 1 && jogadorMao == 1)
			description.setTerceiraCartaHumano(terceiraCartaHumano);

		try {
			chamarTruco = resposta.chamaTruco(description, rodada);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chamarTruco;
	}

	public Integer respostaChamarRetruco(	int Alta, int Media, int Baixa, 
											int jogadorMao, int primeiraCartaHumano, int segundaCartaHumano, 
											int terceiraCartaHumano, 
											int rodada) {
		
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int chamarRetruco = 0;
		RespondeRobo resposta = new RespondeRobo();

		if(rodada == 1 && jogadorMao == 1)
			description.setPrimeiraCartaHumano(primeiraCartaHumano);
		if(rodada == 2 && jogadorMao == 1)
			description.setSegundaCartaHumano(segundaCartaHumano);
		if(rodada == 1 && jogadorMao == 1)
			description.setTerceiraCartaHumano(terceiraCartaHumano);

		try {
			chamarRetruco = resposta.chamaRetruco(description, rodada);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chamarRetruco;
	}

	public Integer respostaChamarVale4(		int Alta, int Media, int Baixa, 
											int jogadorMao, int primeiraCartaHumano, 
											int segundaCartaHumano, int terceiraCartaHumano, int rodada) {
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int chamarVale4 = 0;
		RespondeRobo resposta = new RespondeRobo();

		if(rodada == 1 && jogadorMao == 1)
			description.setPrimeiraCartaHumano(primeiraCartaHumano);
		if(rodada == 2 && jogadorMao == 1)
			description.setSegundaCartaHumano(segundaCartaHumano);
		if(rodada == 1 && jogadorMao == 1)
			description.setTerceiraCartaHumano(terceiraCartaHumano);

		try {
			chamarVale4 = resposta.chamaVale4(description, rodada);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chamarVale4;
	}
	
	public Integer respostaAceitarTruco(	int Alta, int Media, int Baixa, 
											int jogadorMao, int primeiraCartaHumano, 
											int segundaCartaHumano, int terceiraCartaHumano,
											int rodada) {
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int aceitarTruco = 0;
		RespondeRobo resposta = new RespondeRobo();

		if(rodada == 1 && jogadorMao == 1)
			description.setPrimeiraCartaHumano(primeiraCartaHumano);
		if(rodada == 2 && jogadorMao == 1)
			description.setSegundaCartaHumano(segundaCartaHumano);
		if(rodada == 1 && jogadorMao == 1)
			description.setTerceiraCartaHumano(terceiraCartaHumano);

		try {
			aceitarTruco = resposta.aceitaTruco(description, rodada);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aceitarTruco;
	}

	public Integer respostaAceitarRetruco(	int Alta, int Media, int Baixa, 
											int jogadorMao, int primeiraCartaHumano, 
											int segundaCartaHumano, int terceiraCartaHumano,
											int rodada) {
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int aceitarRetruco = 0;
		RespondeRobo resposta = new RespondeRobo();

		if(rodada == 1 && jogadorMao == 1)
			description.setPrimeiraCartaHumano(primeiraCartaHumano);
		if(rodada == 2 && jogadorMao == 1)
			description.setSegundaCartaHumano(segundaCartaHumano);
		if(rodada == 1 && jogadorMao == 1)
			description.setTerceiraCartaHumano(terceiraCartaHumano);

		try {
			aceitarRetruco = resposta.aceitaRetruco(description, rodada);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aceitarRetruco;
	}

	public Integer respostaAceitarVale4(	int Alta, int Media, int Baixa, 
											int jogadorMao, int primeiraCartaHumano, 
											int segundaCartaHumano, int terceiraCartaHumano,
											int rodada) {
		description.setCartaAltaRobo(Alta);
		description.setCartaMediaRobo(Media);
		description.setCartaBaixaRobo(Baixa);
		int aceitarVale4= 0;
		RespondeRobo resposta = new RespondeRobo();

		if(rodada == 1 && jogadorMao == 1)
			description.setPrimeiraCartaHumano(primeiraCartaHumano);
		if(rodada == 2 && jogadorMao == 1)
			description.setSegundaCartaHumano(segundaCartaHumano);
		if(rodada == 1 && jogadorMao == 1)
			description.setTerceiraCartaHumano(terceiraCartaHumano);

		try {
			aceitarVale4 = resposta.aceitaVale4(description, rodada);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aceitarVale4;
	}

// -----------------------------------------------------------------------------------------------------------------------------//
// -------------------------------------------------------ENVIDO----------------------------------------------------------------//
	
	public Integer respostaChamarEnvido(int jogadorMao, int pontosEnvidoRobo) {

		description.setJogadorMao(jogadorMao);
		description.setPontosEnvidoRobo(pontosEnvidoRobo);
		int chamarEnvido=1;
		RespondeRobo resposta = new RespondeRobo();

		try {
			chamarEnvido = resposta.chamaEnvido(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chamarEnvido;
	}

	public Integer respostaChamarRealEnvido(int jogadorMao, int pontosEnvidoRobo) {

		description.setJogadorMao(jogadorMao);
		description.setPontosEnvidoRobo(pontosEnvidoRobo);
		int chamarRealEnvido=1;
		RespondeRobo resposta = new RespondeRobo();

		try {
			chamarRealEnvido = resposta.chamaRealEnvido(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chamarRealEnvido;
	}

	public Integer respostaChamarFaltaEnvido(int jogadorMao, int pontosEnvidoRobo) {

		description.setJogadorMao(jogadorMao);
		description.setPontosEnvidoRobo(pontosEnvidoRobo);
		int chamarFaltaEnvido=1;
		RespondeRobo resposta = new RespondeRobo();

		try {
			chamarFaltaEnvido = resposta.chamaFaltaEnvido(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chamarFaltaEnvido;
	}

	public Integer respostaAceitarEnvido(int jogadorMao, int pontosEnvidoRobo) {

		description.setJogadorMao(jogadorMao);
		description.setPontosEnvidoRobo(pontosEnvidoRobo);
		int aceitarEnvido=1;
		RespondeRobo resposta = new RespondeRobo();

		try {
			aceitarEnvido = resposta.aceitaEnvido(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aceitarEnvido;
	}

	public Integer respostaAceitarRealEnvido(int jogadorMao, int pontosEnvidoRobo) {

		description.setJogadorMao(jogadorMao);
		description.setPontosEnvidoRobo(pontosEnvidoRobo);
		int aceitarRealEnvido=1;
		RespondeRobo resposta = new RespondeRobo();

		try {
			aceitarRealEnvido = resposta.aceitaRealEnvido(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aceitarRealEnvido;
	}

	public Integer respostaAceitarFaltaEnvido(int jogadorMao, int pontosEnvidoRobo) {

		description.setJogadorMao(jogadorMao);
		description.setPontosEnvidoRobo(pontosEnvidoRobo);
		int aceitarFaltaEnvido=1;
		RespondeRobo resposta = new RespondeRobo();

		try {
			aceitarFaltaEnvido = resposta.aceitaFaltaEnvido(description, 1);
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aceitarFaltaEnvido;
	}
	
}


