package org.kth.HI1034.model.domain.entity.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority>{

	@Query(value = "SELECT A FROM Authority A where A.userRole = :role")
	Authority findOneByUserRole(@Param("role") String userRole);

	@Query(value = "SELECT UA.pk.authority FROM UserAuthority UA where UA.pk.user.id = :userId")
	List<Authority> findAuthorityByUserId(@Param("userId") Long user_id);

}
