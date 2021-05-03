package by.tms.shop.service;

import by.tms.shop.entity.Order;
import by.tms.shop.entity.Technic;
import by.tms.shop.entity.TechnicStatus;
import by.tms.shop.repository.TechnicRepository;
import by.tms.shop.service.exception.TechnicAlreadyExistsException;
import by.tms.shop.service.exception.TechnicNotFoundException;
import by.tms.shop.service.exception.TechnicsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TechnicService {

    @Autowired
    private TechnicRepository technicRepository;

    public void addTechnic(Technic newTechnic) {
        if (technicRepository.findAll().size() == 0) {
            technicRepository.save(newTechnic);
            log.info("technicService, addTechnic - success");
        } else {
            if (technicRepository.existsTechnicBySerialNumber(newTechnic.getSerialNumber())) {
                log.info("technicService, TechnicAlreadyExistsException - success");
                throw new TechnicAlreadyExistsException("Technic is exists");
            } else {
                technicRepository.save(newTechnic);
                log.info("technicService, addTechnic - success");
            }
        }
    }

    public List<Technic> findTechnicsByCategoryNameAndTechnicStatus(String categoryName, TechnicStatus technicStatus) {
        if (technicRepository.findAll().size() != 0) {
            if (technicRepository.findTechnicsByCategoryNameAndTechnicStatus(categoryName, TechnicStatus.ACTIVE).size() != 0) {
                List<Technic> technics = technicRepository.findTechnicsByCategoryNameAndTechnicStatus(categoryName, technicStatus);
                Set<Technic> technicSet = new HashSet<>(technics);
                technics.clear();
                technics.addAll(technicSet);
                log.info("technicService, findTechnicsByCategoryNameAndTechnicStatus - success");
                return technics;
            } else {
                log.info("technicService, TechnicsNotFoundException - success");
                throw new TechnicsNotFoundException("Technics with this category name are not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public int numbersOfTechnicsByModelAndTechnicStatusAndCategoryName(String model, TechnicStatus technicStatus, String name) {
        if (technicRepository.findAll().size() != 0) {
            if (technicRepository.existsTechnicsByModelAndTechnicStatusAndCategoryName(model, technicStatus, name)) {
                log.info("technicService, numbersOfTechnicsByModelAndTechnicStatusAndCategoryName - success");
                return technicRepository.findTechnicsByModelAndTechnicStatusAndCategoryName(model, technicStatus, name).size();
            } else {
                log.info("technicService, TechnicsNotFoundException - success");
                throw new TechnicsNotFoundException("Technics with this model and category are not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public Technic findTechnicBySerialNumber(String serialNumber) {
        if (technicRepository.findAll().size() != 0) {
            if (technicRepository.existsTechnicBySerialNumber(serialNumber)) {
                log.info("technicService, findTechnicBySerialNumber - success");
                return technicRepository.findTechnicBySerialNumber(serialNumber);
            } else {
                log.info("technicService, TechnicNotFoundException - success");
                throw new TechnicNotFoundException("Technic is not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public List<Technic> findAllTechnics() {
        if (technicRepository.findAll().size() != 0) {
            log.info("technicService, findAllTechnics - success");
            return technicRepository.findAll();
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public Technic findTechnicById(long id) {
        if (technicRepository.findAll().size() != 0) {
            if (technicRepository.existsTechnicById(id)) {
                log.info("technicService, findTechnicById - success");
                return technicRepository.findTechnicById(id);
            } else {
                log.info("technicService, TechnicNotFoundException - success");
                throw new TechnicNotFoundException("Technic is not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public void updateTechnicStatusById(long id) {
        if (technicRepository.findAll().size() != 0) {
            if (technicRepository.existsTechnicById(id)) {
                Technic technic = technicRepository.findTechnicById(id);
                technic.setTechnicStatus(TechnicStatus.SOLD);
                technicRepository.save(technic);
                log.info("technicService, updateTechnicStatusById - success");
            } else {
                log.info("technicService, TechnicNotFoundException - success");
                throw new TechnicNotFoundException("Technic is not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public int findNumbersTechnicByModelAndTechnicStatusAndCategoryName(String model, TechnicStatus technicStatus, String name) {
        if (technicRepository.findAll().size() != 0) {
            if (technicRepository.existsTechnicsByModelAndTechnicStatusAndCategoryName(model, technicStatus, name)) {
                log.info("technicService, findNumbersTechnicByModelAndTechnicStatusAndCategoryName - success");
                return technicRepository.findTechnicsByModelAndTechnicStatusAndCategoryName(model, technicStatus, name).size();
            } else {
                log.info("technicService, TechnicsNotFoundException - success");
                throw new TechnicsNotFoundException("Technics are not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public void updateTechnic(Order order, long id) {
        Technic technic = technicRepository.findTechnicById(id);
        technic.setOrder(order);
        technicRepository.save(technic);
        log.info("technicService, updateTechnic - success");
    }

    public List<Technic> findTechnicByModelAndTechnicStatusAndCategoryName(String model, TechnicStatus technicStatus, String name) {
        if (technicRepository.findAll().size() != 0) {
            if(technicRepository.existsTechnicsByModelAndTechnicStatusAndCategoryName(model, technicStatus, name)) {
                log.info("technicService, findTechnicByModelAndTechnicStatusAndCategoryName - success");
                return technicRepository.findTechnicsByModelAndTechnicStatusAndCategoryName(model, technicStatus, name);
            } else {
                log.info("technicService, TechnicsNotFoundException - success");
                throw new TechnicsNotFoundException("Technics are not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }

    public List<Technic> findTechnicsByStatus(TechnicStatus technicStatus) {
        if (technicRepository.findAll().size() != 0) {
            if(technicRepository.existsTechnicsByTechnicStatus(technicStatus)) {
                log.info("technicService, findTechnicsByStatus - success");
                return technicRepository.findTechnicsByTechnicStatus(technicStatus);
            } else {
                log.info("technicService, TechnicsNotFoundException - success");
                throw new TechnicsNotFoundException("Technics are not found");
            }
        } else {
            log.info("technicService, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Technics are not found");
        }
    }
}
