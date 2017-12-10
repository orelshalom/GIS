package assignment;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class extends @see {@link FilteringKml} and implements @see {@link Filtering}.
 * This class filtering the data by the id : only the scan with the id that the user choosed will appear in the kml place.
 * @author Orel and Samuel.
 */

public class FilteringKmlId extends FilteringKml implements Filtering {

	/**
	 * This method ask the user to input the id.
	 * @param arrayObject.
	 * @return {@link WriteKmlId}.
	 */
	@SuppressWarnings("resource")
	public WriteFile filteringBy(ArrayList<?> arrayObject) throws InputException {
		ArrayList<Scan> array = (ArrayList<Scan>) arrayObject;
		System.out.println("Input an Id please :");
		String id = new Scanner(System.in).nextLine();
		for(Scan scan : array) 
			if (id.equals(scan.getId())) 
				return new WriteKmlId(id);
			else throw new InputException("Couldn't find the id :" + id);
		return filteringBy(array);
	}

}
