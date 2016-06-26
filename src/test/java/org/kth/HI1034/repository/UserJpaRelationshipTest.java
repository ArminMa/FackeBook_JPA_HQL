package org.kth.HI1034.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.model.FaceMail;
import org.kth.HI1034.model.FaceUser;
import org.kth.HI1034.model.FriendRequest;
import org.kth.HI1034.model.UserReceivedMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationWar.class)
//@ActiveProfiles(DbProfile.H2)
public class UserJpaRelationshipTest {

	@Autowired
	private FaceMailRepository mailRepo;


	@Autowired
	private FaceUserRepository userRepo;

	@Autowired
	private UserReceivedMailRepository userReceivedMailRepo;

	@Autowired
	private FriendRequestRepository friendRequestRepo;


	private FaceMail faceFaceMailA;
	private FaceMail faceFaceMailB;
	private FaceMail faceFaceMailC;
	private FaceUser faceFaceUser1;
	private FaceUser faceFaceUser2;
	private FaceUser faceFaceUser3;
	private FriendRequest friendRequest1;

	private UserReceivedMail userReceivedMail;

	@Before
	public void setUp() throws Exception {
		System.out.println("\n\n-----------------MailRepositoryTest.setUp-start----------------------------\n\n");

		faceFaceUser1 = new FaceUser("majeriemail@gmail.com", "FaceUserA", "password", "ar", "maja" , new Date());
		faceFaceUser1 = userRepo.save(faceFaceUser1);
		userRepo.flush();
		assertNotNull(faceFaceUser1);

		faceFaceUser2 = new FaceUser("majeriarmin@gmail.com", "FaceUserB", "password", "arr", "majo", new Date() );
		faceFaceUser2 = userRepo.save(faceFaceUser2);
		userRepo.flush();
		assertNotNull(faceFaceUser2);

		faceFaceUser3 = new FaceUser("majeriarmin2@gmail.com", "FaceUserC", "password", "arrr", "major"  , new Date());
		faceFaceUser3 = userRepo.save(faceFaceUser3);
		userRepo.flush();
		assertNotNull(faceFaceUser3);

		//------------ Messages to and from Users -----------

		/* creat mail B from user 1, receiver is user 2 */
		faceFaceMailA = new FaceMail("FaceMail text A","subject about Something A", new Date(), faceFaceUser1 );
		faceFaceMailA = mailRepo.save(faceFaceMailA);
		mailRepo.flush();
		assertNotNull(faceFaceMailA);

		userReceivedMail = new UserReceivedMail(faceFaceMailA, faceFaceUser2);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* creat mail B from user 1, receiver is user 2 and 3 */
		List<FaceUser> sendToUsers = new ArrayList<FaceUser>();
		sendToUsers.add(faceFaceUser2);
		sendToUsers.add(faceFaceUser3);
		faceFaceMailB = new FaceMail("FaceMail text B", "subject about Something B", new Date(), faceFaceUser1 );
		faceFaceMailB = mailRepo.save(faceFaceMailB);
		mailRepo.flush();

		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser2);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser3);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* creat mail C from user 2, receiver is user 1 and 3 */
		faceFaceMailC = new FaceMail("FaceMail text C", "subject about Something C", new Date(), faceFaceUser2 );
		faceFaceMailC = mailRepo.save(faceFaceMailC);
		mailRepo.flush();
		assertNotNull(faceFaceMailC);

		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser1);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* send to user 3 */
		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser3);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		//------------ Create friend relationship between Users ------------

		// user 1 sends a request to user 2
		friendRequest1 = new FriendRequest(faceFaceUser1, faceFaceUser2, new Date());
		friendRequest1 = friendRequestRepo.save(friendRequest1);
		friendRequestRepo.flush();
		assertNotNull(friendRequest1);
		assertTrue(friendRequest1.getRequestFrom().getId().equals(faceFaceUser1.getId()));


		// user 1 sends a request to user 3
		friendRequest1 = new FriendRequest(faceFaceUser1, faceFaceUser3, new Date());
		friendRequest1 = friendRequestRepo.save(friendRequest1);
		friendRequestRepo.flush();
		assertNotNull(friendRequest1);
		assertTrue(friendRequest1.getRequestFrom().getId().equals(faceFaceUser1.getId()));


		// user 3 sends a request to user 2
		friendRequest1 = new FriendRequest(faceFaceUser3, faceFaceUser2, new Date());
		friendRequest1 = friendRequestRepo.save(friendRequest1);
		friendRequestRepo.flush();
		assertNotNull(friendRequest1);
		assertTrue(friendRequest1.getRequestFrom().getId().equals(faceFaceUser3.getId()));


		System.out.println("\n\n-----------------MailRepositoryTest.setUp-end----------------------------\n\n");

	}



	@Test
	public void testUserMailRelationshipFaceUser(){

		System.out.println("\n-----------------MailRepositoryTest.testUserMailRelationshipFaceUser-start----------------------------\n\n");

		FaceUser testFaceUser11 = userRepo.findOne(faceFaceUser1.getId());
		assertNotNull(testFaceUser11);

		List<FaceMail> faceMailsUser1 = mailRepo.findAllReceivedMailByUserId(testFaceUser11.getId());
		assertNotNull(faceMailsUser1);
		assertFalse(faceMailsUser1.isEmpty());

		FaceUser testFaceUser2 = userRepo.findByEmail(faceFaceUser2.getEmail());
		assertNotNull(testFaceUser2);

		List<FaceMail> faceMails1 = mailRepo.findAllReceivedMailByUserId(testFaceUser2.getId());
		assertNotNull(faceMails1);
		assertFalse(faceMails1.isEmpty());

		// user 2 reads received MailX
		Long MailX = faceMails1.get(1).getId();
		mailRepo.markReceivedMailByUserIdAndMailIdAsRead(true, testFaceUser2.getId(), MailX );
		mailRepo.flush();

		//check if MailX is read
		List<FaceMail> readUser2FaceMail = mailRepo.findAllReadReceivedMailByUserId(testFaceUser2.getId());
		assertNotNull(readUser2FaceMail);
		assertTrue(readUser2FaceMail.size() == 1);
		assertTrue(readUser2FaceMail.get(0).getId().equals(MailX));

		//check all Unread Mails
		List<FaceMail> user2UnReadFaceMails = mailRepo.findAllUnReadReceivedMailByUserId(testFaceUser2.getId());
		assertNotNull(user2UnReadFaceMails);
		assertTrue(user2UnReadFaceMails.size() == 1);

		FaceMail faceMailsread = mailRepo.findOne(faceMails1.get(1).getId());
		assertNotNull(faceMailsread);

		List<FaceMail> userReceivedMail1 = mailRepo.findAllMailByAuthorAndReceivingUserId(testFaceUser11.getId(), testFaceUser2.getId() );
		assertNotNull(userReceivedMail1);
		assertTrue( userReceivedMail1.size() == 2 );







		List<FriendRequest> friendRequestFromUser1List = friendRequestRepo.findAllFromeThisUserId(faceFaceUser1.getId());
		assertNotNull(friendRequestFromUser1List);
		assertTrue(!friendRequestFromUser1List.isEmpty());
		assertTrue(friendRequestFromUser1List.get(0).getRequestFrom().getId().equals(faceFaceUser1.getId()));


		System.out.println("\n-----------------MailRepositoryTest.testUserMailRelationshipFaceUser-end----------------------------\n\n");

	}

