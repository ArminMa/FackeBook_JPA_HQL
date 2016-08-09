package org.kth.HI1034.service.impl;

import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.post.FacePost;
import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.model.domain.post.PostRepository;
import org.kth.HI1034.service.FaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaceUserServiceImp implements FaceUserService {

	@Autowired
	private PostRepository postRepo;

	@Value("${token.header}")
	private String tokenHeader;

	@Override
	public List<FacePostPojo> getUserReceivdPosts(String email) {
		List<FacePost> facePosts = postRepo.findAllReceivedPostsToUserByUserEmail(email);

		@SuppressWarnings("unchecked")
		List<FacePostPojo> facePostPojos = (List<FacePostPojo>) Converter.convert(facePosts);

		return facePostPojos;
	}

	@Override
	public ResponseEntity getUserReceivdPosts(Long is) {
		return null;
	}

	@Override
	public ResponseEntity<?> getUserReceivdPosts(String userEmail, String authToken) {

		List<FacePost> facePosts = postRepo.findAllReceivedPostsToUserByUserEmail(userEmail);

		@SuppressWarnings("unchecked")
		List<FacePostPojo> facePostPojos = (List<FacePostPojo>) Converter.convert(facePosts);




		return ResponseEntity.ok()
				.contentType(MediaTypes.JsonUtf8)
				.header(tokenHeader, authToken)
				.body(facePostPojos.toString());

	}
}
