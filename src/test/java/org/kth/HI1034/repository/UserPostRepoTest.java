package org.kth.HI1034.repository;

import com.google.common.base.MoreObjects;
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
		Assert.notNull(userList);
		Assert.isTrue(userList.size() == 5);


		postList.add( new FacePost("Post 0", new Date(), userList.get(0), userList.get(1) ) );
		postList.add( new FacePost("Post 1", new Date(), userList.get(0), userList.get(2) ) );
		postList.add( new FacePost("Post 2", new Date(), userList.get(0), userList.get(3) ) );
		postList.add( new FacePost("Post 3", new Date(), userList.get(0), userList.get(4) ) );

		postList.add( new FacePost("Post 4", new Date(), userList.get(1), userList.get(0) ) );
		postList.add( new FacePost("Post 5", new Date(), userList.get(1), userList.get(2) ) );
		postList.add( new FacePost("Post 6", new Date(), userList.get(2), userList.get(2) ) );
		postList = userPostRepo.save( postList);
		userPostRepo.flush();

		System.out.println("\n\nUserPostRepoTest.setUp\n" +
		                MoreObjects.toStringHelper(postList)
		                .add("postList.size ", postList.size())
		                .toString() + "\n\n");



		Assert.notNull(postList);
		Assert.isTrue(postList.size() == 7);

		System.out.println("\n\n-----------------UserPostRepoTest.setUp-end----------------------------\n\n");

	}

	@Test
	public void postTest() throws Exception {

		System.out.println("\n\n----------------- UserPostRepoTest.setUp-start ----------------------------\n\n");



		System.out.println("\n\n----------------- UserPostRepoTest.setUp-end ----------------------------\n\n");
	}


	@After
	public void tearDown() throws Exception {

		userPostRepo.delete(postList.get(0));
		postList.remove(0);
		userPostRepo.delete(postList.get(2));
		postList.remove(2);

		userPostRepo.delete(postList);

		userRepo.delete(userList);

	}
}
