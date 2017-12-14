package kml;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import filter.Filtering;
import library.InputException;
import library.OpenFile;
import library.ReadFolder;
import library.UserChoice;
import read.CsvFile;
import read.ReadCombo;
import read.ReadCsv;
import read.ReadFile;
import read.ReadWigleWifi;
import read.SampleScan;
import read.SortWigleWifiTime;
import read.WigleWifiLine;
import write.WriteCombo;
import write.WriteFile;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		//Get workspace path
		String folderPathWorkspace = new File(".").getAbsolutePath();

		//Initialize the array
		ArrayList<CsvFile> arrayCsv = new ArrayList<CsvFile>();
		ArrayList<SampleScan> arrayScan =  new ArrayList<SampleScan>();

		//  ==============================
		//  = BEGINNING OF THE ALGORITHM =
		//  ==============================

		///////////////////////////////////////
		//First part : Writting the csv file.//
		///////////////////////////////////////

		//Get folder path
		System.out.println("Input the name of the folder please : ");
		String folderName = new Scanner(System.in).nextLine();
		String folderPath = folderPathWorkspace.substring(0, folderPathWorkspace.length() - 1);

		//Read the csv file
		File[] listOfFile = ReadFolder.read(folderName);
		ReadFile<WigleWifiLine> read;
		for (File file : listOfFile) {
			read = new ReadWigleWifi(folderPath, arrayCsv, file);
			read.readBuffer();
		}

		//Sort Csv (time)
		SortWigleWifiTime sortScan = new SortWigleWifiTime();
		arrayScan = sortScan.sortBy(arrayCsv);

		//Write Csv
		System.out.println("Input a name for the csv file you want to create : ");
		String fileNameCombo = new Scanner(System.in).nextLine();
		WriteFile<SampleScan> write = new WriteCombo(fileNameCombo);
		write.receiveData(arrayScan);

		//Open the file
		OpenFile.open(fileNameCombo + ".csv");

		////////////////////////////////////////
		//Second part : Writting the kml file.//
		////////////////////////////////////////

		//Read the combo
		File combo = new File(fileNameCombo);
		arrayScan = new ArrayList<SampleScan>();
		ReadCsv<SampleScan> readCombo = new ReadCombo(folderPath, arrayScan, combo);
		readCombo.readBuffer();

		//Choice of the user
		Filtering<SampleScan, SampleScan> filter = UserChoice.userChoice();

		//Filtering kml
		try {
			write = filter.filteringBy(arrayScan);
		} 
		catch (InputException e) {
			e.printStackTrace();
		}

		//Write kml
		write.receiveData(arrayScan);

		//Open the file
		OpenFile.open(write.getFileName());
	}
}
