package org.kth.HI1034.model.domain.repository.post;


import org.kth.HI1034.model.domain.entity.post.FacePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<FacePost, Long>, JpaSpecificationExecutor<FacePost> {

	@Query(value = "SELECT P FROM FacePost P " +
			"where P.author.email = :author_email")
	List<FacePost> findAllSentByUserByUserId(@Param("author_email") String authorEmail);

	@Query(value = "SELECT P FROM FacePost P " +
			"where :receiver_email = (select PU.email from P.receivers PU WHERE PU.email  = :receiver_email) ")
//	@Query(value = "SELECT U FROM FaceUser U, IN(U.sentFriendRequests) SFR WHERE SFR.pk.requestFrom.id = :userId" )
	List<FacePost> findAllReceiverToUserByUserId(@Param("receiver_email") String receiverEmail);
}
