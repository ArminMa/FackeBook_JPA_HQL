package org.kth.HI1034.controller;

import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.JWT.TokenUtils;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;
import org.kth.HI1034.model.pojo.FaceuserPojo;
import org.kth.HI1034.security.util.ciperUtil.CipherUtils;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.kth.HI1034.util.GsonX;
import org.kth.HI1034.util.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationWar.class, loader=SpringApplicationContextLoader.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private AppPublicKeys appPublicKeys;

	@Before
	public void setUp() throws Exception {
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

	}

	// en test för när det inte finns en användare redan registrerad och en när det finns en registrerad
//
//	@Test
//	public void A_getPublicAppKey() throws Exception {
//
//
//
//
//
//	}

	@Test
	public void B_registerUser() throws Exception {

		assertThat(appPublicKeys).isNotNull();


		EllipticCurveJsonWebKey jsonWebKey = JsonWebKeyUtil.generateEllipticWebKey();


		FaceuserPojo faceuserPojo = new FaceuserPojo("registerTest@gmail.com", "myUserName", "password", "MyFirstName", "MyLastName", new Date());

		TokenPojo tokenPojo = new TokenPojo();

		tokenPojo.setIssuer("registerTest@gmail.com");
		tokenPojo.setAudience("fackebook.se");
		tokenPojo.setSenderJwk(JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey));
		tokenPojo.setReceiverJwk(appPublicKeys.getPublicEllipticWebKeyAsJson());
		tokenPojo.setSubject("register");

		//use the servers public key. to encypt the secret key
		RsaJsonWebKey serverRsaJsonWebKey = JsonWebKeyUtil.getPublicRSAJwkFromJson(appPublicKeys.getPublicRsaWebKeyAsJson());
		Key secretKey = JsonWebKeyUtil.symmetricKey.generateSecretAesKey();
		// the encrypted private part of the key kan only be opened with the private part of the key
		String encryptedSharedKey = CipherUtils.encryptWithPublicKey(JsonWebKeyUtil.symmetricKey.keyToString(secretKey.getEncoded()), serverRsaJsonWebKey.getPublicKey() );

		faceuserPojo.setUserServerKeyPojo( new UserServerKeyPojo( "registerTest@gmail.com", encryptedSharedKey ) );

		String JWT = TokenUtils.EllipticJWT.ProduceJWT(
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey),
				appPublicKeys.getPublicEllipticWebKeyAsJson(),
				faceuserPojo.toString()
		);

		tokenPojo.setToken(JWT);


//



		String jwt = this.mockMvc.perform
						(
								post("/user/register")
										.contentType(MediaTypes.JsonUtf8)
										.content( tokenPojo.toString() )
						)
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaTypes.JsonUtf8))
						.andReturn().getResponse().getContentAsString();

		assertThat(jwt).isNotNull();

		System.out.println("\n\n\n\n" +
				"----------------------------------- RegisterControllerTest_79 -------------------------------------\n\n" +
				"\n\n" +
				"jwt = \n" + jwt +
				"\n\n" +
				"------------------------------------------------------------------------\n\n\n\n\n");


	}



}