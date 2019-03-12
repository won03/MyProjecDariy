package vo;

import java.sql.Date;

public class DiaryVO {
	// diary 부분
	private int idx;
	private String title;
	private String content;
	private Date the_date;
	
	// member 부분 (혹시 나중에 diary와 member를 결합한 view 테이블을 만드실지 몰라서 일단 변수를 다 만들어두었습니다)
	private int member_idx;
	private String id;
	private String pw;
	private String name;
	
	public DiaryVO() {
		// TODO Auto-generated constructor stub
	}

	// diary_InsertAction 에서 사용
	public DiaryVO(String title, String content, Date the_date, int member_idx) {
		super();
		this.title = title;
		this.content = content;
		this.the_date = the_date;
		this.member_idx = member_idx;
	}

	// diary_UpdateAction 에서 사용
	public DiaryVO(int idx, String title, String content, Date the_date) {
		super();
		this.idx = idx;
		this.title = title;
		this.content = content;
		this.the_date = the_date;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getThe_date() {
		return the_date;
	}

	public void setThe_date(Date the_date) {
		this.the_date = the_date;
	}

	public int getMember_idx() {
		return member_idx;
	}

	public void setMember_idx(int member_idx) {
		this.member_idx = member_idx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
