package com.fg.vo;

public class ProfileVO {
	private int profile_idx;			// 시퀀스
	private String member_id;			// 회원아이디
	private String profile_image;		// 프로필사진
	private String profile_text;		// 프로필 내용
	private String profile_phonenumber; // 프로필 휴대폰번호 (미사용)
	
	public ProfileVO() {
		
	}

	public int getProfile_idx() {
		return profile_idx;
	}

	public void setProfile_idx(int profile_idx) {
		this.profile_idx = profile_idx;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public String getProfile_text() {
		return profile_text;
	}

	public void setProfile_text(String profile_text) {
		this.profile_text = profile_text;
	}

	public String getProfile_phonenumber() {
		return profile_phonenumber;
	}

	public void setProfile_phonenumber(String profile_phonenumber) {
		this.profile_phonenumber = profile_phonenumber;
	}
	
}
