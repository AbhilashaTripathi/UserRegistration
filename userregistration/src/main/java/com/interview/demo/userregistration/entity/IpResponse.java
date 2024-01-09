package com.interview.demo.userregistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class IpResponse {
    private String ip;
    private String country;
    private String countryCode;

    private String regionName;

    private String city;

}
