package org.kth.HI1034.model.domain.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.model.domain.faceMail.FaceMail;
import org.kth.HI1034.model.domain.faceMail.FaceMailRepository;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMail;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationWar.class)
//@ActiveProfiles(DbProfile.H2)
public class UserMailRepoTest {

	@Autowired
	private FaceUserRepository userRepo;
	private FaceUser faceFaceUser1;
	private FaceUser faceFaceUser2;
	private FaceUser faceFaceUser3;
	private FaceUser faceFaceUser4;

	@Autowired
	private FaceMailRepository mailRepo;
	private UserReceivedMail userReceivedMail;
	private FaceMail faceFaceMailA;
	private FaceMail faceFaceMailB;
	private FaceMail faceFaceMailC;



	@Autowired
	private UserReceivedMailRepository userReceivedMailRepo;
//
//	@Autowired
//	private FriendRequestRepository friendRequestRepo;
//	private FriendRequest friendRequest1;
//
//	@Autowired
//	private UserFriendRepository userFriendRepo;
//	private UserFriend userFriend;
//
//






	@Before
	public void setUp() throws Exception {
		System.out.println("\n\n-----------------UserMailRepoTest.setUpUserControllerTest-start----------------------------\n\n");


		//------------ creating Users -----------
		faceFaceUser1 = new FaceUser("UserMailRepoTest1@gmail.com", "FaceUser1", "password", "firstName1", "lastName1" , new Date());
		faceFaceUser1 = userRepo.save(faceFaceUser1);
		userRepo.flush();
		Assert.notNull(faceFaceUser1);


		faceFaceUser2 = new FaceUser("UserMailRepoTest2@gmail.com", "FaceUser2", "password", "firstName2", "lastName2", new Date() );
		faceFaceUser2 = userRepo.save(faceFaceUser2);
		userRepo.flush();
		assertNotNull(faceFaceUser2);


		faceFaceUser3 = new FaceUser("UserMailRepoTest3@gmail.com", "FaceUser3", "password", "firstName3", "lastName3"  , new Date());
		faceFaceUser3 = userRepo.save(faceFaceUser3);
		userRepo.flush();
		Assert.notNull(faceFaceUser3);


		faceFaceUser4 = new FaceUser("UserMailRepoTest4@gmail.com", "FaceUser4", "password", "firstName4", "lastName4"  , new Date());
		faceFaceUser4 = userRepo.save(faceFaceUser4);
		userRepo.flush();
		Assert.notNull(faceFaceUser4);






		//********************* Messages to and from Users *********************

		/* creat mail A from user 1, receiver is user 2 */
		faceFaceMailA = mailRepo.save(new FaceMail("FaceMail text A","subject about Something A", new Date() ));
		mailRepo.flush();
		assertNotNull(faceFaceMailA);

		userReceivedMail = new UserReceivedMail(faceFaceMailA, faceFaceUser2, faceFaceUser1);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* creat mail B from user 1, receiver is user 2 and 3 */
		Set<FaceUser> sendToUsers = new LinkedHashSet<FaceUser>();
		sendToUsers.add(faceFaceUser2);
		sendToUsers.add(faceFaceUser3);
		faceFaceMailB = mailRepo.save(new FaceMail("FaceMail text B", "subject about Something B", new Date() ));
		mailRepo.flush();

		for (FaceUser F: sendToUsers){
			userReceivedMail = new UserReceivedMail(faceFaceMailB, F, faceFaceUser1);
			userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
			userReceivedMailRepo.flush();
			assertNotNull(userReceivedMail);
		}

		//user 2 read mail B
		mailRepo.markReceivedMailByUserIdAndMailIdAsRead(true, faceFaceUser2.getId(), faceFaceMailB.getId() );
		mailRepo.flush();

		/* creat mail C from user 2, receiver is user 1 and 3 */
		faceFaceMailC = new FaceMail("FaceMail text C", "subject about Something C", new Date() );
		faceFaceMailC = mailRepo.save(faceFaceMailC);
		mailRepo.flush();
		assertNotNull(faceFaceMailC);

		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser1, faceFaceUser2);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* send to user 3 */
		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser3, faceFaceUser2);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);


		System.out.println("\n\n-----------------UserMailRepoTest.setUpUserControllerTest-end----------------------------\n\n");

	}



	@Test
	public void testUserMailRelationshipFaceUser(){

		System.out.println("\n-----------------UserMailRepoTest.testUserMailRelationshipFaceUser-start----------------------------\n\n");

		FaceUser testFaceUser11 = userRepo.findOne(faceFaceUser1.getId());
		assertNotNull(testFaceUser11);

		Set<FaceMail> faceMailsUser1 = mailRepo.findAllReceivedMailByUserId(testFaceUser11.getId());
		Assert.notNull(faceMailsUser1);
		Assert.notEmpty(faceMailsUser1);

		FaceUser testFaceUser2 = userRepo.findByEmail(faceFaceUser2.getEmail());
		Assert.notNull(testFaceUser2);

		Set<FaceMail> faceMails1 = mailRepo.findAllReceivedMailByUserId(testFaceUser2.getId());
		Assert.notNull(faceMails1);
		Assert.notEmpty(faceMails1);

		//test to fiend one unique mail by id
		FaceMail faceMailsread = mailRepo.findOne(faceMails1.iterator().next().getId());
		assertNotNull(faceMailsread);


		//check all Unread Mails
		Set<FaceMail> user2UnReadFaceMails = mailRepo.findAllUnReadReceivedMailById(testFaceUser2.getId());
		assertNotNull(user2UnReadFaceMails);
		Assert.isTrue(user2UnReadFaceMails.size() == 1);

		Set<FaceMail> userReceivedMail1 = mailRepo.findAllMailByAuthorAndReceivingUserId(testFaceUser11.getId(), testFaceUser2.getId() );
		assertNotNull(userReceivedMail1);
		Assert.isTrue( userReceivedMail1.size() == 2 );

		//check if MailX is read
		Set<FaceMail> readUser2FaceMail = mailRepo.findAllReadReceivedMailByUserId(testFaceUser2.getId());
		assertNotNull(readUser2FaceMail);
		Assert.isTrue(readUser2FaceMail.size() == 1);

		//their should be 1 read mail adn that mail should b Mail B, everything else while throw BUILD FAILURE
		readUser2FaceMail.stream().forEach(readMail -> Assert.isTrue(readMail.getId().equals(faceFaceMailB.getId())));



		System.out.println("\n-----------------UserMailRepoTest.testUserMailRelationshipFaceUser-end----------------------------\n\n");

	}

	@After
	public void tearDown() throws Exception {

		System.out.println("\n\n-----------------UserMailRepoTest.tearDown-start----------------------------\n\n");

		//remove all received mails from user 1
		userReceivedMailRepo.deleteReceivedMailByUserId(faceFaceUser1.getId());
		userReceivedMailRepo.flush();

		//remove user 1 this will remove all connection to other entity's
		userRepo.delete(faceFaceUser1.getId());
		userRepo.flush();


		//remove all received mails from user 2
		userReceivedMailRepo.deleteReceivedMailByUserId(faceFaceUser2.getId());
		userReceivedMailRepo.flush();

		//remove user 3 this will remove all connection to other entity's
		userReceivedMailRepo.deleteReceivedMailByUserId(faceFaceUser3.getId());
		userReceivedMailRepo.flush();


//		remove all mails from database
		mailRepo.delete(faceFaceMailA.getId());
		mailRepo.flush();
		mailRepo.delete(faceFaceMailB.getId());
		mailRepo.flush();
		mailRepo.delete(faceFaceMailC.getId());
		mailRepo.flush();



		//remove the remaining users from database
		userRepo.delete(faceFaceUser3.getId());
		userRepo.flush();
		userRepo.delete(faceFaceUser2.getId());
		userRepo.flush();
		userRepo.delete(faceFaceUser4.getId());
		userRepo.flush();


		System.out.println("\n\n-----------------UserMailRepoTest.tearDown-end----------------------------\n\n");

	}

//	@Autowired
//	private AuthorityRepository authorityRepo;
//	@After
//	public void clean() throws Exception{
//		authorityRepo.delete(1L);
//		authorityRepo.delete(2L);
//		authorityRepo.delete(3L);
//		authorityRepo.flush();
//	}

}