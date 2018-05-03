package application;

<<<<<<< HEAD
import javafx.scene.layout.HBox;

public interface BracketProcessorADT {
=======
public interface BracketProcessorADT  {
>>>>>>> 93834a57c2d07657dd39799649eb0f05d77895d3
    public Team[] seed();

    public void advanceRound(Team team1, Team team2, int round, int gameIndex); 
    
<<<<<<< HEAD
    public Team[] getData(int index);
=======
    public Match[] getData(int index);
>>>>>>> 93834a57c2d07657dd39799649eb0f05d77895d3
    
}
