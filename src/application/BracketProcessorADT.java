package application;

public interface BracketProcessorADT  {
    public Team[] seed();

    public void advanceRound(Team team1, Team team2, int round, int gameIndex); 
    
    public Team[] getData(int index);
}
