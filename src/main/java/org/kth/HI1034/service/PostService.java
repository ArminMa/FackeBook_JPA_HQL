package org.kth.HI1034.service;

import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


public interface PostService {

    ResponseEntity<?> post(FacePostPojo facePostPojo, HttpServletRequest request);

    ResponseEntity<?> getPostToUserByUserEmail(FaceUserPojo faceUserPojo, HttpServletRequest request);
}
