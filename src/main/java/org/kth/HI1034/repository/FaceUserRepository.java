package org.kth.HI1034.repository;

import org.kth.HI1034.model.FaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface FaceUserRepository extends JpaRepository<FaceUser, Long>, JpaSpecificationExecutor<FaceUser> {



	@Query(value = "select U FROM FaceUser U WHERE U.email = :theEmail")
	FaceUser findByEmail(@Param("theEmail")String email);

//	@Query(value = "SELECT U FROM FaceUser U, IN(U.sentFriendRequests) SFR WHERE SFR.pk.requestFrom.id = :userId" )
//	Set<FaceUser> findUsersWhereFriendRequestFromThisUserIsSent(@Param("fromUserId") Long userFromId);


//	SELECT DISTINCT auth FROM Author auth
//	WHERE EXISTS
//			(SELECT spouseAuthor FROM Author spouseAuthor WHERE spouseAuthor = auth.spouse)

	@Modifying
	@Transactional
	@Query(value = "delete from FaceUser U where U.id = :userId" )
	void deleteThisFaceUser(@Param("userId") Long userID);

//	@Modifying
//	@Transactional
//	FaceUser save(FaceUser userList);

//	@Modifying
//	@Transactional
//	List<FaceUser> save(List<FaceUser> userList);

// "SELECT p FROM Peron p WHERE name IN (:names)
// INSERT INTO X(A, B, C1, C2) SELECT A, B, 'foo', 'bar' FROM X data
//
//	@Modifying/*(clearAutomatically = true)*/
//	@Transactional
//	@Query(value = "INSERT INTO into UserReceivedMail  set urm.read= :isRead WHERE urm.pk.receivingUser.id = :userId and urm.pk.receivedMail.id = :mailId")
//	void updateReceivedMailByUserIdAndMailId(@Param("isRead") Boolean read,@Param("userId") Long userID, @Param("mailId") Long mailID);

//	@Modifying
//	@Transactional
//	@Query(value = "INSERT from FaceUser U where U.id = :userId" )
//	void saveUserAndFriend(@Param("userId") Long userID);



//	@Query(value = "SELECT M FROM FaceMail M, in(M.receiversEmbedded.receivingUsers) RFM where RFM.id = :userId" )
}
