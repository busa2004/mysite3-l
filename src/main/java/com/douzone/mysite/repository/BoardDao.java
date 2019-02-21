package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert", vo);
	}

	public List<BoardVo> getList(BoardVo vo) {
		List<BoardVo> list = sqlSession.selectList("board.getListByStartAndEnd", vo);
		return list;
	}

	public List<BoardVo> getList() {
		List<BoardVo> list = sqlSession.selectList("board.getList");
		return list;
	}

	public List<BoardVo> getListSearch(BoardVo vo) {

		List<BoardVo> list = sqlSession.selectList("board.getListByStartAndEndAndSearch", vo);
		return list;
	}

	public List<BoardVo> getListCount(BoardVo vo) {
		List<BoardVo> list = sqlSession.selectList("board.getListBySearch", vo);
		return list;
	}

	public BoardVo getVo(Long no) {
		return sqlSession.selectOne("board.getVo", no);
	}

	public int getMaxGno() {
		return sqlSession.selectOne("board.getMaxGno");
	}

	public int getMaxono(int no) {
		return sqlSession.selectOne("board.getMaxono", no);
	}

	public void update(BoardVo vo) {
		sqlSession.update("board.update", vo);
	}

	public void updateCheck(long no) {
		sqlSession.update("board.updateCheck", no);
	}

	public void remove(Long no) {
		sqlSession.delete("board.remove", no);
	}

	public void update(Long no) {
		sqlSession.delete("board.hitUpdate", no);
	}

}
