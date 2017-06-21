package exercise_knn;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ReadFile {
	private String fileName  = "titanic.dat";
	
	public void readFile(List<Person> exerciseDt, List<Person> forecastDt){
		List<Person> data = new ArrayList<Person>();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(fileName);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String string = "";
		try {
			while ((string=bufferedReader.readLine())!=null){
				if(!string.startsWith("@")){
					StringTokenizer tokenizer = new StringTokenizer(string, ",");
						Person person = new Person();
						person.setClassReal(Float.parseFloat(tokenizer.nextToken()));
						person.setAge(Float.parseFloat(tokenizer.nextToken()));
						person.setSex(Float.parseFloat(tokenizer.nextToken()));
						person.setSurvived(Float.parseFloat(tokenizer.nextToken()));
						data.add(person);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int size = data.size();
		int exerciseSize = size/10*7;
		int forecastSize = size-exerciseSize;
		
		//randomly disorganize list data 
		Collections.shuffle(data);
		
		exerciseDt.addAll(data.subList(0, exerciseSize));
		forecastDt.addAll(data.subList(exerciseSize, size));
	}
	
}
