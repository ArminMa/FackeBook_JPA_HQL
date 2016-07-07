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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationWar.class)
//@ActiveProfiles(DbProfile.H2)
public class UserPostRepoTest {

	@Autowired
	private FaceUserRepository userRepo;
	private List<FaceUser> userList = new ArrayList<>();
	private FaceUser user;

	@Autowired
	private FacePostRepository userPostRepo;
	private List<FacePost> postList = new ArrayList<>();

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

		assertThat(userList).isNotNull();
		assertThat(userList.size()).isEqualTo(5);


		postList.add( new FacePost("Post 0", new Date(), userList.get(0), userList.get(0))  ) ;
		postList.add( new FacePost("Post 1", new Date(), userList.get(0) , userList.get(1)) );
		postList = userPostRepo.save( postList);
		userPostRepo.flush();
		assertThat(postList).isNotNull();
		assertThat(postList ).isNotEmpty();
		assertThat(postList.size()).isEqualTo(2);





		System.out.println("\n\n-----------------UserPostRepoTest.setUp-end----------------------------\n\n");

	}

	@Test
	public void postTest() throws Exception {

		System.out.println("\n\n----------------- UserPostRepoTest.setUp-start ----------------------------\n\n");



		List<FacePost> sentPostListForUser0  = userPostRepo.findAllSentByUserByUserId(userList.get(0).getId());
		assertThat(sentPostListForUser0).isNotNull();
		assertThat(sentPostListForUser0).isNotEmpty();
		assertThat(sentPostListForUser0).hasSize(2);

		List<FacePost> receivedPostListForUser0 = userPostRepo.findAllReceiverToUserByUserId(userList.get(0).getId());
		assertThat(receivedPostListForUser0).isNotNull();
		assertThat(receivedPostListForUser0).isNotEmpty();
		assertThat(receivedPostListForUser0).hasSize(1);


		//a User is supposed to get sent posts Lazy, lets test that by asserting the exception thrown
//		assertThatThrownBy(() -> { user.getSentFacePost().first(); }).isInstanceOf(LazyInitializationException.class);


		List<FacePost> sentPostListForUser1  = userPostRepo.findAllSentByUserByUserId(userList.get(1).getId());
		assertThat(sentPostListForUser1).isNotNull();
		assertThat(sentPostListForUser1).isNotEmpty();
		assertThat(sentPostListForUser1).hasSize(0);

		List<FacePost> receivedPostListForUser1 = userPostRepo.findAllReceiverToUserByUserId(userList.get(1).getId());
		assertThat(receivedPostListForUser1).isNotNull();
		assertThat(receivedPostListForUser1).isNotEmpty();
		assertThat(receivedPostListForUser1).hasSize(1);


		System.out.println("\n\n----------------- UserPostRepoTest.setUp-end ----------------------------\n\n");
	}



	@After
	public void tearDown() throws Exception {

//		userPostRepo.delete(postList.get(0));		postList.remove(0);
//		userPostRepo.delete(postList.get(0));       postList.remove(0);


		// removal of a user should not remove his sent post




//
//
//		userPostRepo.delete(postList);
		userRepo.delete(userList);

	}
}
