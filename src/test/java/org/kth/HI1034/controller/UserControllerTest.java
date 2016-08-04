package org.kth.HI1034.controller;

import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.JWT.TokenJose4jUtils;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.entity.FaceMail;
import org.kth.HI1034.model.domain.entity.FriendRequest;
import org.kth.HI1034.model.domain.entity.UserFriends.UserFriend;
import org.kth.HI1034.model.domain.entity.UserReceivedMail;
import org.kth.HI1034.model.domain.entity.authority.Authority;
import org.kth.HI1034.model.domain.entity.authority.AuthorityRepository;
import org.kth.HI1034.model.domain.entity.authority.UserAuthorityRepository;
import org.kth.HI1034.model.domain.entity.post.FacePost;
import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.kth.HI1034.model.domain.entity.user.UserDetached;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKey;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.repository.FaceMailRepository;
import org.kth.HI1034.model.domain.repository.FaceUserRepository;
import org.kth.HI1034.model.domain.repository.FriendRequestRepository;
import org.kth.HI1034.model.domain.entity.UserFriends.UserFriendRepository;
import org.kth.HI1034.model.domain.keyUserServer.UserKeyRepository;
import org.kth.HI1034.model.domain.repository.UserReceivedMailRepository;
import org.kth.HI1034.model.domain.repository.post.PostRepository;
import org.kth.HI1034.model.domain.repository.post.PostUserRepository;
import org.kth.HI1034.model.pojo.FaceuserPojo;
import org.kth.HI1034.security.util.CipherUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.MediaTypes;
import org.kth.HI1034.util.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationWar.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {


	@Autowired
	private AuthorityRepository authorityRepo;

	@Autowired
	private UserAuthorityRepository userAuthorityRepos;

	@Autowired
	private UserKeyRepository userServerKeyRepository;

	@Autowired
	private FaceUserRepository userRepo;
	private List<FaceuserPojo> userPojoList = new ArrayList<>();
	private List<FaceUser> faceUserList = new ArrayList<>();
	private FaceUser user;

	private FaceUser faceFaceUser2;
	private FaceUser faceFaceUser3;
	private FaceUser faceFaceUser4;
	private List<UserServerKeyPojo> userServerKeyPojos = new ArrayList<>();
	private List<UserServerKey> userServerKeys = new ArrayList<>();

	@Autowired
	private FaceMailRepository mailRepo;
	private UserReceivedMail userReceivedMail;
	private FaceMail faceFaceMailA;
	private FaceMail faceFaceMailB;
	private FaceMail faceFaceMailC;

	@Autowired
	private UserReceivedMailRepository userReceivedMailRepo;

	@Autowired
	private PostRepository postRepo;
	private List<FacePost> postList = new ArrayList<>();

	@Autowired
	private PostUserRepository postUserRepo;
	private List<UserDetached> userDetachedList = new ArrayList<>();

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private AppPublicKeys appPublicKeys;

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
		System.out.println("\n\n----------------- UserControllerTest.setUp-start ----------------------------\n\n");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		appPublicKeys = GsonX.gson.fromJson
				(
						this.mockMvc.perform(get("/getAppPublicKey")
								.accept(MediaTypes.JsonUtf8))
								.andReturn().getResponse().getContentAsString()

						, AppPublicKeys.class
				);

		assertThat(appPublicKeys).isNotNull();
		assertThat(appPublicKeys.getPublicEllipticWebKeyAsJson()).isNotNull();
		assertThat(appPublicKeys.getPublicRsaWebKeyAsJson()).isNotNull();


		if (authorityRepo.findOneByUserRole(Role.ROLE_USER.toString()) == null) {
			List<Authority> authoritys = new ArrayList<>();
			authoritys.add(new Authority(Role.ROLE_USER));
			authoritys.add(new Authority(Role.ROLE_ADMIN));
			authoritys.add(new Authority(Role.ROLE_SUPER_ADMIN));
			authoritys = authorityRepo.save(authoritys);
			authorityRepo.flush();
			assertThat(authoritys).isNotNull();
		}


		//------------ creating Users -----------
		userPojoList.add(new FaceuserPojo("UserFriendRepoTest0@gmail.com", "FaceUser0", "password", "firstName0", "lastName0", new Date()));
		userPojoList.add(new FaceuserPojo("UserFriendRepoTest1@gmail.com", "FaceUser1", "password", "firstName1", "lastName1", new Date()));
		userPojoList.add(new FaceuserPojo("UserFriendRepoTes2t@gmail.com", "FaceUser2", "password", "firstName2", "lastName2", new Date()));
		userPojoList.add(new FaceuserPojo("UserFriendRepoTest3@gmail.com", "FaceUser3", "password", "firstName3", "lastName3", new Date()));
		userPojoList.add(new FaceuserPojo("UserFriendRepoTest4@gmail.com", "FaceUser4", "password", "firstName4", "lastName4", new Date()));

		List<FaceuserPojo> userPojoListTemp = new ArrayList<>();

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


		faceFaceUser2 = faceUserList.get(1);
		faceFaceUser3 = faceUserList.get(2);
		faceFaceUser4 = faceUserList.get(3);


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

		userReceivedMail = new UserReceivedMail(faceFaceMailA, faceFaceUser2, faceUserList.get(0));
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* creat mail B from user 1, receiver is user 2 and 3 */
		Set<FaceUser> sendToUsers = new LinkedHashSet<FaceUser>();
		sendToUsers.add(faceFaceUser2);
		sendToUsers.add(faceFaceUser3);
		faceFaceMailB = mailRepo.save(new FaceMail("FaceMail text B", "subject about Something B", new Date()));
		mailRepo.flush();

		for (FaceUser FU : sendToUsers) {
			userReceivedMail = new UserReceivedMail(faceFaceMailB, FU, faceUserList.get(0));
			userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
			userReceivedMailRepo.flush();
			assertNotNull(userReceivedMail);
		}

		//user 2 read mail B
		mailRepo.markReceivedMailByUserIdAndMailIdAsRead(true, faceFaceUser2.getId(), faceFaceMailB.getId());
		mailRepo.flush();

		/* creat mail C from user 2, receiver is user 1 and 3 */
		faceFaceMailC = new FaceMail("FaceMail text C", "subject about Something C", new Date());
		faceFaceMailC = mailRepo.save(faceFaceMailC);
		mailRepo.flush();
		assertNotNull(faceFaceMailC);

		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceUserList.get(0), faceFaceUser2);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		/* send to user 3 */
		userReceivedMail = new UserReceivedMail(faceFaceMailB, faceFaceUser3, faceFaceUser2);
		userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
		userReceivedMailRepo.flush();
		assertNotNull(userReceivedMail);

		System.out.println("\n\n----------------- UserControllerTest.setUp-end ----------------------------\n\n");

	}


	@Test
	public void A_registerUser() throws Exception {

		System.out.println("\n\n----------------- UserControllerTest.A_registerUser-start ----------------------------\n\n");

		assertThat(appPublicKeys).isNotNull();


		EllipticCurveJsonWebKey jsonWebKey = TokenJose4jUtils.JsonWebKeyUtil.generateEllipticWebKey();
		assertThat(jsonWebKey).isNotNull();
//		jsonWebKey.setAlgorithm("EC");

//		String password = PasswordSaltUtil.encryptSalt( "password", "registerTest@gmail.com"+"password" );

		FaceuserPojo faceuserPojo = new FaceuserPojo("registerTest@gmail.com", "myUserName", "password", "MyFirstName", "MyLastName", new Date());

		TokenPojo tokenPojo = new TokenPojo();

		tokenPojo.setIssuer("registerTest@gmail.com");
		tokenPojo.setAudience("fackebook.se");
		tokenPojo.setSenderKey(TokenJose4jUtils.JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey));
		tokenPojo.setReceiverKey(appPublicKeys.getPublicEllipticWebKeyAsJson());
		tokenPojo.setSubject("register");

		//use the servers public key. to encypt the secret key
		PublicKey serverPublicKey = KeyUtil.getPublicKeyFromString(appPublicKeys.getPublicKey());
		SecretKey secretKey = KeyUtil.SymmetricKey.generateSecretAesKey(16);

		// the encrypted private part of the key kan only be opened with the private part of the key
		String encryptedSharedKey = CipherUtils.encryptWithPublicKey(KeyUtil.SymmetricKey.getKeyAsString(secretKey), serverPublicKey);

		faceuserPojo.setUserServerKeyPojo(new UserServerKeyPojo("registerTest@gmail.com", encryptedSharedKey));

		String JWT = TokenJose4jUtils.EllipticJWT.ProduceJWTVerifyAndEncrypt(
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				TokenJose4jUtils.JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey),
				appPublicKeys.getPublicEllipticWebKeyAsJson(),
				faceuserPojo.toString()
		);

		tokenPojo.setToken(JWT);

		String theTokenPoje = this.mockMvc.perform
				(
						post("/user/register")
								.contentType(MediaTypes.JsonUtf8)
								.content(tokenPojo.toString())
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.JsonUtf8))
				.andReturn().getResponse().getContentAsString();

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
		faceuserPojo = GsonX.gson.fromJson(jsonWebPayload, FaceuserPojo.class);
		assertThat(faceuserPojo).isNotNull();


		System.out.println("\n\n----------------- UserControllerTest.A_registerUser-end ----------------------------\n\n");
	}


	@Test
	public void B_Login() throws Exception {

		System.out.println("\n\n----------------- UserControllerTest.B_Login-start ----------------------------\n\n");

		// 1 generate a new SharedKey (sendersSecretKey) and encrypt is with servers PublicKey

		// 2 create a user and password

		// 3 creat the tokenPojo and set TokenPojo.setSenderKey() with the encryptedKey (sendersEncryptedSecretKey)

		// 4 create a token (keyUserServer) with sendersSecretKey created in from -> 1, set tokenPojo as payload

		// 5 tokenPojo.setToken(keyUserServer); and send tokenPojo and send az request body to :post ../login

		// 6    get the HTTP response and check header and get the
		//      payload from the token (keyUserServer) with with SharedKey (sendersSecretKey) created in from -> 1

		// 7 use the header to call a somewhere where you can test that are Authenticated and Authorized to do something.


		/** 1 generate a new shared key and encrypt is with servers PublicKey */
		SecretKey sendersSecretKey = KeyUtil.SymmetricKey.generateSecretAesKey(16);
		String sendersSecretKeyString = KeyUtil.SymmetricKey.getKeyAsString(sendersSecretKey);
		PublicKey appPublicKey = KeyUtil.getPublicKeyFromString(appPublicKeys.getPublicKey());
		String sendersEncryptedSecretKey = CipherUtils.encryptWithPublicKey(sendersSecretKeyString, appPublicKey);


		/** 2 create a user and salt the password */
		FaceuserPojo faceuserPojoToSend = new FaceuserPojo("registerTest@gmail.com", "password");


		/** 3 creat the tokenPojo and set TokenPojo.setSenderKey() with the encryptedKey (sendersEncryptedSecretKey) */
		TokenPojo createdTokenPojo = new TokenPojo();
		createdTokenPojo.setSubject("login");
		createdTokenPojo.setIssuer("registerTest@gmail.com");
		createdTokenPojo.setAudience("fackeBook.se");
		createdTokenPojo.setReceiverKey(appPublicKeys.getPublicKey());
		createdTokenPojo.setSenderKey(sendersEncryptedSecretKey);

		Map<String, String> mapPayload = new HashMap<>();
		mapPayload.put("payload", faceuserPojoToSend.toString());


		/** 4 create a token with sendersSecretKey created in from -> 1, set tokenPojo as payload */
		String jwt = TokenJose4jUtils.SymmetricJWT.generateJWT(
				sendersSecretKey,
				createdTokenPojo.getIssuer(),
				createdTokenPojo.getAudience(),
				createdTokenPojo.getSubject(),
				mapPayload, 10
		);

		createdTokenPojo.setToken(jwt);

		/** 5 tokenPojo.setToken(keyUserServer); and send tokenPojo and send ass rquest body to :post ../login  */
		MockHttpServletResponse theResponse = this.mockMvc.perform
				(
						post("/user/login")
								.contentType(MediaTypes.JsonUtf8)
								.content(createdTokenPojo.toString())
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.JsonUtf8))
				.andReturn().getResponse();


		assertThat(theResponse).isNotNull();


		/** 6 get the HTTP response and check header and body */
		String header = theResponse.getHeader("AuthToken");
		assertThat(header).isNotNull();

		TokenPojo receivedTokenPojo = GsonX.gson.fromJson(theResponse.getContentAsString(), TokenPojo.class);
		assertThat(receivedTokenPojo).isNotNull();

		List<String> payloadListToToGetFromToken = new ArrayList<>();
		payloadListToToGetFromToken.add("payload");
		Map<String, String> mapedPayload = new HashMap<>();
		mapedPayload = TokenJose4jUtils.SymmetricJWT.getJwtPayloadList(
				sendersSecretKey,
				receivedTokenPojo.getIssuer(),
				receivedTokenPojo.getAudience(),
				receivedTokenPojo.getSubject(),
				payloadListToToGetFromToken,
				receivedTokenPojo.getToken()
		);
		FaceuserPojo faceuserPojoPayload = GsonX.gson.fromJson(mapedPayload.get("payload"), FaceuserPojo.class);
		assertThat(faceuserPojoPayload).isNotNull();
		assertThat(faceuserPojoPayload.getEmail()).isEqualTo(faceuserPojoToSend.getEmail());



		/** 7 use the header to call a uri where you can test that are Authenticated and Authorized to do something. */

