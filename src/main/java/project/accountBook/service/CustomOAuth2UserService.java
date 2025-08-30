package project.accountBook.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.accountBook.dto.*;
import project.accountBook.entity.User;
import project.accountBook.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
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

        UserDto userDto = new UserDto();

        if(existUser == null) {
            User user = new User(userKey, oAuth2Response.getName(), oAuth2Response.getEmail(), "ROLE_USER");

            User savedUser = userRepository.save(user);

            userDto.setUserId(savedUser.getId().toString());
            userDto.setUsername(oAuth2Response.getName());
            userDto.setRole("ROLE_USER");
        }
        else {
            existUser.check(oAuth2Response.getName(), oAuth2Response.getEmail());
        }

        return new CustomUserPrincipal(userDto);
    }
}
