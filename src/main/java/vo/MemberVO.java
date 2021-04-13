package vo;

import org.springframework.web.multipart.MultipartFile;

public class MemberVO {
	private String id;
	private String password;
	private String name;
	private String lev;
	private String birthd;
	private int point;
	private double weight;
	private String rid; //  추천인
	private String uploadfile; //Table 에 저장된 image 파일값
	private MultipartFile uploadfilef ;
	// form 으로부터 Image 관련 정보를 받아 오기 위한 필드
	
	public MemberVO(){
		
	}
	
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getBirthd() {
		return birthd;
	}
	public void setBirthd(String birthd) {
		this.birthd = birthd;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile;
	}

	public MultipartFile getUploadfilef() {
		return uploadfilef;
	}

	public void setUploadfilef(MultipartFile uploadfilef) {
		this.uploadfilef = uploadfilef;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", password=" + password + ", name=" + name + ", lev=" + lev + ", birthd="
				+ birthd + ", point=" + point + ", weight=" + weight + ", rid=" + rid + ", uploadfile=" + uploadfile
				+ ", uploadfilef=" + uploadfilef + "]";
	}

}
