package vo;

//VO: Value Object
//DTO : Data Transfer Object

public class StudentVO {

	private int snum;
	private String sname;
	private String sex;
	private int age;
	private String birthday;
	private String email;
	private String note;
	private int gnum;
	private String regdate;


	public StudentVO(){

	}

	public StudentVO(int snum, String sname, String sex, int age, String birthday, String email, String note, int gnum,
			String regdate) {
		super();
		this.snum = snum;
		this.sname = sname;
		this.sex = sex;
		this.age = age;
		this.birthday = birthday;
		this.email = email;
		this.note = note;
		this.gnum = gnum;
		this.regdate = regdate;
	}

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getGnum() {
		return gnum;
	}

	public void setGnum(int gnum) {
		this.gnum = gnum;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "StudentVO [snum=" + snum + ", sname=" + sname + ", sex=" + sex + ", age=" + age + ", birthday="
				+ birthday + ", email=" + email + ", note=" + note + ", gnum=" + gnum + ", regdate=" + regdate + "]";
	}


}