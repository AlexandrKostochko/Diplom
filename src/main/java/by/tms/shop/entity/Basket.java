package by.tms.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Basket {

    private List<Technic> technicList = new ArrayList<>();

    public void addTechnic(Technic technic) {
        technicList.add(technic);
        log.info("basket, addTechnic - success");
    }
}
