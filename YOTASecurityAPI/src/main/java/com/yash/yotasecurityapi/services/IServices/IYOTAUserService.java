package com.yash.yotasecurityapi.services.IServices;

import com.yash.yotasecurityapi.dto.YotaUserDto;

import java.util.List;

/**
 * Project Name - YOTASecurityPOC
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - yashr
 * @since - 02-04-2024
 */
public interface IYOTAUserService {

    String createNewUser(YotaUserDto userDto);

    YotaUserDto getUserByEmailAdd(String emailAdd);

    List<YotaUserDto> getAllTrainers();

    List<YotaUserDto> getAllStudents();
}
