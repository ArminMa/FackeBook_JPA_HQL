package org.kth.HI1034.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.model.FaceUser;
import org.kth.HI1034.model.FriendRequest;
import org.kth.HI1034.model.UserFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationWar.class)
public class UserFriendRepoTest {

	@Autowired
	private FaceUserRepository userRepo;
	List<FaceUser> userList = new ArrayList<>();

	@Autowired
	private FriendRequestRepository friendRequestRepo;
	private FriendRequest friendRequest;
	private List<FriendRequest> friendRequests = new ArrayList<>();


	@Autowired
	private UserFriendRepository userFriendRepo;
	private UserFriend userFriend;
	List<UserFriend> fromUser3 = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		System.out.println("\n\n-----------------UserFriendRepoTest.setUp-start----------------------------\n\n");

		//------------ creating Users -----------
		userList.add(new FaceUser("UserFriendRepoTest1@gmail.com", "FaceUser1", "password", "firstName1", "lastName1", new Date()));
		userList.add(new FaceUser("UserFriendRepoTest2@gmail.com", "FaceUser2", "password", "firstName2", "lastName2", new Date()));
		userList.add( new FaceUser("UserFriendRepoTes3t@gmail.com", "FaceUser3", "password", "firstName3", "lastName3", new Date()));
		userList.add(new FaceUser("UserFriendRepoTest4@gmail.com", "FaceUser4", "password", "firstName4", "lastName4", new Date()));
		userList.add(new FaceUser("UserFriendRepoTest5@gmail.com", "FaceUser5", "password", "firstName5", "lastName5", new Date()));
		//if the this return set or SortedSet dow cast to ArrayList while fix this problem
		userList = userRepo.save( userList);
		userRepo.flush();
		Assert.notNull(userList);
		Assert.isTrue(userList.size() == 5);

		//******************** creating friend request and friends *********************

		//------------ friend request from user 0 to user 1 -----------
		friendRequests.add(new FriendRequest(userList.get(0), userList.get(1), new Date()) );
		//------------ friend request from user 0 to user 1 -----------
		friendRequests.add(new FriendRequest(userList.get(0), userList.get(2), new Date()));
		//------------ friend request from user 1 to user 2 -----------
		friendRequests.add(new FriendRequest(userList.get(1), userList.get(2), new Date()));

		friendRequests = friendRequestRepo.save(friendRequests);
		friendRequestRepo.flush();
		Assert.notNull(friendRequests);
		Assert.isTrue(friendRequests.size() == 3);



		//------------- user 2 and 3 are friends------------
		userFriend = new UserFriend(userList.get(2), userList.get(3), new Date());
		userFriendRepo.save(userFriend);
		userFriendRepo.flush();

		//saving one friendship by using the FaceUser class and NOT the UserFriend Class
		//------------- user 4 and 3 are friends------------
		userFriend = new UserFriend(userList.get(4), userList.get(2), new Date() );
		userFriendRepo.save( userFriend  ) ;
		userFriendRepo.flush();
		Assert.notNull( userFriend );
		Assert.isTrue( userFriend.getPk().getAccepter().getId().equals(userList.get(4).getId() ) );

		//------------- user 3 is friends with user 0,1 ------------

		fromUser3.add( new UserFriend(userList.get(3), userList.get(0), new Date()) );
		fromUser3.add( new UserFriend(userList.get(3), userList.get(1), new Date()) );
		fromUser3 = userFriendRepo.save(fromUser3);
		userFriendRepo.flush();
		Assert.notEmpty(fromUser3);
		Assert.notNull(fromUser3);
		Assert.isTrue(fromUser3.size() == 2);



		System.out.println("\n\n-----------------UserFriendRepoTest.setUp-End----------------------------\n\n");

	}



	@Test
	public void test() {
		System.out.println("\n\n-----------------UserFriendRepoTest.test-Start----------------------------\n\n");
		//********************* friend request test and friend test *********************

		List<FriendRequest> friendRequestFromUser1List = friendRequestRepo.findAllFriendRequestFromUserByUserId(userList.get(0).getId());
		Assert.isTrue(friendRequestFromUser1List.get(0).getRequestFrom().getId().equals(userList.get(0).getId()));
		Assert.notNull(friendRequestFromUser1List);
		Assert.isTrue(!friendRequestFromUser1List.isEmpty());
		Assert.isTrue( friendRequestFromUser1List.size() == 2);


		//fiend all request to user 2.
		friendRequests = friendRequestRepo.findAllFriendRequestToUserByUserId(userList.get(2).getId() ) ;
		Assert.notNull(friendRequests);
		Assert.notEmpty(friendRequests);
		Assert.isTrue( friendRequests.size() == 2 );     //2 request was sent to user 2

		System.out.println("\n-----------------UserFriendRepoTest.test-End----------------------------\n\n");
	}



	@After
	public void tearDown() throws Exception {
		System.out.println("\n\n-----------------UserFriendRepoTest.tearDown-start----------------------------\n\n");

//		friendRequestRepo.deleteAllInBatch();
//		friendRequestRepo.flush();
//		userRepo.deleteAllInBatch();
//		userRepo.flush();


		friendRequestRepo.deleteToOrFromByUserId(userList.get(1).getId());
		friendRequestRepo.flush();
		userRepo.delete(userList.get(1).getId());
		userRepo.flush();

		friendRequestRepo.deleteToOrFromByUserId(userList.get(2).getId());
		friendRequestRepo.flush();
		userRepo.delete(userList.get(2).getId());
		userRepo.flush();

		friendRequestRepo.deleteToOrFromByUserId(userList.get(3).getId());
		friendRequestRepo.flush();
		userRepo.delete(userList.get(3).getId());
		userRepo.flush();

		friendRequestRepo.deleteToOrFromByUserId(userList.get(0).getId());
		friendRequestRepo.flush();

		userRepo.delete(userList.get(0).getId());
		userRepo.flush();

		userRepo.delete(userList.get(4).getId());
		userRepo.flush();



		System.out.println("\n\n-----------------UserFriendRepoTest.tearDown-End----------------------------\n\n");
	}
}
