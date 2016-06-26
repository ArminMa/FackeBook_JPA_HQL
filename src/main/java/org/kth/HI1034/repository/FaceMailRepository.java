package org.kth.HI1034.repository;


import org.kth.HI1034.model.FaceMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface FaceMailRepository extends JpaRepository<FaceMail, Long>, JpaSpecificationExecutor<FaceMail> {


	/**
	 *
	 * @param read          1   boolean
	 * @param userID        2   Long
	 * @param mailID        3   Long
	 */
	@Modifying/*(clearAutomatically = true)*/
	@Transactional
	@Query(value = "update UserReceivedMail M  set M.read= :isRead " +
			"WHERE M.pk.receivedMail.id = :mailId and M.pk.receivedMail.id = :userId")
	void markReceivedMailByUserIdAndMailIdAsRead(@Param("isRead") Boolean read,
	                                             @Param("userId") Long userID,
	                                             @Param("mailId") Long mailID);

	/**
	 * @param author_Id   1   Long
	 * @param author_Id     2   Long
	 * @return              List<FaceMail>
	 */
	@Query(value = "SELECT M.pk.receivedMail FROM UserReceivedMail M WHERE M.pk.receivedMail.author.id = :author_id AND M.pk.receivingUser.id = :receiver_id" )
	List<FaceMail> findAllMailByAuthorAndReceivingUserId(@Param("author_id") Long author_Id,
	                                                     @Param("receiver_id") Long receiver_Id);


	@Query(value = "SELECT M FROM FaceMail M, in(M.receiversFaceMails) RFM " +
			"where RFM.pk.receivingUser.id = :userId" )
	List<FaceMail> findAllReceivedMailByUserId(@Param("userId") Long user_id);

	@Query(value = "SELECT M FROM FaceMail M where M.author = :userId" )
	List<FaceMail> findAllSentMailByUserId(@Param("userId") Long user_id);

	@Query(value = "SELECT M FROM FaceMail M, IN(M.receiversFaceMails) RFM " +
			"WHERE RFM.pk.receivingUser.id = :userId AND RFM.read = TRUE" )
	List<FaceMail> findAllReadReceivedMailByUserId(@Param("userId") Long user_id);

	@Query(value = "SELECT M FROM FaceMail M, IN(M.receiversFaceMails) RFM " +
			"WHERE RFM.pk.receivingUser.id = :userId AND RFM.read = FALSE" )
	List<FaceMail> findAllUnReadReceivedMailByUserId(@Param("userId") Long user_id);

	@Modifying
	@Transactional
	@Query(value = "delete from UserReceivedMail RM where" +
			"(SELECT URM FROM UserReceivedMail URM, " +
			"in(URM.pk.receivingUser) RU " +
			"where RU.id = :userId1 ) = :userId2 ")
	void deleteConnectionbetweenReceivingUserAndMail(@Param("userId1") Long userID1, @Param("userId2") Long userID2);

	@Modifying
	@Transactional
	void delete( Long id );

	@Modifying
	@Transactional
	@Query(value = "delete from FaceMail U where U.id = :mailId" )
	void deleteFaceMailByMailId(@Param("mailId") Long mailid);


}
