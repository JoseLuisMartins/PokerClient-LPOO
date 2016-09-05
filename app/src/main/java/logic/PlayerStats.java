package logic;


import java.io.Serializable;

public class PlayerStats implements Serializable{

    private static final long serialVersionUID = 1L;

    public int numberOfAllin;
    public int numberOfWins;
    public int numberOfLoses;
    public int maxPotWin;
    public int minPotWin;
    public int playerMoney;
    public String name;
    public boolean isAccount;

    public PlayerStats() {
        name="";
        playerMoney=0;
        isAccount = false;
        resetStats();
    }

    public void resetStats(){
        numberOfAllin=0;
        numberOfWins=0;
        numberOfLoses=0;
        maxPotWin=0;
        minPotWin=0;

    }
}
