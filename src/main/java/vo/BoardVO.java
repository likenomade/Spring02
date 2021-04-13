package vo;

public class BoardVO {
	private int seq;
	private String title;
	private String id;
	private String content;
	private String regdate;
	private int cnt;
	private int root;
	private int step;
	private int indent;
	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getRoot() {
		return root;
	}
	public void setRoot(int root) {
		this.root = root;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	
	
	@Override
	public String toString() {
		return "BoardVO [seq=" + seq + ", title=" + title + ", id=" + id + ", content=" + content + ", regdate="
				+ regdate + ", cnt=" + cnt + ", root=" + root + ", step=" + step + ", indent=" + indent + "]";
	}
	
	
	
}//class
