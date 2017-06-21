package exercise_bayes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import exercise_knn.Person;

public class BeyesFunction {
	private static final float SURVIVED = (float)1.0;
	private static final float NOTSURVIVED = (float)-1.0;
	
	public float beyesFunctionKernel(List<Person> persons ,List<Person> forecastPerson){
		ProbabilityInBayes classPib = new ProbabilityInBayes();
		ProbabilityInBayes agePib = new ProbabilityInBayes();
		ProbabilityInBayes sexPib = new ProbabilityInBayes();
		HashMap<Float, Integer> classStatitics = new HashMap<>();
		HashMap<Float, Integer> agetatitics = new HashMap<>();
		HashMap<Float, Integer> sexStatitics = new HashMap<>();
		HashMap<Float, Integer> surrivedStatitics = new HashMap<>();
		List<HashMap<Float, Integer>> countNumber = new ArrayList<>();
		
		countNumber.add(classStatitics);
		countNumber.add(agetatitics);
		countNumber.add(sexStatitics);
		countNumber.add(surrivedStatitics);
		
		statiticsDifferent(countNumber, persons);
		classPib.setCountNotSurvive(surrivedStatitics.get(NOTSURVIVED));
		classPib.setCountSurvive(surrivedStatitics.get(SURVIVED));		
		agePib.setCountNotSurvive(surrivedStatitics.get(NOTSURVIVED));
		agePib.setCountSurvive(surrivedStatitics.get(SURVIVED));		
		sexPib.setCountNotSurvive(surrivedStatitics.get(NOTSURVIVED));
		sexPib.setCountSurvive(surrivedStatitics.get(SURVIVED));
		
		for(int i = 0 ; i<3;i++){
			ProbabilityInBayes pib = new ProbabilityInBayes();
			float min = 2.0f;
			float threshold = 0.0f;
			List<Float> result = new ArrayList<>();
			HashMap<Float, Integer> map = countNumber.get(i);
			List<Float> list = new ArrayList<>();
			list.addAll(map.keySet());
			Collections.sort(list, new Comparator<Float>() {

				@Override
				public int compare(Float o1, Float o2) {
					// TODO Auto-generated method stub
					return o1.compareTo(o2);
				}
			});
			for(int j=0;j<list.size();j++){
				float e = list.get(j);
				float eoi = caculateEOI(persons, e,  i, pib);
				if(eoi<min){
					min = eoi;
					threshold = e;
					if(i == 0){
						classPib.setCountLessNoS(pib.getCountLessNoS());
						classPib.setCountLessSur(pib.getCountLessSur());
						classPib.setCountMoreNoS(pib.getCountMoreNoS());
						classPib.setCountMoreSur(pib.getCountMoreSur());
						classPib.setThreshold(threshold);
					}else if( i==1){
						agePib.setCountLessNoS(pib.getCountLessNoS());
						agePib.setCountLessSur(pib.getCountLessSur());
						agePib.setCountMoreNoS(pib.getCountMoreNoS());
						agePib.setCountMoreSur(pib.getCountMoreSur());
						agePib.setThreshold(threshold);
					}else{
						sexPib.setCountLessNoS(pib.getCountLessNoS());
						sexPib.setCountLessSur(pib.getCountLessSur());
						sexPib.setCountMoreNoS(pib.getCountMoreNoS());
						sexPib.setCountMoreSur(pib.getCountMoreSur());
						sexPib.setThreshold(threshold);
					}
				}		
			}
//			Collections.sort(result,new Comparator<Float>() {
//
//				@Override
//				public int compare(Float o1, Float o2) {
//					// TODO Auto-generated method stub
//					return o1.compareTo(o2);
//				}
//			});
//			
//			System.out.println("attribute: "+ i);
//			for(float e:result){
//				System.out.println(e);
//			}
		}
		
		forecastInBayes(forecastPerson, classPib, agePib, sexPib);
		return caculatResult(forecastPerson);
		
	}
	private float caculateEOI(List<Person> persons,float key,int attribute,ProbabilityInBayes pib){
		int countLessSur = 0;
		int countLessNoS = 0;
		int countMoreSur = 0;
		int countMoreNoS = 0;
		int countAll = 0;
		int countLessAll = 0;
		int countMoreAll = 0;
		float eoi = 0.0f;
		float pLessSur = 0.0f;
		float pLessNoS = 0.0f;
		float pMoreSur = 0.0f;
		float pMoreNoS = 0.0f;
		float pLess;
		float pMore;
		for(Person e:persons){
			if(noMoreThan(getValueBaseAttribute(e, attribute),key)){
				if(equal(e.getSurvived(), SURVIVED)){
					countLessSur++;
				}
				else {
					countLessNoS++;
				}
			}else{
				if(equal(e.getSurvived(), SURVIVED)){
					countMoreSur++;
				}else {
					countMoreNoS++;
				}
			}
		}
		pib.setCountLessNoS(countLessNoS);
		pib.setCountLessSur(countLessSur);
		pib.setCountMoreNoS(countMoreNoS);
		pib.setCountMoreSur(countMoreSur);
		
		countLessAll = countLessSur+countLessNoS;
		countMoreAll = countMoreSur+countMoreNoS;
		countAll = countLessAll+countMoreAll;
		if(countLessAll==0&&countMoreAll==0){
			return 0.0f;
		}
		else if(countLessAll!=0&&countMoreAll!=0){
			pLessSur = countLessSur/(float) countLessAll;
			pLessNoS = countLessNoS/(float) countLessAll;
			pMoreSur = countMoreSur /(float) countMoreAll;
			pMoreNoS = countMoreNoS /(float) countMoreAll;
		}else if(countLessAll==0){
			pLessSur = 0;
			pLessNoS = 0;
			pMoreSur = countMoreSur /(float) countMoreAll;
			pMoreNoS = countMoreNoS /(float) countMoreAll;
		}else {
			pLessSur = countLessSur/(float) countLessAll;
			pLessNoS = countLessNoS/(float) countLessAll;
			pMoreSur = 0;
			pMoreNoS = 0;
		}
		pLess = countLessAll/(float)countAll;
		pMore = countMoreAll/(float)countAll;
		
		eoi = pLess*(-(pLessSur*log2(pLessSur)+pLessNoS*log2(pLessNoS)))+pMore*(-(pMoreSur*log2(pMoreSur)+pMoreNoS*log2(pMoreNoS)));
		return eoi;
	}

