package application;

public interface BracketProcessorADT {
    public Team[] seed();

    public Team[] advanceRound();

    public Team[] getData();
}
