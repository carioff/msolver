package com.rootech.msolver.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.rootech.msolver.common.dto.CustomUserDetails;
import com.rootech.msolver.dao.UserDao;
import com.rootech.msolver.vo.UserVo;

@Repository("UserDao")
public class UserDaoImpl implements UserDao {

	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	private static final String NAMESPACE = "com.rootech.msolver.mapper.UserMapper";

	@Override
	public CustomUserDetails selectUser(UserVo userVo) {
		return sqlSession.selectOne(NAMESPACE + ".selectUser", userVo);
	}

	@Override
	public int insertUser(UserVo userVo) {
		return sqlSession.insert(NAMESPACE + ".insertUser", userVo);
	}

	@Override
	public int insertUserAuth(UserVo userVo) {
		return sqlSession.insert(NAMESPACE + ".insertUserAuth", userVo);
	}
	
	@Override
	public int updateUser(UserVo userVo) {
		return sqlSession.update(NAMESPACE + ".updateUser", userVo);
	}

	@Override
	public int deleteUser(UserVo userVo) {
		return sqlSession.delete(NAMESPACE + ".deleteUser", userVo);
	}

	@Override
	public String chkDupUser(UserVo userVo) {
		return sqlSession.selectOne(NAMESPACE + ".chkDupUser", userVo);
	}

	@Override
	public UserVo selectLoginUser(UserVo userVo) {
		
		try{
			return sqlSession.selectOne(NAMESPACE + ".selectLoginUser", userVo);
		} catch(RuntimeException e){
			if(logger.isErrorEnabled()){
				logger.error("fail to selectLoginUser", e);
			}
			throw e;
		}
	}


}
