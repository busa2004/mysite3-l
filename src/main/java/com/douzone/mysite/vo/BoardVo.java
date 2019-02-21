package com.douzone.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String contents;
	private String writeDate;
	private int hit;
	private int gNo;
	private int oNo;
	private int depth;
	private long userNo;
	private String userName;
	private String fileName;
	private String removeCheck;
	//
	private String kwd;
	private Integer page;
	private Integer movePage;
	private Integer move;
	private Integer start;
	private Integer end;
	private String search;
	
	
	
	public Long getNo() {
		return no;
	}



	public void setNo(Long no) {
		this.no = no;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContents() {
		return contents;
	}



	public void setContents(String contents) {
		this.contents = contents;
	}



	public String getWriteDate() {
		return writeDate;
	}



	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}



	public int getHit() {
		return hit;
	}



	public void setHit(int hit) {
		this.hit = hit;
	}



	public int getgNo() {
		return gNo;
	}



	public void setgNo(int gNo) {
		this.gNo = gNo;
	}



	public int getoNo() {
		return oNo;
	}



	public void setoNo(int oNo) {
		this.oNo = oNo;
	}



	public int getDepth() {
		return depth;
	}



	public void setDepth(int depth) {
		this.depth = depth;
	}



	public long getUserNo() {
		return userNo;
	}



	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getRemoveCheck() {
		return removeCheck;
	}



	public void setRemoveCheck(String removeCheck) {
		this.removeCheck = removeCheck;
	}



	public String getKwd() {
		return kwd;
	}



	public void setKwd(String kwd) {
		this.kwd = kwd;
	}



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public Integer getMovePage() {
		return movePage;
	}



	public void setMovePage(Integer movePage) {
		this.movePage = movePage;
	}



	public Integer getMove() {
		return move;
	}



	public void setMove(Integer move) {
		this.move = move;
	}



	public Integer getStart() {
		return start;
	}



	public void setStart(Integer start) {
		this.start = start;
	}



	public Integer getEnd() {
		return end;
	}



	public void setEnd(Integer end) {
		this.end = end;
	}



	public String getSearch() {
		return search;
	}



	public void setSearch(String search) {
		this.search = search;
	}



	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", writeDate=" + writeDate
				+ ", hit=" + hit + ", gNo=" + gNo + ", oNo=" + oNo + ", depth=" + depth + ", userNo=" + userNo
				+ ", userName=" + userName + "]";
	}
	
	
	
}
