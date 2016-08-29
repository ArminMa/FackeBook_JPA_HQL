package org.kth.HI1034.controller;


import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.kth.HI1034.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/one",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    ResponseEntity<?> postToUser(
            @RequestBody FacePostPojo postPojo,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return postService.post(postPojo, request);
    }


    @RequestMapping(value = "/getPostToUserByUserEmail",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    ResponseEntity<?> getPostToUserByUserEmail(
            @RequestBody FaceUserPojo faceUserPojo,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return postService.getPostToUserByUserEmail(faceUserPojo, request);
    }


}
