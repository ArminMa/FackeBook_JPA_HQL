package org.kth.HI1034.model.domain.post;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserDetachedRepository extends JpaRepository<UserDetached, Long>, JpaSpecificationExecutor<UserDetached> {

	@Modifying
	@Transactional
	@Query(value = "delete from UserDetached UA where UA.email = :theUserEmail" )
	void deleteUserDetachedByAuthorEmail(@Param("theUserEmail") String userEmail);

	@Query(value = "select UA from UserDetached UA where UA.email = :theUserEmail" )
	UserDetached findOneByEmail(@Param("theUserEmail") String userEmail);
}