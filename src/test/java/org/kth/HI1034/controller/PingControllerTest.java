package org.kth.HI1034.controller;


import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.model.pojo.Ping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationWar.class, loader=SpringApplicationContextLoader.class)
@WebAppConfiguration

public class PingControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
//	private MockRestServiceServer mockServer;

	private MediaType applicationJsonMediaType;


	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		applicationJsonMediaType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void hello() throws Exception {

		Gson gson = new Gson();

		Ping pingReturnd = gson.fromJson(
						this.mockMvc.perform(get("/ping1").accept(applicationJsonMediaType))
						.andExpect(status().isOk())
						.andExpect(content().contentType(applicationJsonMediaType))
						.andReturn().getResponse().getContentAsString()
				, Ping.class);

		assertThat(pingReturnd).isNotNull();
		Ping ping = new Ping("Ping ping1!", "ignore me", "not Ignored");
		assertThat(pingReturnd.equals(ping));

		pingReturnd = gson.fromJson(
				this.mockMvc.perform(get("/ping2/Armin").accept(applicationJsonMediaType))
						.andExpect(status().isOk())
						.andExpect(content().contentType(applicationJsonMediaType))
						.andReturn().getResponse().getContentAsString()
				, Ping.class);
		assertThat(pingReturnd).isNotNull();
		ping = new Ping("Ping Armin", "ignore me", "not Ignored");
		assertThat(pingReturnd.equals(ping));

		pingReturnd = gson.fromJson(
				this.mockMvc.perform(get("/ping3?name=Armin").accept(applicationJsonMediaType))
						.andExpect(status().isOk())
						.andExpect(content().contentType(applicationJsonMediaType))
						.andReturn().getResponse().getContentAsString()
				, Ping.class);
		assertThat(pingReturnd).isNotNull();
		ping = new Ping("Ping Armin", "ignore me", "not Ignored");
		assertThat(pingReturnd.equals(ping));

		pingReturnd = gson.fromJson(
				this.mockMvc.perform(get("/ping4/Armin").header("jwt","my token should be here").accept(applicationJsonMediaType))
						.andExpect(status().isOk())
						.andExpect(content().contentType(applicationJsonMediaType))
						.andReturn().getResponse().getContentAsString()
				, Ping.class);
		assertThat(pingReturnd).isNotNull();
		ping = new Ping("Ping Armin", "ignore me", "jwt = my token should be here");
		assertThat(pingReturnd.equals(ping));

		ping = new Ping("Ping Armin", "ignore me", "jwt = my token should be here");
		pingReturnd = gson.fromJson(
				this.mockMvc.perform
						(
								post("/ping5/Armin")
								.contentType(applicationJsonMediaType)
								.content(gson.toJson(ping))
								.header("jwt","my token should be here")
						)
						.andExpect(status().isOk())
						.andExpect(content().contentType(applicationJsonMediaType))
						.andExpect(header().string("jwt","some random token"))
						.andExpect(header().string("info", "mor header info"))
						.andReturn().getResponse().getContentAsString()
				, Ping.class);
		assertThat(pingReturnd).isNotNull();
		assertThat(pingReturnd.equals(ping));
/*


		System.out.println("\n\n\n\n" +
				"----------------------------------- PCT_83 -------------------------------------\n\n" +
				"" + gson.toJson(pingReturnd) +
				"\n\n" +
				pingReturnd +
				"\n\n" +
				"------------------------------------------------------------------------\n\n\n\n\n");
*/
	}




}