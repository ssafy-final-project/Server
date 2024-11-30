package com.ssafy.member.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.apt.model.DongcodeDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

@Transactional
@SpringBootTest(properties = {"spring.config.location=classpath:application.properties"})
class MemberUnitTest {
	@Autowired
	private MemberService memberService;
	private MemberDto member = new MemberDto();
	private String district, removeDistrict, phone;
	
	@BeforeEach
	void before() {
		member.setId("ssafy");
		member.setPw("1234");
		member.setName("김싸피");
		member.setPhone("010-0000-0000");
		member.setAddress("덕명동");
		member.setSalt("hash value");
		district = "11710";
		removeDistrict = "11680";
		phone = "01099893773";
	}
	
	@Test
	void testMemberServiceImpl() {
		assertNotNull(memberService);
	}

	@Test
	void testAddFavorite() {
		try {
			memberService.addFavorite(member.getId(), district);
		} catch (SQLException e) {
			fail("fail");
		}
	}

	@Test
	void testRemoveFavorite() {
		try {
			memberService.removeFavorite(member.getId(), removeDistrict);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	void testRegisterUser() {
		try {
			memberService.registerUser(new MemberDto("ssafy111", "123", "아아", "덕명구", "1234"));
		} catch (SQLException e) {
			fail("Not yet implemented");
		}
	}

	@Test
	void testUpdateUser() {
		MemberDto newMember = new MemberDto();
		newMember.setId("ssafy");
		newMember.setPw("12");
		newMember.setName("이싸피");
		newMember.setAddress("아아");
		newMember.setPhone("삼성");
		try {
			memberService.updateUser(newMember);
		} catch (SQLException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

	@Test
	void testDeleteUser() {
		try {
			memberService.deleteUser("ssafy");
		} catch (SQLException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

	@Test
	void testSelectUser() {
		try {
			MemberDto m = memberService.selectUser("ssafy");
			System.out.println(m);
		} catch (SQLException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

	@Test
	void testSelectUserAll() {
		try {
			List<MemberDto> memberList = memberService.selectUserAll();
			memberList.stream().forEach(System.out::println);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

	@Test
	void testFindPw() {
		try {
			String pw = memberService.findPw("ssafy", phone);
			assertEquals(pw, "1234");
		} catch (SQLException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

	@Test
	void testGetFavorites() {
		try {
			List<DongcodeDto> favorites = memberService.getFavorites("ssafy");
			favorites.stream().forEach(System.out::println);
		} catch (SQLException e) {
			fail("Not yet implemented");
			e.printStackTrace();
		}
	}

}
