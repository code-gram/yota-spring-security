package com.yash.yotasecurityapi.entity;

import com.yash.yotasecurityapi.constants.UserAccountStatusTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Project Name - YOTASecurityAPI
 * <p>
 * IDE Used - IntelliJ IDEA
 *
 * @author - yash.raj
 * @since - 02-04-2024
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yota_user")
public class YotaUser {

    @Column(name = "full_name")
    private String fullName;

    @Id
    @Column(name = "email_add")
    private String emailAdd;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private UserAccountStatusTypes accountStatus;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private UserRole userRole;
}
