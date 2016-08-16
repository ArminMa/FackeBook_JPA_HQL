package org.kth.HI1034.controller;

import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.security.JWT.TokenJose4jUtils;
import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.UserFriends.UserFriend;
import org.kth.HI1034.model.domain.UserFriends.UserFriendRepository;
import org.kth.HI1034.model.domain.authority.Authority;
import org.kth.HI1034.model.domain.authority.AuthorityRepository;
import org.kth.HI1034.model.domain.authority.UserAuthorityRepository;
import org.kth.HI1034.model.domain.faceMail.FaceMail;
import org.kth.HI1034.model.domain.faceMail.FaceMailRepository;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMail;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailRepository;
import org.kth.HI1034.model.domain.frienRequest.FriendRequest;
import org.kth.HI1034.model.domain.frienRequest.FriendRequestRepository;
import org.kth.HI1034.model.domain.keyUserServer.UserKeyRepository;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKey;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.post.FacePost;
import org.kth.HI1034.model.domain.post.PostRepository;
import org.kth.HI1034.model.domain.post.UserDetachedRepository;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.model.domain.post.UserDetached;
import org.kth.HI1034.security.util.CipherUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.MediaTypes;
import org.kth.HI1034.util.enums.Role;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class PreTest {

	public PreTest() {
	}


	public List<FaceuserPojo> userPojoList = new ArrayList<>();
	public List<FaceUser> faceUserList = new ArrayList<>();
	public FaceUser user;

	public List<UserServerKeyPojo> userServerKeyPojos = new ArrayList<>();
	public List<UserServerKey> userServerKeys = new ArrayList<>();


	public UserReceivedMail userReceivedMail;
	public FaceMail faceFaceMailA;
	public FaceMail faceFaceMailB;
	public FaceMail faceFaceMailC;


	public List<FacePost> postList = new ArrayList<>();


	public List<UserDetached> userDetachedList = new ArrayList<>();


	public MockMvc mockMvc;
	public AppPublicKeys appPublicKeys;


	public FriendRequest friendRequest;
	public List<FriendRequest> friendRequests = new ArrayList<>();


	public UserFriend userFriend;
	public List<UserFriend> fromUser3 = new ArrayList<>();


	public WebApplicationContext context;
	public AuthorityRepository authorityRepo;
	public UserAuthorityRepository userAuthorityRepos;
	public UserKeyRepository userServerKeyRepository;
	public FaceUserRepository userRepo;
	public FaceMailRepository mailRepo;
	public UserDetachedRepository postUserRepo;
	public UserReceivedMailRepository userReceivedMailRepo;
	public PostRepository postRepo;
	public UserFriendRepository userFriendRepo;
	public FriendRequestRepository friendRequestRepo;

	public PreTest(
			WebApplicationContext context,
			AuthorityRepository authorityRepo,
			UserAuthorityRepository userAuthorityRepos,
			UserKeyRepository userServerKeyRepository,
			FaceUserRepository userRepo,
			FaceMailRepository mailRepo,
			UserDetachedRepository postUserRepo,
			UserReceivedMailRepository userReceivedMailRepo,
			PostRepository postRepo,
			UserFriendRepository userFriendRepo,
			FriendRequestRepository friendRequestRepo) {

		this.context = context;
		this.authorityRepo = authorityRepo;
		this.userAuthorityRepos = userAuthorityRepos;
		this.userServerKeyRepository = userServerKeyRepository;
		this.userRepo = userRepo;
		this.mailRepo = mailRepo;
		this.postUserRepo = postUserRepo;
		this.userReceivedMailRepo = userReceivedMailRepo;
		this.postRepo = postRepo;
		this.userFriendRepo = userFriendRepo;
		this.friendRequestRepo = friendRequestRepo;
	}

	public static MockMvc mockMvc(WebApplicationContext context) {
		return MockMvcBuilders.webAppContextSetup(context).build();
	}

	public AppPublicKeys getAppPublicKey(MockMvc mockMvc) throws Exception {
		AppPublicKeys appPublicKeys = GsonX.gson.fromJson
				(
						mockMvc.perform(get("/getAppPublicKey")
								.accept(MediaTypes.JsonUtf8))
								.andReturn().getResponse().getContentAsString()

						, AppPublicKeys.class
				);


		return appPublicKeys;
	}

	public void setUpTest() throws Exception {
		System.out.println("\n\n----------------- PreTest.setUpUserControllerTest-start ----------------------------\n\n");
		if (this.mockMvc == null) {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		}


		appPublicKeys = getAppPublicKey(this.mockMvc);
		assertThat(appPublicKeys).isNotNull();
		assertThat(appPublicKeys.getPublicEllipticWebKeyAsJson()).isNotNull();
		assertThat(appPublicKeys.getPublicRsaWebKeyAsJson()).isNotNull();


		creatAuthoritys();




		List<FaceuserPojo> userPojoListTemp = new ArrayList<>();

		// save all the users
		for (FaceuserPojo FP : userPojoList) {
			userPojoListTemp.add(registerUsers(FP));

		}
		userPojoList = userPojoListTemp;

		// todo move to upper for loop and hope for the best
		for (FaceuserPojo FP : userPojoList) {
			userServerKeyPojos.add(FP.getUserServerKeyPojo());
			userServerKeys.add(Converter.convert(FP.getUserServerKeyPojo()));
			faceUserList.add(Converter.convert(FP));
		}


		// each entry is tightly linked to an email and username.
		// a user may be deleted from the system and this will not affect the sent and mail to other users.
		userDetachedList = postUserRepo.save(userDetachedList);
		postUserRepo.flush();
		assertThat(userDetachedList).isNotNull();
		assertThat(userDetachedList.size()).isEqualTo(5);

		postList.add(new FacePost("Post 0", new Date(), faceUserList.get(0), faceUserList.get(0)));
		postList.add(new FacePost("Post 1", new Date(), faceUserList.get(0), faceUserList.get(1)));
		postList = postRepo.save(postList);
		postRepo.flush();

		//------------ friend request from user 0 to user 1 -----------
		friendRequests.add(new FriendRequest(faceUserList.get(0), faceUserList.get(1), new Date()));
		//------------ friend request from user 0 to user 1 -----------
		friendRequests.add(new FriendRequest(faceUserList.get(0), faceUserList.get(2), new Date()));
		//------------ friend request from user 1 to user 2 -----------
		friendRequests.add(new FriendRequest(faceUserList.get(1), faceUserList.get(2), new Date()));

		friendRequests = friendRequestRepo.save(friendRequests);
		friendRequestRepo.flush();
		Assert.notNull(friendRequests);
		Assert.isTrue(friendRequests.size() == 3);

		//------------- user 2 and 3 are friends------------
		userFriend = new UserFriend(faceUserList.get(2), faceUserList.get(3), new Date());
		userFriendRepo.save(userFriend);
		userFriendRepo.flush();

		//------------- user 4 and 3 are friends------------
		userFriend = new UserFriend(faceUserList.get(4), faceUserList.get(3), new Date());
		userFriend = userFriendRepo.save(userFriend);
		userFriendRepo.flush();
		assertThat(userFriend).isNotNull();
		assertThat(userFriend.getPk().getAccepter().getId()).isEqualTo(userPojoList.get(4).getId());

		//------------- user 3 is friends with user 0,1 ------------
		fromUser3.add(new UserFriend(faceUserList.get(3), faceUserList.get(0), new Date()));
		fromUser3.add(new UserFriend(faceUserList.get(3), faceUserList.get(1), new Date()));
		fromUser3 = userFriendRepo.save(fromUser3);
		userFriendRepo.flush();
		assertThat(fromUser3).isNotNull();
		assertThat(fromUser3).isNotEmpty();
		assertThat(fromUser3).hasSize(2);

		//********************* Messages to and from Users *********************

		/* creat mail A from user 1, receiver is user 2 */
		faceFaceMailA = mailRepo.save(new FaceMail("FaceMail text A", "subject about Something A", new Date()));
		mailRepo.flush();
		assertNotNull(faceFaceMailA);

		userReceivedMail = new UserReceivedMail(faceFaceMailA, faceUserList.get(1), faceUserList.get(0));
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* creat mail B from user 1, receiver is user 2 and 3 */
		Set<FaceUser> sendToUsers = new LinkedHashSet<FaceUser>();
		sendToUsers.add(faceUserList.get(1));
		sendToUsers.add(faceUserList.get(2));
		faceFaceMailB = mailRepo.save(new FaceMail("FaceMail text B", "subject about Something B", new Date()));
		mailRepo.flush();

		for (FaceUser FU : sendToUsers) {
			userReceivedMail = new UserReceivedMail(faceFaceMailB, FU, faceUserList.get(0));
			userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
			userReceivedMailRepo.flush();
			assertNotNull(userReceivedMail);
		}

		//user 2 read mail B
		mailRepo.markReceivedMailByUserIdAndMailIdAsRead(true, faceUserList.get(1).getId(), faceFaceMailB.getId());
		mailRepo.flush();

		/* creat mail C from user 2, receiver is user 1 and 3 */
		faceFaceMailC = new FaceMail("FaceMail text C", "subject about Something C", new Date());
		faceFaceMailC = mailRepo.save(faceFaceMailC);
		mailRepo.flush();
		assertNotNull(faceFaceMailC);

		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceUserList.get(0), faceUserList.get(1));
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* send to user 3 */
		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceUserList.get(2), faceUserList.get(1));
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		System.out.println("\n\n----------------- PreTest.setUpUserControllerTest-end ----------------------------\n\n");

	}





	public void tearDown() throws Exception {

		System.out.println("\n\n----------------- PreTest.tearDown-start ----------------------------\n\n");

		try {

			for (FaceUser FU : faceUserList) {
				userAuthorityRepos.deleteUserAuthorityByUserID(FU.getId());
			}

			for (UserServerKey USK : userServerKeys) {
				userServerKeyRepository.deleteByUserEmail(USK.getEmail());
			}


			//remove all received mails from user 1
			userReceivedMailRepo.deleteReceivedMailByUserId(faceUserList.get(0).getId());
			userReceivedMailRepo.flush();


			//remove all received mails from user 2
			userReceivedMailRepo.deleteReceivedMailByUserId(faceUserList.get(1).getId());
			userReceivedMailRepo.flush();


			//remove user 3 this will remove all connection to other entity's
			userReceivedMailRepo.deleteReceivedMailByUserId(faceUserList.get(2).getId());
			userReceivedMailRepo.flush();


//		remove all mails from database
			mailRepo.delete(faceFaceMailA.getId());
			mailRepo.flush();
			mailRepo.delete(faceFaceMailB.getId());
			mailRepo.flush();
			mailRepo.delete(faceFaceMailC.getId());
			mailRepo.flush();


			//remove all Users and that should not affect sent and received posts
			for (FaceUser FU : faceUserList) {
				friendRequestRepo.deleteToOrFromByUserId(FU.getId());
				friendRequestRepo.flush();
				userFriendRepo.deleteAllFriendsBuUserId(FU.getId());
				userFriendRepo.flush();
				userRepo.delete(FU.getId());
				userRepo.flush();
			}

			//remove the posts
			postRepo.delete(postList);
			postRepo.flush();


			// remove the detached user information
			postUserRepo.delete(userDetachedList);
			postUserRepo.flush();

			userRepo.delete( faceUserList );
			assertThat(faceUserList).isNotEmpty();

			userRepo.flush();


		} catch (Exception e) {
			System.out.println("\n\n----------------- PreTest.TEARDOWN-EXEPTION.372 ----------------------------\n\n");
			e.printStackTrace();
			throw new Exception(
					"\n\n----------------- e.printStackTrace() ----------------------------\n\n" + Arrays.toString(e.getStackTrace()) +
							"\n\n----------------- e.getMessage() ----------------------------\n\n" + e.getMessage());

		}

		System.out.println("\n\n----------------- PreTest.tearDown-end ----------------------------\n\n");
	}

	public void setUpTestUserControllerTest(List<FaceuserPojo> faceuserPojoList) throws Exception {

		System.out.println("\n\n----------------- PreTest.setUpUserControllerTest-start ----------------------------\n\n");


		if (this.mockMvc == null) {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		}

		userPojoList = faceuserPojoList;
		appPublicKeys = getAppPublicKey(this.mockMvc);
		assertThat(appPublicKeys).isNotNull();
		assertThat(appPublicKeys.getPublicEllipticWebKeyAsJson()).isNotNull();
		assertThat(appPublicKeys.getPublicRsaWebKeyAsJson()).isNotNull();

		creatAuthoritys();

		//------------ creating Users -----------


		List<FaceuserPojo> userPojoListTemp = new ArrayList<>();

		for (FaceuserPojo FP : userPojoList) {
			userPojoListTemp.add(registerUsers(FP));
			faceUserList.add(Converter.convert(FP));

		}
		userPojoList = userPojoListTemp;

//		postList.add( new FacePost(
//				"Post 1: \nfrom " + faceUserList.get(0).getEmail() + " \nto " +  faceUserList.get(0).getEmail(),
//				new Date(), faceUserList.get(0),
//				faceUserList.get(0))  ) ;
//		postList.add( new FacePost(
//				"Post 2: \nfrom " + faceUserList.get(0).getEmail() + " \nto " +  faceUserList.get(0).getEmail(),
//				new Date(), faceUserList.get(0),
//				faceUserList.get(0))  ) ;
//		postList = postRepo.save( postList);
//		postRepo.flush();
//		assertThat(postList).isNotNull();
//		assertThat(postList ).isNotEmpty();
//		assertThat(postList.size()).isEqualTo(2);

	}



	public void tearDownUserControllerTest() throws Exception {

		System.out.println("\n\n----------------- PreTest.tearDownUserControllerTest-start ----------------------------\n\n");

		try {


			assertThat(userPojoList).isNotNull();
			List<UserDetached> userDetacheds = new ArrayList<>();

			for (FaceuserPojo FU : userPojoList) {
				userAuthorityRepos.deleteUserAuthorityByUserID(FU.getId());
				userAuthorityRepos.flush();
				userServerKeyRepository.deleteByUserEmail(FU.getEmail());
				userServerKeyRepository.flush();

				FaceuserPojo faceuserPojoToPrintForTest = Converter.convert( userRepo.findOne(FU.getId()) );

				System.out.println("\n\n\n\n" +
						"----------------------------------- PreTest.tearDownUserControllerTest.428 -------------------------------------" +
						"\n\nfaceuserPojoToPrintForTest = " + faceuserPojoToPrintForTest +
						"\n" +
						"------------------------------------------------------------------------\n\n\n\n\n");

				userRepo.delete( FU.getId() );
				userRepo.flush();

				postUserRepo.deleteUserDetachedByAuthorEmail(FU.getEmail());
			}

//				for(UserDetached UD: userDetacheds){
//					postUserRepo.delete(UD.getId());
//				}

//				postRepo.deleteAll();
//				postRepo.flush();
//				postUserRepo.deleteAll();
//				postUserRepo.flush();

			FaceUser faceUser = userRepo.findOne(userPojoList.get(0).getId());
			assertThat(faceUser).isNull();



		} catch (Exception e) {
			System.out.println("\n\n----------------- PreTest.TEARDOWN-EXEPTION.451 ----------------------------\n\n");
			e.printStackTrace();
			throw new Exception(
					"\n\n----------------- e.printStackTrace() ----------------------------\n\n" + Arrays.toString(e.getStackTrace()) +
							"\n\n----------------- e.getMessage() ----------------------------\n\n" + e.getMessage());

		}

		System.out.println("\n\n----------------- PreTest.tearDownUserControllerTest-end ----------------------------\n\n");


	}



	public FaceuserPojo registerUsers(FaceuserPojo faceuserPojo) throws Exception {

		System.out.println("\n\n----------------- PreTest.registerUsers-start ----------------------------\n\n");

		EllipticCurveJsonWebKey jsonWebKey = TokenJose4jUtils.JsonWebKeyUtil.generateEllipticWebKey();
		assertThat(jsonWebKey).isNotNull();
		TokenPojo tokenPojo = new TokenPojo();

		tokenPojo.setIssuer(faceuserPojo.getEmail());
		tokenPojo.setAudience("fackebook.se");
		tokenPojo.setSenderKey(TokenJose4jUtils.JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey));
		tokenPojo.setReceiverKey(appPublicKeys.getPublicEllipticWebKeyAsJson());
		tokenPojo.setSubject("register");

		//use the servers public key. to encypt the secret key
		PublicKey serverPublicKey = KeyUtil.getPublicKeyFromString(appPublicKeys.getPublicKey());
		SecretKey secretKey = KeyUtil.SymmetricKey.generateSecretAesKey(16);

		// the encrypted private part of the key kan only be opened with the private part of the key
		String encryptedSharedKey = null;
		String keyString = KeyUtil.SymmetricKey.getKeyAsString(secretKey);

		encryptedSharedKey = CipherUtils.encryptWithPublicKey(keyString, serverPublicKey);


		faceuserPojo.setUserServerKeyPojo(new UserServerKeyPojo(faceuserPojo.getEmail(), encryptedSharedKey));

		String JWT = TokenJose4jUtils.EllipticJWT.ProduceJWTVerifyAndEncrypt(
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				TokenJose4jUtils.JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey),
				appPublicKeys.getPublicEllipticWebKeyAsJson(),
				faceuserPojo.toString()
		);

		tokenPojo.setToken(JWT);

		String theTokenPoje = null;
		try {
			theTokenPoje = this.mockMvc.perform
					(
							post("/user/register")
									.contentType(MediaTypes.JsonUtf8)
									.content(tokenPojo.toString())
					)
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaTypes.JsonUtf8))
					.andReturn().getResponse().getContentAsString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("----PreTest.registerUsers-start-------- mockMvc.perform to post(\"/user/register\") -------");
		}

		assertThat(theTokenPoje).isNotNull();

		tokenPojo = GsonX.gson.fromJson(theTokenPoje, TokenPojo.class);

		String payloadKey = "payload";
		faceuserPojo = GsonX.gson.fromJson(theTokenPoje, FaceuserPojo.class);
		assertThat(faceuserPojo).isNotNull();

		String jsonWebPayload = TokenJose4jUtils.SymmetricJWT.getJwtPayload(
				secretKey,
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				payloadKey,
				tokenPojo.getToken()
		);
		assertThat(jsonWebPayload).isNotNull();
		faceuserPojo = GsonX.gson.fromJson(jsonWebPayload, FaceuserPojo.class);
		assertThat(faceuserPojo).isNotNull();


		System.out.println("\n\n----------------- PreTest.registerUsers-end ----------------------------\n\n");

		return faceuserPojo;

	}

	private void creatAuthoritys() {
		if (authorityRepo.findOneByUserRole(Role.ROLE_USER.toString()) == null) {
			List<Authority> authoritys = new ArrayList<>();
			authoritys.add(new Authority(Role.ROLE_USER));
			authoritys.add(new Authority(Role.ROLE_ADMIN));
			authoritys.add(new Authority(Role.ROLE_SUPER_ADMIN));
			authoritys = authorityRepo.save(authoritys);
			authorityRepo.flush();
			assertThat(authoritys).isNotNull();
		}
	}

	public List<FaceuserPojo> getUserPojoList() {
		return userPojoList;
	}

	public void setUserPojoList(List<FaceuserPojo> userPojoList) {
		this.userPojoList = userPojoList;
	}

	public List<FaceUser> getFaceUserList() {
		return faceUserList;
	}

	public void setFaceUserList(List<FaceUser> faceUserList) {
		this.faceUserList = faceUserList;
	}

	public List<UserDetached> getUserDetachedList() {
		return userDetachedList;
	}

	public void setUserDetachedList(List<UserDetached> userDetachedList) {
		this.userDetachedList = userDetachedList;
	}
}
