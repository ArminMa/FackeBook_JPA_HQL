package org.kth.HI1034.model.domain.jwt;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserServerKeyRepository extends JpaRepository<UserServerKey, Long>, JpaSpecificationExecutor<UserServerKey> {

	@Query(value = "select UK FROM UserServerKey UK WHERE UK.email = :theEmail")
	UserServerKey findByEmail(@Param("theEmail") String email);

}
