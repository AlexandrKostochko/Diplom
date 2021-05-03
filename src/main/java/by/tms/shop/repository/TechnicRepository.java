package by.tms.shop.repository;

import by.tms.shop.entity.Technic;
import by.tms.shop.entity.TechnicStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnicRepository extends JpaRepository<Technic, Long> {
    Technic findTechnicBySerialNumber(String serialNumber);
    boolean existsTechnicBySerialNumber(String serialNumber);
    Technic findTechnicById(long id);
    boolean existsTechnicById(long id);
    List<Technic> findTechnicsByCategoryNameAndTechnicStatus(String name, TechnicStatus technicStatus);
    List<Technic> findTechnicsByModelAndTechnicStatusAndCategoryName(String model, TechnicStatus technicStatus, String name);
    boolean existsTechnicsByModelAndTechnicStatusAndCategoryName(String model, TechnicStatus technicStatus, String name);
    List<Technic> findTechnicsByTechnicStatus(TechnicStatus technicStatus);
    boolean existsTechnicsByTechnicStatus(TechnicStatus technicStatus);
}
