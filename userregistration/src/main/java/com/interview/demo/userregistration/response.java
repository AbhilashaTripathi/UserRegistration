package com.interview.demo.userregistration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

public class response {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Component
    public static class IpResponse {
        private String ip;
        private String country;
        private String countryCode;

        private String regionName;

        private String city;

    }
}
