package com.interview.demo.userregistration.service;

import com.interview.demo.userregistration.dto.UserDTO;
import com.interview.demo.userregistration.entity.IpResponse;
import com.interview.demo.userregistration.entity.User;
import com.interview.demo.userregistration.exception.ResourceNotFoundException;
import com.interview.demo.userregistration.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@NoArgsConstructor
public class UserService {

    private final String IP_API = "http://ip-api.com/json/";
    private final String COUNTRY_CODE_CANADA = "CA";



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    public UserDTO registerUser(UserDTO userDTO) throws Exception {
        ResponseEntity<IpResponse> ipResponseResponseEntity = restTemplate.getForEntity(
                IP_API + userDTO.getIpAddress(), IpResponse.class);
        if(ipResponseResponseEntity.getStatusCode().is2xxSuccessful()){
            IpResponse ipResponse = ipResponseResponseEntity.getBody();
            if(ipResponse.getCountryCode() != null &&
                    !COUNTRY_CODE_CANADA.equalsIgnoreCase(ipResponse.getCountryCode())) {
                throw new Exception("User is not eligible to register. ");
                }else{
                userDTO.setCountry(ipResponse.getCountry());
                userDTO.setCountryCode(ipResponse.getCountryCode());
                userDTO.setRegionName(ipResponse.getRegionName());
                userDTO.setCity(ipResponse.getCity());
            }
            }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user); //new User();
        userRepository.save(user);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
    public UserDTO getUser(String userName){
        User user = userRepository.findByUserName(userName).orElseThrow(()->
                new ResourceNotFoundException("User", "userName", userName));
        UserDTO userDTO = new UserDTO();
        mapToDto(user, userDTO);
        return userDTO;
    }

    private void mapToDto(User user, UserDTO userDTO) {
        BeanUtils.copyProperties(user, userDTO);
    }

    public boolean isPasswordValid(String password) {


        String regExpn =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        CharSequence inputStr = password;

        Pattern pattern = Pattern.compile(regExpn);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
