package org.kth.HI1034.controller;

import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailPojo;
import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.service.FaceMailService;
import org.kth.HI1034.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/mail")
public class FaceMailController {

    @Autowired
    private FaceMailService faceMailService;

    @RequestMapping(value = "/one",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    ResponseEntity<?> mailToUser(
            @RequestBody UserReceivedMailPojo userReceivedMailPojo,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return faceMailService.sendMail(userReceivedMailPojo, request);
    }



    @RequestMapping(value = "/getReceivedMailToUser/{email:.+}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    ResponseEntity<?> getReceivedMailToUser(
            @PathVariable("email") String email,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return faceMailService.getReceivedMailToUser(email);
    }


}
