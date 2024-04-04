package com.yash.yotasecurityapi.repositories;

import com.yash.yotasecurityapi.entity.YotaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YotaUserRepository extends JpaRepository<YotaUser, String> {

    @Query("select yur from YotaUser yur where yur.emailAdd=?1")
    YotaUser getUserByEmail(String email);

    @Query("select yur from YotaUser yur where yur.userRole.roleTypes=?1")
    List<YotaUser> findAllUsersByRole(String roleTypes);
}
