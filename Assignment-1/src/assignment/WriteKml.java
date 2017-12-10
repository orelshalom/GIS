package assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.ExtendedData;
import org.boehn.kmlframework.kml.IconStyle;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.KmlException;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.SimpleData;
import org.boehn.kmlframework.kml.Style;
import org.boehn.kmlframework.kml.TimeStamp;

/**
 * This absract class write a kml file.
 * This class use the API kmlframework @see {@link https://code.google.com/archive/p/kmlframework/source/default/source}.
 * This class implements @see {@link WriteFile}.
 * @see NOTICE for more informations about how to run with the api.
 * @author Orel and Samuel.
 */
public abstract class WriteKml implements WriteFile {

	private Document document = new Document();
	
	/**
	 * Initialisation of the kml file, we need to write the links with the icons.
	 */
	public void initialize() {
		addIcon("red");
		addIcon("ylw");
		addIcon("grn");
	}

	/**
	 * abstract method, we define it in the other classes.
	 */
	public abstract void checkData(ArrayList<Scan> array, String fileNameExport);

	
	/**
	 * This method construcs the placemark.
	 */
	public void addNetwork(Scan scan) {
		for (Wifi wifi : scan.getArrayStrongerWifi()) {
			if (wifi.getName().contains("&")) wifi.setName(wifi.getName().replaceAll("&", "and"));
			Placemark placemark = new Placemark(wifi.getName());
			TimeStamp time = new TimeStamp(timeInput(scan.getTime()));
			placemark.setTimePrimitive(time);
			placemark.setExtendedData(extendedData(scan, wifi));
			placemark.setLocation(scan.getPointLocation().getLongitude(), scan.getPointLocation().getLatitude());
			placemark.setStyleUrl(color(wifi.getSignal()));
			document.addFeature(placemark);
		}
	}

	/**
	 * This method create the kml file.
	 * @exception IOException | {@link KmlException} : Error writing the file.
	 */
	public void createFile(String fileNameExport) throws InputException {
		Kml kml = new Kml();
		try {
			document.getFeatures().equals(null);
		}
		catch (NullPointerException ex ) {
			throw new InputException("There is no placemark in the document.");
		}
		kml.setFeature(document);
		try {
			kml.createKml(fileNameExport);
		}
		catch(IOException | KmlException ex) {
			System.out.println("Error writing the file." + ex);
		}
	}

	// unimplemented private methods.
	
	/**
	 * This method generate the data to input a new icon.
	 * @param color
	 */
	private void addIcon(String color) {
		Style style = new Style();
		style.setId(color);
		IconStyle iconStyle = new IconStyle();
		iconStyle.setColor(color);
		iconStyle.setIconHref("http://maps.google.com/mapfiles/kml/paddle/" + color + "-stars.png");
		style.setIconStyle(iconStyle);
		document.addStyleSelector(style);
	}

	/**
	 * This method generate the data.
	 * @param scan.
	 * @return the extended data.
	 */
	private ExtendedData extendedData(Scan scan, Wifi wifi) {
		ArrayList<SimpleData> array = new ArrayList<SimpleData>();
		array.add(simpleData("Mac", wifi.getMac()));
		array.add(simpleData("Frequency", Integer.toString(wifi.getFrequency())));
		array.add(simpleData("Date", scan.getTime().getTime().toString()));
		array.add(simpleData("Signal", Integer.toString(wifi.getSignal())));
		array.add(simpleData("Id", scan.getId()));
		ExtendedData extendedData = new ExtendedData();
		extendedData.setSimpleDataElements(array);
		return extendedData;
	}

	/**
	 * @param name.
	 * @param value.
	 * @return simple data.
	 */
	private SimpleData simpleData(String name, String value) {
		SimpleData data = new SimpleData();
		data.setName(name);
		data.setValue(value);
		return data;
	}

	/**
	 * if signal > - 70 green icon, if signal > -90 yellow icon, else red icon.
	 * @param signal.
	 * @return the color.
	 */
	private String color(int signal) {
		if (signal > - 70) return "#grn";
		else if (signal > -90) return "#ylw";
		else return "#red";
	}

	/**
	 * @param time.
	 * @return yyyy-mm-ddThh:mm:ssZ.
	 */
	private String timeInput(GregorianCalendar time) {
		return Integer.toString(time.get(Calendar.YEAR)) + "-" + dataChange((time.get(Calendar.MONTH))) + "-" + 
				dataChange((time.get(Calendar.DATE))) + "T" + dataChange((time.get(Calendar.HOUR_OF_DAY))) 
				+ ":" + dataChange((time.get(Calendar.MINUTE))) + ":" + dataChange((time.get(Calendar.SECOND))) + "Z";
	}

	/**
	 * @param data.
	 * @return data if the data is already with two digits.
	 * @return 0 + data if the data got only one digit.
	 */
	private String dataChange(int data) {
		if (data / 10 >= 1) return Integer.toString(data);
		else return "0" + Integer.toString(data);
	}
	
}