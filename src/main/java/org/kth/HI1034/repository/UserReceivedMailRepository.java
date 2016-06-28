package org.kth.HI1034.repository;

import org.kth.HI1034.model.UserReceivedMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.SortedSet;


public interface UserReceivedMailRepository extends JpaRepository<UserReceivedMail, Long>, JpaSpecificationExecutor<Long> {




	@Query(value = "select urm from UserReceivedMail urm where urm.pk.receivingUser.id = :userId and urm.pk.receivedMail.id = :mailId")
	UserReceivedMail findReceivedMailByUserIdAnMailId(@Param("userId") Long userID, @Param("mailId") Long mailID);

	@Query(value = "select urm from UserReceivedMail urm where urm.pk.receivingUser.id = :userId")
	SortedSet<UserReceivedMail> findAllReceivedMailByUserId(@Param("userId") Long userID);

	@Modifying/*(clearAutomatically = true)*/
	@Transactional
	@Query(value = "update UserReceivedMail urm set urm.read= :isRead WHERE urm.pk.receivingUser.id = :userId and urm.pk.receivedMail.id = :mailId")
	void updateReceivedMailByUserIdAndMailId(@Param("isRead") Boolean read,@Param("userId") Long userID, @Param("mailId") Long mailID);



	@Modifying
	@Transactional
	@Query(value = "delete from UserReceivedMail FR where FR.pk.receivingUser.id = :userId" )
	void deleteReceivedMailByUserId(@Param("userId") Long userID);
}
