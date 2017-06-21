package exercise_knn;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author 吴滔
 *
 */
public class KnnAgorithem {
	private static final float SURVIVED = (float)1.0;
	private static final float NOTSURVIVED = (float)-1.0;
	private static final float CLASS_MAX = (float) 0.965;
	private static final float CLASS_MIN = (float) -1.87;
	private static final float SEX_MAX = (float) 0.521;
	private static final float SEX_MIN = (float) -1.92;
	private static final float AGE_MAX = (float) 4.38;
	private static final float AGE_MIN = (float) -0.228;
	private int key = 5;
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	private List<Person> findPriorityQueue(List<Person> exerciseData, Person person){
			PriorityQueue<Person> result= new PriorityQueue<>();
			for(Person e: exerciseData){
				e.setDistance(getDistance(person, e));
				result.add(e);
			}
			
			List<Person> minCollection= new ArrayList<>();
			for(int i=0;i<this.getKey();i++){
				minCollection.add(result.poll());
			}
		return minCollection;
	}
	
	private float getDistance(Person p1, Person p2){
		float ageDifference = (float) ((p1.getAge()-p2.getAge())/(AGE_MAX-AGE_MIN));
		float sexDifference = (float) ((p1.getSex()-p2.getSex())/(SEX_MAX-SEX_MIN));
		float classDifference = (float) ((p1.getClassReal()-p2.getClassReal())/(CLASS_MAX-CLASS_MIN));
		float distance = (float) (Math.pow(ageDifference, 2)+Math.pow(sexDifference, 2)+Math.pow(classDifference, 2));
		return distance;
	}
	
	private void statisticsMost(List<Person>exerciseData,Person person){
		List<Person> result = findPriorityQueue(exerciseData, person);
		int countSurvived = 0;
		int countNotSurvived = 0;
		for(Person e: result){
			if(e.getSurvived()==SURVIVED)
				countSurvived++;
			else {
				countNotSurvived++;
			}
		}
		if(countSurvived>countNotSurvived)
			person.setPredict(SURVIVED);
		else 
			person.setPredict(NOTSURVIVED);
	}
	
	public List<Person> getForecastResult(List<Person> exerciseDt, List<Person> forecastDt){
		
		for(Person e:forecastDt){
			statisticsMost(exerciseDt, e);
		}
		return forecastDt;
	}
}
