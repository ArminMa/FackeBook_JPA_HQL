package org.kth.HI1034.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.model.FacePost;
import org.kth.HI1034.model.FaceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationWar.class)
//@ActiveProfiles(DbProfile.H2)
public class UserPostRepoTest {

	@Autowired
	private FaceUserRepository userRepo;
	List<FaceUser> userList = new ArrayList<>();

	@Autowired
	private FacePostRepository userPostRepo;
	List<FacePost> postList = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		System.out.println("\n\n-----------------UserPostRepoTest.setUp-start----------------------------\n\n");

		//------------ creating Users -----------
		userList.add(new FaceUser("UserFriendRepoTest0@gmail.com", "FaceUser0", "password", "firstName0", "lastName0", new Date()));
		userList.add(new FaceUser("UserFriendRepoTest1@gmail.com", "FaceUser1", "password", "firstName1", "lastName1", new Date()));
		userList.add( new FaceUser("UserFriendRepoTes2t@gmail.com", "FaceUser2", "password", "firstName2", "lastName2", new Date()));
		userList.add(new FaceUser("UserFriendRepoTest3@gmail.com", "FaceUser3", "password", "firstName3", "lastName3", new Date()));
		userList.add(new FaceUser("UserFriendRepoTest4@gmail.com", "FaceUser4", "password", "firstName4", "lastName4", new Date()));
		//if the this return set or SortedSet dow cast to ArrayList while fix this problem
		userList = userRepo.save( userList);
		userRepo.flush();

		assertNotNull(userList);
		assertTrue(userList.size() == 5);


		postList.add( new FacePost("Post 0", new Date(), userList.get(0), userList.get(1) ) );
		postList.add( new FacePost("Post 1", new Date(), userList.get(0), userList.get(2) ) );
		postList.add( new FacePost("Post 2", new Date(), userList.get(0), userList.get(3) ) );
		postList.add( new FacePost("Post 3", new Date(), userList.get(0), userList.get(4) ) );

		postList.add( new FacePost("Post 4", new Date(), userList.get(1), userList.get(0) ) );
		postList.add( new FacePost("Post 5", new Date(), userList.get(1), userList.get(2) ) );
		postList.add( new FacePost("Post 6", new Date(), userList.get(2), userList.get(2) ) );
		postList = userPostRepo.save( postList);
		userPostRepo.flush();






		assertNotNull(postList);
		assertTrue(postList.size() == 7);

		System.out.println("\n\n-----------------UserPostRepoTest.setUp-end----------------------------\n\n");

	}

	@Test
	public void postTest() throws Exception {

		System.out.println("\n\n----------------- UserPostRepoTest.setUp-start ----------------------------\n\n");

		// A user is supposed to get receivingPost eagerly, lets test that out
		FaceUser user2 = userRepo.findByEmail(userList.get(2).getEmail());
		assertNotNull(user2);
		assertNotNull(user2.getReceivedFacePost());
		assertFalse( user2.getReceivedFacePost().isEmpty() );
		assertTrue(user2.getReceivedFacePost().size() == 3);

		//sent Post is not loaded Eagerly so that should be null
		assertNull(user2.getSentFacePost());

		System.out.println("\n\n----------------- UserPostRepoTest.setUp-end ----------------------------\n\n");
	}


	@After
	public void tearDown() throws Exception {

		userPostRepo.delete(postList.get(0));		postList.remove(0);
		userPostRepo.delete(postList.get(0));       postList.remove(0);


		// removal of a user should not remove his sent post
		FaceUser user3 = userRepo.findByEmail(userList.get(3).getEmail());
		assertNotNull(user3);
		FacePost postToUser3 = userPostRepo.findOne(postList.get(0).getId());
		assertNotNull(postToUser3);
		Assert.notEmpty( user3.getReceivedFacePost() );
		assertEquals(user3.getReceivedFacePost().first().getId(),postToUser3.getId());






		userPostRepo.delete(postList);

		userRepo.delete(userList);

	}
}
