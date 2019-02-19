package com.douzone.mysite.vo;

public class CommentVo {
	private long no;
	private String name;
	private long userNo;
	public long getUserNo() {
		return userNo;
	}
	public void setUserNo(long userNo) {
		this.userNo = userNo;
	}
	private String contents;
	private String regDate;
	private long boardNo;
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(long boardNo) {
		this.boardNo = boardNo;
	}
	@Override
	public String toString() {
		return "CommentVo [no=" + no + ", name=" + name + ", userNo=" + userNo + ", contents=" + contents + ", regDate="
				+ regDate + ", boardNo=" + boardNo + "]";
	}

}