//	@Test
//	public void testUserFriendsRelationship(){
//
////		System.out.println("\n-----------------debug-start----------------------------\nMailRepositoryTest.286: \n" +
////				MoreObjects.toStringHelper(this)
////						.toString() + "\n-----------------debug-end----------------------------\n");
//
//		System.out.println("\n-----------------MailRepositoryTest.testUserFriendsRelationship-start----------------------------\n\n");
//
//
//
//
//		System.out.println("\n-----------------MailRepositoryTest.testUserFriendsRelationship-end----------------------------\n\n");
//
//
//
//
//	}

	@After
	public void tearDown() throws Exception {

		System.out.println("\n\n-----------------MailRepositoryTest.tearDown-start----------------------------\n\n");

		//remove user 1 from database
		userReceivedMailRepo.deleteThisRowByUserId(faceFaceUser1.getId());
		userReceivedMailRepo.flush();

		friendRequestRepo.deleteToOrFromByUserId(faceFaceUser1.getId());
		friendRequestRepo.flush();

		userRepo.deleteThisFaceUser(faceFaceUser1.getId());
		userRepo.flush();

		//remove user 2 from database
		userReceivedMailRepo.deleteThisRowByUserId(faceFaceUser2.getId());
		userReceivedMailRepo.flush();

		friendRequestRepo.deleteToOrFromByUserId(faceFaceUser2.getId());
		friendRequestRepo.flush();

		userRepo.deleteThisFaceUser(faceFaceUser2.getId());
		userRepo.flush();



		//remove user 3 from database
		userReceivedMailRepo.deleteThisRowByUserId(faceFaceUser3.getId());
		userReceivedMailRepo.flush();

		friendRequestRepo.deleteToOrFromByUserId(faceFaceUser3.getId());
		friendRequestRepo.flush();

		userRepo.deleteThisFaceUser(faceFaceUser3.getId());
		userRepo.flush();

		//remove all mails from database
		mailRepo.delete(faceFaceMailA.getId());
		mailRepo.flush();
		mailRepo.delete(faceFaceMailB.getId());
		mailRepo.flush();
		mailRepo.delete(faceFaceMailC.getId());
		mailRepo.flush();


		System.out.println("\n\n-----------------MailRepositoryTest.tearDown-end----------------------------\n\n");

	}

}