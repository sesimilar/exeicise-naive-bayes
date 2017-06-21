package exercise_knn;

import java.util.ArrayList;
import java.util.List;

import exercise_bayes.BeyesFunction;

public class Main {
	public static void main(String[] args){
			int times = 20;
			List<Person> exerciseDt = new ArrayList<Person>();
			List<Person> forecastOriginDt = new ArrayList<Person>();
//			ReadFile readFile = new ReadFile();
//			readFile.readFile(exerciseDt, forecastOriginDt);
//			KnnCall kCall = new KnnCall();
//			kCall.knnCaclulate(exerciseDt, forecastOriginDt);
			BeyesFunction beyesFunction = new BeyesFunction();
			for(int i=1;i<=times;i++){
				ReadFile readFile = new ReadFile();
				readFile.readFile(exerciseDt, forecastOriginDt);
				System.out.println("time:"+i+" Accuracy Rate:"+beyesFunction.beyesFunctionKernel(exerciseDt,forecastOriginDt));
			}

	}
	
}
