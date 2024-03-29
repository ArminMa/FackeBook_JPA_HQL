package org.kth.HI1034.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.security.JWT.TokenJose4jUtils;
import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.model.domain.UserFriends.UserFriendRepository;
import org.kth.HI1034.model.domain.authority.AuthorityRepository;
import org.kth.HI1034.model.domain.authority.UserAuthorityRepository;
import org.kth.HI1034.model.domain.faceMail.FaceMailRepository;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailRepository;
import org.kth.HI1034.model.domain.frienRequest.FriendRequestRepository;
import org.kth.HI1034.model.domain.keyUserServer.UserKeyRepository;
import org.kth.HI1034.model.domain.post.PostRepository;
import org.kth.HI1034.model.domain.post.UserDetachedRepository;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.security.util.CipherUtils;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private PreTest preTest;

	@Autowired
	public WebApplicationContext context;


	@Autowired
	public AuthorityRepository authorityRepo;

	@Autowired
	public UserAuthorityRepository userAuthorityRepos;

	@Autowired
	public UserKeyRepository userServerKeyRepository;

	@Autowired
	public FaceUserRepository userRepo;

	@Autowired
	public FaceMailRepository mailRepo;

	@Autowired
	public UserDetachedRepository postUserRepo;

	@Autowired
	public UserReceivedMailRepository userReceivedMailRepo;

	@Autowired
	public PostRepository postRepo;

	@Autowired
	public UserFriendRepository userFriendRepo;

	@Autowired
	public FriendRequestRepository friendRequestRepo;



	@Before
	public void setUp() throws Exception {
		System.out.println("\n\n----------------- UserControllerTest.setUpUserControllerTest-start ----------------------------\n\n");

		//------------ creating Users -----------
		List<FaceuserPojo> faceuserPojoList = new ArrayList<>();
		faceuserPojoList.add(new FaceuserPojo("registerAndLoginTest@gmail.com", "myUserName", "password", "MyFirstName", "MyLastName", new Date()));

		preTest = new PreTest(
				context,
				authorityRepo,
				userAuthorityRepos,
				userServerKeyRepository,
				userRepo,
				mailRepo,
				postUserRepo,
				userReceivedMailRepo,
				postRepo,
				userFriendRepo,
				friendRequestRepo);
//		preTest.setUpUserControllerTest();


		preTest.setUpTestUserControllerTest(faceuserPojoList);

		System.out.println("\n\n----------------- UserControllerTest.setUpUserControllerTest-end ----------------------------\n\n");

	}

	@After
	public void tearDown() throws Exception {

		System.out.println("\n\n----------------- UserControllerTest.tearDown-start ----------------------------\n\n");

		preTest.tearDownUserControllerTest();

		System.out.println("\n\n----------------- UserControllerTest.tearDown-end ----------------------------\n\n");
	}


	@Test
	public void A_Login() throws Exception {

		System.out.println("\n\n----------------- UserControllerTest.A_Login-start ----------------------------\n\n");



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
		PublicKey appPublicKey = KeyUtil.getPublicKeyFromString(preTest.appPublicKeys.getPublicKey());
		String sendersEncryptedSecretKey = CipherUtils.encryptWithPublicKey(sendersSecretKeyString, appPublicKey);


		/** 2 create a user and salt the password */
		FaceuserPojo faceuserPojoToSend = new FaceuserPojo(preTest.getUserPojoList().get(0).getEmail(), "password");


		/** 3 creat the tokenPojo and set TokenPojo.setSenderKey() with the encryptedKey (sendersEncryptedSecretKey) */
		TokenPojo createdTokenPojo = new TokenPojo();
		createdTokenPojo.setSubject("login");
		createdTokenPojo.setIssuer(preTest.getUserPojoList().get(0).getEmail());
		createdTokenPojo.setAudience("fackeBook.se");
		createdTokenPojo.setReceiverKey(preTest.appPublicKeys.getPublicKey());
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
		MockHttpServletResponse theResponse = this.preTest.mockMvc.perform
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
		String tokenHeader = theResponse.getHeader("AuthToken");
		String UserInfotokenHeader = theResponse.getHeader("UserToken");
		assertThat(tokenHeader).isNotNull();
		assertThat(UserInfotokenHeader).isNotNull();

		TokenPojo receivedTokenPojo = GsonX.gson.fromJson(theResponse.getContentAsString(), TokenPojo.class);
		assertThat(receivedTokenPojo).isNotNull();

		String payloadKeyToGetFromToken = "payload";


		String facePojoJson = TokenJose4jUtils.SymmetricJWT.getJwtPayload(
				sendersSecretKey,
				receivedTokenPojo.getIssuer(),
				receivedTokenPojo.getAudience(),
				receivedTokenPojo.getSubject(),
				payloadKeyToGetFromToken,
				receivedTokenPojo.getToken()
		);

		assertThat(facePojoJson).isNotNull();

		System.out.println("\n\n----------------- UserControllerTest.A_Login().232 ----------------------------" +
				"\nmapedPayload.get(\"payload\") = " + facePojoJson + "\n\n");

		System.out.println("\n\n----------------- UserControllerTest.A_Login().235 ----------------------------" +
				"\nmapedPayload.get(\"payload\") = " + preTest.getUserPojoList().get(0).toString() + "\n\n");



		FaceuserPojo faceuserPojoPayload = GsonX.gson.fromJson(facePojoJson, FaceuserPojo.class);
		assertThat(faceuserPojoPayload).isNotNull();
		assertThat(faceuserPojoPayload.getEmail()).isEqualTo(faceuserPojoToSend.getEmail());


		/** 7 use the header to call a uri where you can test that are Authenticated and Authorized to do something. */

		MockHttpServletResponse servletResponse1 =
				this.preTest.mockMvc.perform
						(
								get("/api/authCheck/"+preTest.getUserPojoList().get(0).getEmail())
										.header("AuthToken", tokenHeader)
										.header("UserToken", UserInfotokenHeader)
						)
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaTypes.JsonUtf8))
						.andReturn().getResponse();


		assertThat(servletResponse1).isNotNull();
		String response2Body = servletResponse1.getContentAsString();
		assertThat(response2Body).isNotNull();

		faceuserPojoPayload = GsonX.gson.fromJson(response2Body, FaceuserPojo.class);

		assertThat(faceuserPojoPayload).isNotNull();

		System.out.println("\n\n\n\n" +
				"----------------------------------- LoginControllerTest.256 -------------------------------------" +
				"\n\nfacePostPojoJson = " + faceuserPojoPayload +
				"\n\n" +
				"------------------------------------------------------------------------\n\n\n\n\n");



