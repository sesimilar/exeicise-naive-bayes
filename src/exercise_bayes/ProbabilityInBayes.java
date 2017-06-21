package exercise_bayes;

public class ProbabilityInBayes {
	private int  countLessSur ;
	private int  countLessNoS ;
	private int  countMoreSur ;
	private int  countMoreNoS ;
	private int  countSurvive ;
	private int  countNotSurvive ;
	
	private float threshold;
	
	private float pLessInSur ;
	private float pLessInNoS ;
	private float pMoreInSur ;
	private float pMoreInNoS ;
	private float pSurvived;
	private float pNotSurvived;
	
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public int getCountLessSur() {
		return countLessSur;
	}
	public void setCountLessSur(int countLessSur) {
		this.countLessSur = countLessSur;
	}
	public int getCountLessNoS() {
		return countLessNoS;
	}
	public void setCountLessNoS(int countLessNoS) {
		this.countLessNoS = countLessNoS;
	}
	public int getCountMoreSur() {
		return countMoreSur;
	}
	public void setCountMoreSur(int countMoreSur) {
		this.countMoreSur = countMoreSur;
	}
	public int getCountMoreNoS() {
		return countMoreNoS;
	}
	public void setCountMoreNoS(int countMoreNoS) {
		this.countMoreNoS = countMoreNoS;
	}
	public int getCountSurvive() {
		return countSurvive;
	}
	public void setCountSurvive(int countSurvive) {
		this.countSurvive = countSurvive;
	}
	public int getCountNotSurvive() {
		return countNotSurvive;
	}
	public void setCountNotSurvive(int countNotSurvive) {
		this.countNotSurvive = countNotSurvive;
	}
	
	
	public float getpLessInSur() {
		return this.countLessSur/(float)this.countSurvive;
	}

	public float getpLessInNoS() {
		return this.countLessNoS/(float)this.countNotSurvive;
	}

	public float getpMoreInSur() {
		return this.countMoreSur/(float)this.countSurvive;
	}
	
	public float getpMoreInNoS() {
		return this.countMoreNoS/(float)this.countNotSurvive;
	}
	public float getpSurvived() {
		return this.countSurvive/(float)(this.countSurvive+this.countNotSurvive);
	}
	public float getpNotSurvived() {
		return this.countNotSurvive/(float)(this.countSurvive+this.countNotSurvive);
	}
	
	
}
