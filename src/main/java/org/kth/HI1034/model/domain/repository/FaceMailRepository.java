package org.kth.HI1034.model.domain.repository;


import org.kth.HI1034.model.domain.entity.FaceMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


public interface FaceMailRepository extends JpaRepository<FaceMail, Long>, JpaSpecificationExecutor<FaceMail> {


	/**
	 *
	 * @param read          1   boolean
	 * @param userID        2   Long
	 * @param mailID        3   Long
	 */
	@Modifying/*(clearAutomatically = true)*/
	@Transactional
	@Query(value = "update UserReceivedMail M set M.read= :isRead " +
			"WHERE M.pk.receivedMail.id = :mailId and M.pk.receivingUser.id = :userId")
	void markReceivedMailByUserIdAndMailIdAsRead(@Param("isRead") Boolean read,
	                                             @Param("userId") Long userID,
	                                             @Param("mailId") Long mailID);

	/**
	 * @param author_Id   1   Long
	 * @param author_Id     2   Long
	 * @return              Set<FaceMail>
	 */
	@Query(value = "SELECT M.pk.receivedMail FROM UserReceivedMail M WHERE M.pk.author.id = :authorId AND M.pk.receivingUser.id = :receiverId" )
	Set<FaceMail> findAllMailByAuthorAndReceivingUserId(@Param("authorId") Long author_Id,
	                                                     @Param("receiverId") Long receiver_Id);


	@Query(value = "SELECT M.pk.receivedMail FROM UserReceivedMail M where M.pk.receivingUser.id = :userId" )
	Set<FaceMail> findAllReceivedMailByUserId(@Param("userId") Long user_id);

	@Query(value = "SELECT M.pk.receivedMail FROM UserReceivedMail M where M.pk.receivedMail.id = :senderId" )
	Set<FaceMail> findAllSentMailByUserId(@Param("senderId") Long user_id);

	@Query(value = "SELECT M.pk.receivedMail FROM UserReceivedMail M " +
			"where M.pk.receivingUser.id =:receiver_id and M.read = TRUE")
	Set<FaceMail> findAllReadReceivedMailByUserId(@Param("receiver_id") Long user_id);

	@Query(value = "SELECT M.pk.receivedMail FROM UserReceivedMail M " +
			"where M.pk.receivingUser.id =:receiver_id and M.read = FALSE")
	Set<FaceMail> findAllUnReadReceivedMailById(@Param("receiver_id") Long user_id);

	@Modifying
	@Transactional
	@Query(value = "delete from UserReceivedMail RM where RM.pk.receivingUser.id = :userId or RM.pk.author.id = :userId")
	void deleteConnectionbetweenReceivingUsersAndMail(@Param("userId") Long userId);

	@Modifying
	@Transactional
	void delete( Long id );

	@Modifying
	@Transactional
	@Query(value = "delete from FaceMail U where U.id = :mailId" )
	void deleteFaceMailByMailId(@Param("mailId") Long mailid);




}
