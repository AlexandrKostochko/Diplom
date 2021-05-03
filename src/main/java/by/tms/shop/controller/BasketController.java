package by.tms.shop.controller;

import by.tms.shop.entity.*;
import by.tms.shop.service.OrderService;
import by.tms.shop.service.TechnicService;
import by.tms.shop.service.UserService;
import by.tms.shop.service.exception.TechnicsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/basket")
@Slf4j
public class BasketController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private TechnicService technicService;

    @GetMapping
    public ModelAndView basketGet(ModelAndView modelAndView) {
        modelAndView.setViewName("basket");
        log.info("basketController, basketGet - success");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView basketPost(ModelAndView modelAndView, HttpSession httpSession) {
        Basket basket = (Basket) httpSession.getAttribute("basket");
        Order order = new Order();
        List<Technic> technicList = new ArrayList<>();
        for (int i = 0; i < basket.getTechnicList().size(); i++) {
            long id = basket.getTechnicList().get(i).getId();
            Technic technic = technicService.findTechnicById(id);
            if(technic.getTechnicStatus().equals(basket.getTechnicList().get(i).getTechnicStatus())) {
                technicList.add(basket.getTechnicList().get(i));
            }
        }
        if(technicList.size() != basket.getTechnicList().size()) {
            basket.setTechnicList(technicList);
            log.info("basketController, TechnicsNotFoundException - success");
            throw new TechnicsNotFoundException("Some technics are sold, please refresh your basket");
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            order.setUser(userService.findUserByLogin(authentication.getName()));
            order.setTechnicList(basket.getTechnicList());
            order.setOrderStatus(OrderStatus.NEW);
            LocalDate date = LocalDate.now();
            order.setOrderCreatedDate(date.toString());
            order.setOrderDeliveredDate("-");
            orderService.addOrder(order);
            for (int i = 0; i < basket.getTechnicList().size(); i++) {
                technicService.updateTechnic(order, basket.getTechnicList().get(i).getId());
            }
            basket.getTechnicList().clear();
            modelAndView.setViewName("redirect:/");
            log.info("basketController, basketPost - success");
        }
        return modelAndView;
    }
}
