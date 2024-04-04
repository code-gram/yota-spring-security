package com.yash.yotasecurityapi.controllers;

import com.yash.yotasecurityapi.dto.YotaUserDto;
import com.yash.yotasecurityapi.services.IServices.IYOTAUserService;
import com.yash.yotasecurityapi.validators.IsTechnicalManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project Name - YOTASecurityPOC
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - yashr
 * @since - 02-04-2024
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IYOTAUserService userService;

    @GetMapping("/get/all-trainers")
    @IsTechnicalManager
    public ResponseEntity<List<YotaUserDto>> getAllTrainers() {
        List<YotaUserDto> allTrainers = this.userService.getAllTrainers();
        return new ResponseEntity<>(allTrainers, HttpStatus.OK);
    }

    @GetMapping("/get/all-students")
    @PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_TECHNICAL_MANAGER')")
    public ResponseEntity<List<YotaUserDto>> getAllStudents() {
        List<YotaUserDto> allStudents = this.userService.getAllStudents();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }
}
