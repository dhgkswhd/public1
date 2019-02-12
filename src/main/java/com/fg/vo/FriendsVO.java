package com.fg.vo;

import java.io.Serializable;

// 자꾸 오류떠서 implements Serializable 해줌
public class FriendsVO implements Serializable {
	private int friends_idx;		// 시퀀스
	private String my_id;			// 내 아이디
	private String friend_id;		// 친구 아이디
	private String completedate;	// 친구된 날짜
	
	public FriendsVO() {}
	
	public int getFriends_idx() {
		return friends_idx;
	}
	public void setFriends_idx(int friends_idx) {
		this.friends_idx = friends_idx;
	}
	public String getMy_id() {
		return my_id;
	}
	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}
	public String getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
	public String getCompletedate() {
		return completedate;
	}
	public void setCompletedate(String completedate) {
		this.completedate = completedate;
	}
	
	
}
