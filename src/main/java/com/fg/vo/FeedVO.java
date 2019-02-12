package com.fg.vo;

import java.util.List;

public class FeedVO {
	
	private String friend_profileImg;		// FEED에 없는 컬럼 (vo에 담아서 가져올때 사용) - 작성자 프로필사진
	private List<CommentsVO> com_list;		// FEED에 없는 컬럼 (vo에 담아서 가져올때 사용) - 댓글
	
	private int feed_idx;			// 피드 번호
	private String user_id;			// 작성자 아이디
	private String feed_image;		// 피드 이미지
	private String feed_text;		// 피드 내용
	private String feed_regidate;	// 피드 작성일시
	
	
	public FeedVO() {}
	public List<CommentsVO> getCom_list() {
		return com_list;
	}

	public void setCom_list(List<CommentsVO> com_list) {
		this.com_list = com_list;
	}
	
	public String getFriend_profileImg() {
		return friend_profileImg;
	}
	public void setFriend_profileImg(String friend_profileImg) {
		this.friend_profileImg = friend_profileImg;
	}
	public int getFeed_idx() {
		return feed_idx;
	}
	public void setFeed_idx(int feed_idx) {
		this.feed_idx = feed_idx;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFeed_image() {
		return feed_image;
	}
	public void setFeed_image(String feed_image) {
		this.feed_image = feed_image;
	}
	public String getFeed_text() {
		return feed_text;
	}
	public void setFeed_text(String feed_text) {
		this.feed_text = feed_text;
	}
	public String getFeed_regidate() {
		return feed_regidate;
	}
	public void setFeed_regidate(String feed_regidate) {
		this.feed_regidate = feed_regidate;
	}
	
	
}
