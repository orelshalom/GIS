package assignment;

import java.util.ArrayList;
import java.util.Collections;

import org.boehn.kmlframework.coordinates.EarthCoordinate;

public class SortCsvMac extends SortCsv {

	/**
	 * Empty constructor.
	 */
	public SortCsvMac() {}

	@Override
	public ArrayList<Mac> sortBy(ArrayList<CsvFile> arrayCsv) {
		ArrayList<Mac> array = new ArrayList<Mac>();
		for (CsvFile csvFile : arrayCsv) Collections.sort(csvFile.getLine());
		for (CsvFile csvFile : arrayCsv) {
			for (Line line : csvFile.getLine()) { 
				if (array.size() != 0 && needToCreateObject(line.getMac(), array.get(array.size() - 1))) { 
					if (line.getType().equals("WIFI")) array.get(array.size() - 1).getArrayMacLocation().add((MacLocation)addObject(line));
				}
				else if (!line.getFirstseen().contains("1970") && line.getType().equals("WIFI")) array.add((Mac) addMotherObject(line));
			}
		}
		for (Mac mac: array) mac.sort();
		return array;
	}

	public boolean needToCreateObject(String mac, Object object) {
		Mac macLocation = (Mac) object;
		return macLocation.getMacName().equals(mac);
	}

	public Object addMotherObject(Line line) {
		ArrayList<MacLocation> array = new ArrayList<MacLocation>();
		if (line.getType().equals("WIFI")) array.add((MacLocation) addObject(line));
		return new Mac(
				line.getMac(), 
				array);
	}

	public Object addObject(Line line) {
		return new MacLocation(
				new EarthCoordinate(Double.parseDouble(line.getCurrentLongitude()),
						Double.parseDouble(line.getCurrentLatitude()), 
						Double.parseDouble(line.getAltitudeMeters())), 
				channelToFrequency(Integer.parseInt(line.getChannel()))
				);
	}

}