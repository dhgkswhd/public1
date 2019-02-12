package com.fg.vo;

public class MessageVO {
	private int message_idx;			// 메세지 seq
	private String my_id;				// 발신자 아이디
	private String friend_id;			// 수신자 아이디
	private String msg_text;			// 메세지 내용
	private String msg_regidate;		// 보낸시간
	private String room_name;			// 그룹네임
	private int checkcount;				// 읽었는지 안읽었는지 체크할 변수
	private String profile_image;		// 프로필 사진 (가져올때만 씀)
	
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
	
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public int getCheckcount() {
		return checkcount;
	}
	public void setCheckcount(int checkcount) {
		this.checkcount = checkcount;
	}
	public int getMessage_idx() {
		return message_idx;
	}
	public void setMessage_idx(int message_idx) {
		this.message_idx = message_idx;
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
	public String getMsg_text() {
		return msg_text;
	}
	public void setMsg_text(String msg_text) {
		this.msg_text = msg_text;
	}
	public String getMsg_regidate() {
		return msg_regidate;
	}
	public void setMsg_regidate(String msg_regidate) {
		this.msg_regidate = msg_regidate;
	}
	
}
