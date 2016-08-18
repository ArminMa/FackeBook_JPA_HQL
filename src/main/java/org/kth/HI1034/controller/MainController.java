package org.kth.HI1034.controller;

import com.google.gson.Gson;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.pojo.Ping;
import org.kth.HI1034.util.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

@RestController
public class MainController {

  @RequestMapping(value = "/ping", method = RequestMethod.GET)
  @ResponseBody
  @Secured(Role.USER)
  public String getHelloWorld() {
    return "Hello  World";
  }

  @RequestMapping(value = "/securedping", method = RequestMethod.GET)
  @ResponseBody
  @Secured(Role.USER)
  public String getSecuredHelloWorld() {
    return "Hello Secured World";
  }

  @RequestMapping(value = "/security", method = RequestMethod.GET)
  @ResponseBody
  public Object getAuthStatus() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
  }


  @RequestMapping(value = "/ping1", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public
  @ResponseBody
  Ping ping1(
      HttpServletRequest request,
      HttpServletResponse response) {

    Ping ping = new Ping("Ping ping1!", "ignore me", "not Ignored");
    response.setStatus(HttpStatus.OK.value());
    return ping;
  }

  @RequestMapping(value = "/ping2/{name}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public
  @ResponseBody
  Ping ping2(@PathVariable("name") String name) {
    Ping ping = new Ping("Ping " + name, "ignore me", "not Ignored");
    return ping;
  }

  @RequestMapping(value = "/ping3", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
  public
  @ResponseBody
  Ping ping3(@RequestParam("name") String name) {
    Ping ping = new Ping("Ping " + name, "ignore me", "not Ignored");
    return ping;
  }


  @RequestMapping
      (
          value = "/ping4/{name}",
          method = RequestMethod.GET,
          produces = {MediaType.APPLICATION_JSON_VALUE}
      )
  public
  @ResponseBody
  Ping ping4(
      @RequestHeader(name = "jwt", defaultValue = "where is the Token?") String jwt,
      @PathVariable("name") String name) {
    Ping ping = new Ping("Ping " + name, "ignore me", "keyUserServer = " + jwt);
    return ping;
  }

  @RequestMapping(
      value = "/ping5/{name}",
      produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      method = RequestMethod.POST)

  public
  @ResponseBody
  Ping ping5(
      @RequestHeader(name = "jwt", defaultValue = "where is the Token?") String jwt,
      @PathVariable("name") String name,
      @RequestBody Ping ping,
      HttpServletRequest request,
      HttpServletResponse response) {

    response.addHeader("keyUserServer", "some random token");
    response.addHeader("info", "mor header info");
    response.setStatus(HttpStatus.OK.value());
    return ping;
  }


  protected Logger
      logger = LoggerFactory.getLogger(getClass()


  );

  @ExceptionHandler
  public
  @ResponseBody
  ResponseEntity<?> handleDemoException(JoseException exception,
                                        HttpServletRequest req) {
    logger.error("\nJoseException - " + exception.toString() + "\n");

    Gson gson = new Gson();


    // Because we are handling the error, the server thinks everything is
    // OK, so the status is 200. So let's set it to something else.
    req.setAttribute("javax.servlet.error.status_code", HttpStatus.INTERNAL_SERVER_ERROR.value());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .contentType(MediaTypes.JsonUtf8)
        .body(gson.toJson(exception.toString()));


  }

}
