package org.kth.HI1034.model.domain.entity.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Sys on 2016-08-03.
 */
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, UserAuthorityId>, JpaSpecificationExecutor<UserAuthority> {

	@Query(value = "SELECT UA.pk.authority FROM UserAuthority UA where UA.pk.user.id = :userId")
	List<Authority> findAuthorityByUserId(@Param("userId") Long user_id);


	@Modifying
	@Transactional
	@Query(value = "delete from UserAuthority UA where UA.id.user.id = :userId1" )
	void deleteUserAuthorityByUserID(@Param("userId1") Long userID1);
}
