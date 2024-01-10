package com.interview.demo.userregistration.service;

import com.interview.demo.userregistration.dto.UserDTO;
import com.interview.demo.userregistration.entity.User;
import com.interview.demo.userregistration.exception.InvalidUserException;
import com.interview.demo.userregistration.exception.ResourceNotFoundException;
import com.interview.demo.userregistration.repository.UserRepository;
import com.interview.demo.userregistration.response.IpResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
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

    public UserDTO registerUser(UserDTO userDTO) throws InvalidUserException {
        ResponseEntity<IpResponse> ipResponseResponseEntity = restTemplate.getForEntity(
                IP_API + userDTO.getIpAddress(), IpResponse.class);
        if(ipResponseResponseEntity.getStatusCode().is2xxSuccessful()){
            IpResponse ipResponse = ipResponseResponseEntity.getBody();
            if(ipResponse.getCountryCode() != null &&
                    COUNTRY_CODE_CANADA.equalsIgnoreCase(ipResponse.getCountryCode())) {
                userDTO.setCountry(ipResponse.getCountry());
                userDTO.setCountryCode(ipResponse.getCountryCode());
                userDTO.setRegionName(ipResponse.getRegionName());
                userDTO.setCity(ipResponse.getCity());
                }else{
                throw new InvalidUserException("User is not eligible to register. ");
            }
            }
        if(userRepository.findByUserName(userDTO.getUserName()).isPresent()){
            throw new InvalidUserException("User already exists. ");
        }

        User user = new User();
        mapToEntity(userDTO, user); //new User();
        userRepository.save(user);
        mapToDto(user, userDTO);
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

    private void mapToEntity(UserDTO userDTO, User user) {
        BeanUtils.copyProperties(userDTO,user);
    }


}
