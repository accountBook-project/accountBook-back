package project.accountBook.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.accountBook.dto.*;
import project.accountBook.entity.User;
import project.accountBook.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if(registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_provider"));
        }

        String userKey = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existUser = userRepository.findByUserKey(userKey).orElse(null);

        if(existUser == null) {
            User user = new User(userKey, oAuth2Response.getName(), oAuth2Response.getEmail(), "ROLE_USER");

            userRepository.save(user);
        }
        else {
            existUser.check(oAuth2Response.getName(), oAuth2Response.getEmail());
        }


        UserDto userDto = new UserDto();
        userDto.setUserKey(userKey);
        userDto.setUsername(oAuth2Response.getName());
        userDto.setRole("ROLE_USER");



        return new CustomOAuth2User(userDto);
    }
}
