package assignment;

import java.util.GregorianCalendar;

import org.boehn.kmlframework.coordinates.EarthCoordinate;

public class SampleAlgo1 {

	private int index;
	private String macName;
	private String ssid;
	private int frequency;
	private double signal;
	private EarthCoordinate localisation;
	private GregorianCalendar date;

	/**
	 * Constructor.
	 * @param index
	 * @param macName
	 * @param ssid
	 * @param frequency
	 * @param signal
	 * @param localisation
	 * @param date
	 */
	public SampleAlgo1(int index, String macName, String ssid, int frequency, double signal, EarthCoordinate localisation,
			GregorianCalendar date) {
		this.index = index;
		this.macName = macName;
		this.ssid = ssid;
		this.frequency = frequency;
		this.signal = signal;
		this.localisation = localisation;
		this.date = date;
	}

	/**
	 * @return index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return macName.
	 */
	public String getMacName() {
		return macName;
	}

	/**
	 * @return ssid.
	 */
	public String getSsid() {
		return ssid;
	}

	/**
	 * @return frequency.
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @return signal.
	 */
	public double getSignal() {
		return signal;
	}
	
	/**
	 * @return localisation.
	 */
	public EarthCoordinate getLocalisation() {
		return localisation;
	}

	/**
	 * @return date.
	 */
	public GregorianCalendar getDate() {
		return date;
	}

}
