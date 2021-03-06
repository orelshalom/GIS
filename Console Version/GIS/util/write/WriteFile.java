package write;

import java.util.ArrayList;

import libraries.InputException;

/**
 * This class interface defines for method : an abstract method receiveData, writeHeader,  writeFile and getFileName.
 * Then, heights classes implements this interface :
 * One abstract classe :  @see {@link WriteKml}.
 * WriteKml is extended by : @see {@link WriteKmlPlace}, @see {@link WriteKmlId}, @see {@link WriteKmlTime}, @see {@link WriteKmlWithoutFilter}.
 * Also @see {@link WriteCombo}, @see {@link WriteComboAlgo1}, and @see {@link WriteDifference} implement this interface.
 * 
 * The main goal of this interface is to write a file.
 * We are able to write a kml or a cvs file.
 * 
 * @author Orel and Samuel.
 * @param <t>
 */
public interface WriteFile <T> {
	
	public abstract void receiveData(ArrayList<T> array);
	public void writeHeader();
	public void writeFile() throws InputException;
	public String getFileName();
}