	private void forecastInBayes(List<Person> forecasrPerson,
			ProbabilityInBayes classPib,
			ProbabilityInBayes agePib,
			ProbabilityInBayes sexPib){
			float pSurvived;
			float pNotSurvived;
		for(Person person:forecasrPerson){
			pSurvived = classPib.getpSurvived()
					*caculateSurProbabilityOfClass(classPib, person.getClassReal())
					*caculateSurProbabilityOfAge(agePib, person.getAge())
					*caculateSurProbabilityOfSex(sexPib, person.getSex());
			pNotSurvived = classPib.getpNotSurvived()
					*caculateNotSurProbabilityOfClass(classPib, person.getClassReal())
					*caculateNotSurProbabilityOfAge(agePib, person.getAge())
					*caculateNotSurProbabilityOfSex(sexPib, person.getSex());
			if(noMoreThan(pSurvived, pNotSurvived)){
				person.setPredict(NOTSURVIVED);
			}else{
				person.setPredict(SURVIVED);
			}
		}
		
	}
	private float caculateSurProbabilityOfClass(ProbabilityInBayes classPib,float key){
		float result;
		if(noMoreThan(key, classPib.getThreshold())){
			return result=classPib.getpLessInSur();
		}else {
			return result = classPib.getpMoreInSur();
		}
	}
	private float caculateNotSurProbabilityOfClass(ProbabilityInBayes classPib,float key){
		float result;
		if(noMoreThan(key, classPib.getThreshold())){
			return result = classPib.getpLessInNoS();
		}else {
			return result = classPib.getpMoreInNoS();
		}
	}
	private float caculateSurProbabilityOfAge(ProbabilityInBayes agePib,float key){
		float result;
		if(noMoreThan(key, agePib.getThreshold())){
			return result = agePib.getpLessInSur();
		}else {
			return result = agePib.getpMoreInSur();
		}
	}
	private float caculateNotSurProbabilityOfAge(ProbabilityInBayes agePib,float key){
		float result;
		if(noMoreThan(key, agePib.getThreshold())){
			return result = agePib.getpLessInNoS();
		}else {
			return result = agePib.getpMoreInNoS();
		}
	}
	private float caculateSurProbabilityOfSex(ProbabilityInBayes sexPib,float key){
		float result;
		if(noMoreThan(key, sexPib.getThreshold())){
			return result = sexPib.getpLessInSur();
		}else {
			return result = sexPib.getpMoreInSur();
		}
	}
	private float caculateNotSurProbabilityOfSex(ProbabilityInBayes sexPib,float key){
		float result;
		if(noMoreThan(key, sexPib.getThreshold())){
			return result = sexPib.getpLessInNoS();
		}else {
			return result = sexPib.getpMoreInNoS();
		}
	}
	private void statiticsDifferent(List<HashMap<Float, Integer>> countNumber,List<Person> persons){
		
		for(Person e:persons){
			int countClass = 1;
			int countAge = 1;
			int countSex = 1;
			int countSurrived = 1;
			float classReal = e.getClassReal();
			float age = e.getAge();
			float sex = e.getSex();
			float surrived = e.getSurvived();
			HashMap<Float, Integer> classStatitics = countNumber.get(0);
			HashMap<Float, Integer> agetatitics = countNumber.get(1);
			HashMap<Float, Integer> sexStatitics = countNumber.get(2);
			HashMap<Float, Integer> surrivedStatitics = countNumber.get(3);
			if(classStatitics.containsKey(classReal)){
					countClass = classStatitics.get(classReal)+1;	
			}
			classStatitics.put(classReal, countClass);
			
			if(agetatitics.containsKey(age)){
				countAge = agetatitics.get(age)+1;
			}
			agetatitics.put(age, countAge);
			
			if(sexStatitics.containsKey(sex)){
				countSex= sexStatitics.get(sex)+1;
			}
			sexStatitics.put(sex,countSex);	
			
			if(surrivedStatitics.containsKey(surrived)){
				countSurrived = surrivedStatitics.get(surrived)+1;
			}
			surrivedStatitics.put(surrived, countSurrived);
		}
	}
	
