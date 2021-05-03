package by.tms.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnicAdd {

    @NotBlank
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+")
    private String categoryName;

    @NotBlank
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+")
    private String manufacturer;

    @NotBlank
    @NotNull
    @Size(max = 2000)
    private String description;

    @NotBlank
    @NotNull
    private String model;

    @NotBlank
    @NotNull
    private String serialNumber;

    @NotBlank
    @NotNull
    @Pattern(regexp = "\\d+([.]\\d+)?")
    private String price;

    @NotBlank
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]+")
    private String countryManufacture;

    private MultipartFile image;
}
