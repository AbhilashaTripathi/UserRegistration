package com.interview.demo.userregistration.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private final String SPECIAL_CHARACTERS = "_#$%";
    private final int PASSWORD_MIN_SIZE = 8;
    private final String PASSWORD_REGEX_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?["
            +SPECIAL_CHARACTERS+"]).{" +
            PASSWORD_MIN_SIZE+ ",}$";

    @NotBlank(message="UserName cannot be blank")
    private String userName;
    @NotBlank(message="Password cannot be blank")
    @Size(min = PASSWORD_MIN_SIZE, message = "Password must be at least "+PASSWORD_MIN_SIZE +
            " characters")
    @Pattern(regexp   = PASSWORD_REGEX_PATTERN, message = "Password should contain at least one uppercase, one lowercase, one digit and one special character")
    private String password;
    @NotBlank(message="IpAddress cannot be blank")
    private String ipAddress; // Need to extract from header instaed of getting form user

    private Long id;
    private String country;
    private String countryCode;
    private String regionName;
    private String city;


}
