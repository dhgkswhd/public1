package com.fg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.fg.vo.MessageVO;

public class MessageDAO {
	SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 메세지 한줄 삽입
	public void insertMsg ( MessageVO vo ) {
		
		sqlSession.insert( "message.insertMsg", vo );
		
	}
	
	// 이전 대화내용 가져오기
	public List<MessageVO> myMsgList( String room_name ) {
		List<MessageVO> list = null;
		//System.out.println("룸네임" + room_name);
		
		list = sqlSession.selectList( "message.myMsgList", room_name );
		
		//System.out.println("listsize : " + list.size());
		return list;
	}

	// 메세지 읽음, 안읽음
	public void updatecheck( Map<String,String> inputData ) {
		
		sqlSession.update( "message.updatecheck", inputData );
		
	}
	
	// 피드메인화면으로 이동할 때, 안읽은 메세지 수 count
	public int checkcount( String my_id ) {
		
		int count = sqlSession.selectOne( "message.checkcount", my_id );
		
		return count;
	}
	
	// 친구 각각의 안읽은 메세지 체크
	public int list_checkcount ( Map<String,String> inputData ) {
		
		int count = sqlSession.selectOne( "message.list_checkcount", inputData );
		
		return count;
	}
	
	
	// 나랑 대화한 사람과의 마지막 메세지 가져오기
	public List<MessageVO> last_message( String my_id ) {
		List<MessageVO> list = null;
		
		list = sqlSession.selectList( "message.last_message", my_id );
		
		System.out.println("마지막 메세지 : " + list);
		return list;
	}
}
