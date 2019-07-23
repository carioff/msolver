package com.rootech.msolver;

import java.sql.Connection;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rootech.msolver.service.UserService;
import com.rootech.msolver.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/config/*-config.xml" })
@Transactional
public class DbConnectionTest {

	@Inject
	private DataSource ds;
	
	@Inject
	private SqlSessionFactory sqlFactory;

	@Autowired
	private UserService userService;
	
	@Test
	@Ignore
	public void testConnection() throws Exception {

		try (Connection con = ds.getConnection()) {
			System.out.println(con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testFactory(){
		System.out.println(sqlFactory);
	}
	
	@Test
	@Ignore
	public void testSession() throws Exception{
		try(SqlSession session = sqlFactory.openSession()){
			System.out.println(session);
		}catch(Exception e){

			e.printStackTrace();
		}
	}
	
//	@SuppressWarnings("static-access")
	@Test
	@Ignore
	public void getUsers() {
		UserVo userVo = new UserVo();
		userVo.setUserId("tester");
		userVo.setPassword("1234");
		System.out.println(userService.getUser(userVo).getUsername());
		System.out.println(userService.getUser(userVo).getPassword());
		System.out.println(userService.getUser(userVo).getAuthorities().toString());
	}
	
	@Test
	@Ignore
	public void equalsIgnoreCaseTest() {
		String username =  "tester";
		if (username.equalsIgnoreCase("admin")) {
			System.out.println(username);
		}
		System.out.println(username);
	}

	

}
