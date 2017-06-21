package exercise_knn;

import java.util.List;

public class KnnCall {
	private  void caculatResult(List<Person> forecastOriginDt,int j){
		int size = forecastOriginDt.size();
		int count = 0;
		for(int i = 0 ; i<size;i++){
			Person person = forecastOriginDt.get(i);
			if(equal(person.getPredict(), person.getSurvived())){
				count++;
			}
		}
		System.out.println("key value : "+ j + "  forecast result is :" + (count/(float)size));
//		System.out.println(count/(float)size);
		
	}
	private  boolean equal(float a, float b){
		if((a-b>-0.000001)&&(a-b)<0.000001)
			return true;
		else {
			return false;
		}
	}
	
	public void knnCaclulate(List<Person> exerciseDt, List<Person> forecastDt){
		KnnAgorithem kAgorithem= new KnnAgorithem();
		for(int i = 1 ; i<=20;i++){
			kAgorithem.setKey(i);
			kAgorithem.getForecastResult(exerciseDt, forecastDt);			
			caculatResult(forecastDt,i);
			i++;
		}
	}
}