	private float getValueBaseAttribute(Person person,int attribute){
		float result;
		switch (attribute) {
		case 0:
			 result = person.getClassReal();
			break;
		case 1:
			result = person.getAge();
			break;
		case 2:
			result = person.getSex();
			break;
			
		default:
			
			result = 0.0f;
			break;
		}
		return result;
	}
	
	private  boolean equal(float a, float b){
		if((a-b>-0.000001)&&(a-b)<0.000001)
			return true;
		else {
			return false;
		}
	}
	
	private boolean noMoreThan(float a, float b){
		if((a-b)<0.000001)
			return true;
		else {
			return false;
		}
	}
	
	private float log2(float a){
		if(equal(a, 0.0f)){
			return 0.0f;
		}
		return (float)(Math.log(a)/Math.log(2.0));
	}
	
	private  float caculatResult(List<Person> forecastOriginDt){
		float rate = 0.0f;
		int size = forecastOriginDt.size();
		int count = 0;
		for(int i = 0 ; i<size;i++){
			Person person = forecastOriginDt.get(i);
			if(equal(person.getPredict(), person.getSurvived())){
				count++;
			}
		}
//		System.out.println(" Accuracy Rate is :" + (count/(float)size));
		rate = count/(float)size;
	return	rate;
	}
}
