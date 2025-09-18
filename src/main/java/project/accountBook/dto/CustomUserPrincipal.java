package project.accountBook.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class CustomUserPrincipal implements OAuth2User, UserDetails {

    private final UserDto userDto;

    public CustomUserPrincipal(UserDto userDto) {
        this.userDto = userDto;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(() -> userDto.getRole());
        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    public String getName() {
        return userDto.getUsername();
    }

    public String getUserId() {
        return userDto.getUserId();
    }
}
