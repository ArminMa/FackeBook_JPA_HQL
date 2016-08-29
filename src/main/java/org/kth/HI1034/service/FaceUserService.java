package org.kth.HI1034.service;

import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface FaceUserService {

	List<FacePostPojo> getUserReceivdPosts(String email);

	ResponseEntity getUserReceivdPosts(Long is);

	ResponseEntity<?> getUserReceivdPosts(String userEmail, String authToken);

  ResponseEntity<?> emailExist(FaceUserPojo faceUserPojo);

	ResponseEntity<?> getAllUsers();

    ResponseEntity<?> emailOrUsernameExist(String email, String userName);

	ResponseEntity<?> findUserSimilarToThis(String searchValue);

    ResponseEntity<?> finUserByEmail(String email);
}
