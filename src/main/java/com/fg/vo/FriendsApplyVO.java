package com.fg.vo;

public class FriendsApplyVO {
	private int friends_apply_idx;	// 시퀀스
	private String sender_id;			// 친구요청 한 사람 아이디
	private String receiver_id;		// 친구요청 받은사람 아이디
	private String apply_date;		// 친구신청일
	
	public FriendsApplyVO() {}

	public int getFriends_apply_idx() {
		return friends_apply_idx;
	}

	public void setFriends_apply_idx(int friends_apply_idx) {
		this.friends_apply_idx = friends_apply_idx;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getApply_date() {
		return apply_date;
	}

	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	
	
	
}
