package de.dominik_geyer.jtimesched.project;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class ProjectFileStorage {
	
	final private String fileName;
	
	private final static Logger LOG = Logger.getLogger(ProjectFileStorage.class.getName());
	
	private final Lock lock = new ReentrantLock();
	
	public ProjectFileStorage(String fileName) {
		this.fileName = fileName;
	}
	
	public void store(List<Project> projects){
		lock.lock();
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)))){
			for (Project project : projects) {
				oos.writeObject(project);
			}
			
		}catch(IOException e){
			LOG.severe(e.toString());
		}
		finally{
			lock.unlock();
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Project> load() throws FileNotFoundException, IOException, ClassNotFoundException{
		ArrayList<Project> result = new ArrayList<Project>();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)))){
			while(true){
				result.add((Project) ois.readObject());
			}
		}
		catch (EOFException eof) {
			return result;
		}
	}
}
