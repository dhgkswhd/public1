package com.fg.vo;

public class CommentsVO {
	// COMMENTS에 없는 컬럼들 (getter만 사용)
	private String profile_image;   // 댓글 작성자 프로필사진 
	
	private int comments_idx;			// 댓글 번호
	private String user_id;				// 작성자 id
	private String comments_text;		// 댓글 내용
	private String comments_regidate; 	// 댓글 작성일
	private int feed_idx;				// 원글 번호
	private int comments_refNum;		// 부모글 번호 (댓글이면 원글번호와 동일)
	private int comments_div;			// 댓글이면 0, 답글이면 1
	
	public CommentsVO() {}
	
	
	public int getComments_refNum() {
		return comments_refNum;
	}

	public void setComments_refNum(int comments_refNum) {
		this.comments_refNum = comments_refNum;
	}

	public int getComments_div() {
		return comments_div;
	}

	public void setComments_div(int comments_div) {
		this.comments_div = comments_div;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public int getComments_idx() {
		return comments_idx;
	}

	public void setComments_idx(int comments_idx) {
		this.comments_idx = comments_idx;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getComments_text() {
		return comments_text;
	}

	public void setComments_text(String comments_text) {
		this.comments_text = comments_text;
	}

	public String getComments_regidate() {
		return comments_regidate;
	}

	public void setComments_regidate(String comments_regidate) {
		this.comments_regidate = comments_regidate;
	}

	public int getFeed_idx() {
		return feed_idx;
	}

	public void setFeed_idx(int feed_idx) {
		this.feed_idx = feed_idx;
	}
	
	
}
