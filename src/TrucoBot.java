

public class TrucoBot {

    public static void main(String[] args) {
        Player Mario = new Player();
        Player Luigi = new Player();
        Mario.setNome("HUMANO");
        Luigi.setNome("BOT_Cbr");
        
        PlayingTruco JogoDeTruco = new PlayingTruco();
     
        JogoDeTruco.Match(Mario, Luigi);       

    }

}
