package by.tms.shop.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWallet {

    @NotBlank
    @NotNull
    @Pattern(regexp = "\\d+([.]\\d+)?")
    private String wallet;
}
