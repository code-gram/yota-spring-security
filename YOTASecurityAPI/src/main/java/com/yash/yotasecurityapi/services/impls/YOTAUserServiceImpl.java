package com.yash.yotasecurityapi.services.impls;

import com.yash.yotasecurityapi.constants.UserAccountStatusTypes;
import com.yash.yotasecurityapi.constants.UserRoleTypes;
import com.yash.yotasecurityapi.dto.UserRoleDto;
import com.yash.yotasecurityapi.dto.YotaUserDto;
import com.yash.yotasecurityapi.entity.YotaUser;
import com.yash.yotasecurityapi.exceptions.ApplicationException;
import com.yash.yotasecurityapi.repositories.YotaUserRepository;
import com.yash.yotasecurityapi.services.IServices.IUserRoleService;
import com.yash.yotasecurityapi.services.IServices.IYOTAUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Project Name - YOTASecurityPOC
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - yashr
 * @since - 02-04-2024
 */
@Service
public class YOTAUserServiceImpl implements IYOTAUserService {

    @Autowired
    private YotaUserRepository userRepository;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public String createNewUser(YotaUserDto userDto) {
        YotaUser user = null;
        String message = null;
        if (ObjectUtils.isNotEmpty(userDto)) {

            user = this
                    .userRepository
                    .getUserByEmail(userDto.getEmailAdd());

            if (ObjectUtils.isEmpty(user)) {
                //user do not exist, new user will be created
                if (StringUtils.equals(userDto.getPassword(), userDto.getConfirmPassword())) {

                    UserRoleDto userRoleDto = this
                            .userRoleService
                            .getUserRoleByRoleName(UserRoleTypes.ROLE_STUDENT.toString());

                    userDto.setUserRole(userRoleDto);

                    user = this
                            .modelMapper
                            .map(userDto, YotaUser.class);

                    user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
                    user.setAccountStatus(UserAccountStatusTypes.APPROVED);

                    //reassigned with the new created data
                    user = this
                            .userRepository
                            .save(user);

                    if (ObjectUtils.isNotEmpty(user))
                        message = "YOTA User created successfully";
                    else
                        message = "YOTA User creation failed";
                } else {
                    throw new ApplicationException("Password did not matched, please try again");
                }
            } else {
                throw new ApplicationException("User already exists with this email address");
            }
        } else {
            throw new ApplicationException("Invalid user details");
        }

        return message;
    }

    @Override
    public YotaUserDto getUserByEmailAdd(String emailAdd) {
        YotaUserDto userDto = null;
        YotaUser user = null;

        if (StringUtils.isNotEmpty(emailAdd)) {
            user = this.userRepository.getUserByEmail(emailAdd);

            userDto = this
                    .modelMapper
                    .map(user, YotaUserDto.class);
        } else {
            throw new ApplicationException("Email add is empty");
        }
        return userDto;
    }

    @Override
    public List<YotaUserDto> getAllTrainers() {
        List<YotaUser> allTrainers = this.userRepository.findAllUsersByRole(UserRoleTypes.ROLE_TRAINER.toString());
        if (!allTrainers.isEmpty()) {
            return allTrainers
                    .stream()
                    .map(yur -> this
                            .modelMapper
                            .map(yur, YotaUserDto.class))
                    .collect(Collectors.toList());
        } else
            throw new ApplicationException("No Trainers found !");
    }

    @Override
    public List<YotaUserDto> getAllStudents() {
        List<YotaUser> allTrainers = this.userRepository.findAllUsersByRole(UserRoleTypes.ROLE_STUDENT.toString());
        if (!allTrainers.isEmpty()) {
            return allTrainers
                    .stream()
                    .map(yur -> this
                            .modelMapper
                            .map(yur, YotaUserDto.class))
                    .collect(Collectors.toList());
        } else
            throw new ApplicationException("No Students found !");
    }
}
