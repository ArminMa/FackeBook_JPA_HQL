package org.kth.HI1034.repository;

import org.kth.HI1034.model.FacePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FacePostRepository extends JpaRepository<FacePost, Long>, JpaSpecificationExecutor<FacePost> {

	@Query(value = "SELECT P FROM FacePost P " +
			"where P.author =:author_id")
	List<FacePost> findAllSentByUserByUserId(@Param("author_id") Long authorId);

	@Query(value = "SELECT P FROM FacePost P " +
			"where (SELECT U FROM FaceUser U, IN(P.receiver) PU WHERE PU.id  = :sender_id)  = :sender_id")
//	@Query(value = "SELECT U FROM FaceUser U, IN(U.sentFriendRequests) SFR WHERE SFR.pk.requestFrom.id = :userId" )
	List<FacePost> findAllReceiverToUserByUserId(@Param("sender_id") Long senderId);
}
