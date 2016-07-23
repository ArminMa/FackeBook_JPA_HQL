//package org.kth.HI1034.controller;
//
//import com.google.gson.Gson;
//import org.jose4j.jwk.EllipticCurveJsonWebKey;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.kth.HI1034.ApplicationWar;
//import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
//import org.kth.HI1034.util.MediaTypes;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationContextLoader;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ApplicationWar.class, loader=SpringApplicationContextLoader.class)
//@WebAppConfiguration
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class apiKeyControllerTest {
//
//	@Autowired
//	private WebApplicationContext context;
//
//	private MockMvc mockMvc;
////	private MockRestServiceServer mockServer;
//
//	private Gson gson;
//	private EllipticCurveJsonWebKey JsonWebKey;
//
//	@Before
//	public void setUp() throws Exception {
//		this.gson = new Gson();
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//	}
//
//	// en test för när det inte finns en användare redan registrerad och en när det finns en registrerad
//
//	@Test
//	public void A_getPublicAppKey() throws Exception {
//
//		JsonWebKey = JsonWebKeyUtil.getEllipticJwkFromJson(
//				this.mockMvc.perform(get("/getAppPublicKey")
//						.accept(MediaTypes.JsonUtf8))
//						.andReturn().getResponse().getContentAsString()
//				);
//
//		assertThat(JsonWebKey).isNotNull();
//
//	}
//
//
//	@Test
//	public void B_getPublicAppKey() throws Exception {
//
//
//		KeyPair keyPair = KeyUtil.generateKeyPair();
//		RsaJsonWebKey rsaJsonWebKey = JsonWebKeyUtil.generateRsaWebKey();
//
//
//		TokenPojo tokenPojo = new TokenPojo();
//		tokenPojo.setAudience("fackebook.se");
//		tokenPojo.setIssuer("armin@gmail.com");
//		tokenPojo.setSubject("shareKey");
//		tokenPojo.setReceiverJwk(JsonWebKeyUtil.getPublicRsaWebKeyAsJson( rsaJsonWebKey ));
//
//		CipherUtils.encryptWithPublicKey(tokenPojo);
//
//
//
//
//
//		CipherUtils.encryptWithPublicKey()
//
//
//		String encypted = this.mockMvc.perform(get("/getAppPublicKey/crypto")
//						.accept(MediaType.TEXT_PLAIN))
//						.andExpect(status().isOk())
//						.andExpect(content().contentType(MediaType.TEXT_PLAIN))
//						.andReturn().getResponse().getContentAsString();
//
//		assertThat(encypted).isNotNull();
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- apiKeyControllerTest_81 -------------------------------------\n\n" +
//				"\n\n" +
//				encypted +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//		 ApiKeyFactory apiKeyFactory = new ApiKeyFactory();
//
//		String decrypted = CipherUtils.decryptWithSymmetricKey(encypted, apiKeyFactory.getSymmetricKey());
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- apiKeyControllerTest_95 -------------------------------------\n\n" +
//				"\n\n" +
//				decrypted +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//
//		FaceuserPojo faceuserPojo = gson.fromJson(decrypted, FaceuserPojo.class);
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- apiKeyControllerTest_105 -------------------------------------\n\n" +
//				"\n\n" +
//				faceuserPojo +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//
//	}
//
//}