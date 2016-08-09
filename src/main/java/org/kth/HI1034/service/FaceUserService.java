package org.kth.HI1034.service;

import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by Sys on 2016-08-08.
 */
public interface FaceUserService {

	List<FacePostPojo> getUserReceivdPosts(String email);

	ResponseEntity getUserReceivdPosts(Long is);

	ResponseEntity<?> getUserReceivdPosts(String userEmail, String authToken);
}
