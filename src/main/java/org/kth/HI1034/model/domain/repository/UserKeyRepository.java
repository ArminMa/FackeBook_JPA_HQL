package org.kth.HI1034.model.domain.repository;

import org.kth.HI1034.model.domain.jwt.UserServerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Sys on 2016-07-16.
 */
public interface UserKeyRepository 	extends
		JpaRepository<UserServerKey, Long>,
		JpaSpecificationExecutor<UserServerKey> {


	@Query(value = "select UK FROM UserServerKey UK WHERE UK.email = :theEmail")
	UserServerKey findByEmail(@Param("theEmail") String email);

}
