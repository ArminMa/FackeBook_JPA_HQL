
package org.kth.HI1034.controller;

import com.google.gson.Gson;
import org.kth.HI1034.model.pojo.Ping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//import javax.servlet.http.HttpServletRequest;

@RestController
public class PingController {

	@RequestMapping(value = "/ping1", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public @ResponseBody
	Ping ping1(
			HttpServletRequest request,
			HttpServletResponse response) {
		Gson gson = new Gson();
		Ping ping = new Ping("Ping ping1!", "ignore me", "not Ignored");
		response.setStatus(HttpStatus.OK.value());
		return ping;
	}

	@RequestMapping(value="/ping2/{name}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public @ResponseBody
	Ping ping2(@PathVariable("name") String name){
		Ping ping = new Ping("Ping " + name, "ignore me", "not Ignored");
		return ping;
	}

	@RequestMapping(value="/ping3", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public @ResponseBody
	Ping ping3(@RequestParam("name") String name){
		Ping ping = new Ping("Ping " + name, "ignore me", "not Ignored");
		return ping;
	}


	@RequestMapping
			(
					value = "/ping4/{name}",
					method = RequestMethod.GET,
					produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	public @ResponseBody
	Ping ping4(
			@RequestHeader(name="jwt",defaultValue="where is the Token?") String jwt,
			@PathVariable("name") String name) {
		Ping ping = new Ping("Ping " + name, "ignore me", "jwt = " + jwt);
		return ping;
	}

	@RequestMapping
			(
					value = "/ping5/{name}",
					produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
					consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
					method = RequestMethod.POST
			)

	public @ResponseBody
	Ping ping5(
			@RequestHeader(name="jwt",defaultValue="where is the Token?") String jwt,
			@PathVariable("name") String name,
			@RequestBody Ping ping,
			HttpServletRequest request,
			HttpServletResponse response) {

		response.addHeader("jwt","some random token");
		response.addHeader("info", "mor header info");
		response.setStatus(HttpStatus.OK.value());
		return ping;
	}



}