//		MockHttpServletResponse servletResponse2 =
//				this.preTest.mockMvc.perform
//						(
//								get("/api/getPosts/"+preTest.getUserPojoList().get(0).getEmail())
//										.header("AuthToken", tokenHeader)
//						)
//						.andExpect(status().isOk())
//						.andExpect(content().contentType(MediaTypes.JsonUtf8))
//						.andReturn().getResponse();
//
//
//		assertThat(servletResponse2).isNotNull();
//		String facePostPojoJson = servletResponse2.getContentAsString();
//		assertThat(facePostPojoJson).isNotNull();
//
//		List<FacePostPojo> facePostPojos = new ArrayList<>();
//
//		Type listType = new TypeToken<ArrayList<FacePostPojo>>(){}.getType();
//
//		facePostPojos = GsonX.gson.fromJson(response2Body, listType);

//		List<FacePostPojo> facePostPojos = new ArrayList<>();
//
//		Type listType = new TypeToken<ArrayList<FacePostPojo>>(){}.getType();
//
//		facePostPojos = GsonX.gson.fromJson(response2Body, listType);

//		System.out.println("\n\n\n\n" +
//				"----------------------------------- LoginControllerTest.269 -------------------------------------" +
//				"\n\nfacePostPojoJson = " + facePostPojoJson +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");




		System.out.println("\n\n----------------- UserControllerTest.A_Login-end ----------------------------\n\n");

	}



}