//package org.kth.HI1034.controller;
//
//import org.jose4j.jwk.RsaJsonWebKey;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.kth.HI1034.AppPublicKeys;
//import org.kth.HI1034.ApplicationWar;
//import org.kth.HI1034.JWT.TokenPojo;
//import org.kth.HI1034.JWT.TokenUtils;
//import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
//import org.kth.HI1034.model.pojo.FaceuserPojo;
//import org.kth.HI1034.security.util.ciperUtil.CipherUtils;
//import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
//import org.kth.HI1034.util.GsonX;
//import org.kth.HI1034.util.MediaTypes;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationContextLoader;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.security.Key;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ApplicationWar.class, loader=SpringApplicationContextLoader.class)
//@WebAppConfiguration
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class LoginControllerTest {
//
//
//
//	@Autowired
//	private WebApplicationContext context;
//
//	private  MockMvc mockMvc;
//	private AppPublicKeys appPublicKeys ;
//	private RsaJsonWebKey serverRsaJsonWebKey ;
//
//	@Before
//	public void setUp() throws Exception {
//		this.mockMvc = SetUp.mockMvc(context);
//		appPublicKeys = SetUp.getAppPublicKey(mockMvc);
//		serverRsaJsonWebKey = JsonWebKeyUtil.getPublicRSAJwkFromJson(appPublicKeys.getPublicRsaWebKeyAsJson());
//		assertThat(appPublicKeys).isNotNull();
//		assertThat(serverRsaJsonWebKey).isNotNull();
//
//		// register a user FaceuserPojo("registerTest@gmail.com", "password");
//		TokenPojo tokenPojo = SetUp.saveUser(mockMvc, appPublicKeys);
//		assertThat(tokenPojo).isNotNull();
//
//	}
//
//
//	@Test
//	public void A_Login() throws Exception {
//
//		assertThat(appPublicKeys).isNotNull();
//
//
//		// generate a new shared key
//		Key secretKey = JsonWebKeyUtil.SymmetricKey.generate128Bit_16ByteSecretAesKey();
//		String secretKeyString = JsonWebKeyUtil.SymmetricKey.keyToString(secretKey.getEncoded());
//
//		FaceuserPojo faceuserPojo = new FaceuserPojo("registerTest@gmail.com", "password");
//
//		faceuserPojo.setUserServerKeyPojo( new UserServerKeyPojo( "registerTest@gmail.com", secretKeyString ) );
//
//		// encrypt the email and password with the Servers private key
//		RsaJsonWebKey serverRsaJsonWebKey = JsonWebKeyUtil.getPublicRSAJwkFromJson(appPublicKeys.getPublicRsaWebKeyAsJson());
//		String payload = CipherUtils.encryptWithPublicKey(faceuserPojo.toString(), serverRsaJsonWebKey.getPublicKey() );
//		TokenPojo tokenPojo = new TokenPojo();
//
//		tokenPojo.setIssuer("registerTest@gmail.com");
//		tokenPojo.setAudience("fackebook.se");
//		tokenPojo.setSubject("login");
//		tokenPojo.setReceiverKey(appPublicKeys.getPublicRsaWebKeyAsJson());
//
//
//		String keyUserServer = TokenUtils.ProduceJWT(
//				tokenPojo.getIssuer(),
//				tokenPojo.getAudience(),
//				tokenPojo.getSubject(),
//				payload
//		);
//
//		tokenPojo.setToken(keyUserServer);
//
//
//		String theResponse = this.mockMvc.perform
//				(
//						post("/user/login")
//								.contentType(MediaTypes.JsonUtf8)
//								.content( tokenPojo.toString() )
//				)
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaTypes.JsonUtf8))
//				.andReturn().getResponse().getContentAsString();
//
//
//		assertThat(theResponse).isNotNull();
//
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- LoginControllerTest_112 -------------------------------------\n\n" +
//				"keyUserServer = \n" + theResponse +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//
//		FaceuserPojo faceUserPojoLoggedIn = GsonX.gson.fromJson( TokenUtils.SymmetricJWT.getJwtPayloadList(
//				secretKey,
//				tokenPojo.getIssuer(),
//				tokenPojo.getAudience(),
//				tokenPojo.getSubject(),
//				faceuserPojo.toString()
//		),  FaceuserPojo.class);
//
//		faceUserPojoLoggedIn.setPassword(null);
//
//		String userServerSecretKey = CipherUtils.decryptWithSymmetricKey(faceUserPojoLoggedIn.getUserServerKeyPojo().getSharedKey(), secretKey);
//
//		Key sharedSecretKey =  JsonWebKeyUtil.SymmetricKey.stringToSecretKey(userServerSecretKey);
//
//
//
//		theResponse = this.mockMvc.perform
//				(
//						post("/user/authorized")
//								.contentType(MediaTypes.JsonUtf8)
//								.content( tokenPojo.toString() )
//				)
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaTypes.JsonUtf8))
//				.andReturn().getResponse().getContentAsString();
//
//
//		assertThat(theResponse).isNotNull();
//
//
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- LoginControllerTest_147 -------------------------------------\n\n" +
//				"theResponse = \n" + theResponse +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//
//	}
//
//
//}