package com.example.article.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp {



    private String lastName;
    private String fatherName;
    private String firstName;
    private String email;


//    @Pattern(regexp = "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$") //+998931234988
    private String phoneNumber;
    private String password;



}
