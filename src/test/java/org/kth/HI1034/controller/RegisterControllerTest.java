package org.kth.HI1034.controller;

import com.google.gson.Gson;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.JWT.TokenPojo;
import org.kth.HI1034.JWT.TokenUtils;
import org.kth.HI1034.model.pojo.FaceuserPojo;
import org.kth.HI1034.security.util.ciperUtil.JsonWebKeyUtil;
import org.kth.HI1034.util.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
//	private MockRestServiceServer mockServer;
	private EllipticCurveJsonWebKey serverPublicEllipticJWK;
	private String serverPublicJWK;
	private Gson gson;

	@Before
	public void setUp() throws Exception {
		this.gson = new Gson();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

		serverPublicJWK = this.mockMvc.perform(get("/getAppPublicKey")
				.accept(MediaTypes.JsonUtf8))
				.andReturn().getResponse().getContentAsString();

		serverPublicEllipticJWK = JsonWebKeyUtil.getEllipticJwkFromJson(serverPublicJWK);
		assertThat(serverPublicEllipticJWK).isNotNull();

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

		assertThat(serverPublicEllipticJWK).isNotNull();
		assertThat(serverPublicJWK).isNotNull();

		EllipticCurveJsonWebKey jsonWebKey = JsonWebKeyUtil.generateEllipticWebKey();


		FaceuserPojo faceuserPojo = new FaceuserPojo("registerTest@gmail.com", "myUserName", "password", "MyFirstName", "MyLastName", new Date());

		TokenPojo tokenPojo = new TokenPojo();

		tokenPojo.setIssuer("registerTest@gmail.com");
		tokenPojo.setAudience("fackebook.se");
		tokenPojo.setSenderJwk(JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey));
		tokenPojo.setReceiverJwk(serverPublicJWK);
		tokenPojo.setSubject("register");


		String JWT = TokenUtils.EllipticJWT.ProduceJWT(
				tokenPojo.getIssuer(),
				tokenPojo.getAudience(),
				tokenPojo.getSubject(),
				JsonWebKeyUtil.getPrivateEcllipticWebKeyAsJson(jsonWebKey),
				serverPublicJWK,
				faceuserPojo.toString()
		);

		tokenPojo.setToken(JWT);

		//encypt senders PublicKey with receivers public key
//		String encyptedPublicKey = CipherUtils.encryptWithPublicKey(JsonWebKeyUtil.getPublicEcllipticWebKeyAsJson(jsonWebKey), serverPublicEllipticJWK.getPublicKey());
//		tokenPojo.setSenderJwk(encyptedPublicKey);




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