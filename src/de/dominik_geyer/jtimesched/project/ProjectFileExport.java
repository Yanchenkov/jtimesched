package de.dominik_geyer.jtimesched.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.List;
import java.util.logging.Logger;

import de.dominik_geyer.jtimesched.project.ProjectTime;

public class ProjectFileExport {

	final private String fileName;

	private final static Logger LOG = Logger.getLogger(ProjectFileExport.class.getName());

	public ProjectFileExport(String fileName) {
		this.fileName = fileName;
	}

	public void storeToday(List<Project> projects) {

		try (FileWriter writer = new FileWriter(fileName);
      BufferedWriter bw = new BufferedWriter(writer)) {
				for (Project project : projects) {
					if (project.getSecondsToday() > 0) {
						String content = String.format("%s;%s",
								project.getTitle(),
								ProjectTime.formatSeconds(project.getSecondsToday()));
						bw.write(content);
						bw.newLine();
					}
				}
			} catch (IOException e) {
				LOG.severe(e.toString());
			}
	}
}
