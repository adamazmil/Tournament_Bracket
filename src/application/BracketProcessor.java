package application;

public class BracketProcessor implements BracketProcessorADT {
    private Team[][] teamRounds;
   

    public Team[] seed() {
        return null;
    }

    public int advanceRound(Team team1, Team team2, int round, int gameIndex) {
    	Team winner = (team1.getScore() > team2.getScore()) ? team1 : team2;
    	int winnerPosition = gameIndex / 2;
    	teamRounds[round + 1][winnerPosition] = winner;
    	return winnerPosition;
    }

    public Team[] getData() {
        return null;
    }

}
