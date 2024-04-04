package com.yash.yotasecurityapi.services.IServices;

import com.yash.yotasecurityapi.dto.UserRoleDto;

import java.util.Set;

/**
 * Project Name - YOTASecurityPOC
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - yashr
 * @since - 02-04-2024
 */
public interface IUserRoleService {

    UserRoleDto createUserRole(UserRoleDto userRoleDto);

    UserRoleDto getUserRoleById(Long roleId);

    UserRoleDto getUserRoleByRoleName(String roleName);

    Set<UserRoleDto> getAllUserRoles();

    void roleUpdateScheduler();
}
