package genericCheckpointing.fileProcessor;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileProcessor {
	private Scanner in = null;
	private BufferedWriter bw = null;
	private String fileName = null;
	private File f = null;

	public FileProcessor(String fileNameIn) throws Exception {
		this.fileName = fileNameIn;
	}

	/**
	 * @return next line of the input file
	 */
	
	public void openScanner(){
		try {
			closeWriter();
			in = new Scanner(new File(fileName));
		}
		catch (Exception e) {
			e.printStackTrace();
			closeInScanner();
		}
	}
	
	public void openWriter() {
		try {
			closeInScanner();
			bw = new BufferedWriter(new FileWriter(new File(fileName), true));
		}
		catch (Exception e) {
			e.printStackTrace();
			closeInScanner();
		}
		
	}
	
	public String readLine() throws FileNotFoundException {
		
		String line = "";
		try {
			if (in == null) {
                //throw new RuntimeException("File is closed!");
			}
			while (in.hasNextLine()) {
				line = in.nextLine();
				return line;
			}
			line = null;
			closeInScanner();
			return line;
		} catch (Exception e) {
			e.printStackTrace();
			closeInScanner();
			throw e;
		}
	}
	
	public void writeLine(String line) throws Exception{
		try{
			bw.write(line+"\n");
		}
		
		catch(FileNotFoundException e){
		    e.printStackTrace();
		    System.exit(0);
		}
    }

	public void closeInScanner() {
		if (in != null) {
			in.close();
			in = null;
		}
	}
	
	public void closeWriter() {
		try {
			if(bw != null) {
				bw.close();
				bw = null;
			}
		}
		catch(Exception e) {
			System.err.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
