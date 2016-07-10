package org.kth.HI1034;


import com.google.common.base.MoreObjects;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.bind.annotation.XmlRootElement;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
		String hello2 = this.mockMvc.perform(get("/hello").accept(applicationJsonMediaType))
						.andExpect(status().isOk())
						.andReturn().getResponse().getContentAsString();

		System.out.println("\n\n\n\n" + hello2 +"\n\n\n\n\n");




		Hello hello = gson.fromJson(
				this.mockMvc.perform(get("/hello").accept(applicationJsonMediaType))
						.andExpect(status().isOk())
						.andExpect(content().contentType(applicationJsonMediaType))
						.andReturn().getResponse().getContentAsString()

				, Hello.class);

		System.out.println("\n\n\n\n" +
				"------------------------------------------------------------------------" +
				"\n\nPingControllerTest.hello\n" +
				MoreObjects.toStringHelper(hello)
						.add("helloPing", hello.helloPing)
				+ "\n\n" +
				"------------------------------------------------------------------------\n\n\n\n\n");


		Assert.assertTrue(hello.toString().equals("Hello{helloPing=Hello Worls!}"));

	}


	@XmlRootElement
	private class Hello{
		String helloPing;
		public Hello() {

		}
		public Hello(String helloPing) {
			helloPing = helloPing;
		}
		public String getHelloPing() {
			return helloPing;
		}
		public void setHelloPing(String helloPing) {
			helloPing = helloPing;
		}
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("helloPing", helloPing)
					.toString();
		}

	}

}