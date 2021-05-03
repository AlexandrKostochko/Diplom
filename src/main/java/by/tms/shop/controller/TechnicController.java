package by.tms.shop.controller;

import by.tms.shop.entity.*;
import by.tms.shop.service.*;
import by.tms.shop.service.exception.OrderNotFoundException;
import by.tms.shop.service.exception.TechnicsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/technic")
@Slf4j
public class TechnicController {

    @Autowired
    private TechnicService technicService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ImageService imageService;

    @GetMapping("add")
    public ModelAndView addTechnicGet(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.ADMIN) || userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.MANAGER)) {
            modelAndView.setViewName("technicAdd");
            modelAndView.addObject("technic", new TechnicAdd());
            log.info("technicController, addTechnicGet(ADMIN or MANAGER) - success");
        } else {
            modelAndView.setViewName("redirect:/");
            log.info("technicController, addTechnicGet(USER) - success");
        }
        return modelAndView;
    }

    @PostMapping("add")
    public ModelAndView addTechnicPost(@Valid @ModelAttribute("technic") TechnicAdd technicAdd, Errors errors, ModelAndView modelAndView) throws IOException {
        int marker;
        if (errors.hasErrors()) {
            modelAndView.setViewName("technicAdd");
            log.info("technicController, addTechnicPost(Error) - success");
        } else {
            if (categoryService.existCategoryByName(technicAdd.getCategoryName())) {
                Technic technic = new Technic();
                technic.setCategory(categoryService.findCategoryByName(technicAdd.getCategoryName()));
                technic.setDescription(technicAdd.getDescription());
                technic.setCountryManufacture(technicAdd.getCountryManufacture());
                technic.setModel(technicAdd.getModel());
                technic.setSerialNumber(technicAdd.getSerialNumber());
                technic.setManufacturer(technicAdd.getManufacturer());
                technic.setPrice(technicAdd.getPrice());
                technic.setTechnicStatus(TechnicStatus.ACTIVE);
                MultipartFile multiImage = technicAdd.getImage();
                Image image = imageService.upload(multiImage, "technic", technicAdd.getCategoryName(), technicAdd.getModel());
                technic.setImage(image);
                marker = 1;
                technicService.addTechnic(technic);
                log.info("technicController, addTechnicGet - success");
            } else {
                marker = 2;
                modelAndView.addObject("message", "The entered category name does not exist");
                log.info("technicController, addTechnicGet(message) - success");
            }
            modelAndView.addObject("marker", marker);
            modelAndView.setViewName("technicAdd");
        }
        return modelAndView;
    }

    @GetMapping("technics/{id}")
    public ModelAndView technicGet(@PathVariable("id") long id, ModelAndView modelAndView) {
        int marker = 0;
        Technic technic = technicService.findTechnicById(id);
        modelAndView.addObject("technic", technic);
        modelAndView.setViewName("technic");
        modelAndView.addObject("marker", marker);
        modelAndView.addObject("quantity", technicService.findNumbersTechnicByModelAndTechnicStatusAndCategoryName(technic.getModel(), TechnicStatus.ACTIVE, technic.getCategory().getName()));
        log.info("technicController, technicGet - success");
        return modelAndView;
    }

    @PostMapping("/addToBasket")
    public ModelAndView postAddTechnic(long technicId, ModelAndView modelAndView, HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Order> orders = orderService.findOrdersByLogin(authentication.getName());
        for (Order order : orders) {
            if(order.getOrderStatus().equals(OrderStatus.NEW) || order.getOrderStatus().equals(OrderStatus.PROCESSED)) {
                log.info("technicController, OrderNotFoundException - success");
                throw new OrderNotFoundException("Pay for your previous orders");
            }
        }
        Technic technic = technicService.findTechnicById(technicId);
        List<Technic> technics = technicService.findTechnicByModelAndTechnicStatusAndCategoryName(technic.getModel(), TechnicStatus.ACTIVE, technic.getCategory().getName());
        Basket basket = (Basket) httpSession.getAttribute("basket");
        int numbersOfTechnic = technics.size();
        if(basket.getTechnicList().size() > 0) {
            int numOfBasketModel = 0;
            for (int i = 0; i < basket.getTechnicList().size(); i++) {
                if(basket.getTechnicList().get(i).getModel().equals(technics.get(0).getModel()) && basket.getTechnicList().get(i).getCategory().equals(technics.get(0).getCategory())) {
                    numOfBasketModel++;
                }
            }
            if (numbersOfTechnic > numOfBasketModel) {
                basket.addTechnic(technics.get(numOfBasketModel));
                log.info("technicController, postAddTechnic - success");
            } else {
                log.info("technicController, TechnicsNotFoundException - success");
                throw new TechnicsNotFoundException("Technics are not found");
            }
        } else {
            basket.addTechnic(technics.get(0));
            log.info("technicController, postAddTechnic - success");
        }
        modelAndView.setViewName("redirect:/technic/technics/" + technicId);
        return modelAndView;
    }

    @GetMapping("/allTechnics")
    public ModelAndView allTechnicsGet(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.ADMIN)) {
            modelAndView.addObject("allTechnics", technicService.findAllTechnics());
            modelAndView.setViewName("technics");
            log.info("technicController, allTechnicsGet(ADMIN) - success");
        } else if (userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.MANAGER)) {
            modelAndView.addObject("allTechnics", technicService.findAllTechnics());
            modelAndView.setViewName("technics");
            log.info("technicController, allTechnicsGet(MANAGER) - success");
        } else {
            modelAndView.setViewName("redirect:/");
            log.info("technicController, allTechnicsGet(USER) - success");
        }
        return modelAndView;
    }

    @GetMapping("/serialNumber")
    public ModelAndView getTechnicBySerialNumber(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.ADMIN) || userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.MANAGER)) {
            modelAndView.setViewName("technicSerialNumber");
            modelAndView.addObject("serialNumber", new TechnicSerialNumber());
            log.info("technicController, getTechnicBySerialNumber(ADMIN or MANAGER) - success");
        } else {
            modelAndView.setViewName("redirect:/");
            log.info("technicController, getTechnicBySerialNumber(USER) - success");
        }
        return modelAndView;
    }

    @PostMapping("/serialNumber")
    public ModelAndView postTechnicBySerialNumber(@Valid @ModelAttribute("serialNumber") TechnicSerialNumber technicSerialNumber,Errors errors, ModelAndView modelAndView) {
        if (errors.hasErrors()) {
            modelAndView.setViewName("technicSerialNumber");
            log.info("technicController, postTechnicBySerialNumber(Error) - success");
        } else {
            Technic technic = technicService.findTechnicBySerialNumber(technicSerialNumber.getSerialNumber());
            int marker = 1;
            modelAndView.addObject("technic", technic);
            modelAndView.addObject("marker", marker);
            modelAndView.setViewName("technic");
            log.info("technicController, postTechnicBySerialNumber - success");
        }
        return modelAndView;
    }

    @GetMapping("/status")
    public ModelAndView getTechnicsByStatus(ModelAndView modelAndView) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.ADMIN) || userService.findUserByLogin(authentication.getName()).getUserRole().equals(UserRole.MANAGER)) {
            modelAndView.setViewName("technicStatus");
            log.info("technicController, getTechnicsByStatus(ADMIN or MANAGER) - success");
        } else {
            modelAndView.setViewName("redirect:/");
            log.info("technicController, getTechnicsByStatus(USER) - success");
        }
        return modelAndView;
    }

    @PostMapping("/status")
    public ModelAndView postTechnicsByStatus(TechnicStatus technicStatus, ModelAndView modelAndView) {
        List<Technic> technics = technicService.findTechnicsByStatus(technicStatus);
        modelAndView.addObject("technics", technics);
        modelAndView.setViewName("technicsByStatus");
        log.info("technicController, postTechnicsByStatus - success");
        return modelAndView;
    }
}
