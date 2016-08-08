package org.kth.HI1034.model.domain.keyUserServer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserKeyRepository 	extends
		JpaRepository<UserServerKey, Long>,
		JpaSpecificationExecutor<UserServerKey> {


	@Query(value = "select UK FROM UserServerKey UK WHERE UK.email = :theEmail")
	UserServerKey findByEmail(@Param("theEmail") String userEmail);

	@Modifying/*(clearAutomatically = true)*/
	@Transactional
	@Query(value = "update UserServerKey usk set usk.sharedKey= :sharedKey, usk.tokenKey= :tokenKey  WHERE usk.email = :email")
	Integer update(@Param("email")String email, @Param("sharedKey")String sharedKey, @Param("tokenKey")String tokenKey );


	@Modifying
	@Transactional
	@Query(value = "delete from UserServerKey USK where USK.email = :email" )
	void deleteByUserEmail(@Param("email") String userEmail);
}
