package org.kth.HI1034.repository;

import org.kth.HI1034.model.FriendRequest;
import org.kth.HI1034.model.FriendRequestID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, FriendRequestID>,
		JpaSpecificationExecutor<FriendRequest> {


	@Query(value = "select FR from FriendRequest FR where FR.pk.requestFrom.id = :userId")
	List<FriendRequest> findAllFromeThisUserId(@Param("userId") Long userID);

	@Modifying
	@Transactional
	@Query(value = "delete from FriendRequest FR where FR.pk.requestFrom.id = :fromUserId AND FR.pk.requestTo.id = :toUserId" )
	void deleteToAndFromByUserId(@Param("fromUserId") Long userFromId, @Param("toUserId") Long UserToId);

	@Modifying
	@Transactional
	@Query(value = "delete from FriendRequest FR where FR.pk.requestFrom.id = :userID or FR.pk.requestTo.id = :userID" )
	void deleteToOrFromByUserId(@Param("userID") Long userId);

	@Modifying
	@Transactional
	@Query(value = "delete from FriendRequest FR where FR.pk.requestTo.id = :toUserId" )
	void deleteToUserId(@Param("toUserId") Long UserToId);

	@Modifying
	@Transactional
	@Query(value = "delete from FriendRequest FR where FR.pk.requestFrom.id = :fromUserId" )
	void deleteFromByUserId(@Param("fromUserId") Long userFromId);
}