//		MockHttpServletResponse theResponse2 = this.mockMvc.perform
//				(
//						get("/api/getPosts/{email}")
//								.contentType(MediaTypes.JsonUtf8)
//								.content( tokenPojo.toString() )
//				)
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaTypes.JsonUtf8))
//				.andReturn().getResponse().getContentAsString();
//
//
//		assertThat(theResponse2).isNotNull();
//
//
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- LoginControllerTest_147 -------------------------------------\n\n" +
//				"theResponse = \n" + theResponse +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");


//		//remove all Users and that should not affect sent and received posts
//		userRepo.delete(Converter.convert(faceuserPojo));
//		userRepo.flush();

		System.out.println("\n\n----------------- UserControllerTest.B_Login-end ----------------------------\n\n");

	}


	public FaceuserPojo registerUsers(FaceuserPojo faceuserPojo) throws Exception {

		System.out.println("\n\n----------------- UserControllerTest.registerUsers-start ----------------------------\n\n");

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
			throw new Exception("----UserControllerTest.registerUsers-start-------- mockMvc.perform to post(\"/user/register\") -------");
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


		System.out.println("\n\n----------------- UserControllerTest.registerUsers-end ----------------------------\n\n");

		return faceuserPojo;

	}

	@After
	public void tearDown() throws Exception {

		System.out.println("\n\n----------------- UserControllerTest.tearDown-start ----------------------------\n\n");

		try {
			System.out.println("\n\n----------------- UserControllerTest.tearDown-586 ----------------------------\n\n");
			for(FaceUser FU : faceUserList){
				userAuthorityRepos.deleteUserAuthorityByUserID(FU.getId());
			}
			System.out.println("\n\n----------------- UserControllerTest.tearDown-589 ----------------------------\n\n");
			for(UserServerKey USK : userServerKeys){
				userServerKeyRepository.deleteByUserEmail(USK.getEmail());
			}

			System.out.println("\n\n----------------- UserControllerTest.tearDown-594 ----------------------------\n\n");

			//remove all received mails from user 1
			userReceivedMailRepo.deleteReceivedMailByUserId(faceUserList.get(0).getId());
			userReceivedMailRepo.flush();

			System.out.println("\n\n----------------- UserControllerTest.tearDown-600 ----------------------------\n\n");

			//remove all received mails from user 2
			userReceivedMailRepo.deleteReceivedMailByUserId(faceUserList.get(1).getId());
			userReceivedMailRepo.flush();

			System.out.println("\n\n----------------- UserControllerTest.tearDown-611 ----------------------------\n\n");

			//remove user 3 this will remove all connection to other entity's
			userReceivedMailRepo.deleteReceivedMailByUserId(faceUserList.get(2).getId());
			userReceivedMailRepo.flush();

			System.out.println("\n\n----------------- UserControllerTest.tearDown-617 ----------------------------\n\n");

//		remove all mails from database
			mailRepo.delete(faceFaceMailA.getId());
			mailRepo.flush();
			mailRepo.delete(faceFaceMailB.getId());
			mailRepo.flush();
			mailRepo.delete(faceFaceMailC.getId());
			mailRepo.flush();

			System.out.println("\n\n----------------- UserControllerTest.tearDown-627 ----------------------------\n\n");



			//remove all Users and that should not affect sent and received posts
			for(FaceUser FU: faceUserList){
				friendRequestRepo.deleteToOrFromByUserId(FU.getId());
				friendRequestRepo.flush();
				userFriendRepo.deleteAllFriendsBuUserId(FU.getId());
				userFriendRepo.flush();
				userRepo.delete(FU.getId());
				userRepo.flush();
			}

			System.out.println("\n\n----------------- UserControllerTest.tearDown-633 ----------------------------\n\n");

			//remove the posts
			postRepo.delete(postList);
			postRepo.flush();


			System.out.println("\n\n----------------- UserControllerTest.tearDown-639 ----------------------------\n\n");
			// remove the detached user information
			postUserRepo.delete(userDetachedList);
			postUserRepo.flush();



		} catch (Exception e) {
			System.out.println("\n\n----------------- USERCONTROLLERtEST.TEARDOWN-EXEPTION ----------------------------\n\n");
			e.printStackTrace();
			throw new  Exception(
					"\n\n----------------- e.printStackTrace() ----------------------------\n\n" + Arrays.toString(e.getStackTrace())+
					"\n\n----------------- e.getMessage() ----------------------------\n\n" + e.getMessage());

		}

		System.out.println("\n\n----------------- UserControllerTest.tearDown-end ----------------------------\n\n");
	}


}