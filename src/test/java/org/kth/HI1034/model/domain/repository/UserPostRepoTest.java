package org.kth.HI1034.model.domain.repository;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.kth.HI1034.model.domain.entity.post.FacePost;
import org.kth.HI1034.model.domain.entity.user.UserDetached;
import org.kth.HI1034.model.domain.repository.post.PostRepository;
import org.kth.HI1034.model.domain.repository.post.PostUserRepository;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserPostRepoTest {

	@Autowired
	private FaceUserRepository userRepo;
	private List<FaceUser> userList = new ArrayList<>();
	private FaceUser user;

	@Autowired
	private PostRepository postRepo;
	private List<FacePost> postList = new ArrayList<>();

	@Autowired
	private PostUserRepository postUserRepo;
	private List<UserDetached> userDetachedList = new ArrayList<>();

	@Test
	public void A_setUp() throws Exception {
		System.out.println("\n\n-----------------UserPostRepoTest.A_setUp-start----------------------------\n\n");

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


		// each entry is tightly linked to an email and username.
		// a user may be deleted from the system and this will not affect the sent and mail to other users.
		userDetachedList.add(new UserDetached("UserFriendRepoTest0@gmail.com", "FaceUser0"));
		userDetachedList.add(new UserDetached("UserFriendRepoTest1@gmail.com", "FaceUser1"));
		userDetachedList.add(new UserDetached("UserFriendRepoTest2@gmail.com", "FaceUser2"));
		userDetachedList.add(new UserDetached("UserFriendRepoTest3@gmail.com", "FaceUser3"));
		userDetachedList.add(new UserDetached("UserFriendRepoTest4@gmail.com", "FaceUser4"));
		userDetachedList = postUserRepo.save(userDetachedList);
		postUserRepo.flush();
		assertThat(userDetachedList).isNotNull();
		assertThat(userDetachedList.size()).isEqualTo(5);

		postList.add( new FacePost("Post 0", new Date(), userList.get(0), userList.get(0))  ) ;
		postList.add( new FacePost("Post 1", new Date(), userList.get(0) , userList.get(1)) );
		postList = postRepo.save( postList);
		postRepo.flush();
		assertThat(postList).isNotNull();
		assertThat(postList ).isNotEmpty();
		assertThat(postList.size()).isEqualTo(2);





		System.out.println("\n\n-----------------UserPostRepoTest.A_setUp-end----------------------------\n\n");



		System.out.println("\n\n----------------- UserPostRepoTest.B_postTest-start ----------------------------\n\n");



		List<FacePost> sentPostListForUser0  = postRepo.findAllSentByUserByUserId(userList.get(0).getEmail());
		assertThat(sentPostListForUser0).isNotNull();
		assertThat(sentPostListForUser0).isNotEmpty();
		assertThat(sentPostListForUser0).hasSize(2);

		List<FacePost> receivedPostListForUser0 = postRepo.findAllReceiverToUserByUserId(userList.get(0).getEmail());
		assertThat(receivedPostListForUser0).isNotNull();
		assertThat(receivedPostListForUser0).isNotEmpty();
		assertThat(receivedPostListForUser0).hasSize(1);


		//a User is supposed to get sent posts Lazy, lets test that by asserting the exception thrown
//		assertThatThrownBy(() -> { user.getSentFacePost().first(); }).isInstanceOf(LazyInitializationException.class);


		List<FacePost> sentPostListForUser1  = postRepo.findAllSentByUserByUserId(userList.get(1).getEmail());
		assertThat(sentPostListForUser1).isNotNull();
		assertThat(sentPostListForUser1).isEmpty();
		assertThat(sentPostListForUser1).hasSize(0);

		List<FacePost> receivedPostListForUser1 = postRepo.findAllReceiverToUserByUserId(userList.get(1).getEmail());
		assertThat(receivedPostListForUser1).isNotNull();
		assertThat(receivedPostListForUser1).isNotEmpty();
		assertThat(receivedPostListForUser1).hasSize(1);

		// remove the post from the users received post list
		postRepo.delete(receivedPostListForUser1.get(0));
		postRepo.flush();

		// the list should contain 0 posts
		receivedPostListForUser1 = postRepo.findAllReceiverToUserByUserId(userList.get(1).getEmail());
		assertThat(receivedPostListForUser1).isNotNull();
		assertThat(receivedPostListForUser1).isEmpty();
		assertThat(receivedPostListForUser1).hasSize(0);

		System.out.println("\n\n----------------- UserPostRepoTest.B_postTest-end ----------------------------\n\n");



		System.out.println("\n\n----------------- UserPostRepoTest.Z_tearDown-start ----------------------------\n\n");

		//remove all Users and that should not affect sent and received posts
		userRepo.delete(userList);
		userRepo.flush();

		//remove the posts
		postRepo.delete(postList);
		postRepo.flush();
		// remove the detached user information
		postUserRepo.delete(userDetachedList);
		postUserRepo.flush();



		System.out.println("\n\n----------------- UserPostRepoTest.Z_tearDown-end ----------------------------\n\n");
	}
}
