package application;

public class Student {
	
	private String name;
	private int korea;
	private int math;
	private int eng;
	
	public Student() {}

	public Student(String name, int korea, int math, int eng) {
		this.name = name;
		this.korea = korea;
		this.math = math;
		this.eng = eng;
	}

	
	@Override
	public String toString() {
		return "Student [name=" + name + ", korea=" + korea + ", math=" + math + ", eng=" + eng + "]";
	}
	
}
