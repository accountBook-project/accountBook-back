package project.accountBook.dto;


import lombok.Data;

@Data
public class UserDto {

    private String role;
    private String username;
    private String userId;

    public UserDto() {}
    public UserDto(String userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }
}
