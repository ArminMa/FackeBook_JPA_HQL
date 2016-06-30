package org.kth.HI1034.repository;

import org.hibernate.LazyInitializationException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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


		postList.add( new FacePost("Post 0", new Date(), userList.get(0), userList.get(0) ) );
		postList.add( new FacePost("Post 1", new Date(), userList.get(0) , userList.get(1)) );
		postList = userPostRepo.save( postList);
		userPostRepo.flush();
		assertThat(postList).isNotNull();
		assertThat(postList ).isNotEmpty();
		assertThat(postList.size()).isEqualTo(2);

//		userList.get(1).getReceivedFacePost().add(postList.get(0));
//		user = userRepo.save(userList.get(1));
//		assertNotNull(user);
//		assertNotNull(user.getReceivedFacePost() );
//		assertTrue(user.getReceivedFacePost().size() == 1 );
//
//		userList.get(1).getReceivedFacePost().add(postList.get(1));
//		user = userRepo.save(userList.get(1));
//		assertNotNull(user);
//		assertNotNull(user.getReceivedFacePost() );
//		assertTrue(user.getReceivedFacePost().size() == 1 );





//		postList.add( new FacePost("Post 1", new Date(), userList.get(0), userList.get(2) ) );
//		postList.add( new FacePost("Post 2", new Date(), userList.get(0), userList.get(3) ) );
//		postList.add( new FacePost("Post 3", new Date(), userList.get(0), userList.get(4) ) );
//
//		postList.add( new FacePost("Post 4", new Date(), userList.get(1), userList.get(0) ) );
//		postList.add( new FacePost("Post 5", new Date(), userList.get(1), userList.get(2) ) );
//		postList.add( new FacePost("Post 6", new Date(), userList.get(2), userList.get(2) ) );







//		assertNotNull(postList);
//		assertTrue(postList.size() == 2);

		System.out.println("\n\n-----------------UserPostRepoTest.setUp-end----------------------------\n\n");

	}

	@Test
	public void postTest() throws Exception {

		System.out.println("\n\n----------------- UserPostRepoTest.setUp-start ----------------------------\n\n");

		// A user is supposed to get receivingPost eagerly, lets test that out
		user = userRepo.findByEmail(userList.get(0).getEmail());

		assertThat(user).isNotNull();
		assertThat(user.getReceivedFacePost().first()).isNotNull();
		assertThat( user.getReceivedFacePost() ).isNotEmpty();
		assertThat(user.getReceivedFacePost().size() ).isEqualTo(1);

		//a User is supposed to get sent posts Lazy, lets test that by asserting the exeption thrown
		assertThatThrownBy(() -> { user.getSentFacePost().first(); }).isInstanceOf(LazyInitializationException.class);

		// removal of a user should not remove his sent post
		FaceUser user1 = userRepo.findByEmail(userList.get(1).getEmail());
		assertThat(user1).isNotNull();
		FacePost postToUser3 = userPostRepo.findOne(postList.get(1).getId());
		assertThat(postToUser3).isNotNull();
		assertThat( user1.getReceivedFacePost() ).isNotEmpty();
		assertThat(user1.getReceivedFacePost().first().getId()).isEqualTo(postToUser3.getId());

		System.out.println("\n\n----------------- UserPostRepoTest.setUp-end ----------------------------\n\n");
	}


	@After
	public void tearDown() throws Exception {

//		userPostRepo.delete(postList.get(0));		postList.remove(0);
//		userPostRepo.delete(postList.get(0));       postList.remove(0);







//
//
//		userPostRepo.delete(postList);

		userRepo.delete(userList);

	}
}
