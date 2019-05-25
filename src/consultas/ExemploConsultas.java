package consultas;

import cbr.Adaptacoes.RespondeRobo;
import cbr.cbrDescriptions.TrucoDescription;
import jcolibri.exception.ExecutionException;

public class ExemploConsultas {
 RespondeRobo respondeRobo = new RespondeRobo();
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
}


