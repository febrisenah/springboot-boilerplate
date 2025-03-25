package javaApp.users.model;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUserRequest {
    private MultipartFile file;
    
    private String longName;

    private String shortName;

    private String province;

    private String city;

    private String district;

    private String subDistrict;
}
