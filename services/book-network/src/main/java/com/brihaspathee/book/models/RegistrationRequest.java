package com.brihaspathee.book.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/19/25
 * Time: 6:20 PM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.models
 * To change this template use File | Settings | File and Code Template
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotEmpty(message = "First name is mandatory")
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @Email(message = "Email is formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password should be at least 8 characters is minimum")
    private String password;
}
