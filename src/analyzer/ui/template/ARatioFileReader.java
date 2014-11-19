package analyzer.ui.template;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import util.annotations.Column;
import util.annotations.ComponentWidth;
import util.annotations.Row;

public class ARatioFileReader implements RatioFileReader {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	private RatioFeatures ratioFeatures = new ARatioFeatures();
	private String path = "";
	private JFileChooser fileChooser;

	@Row(1)
	@Column(0)
	@ComponentWidth(120)
	public void chooseFile() {
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fileChooser.showOpenDialog(fileChooser);
		File file = fileChooser.getSelectedFile();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = file.getAbsolutePath(); // finds the file path if a file is
											// chosen.
		}
		if (path != null && path != "") {
			propertyChangeSupport.firePropertyChange("file", "", path);
			readFile(path);
		}
	}

	@Row(1)
	@Column(1)
	@ComponentWidth(350)
	public String getPath() {
		return path;
	}

	public void readFile(String fileName) {
		BufferedReader br = null;
		String row = "";

		try {
			br = new BufferedReader(new FileReader(fileName));
			while ((row = br.readLine()) != null) {
				readRow(row);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void readRow(String row) {
		// TODO: fix oldRatioFeatures
		RatioFeatures oldRatioFeatures = new ARatioFeatures();
		String[] parts = row.split(",");
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy H:mm:ss");
		ratioFeatures.setInsertionRatio(Double.parseDouble(parts[1]));
		ratioFeatures.setDeletionRatio(Double.parseDouble(parts[2]));
		ratioFeatures.setDebugRatio(Double.parseDouble(parts[3]));
		ratioFeatures.setNavigationRatio(Double.parseDouble(parts[4]));
		ratioFeatures.setFocusRatio(Double.parseDouble(parts[5]));
		ratioFeatures.setRemoveRatio(Double.parseDouble(parts[6]));
		ratioFeatures.setPredictedStatus(Integer.parseInt(parts[7]));
		ratioFeatures.setActualStatus(Integer.parseInt(parts[8]));
		ratioFeatures.setDifficultyType(parts[9]);
		if (parts[10].equalsIgnoreCase(" null")) {
			ratioFeatures.setWebLinkList(null);
		} else {
			List<WebLink> list = new ArrayList<WebLink>();
			for (int i = 10; i < parts.length; i++) {
				String[] searchAndUrl = parts[i].split("\t");
				if (searchAndUrl.length > 1) {
					list.add(new AWebLink(searchAndUrl[0], searchAndUrl[1]));
				} else {
					// System.out.println(searchAndUrl[0]);
				}
			}
			ratioFeatures.setWebLinkList(list);
		}
		try {
			ratioFeatures.setSavedTimeStamp(format.parse(parts[0]).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		propertyChangeSupport.firePropertyChange("newRatioFeatures",
				oldRatioFeatures, ratioFeatures);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener aListener) {
		propertyChangeSupport.addPropertyChangeListener(aListener);
	}

}