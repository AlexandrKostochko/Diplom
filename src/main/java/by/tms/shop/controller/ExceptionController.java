package by.tms.shop.controller;

import by.tms.shop.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(AdminExistException.class)
    public String adminExistException(AdminExistException adminExistException, Model model) {
        model.addAttribute("message", adminExistException.getMessage());
        log.info("exceptionController, adminExistException - success");
        return "firstAdminException";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userIsExistException(UserAlreadyExistsException userAlreadyExistsException, Model model) {
        model.addAttribute("message", userAlreadyExistsException.getMessage());
        log.info("exceptionController, userIsExistException - success");
        return "userIsExistException";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userIsNotFoundException(UserNotFoundException userNotFoundException, Model model) {
        model.addAttribute("message", userNotFoundException.getMessage());
        log.info("exceptionController, userIsNotFoundException - success");
        return "userIsNotFoundException";
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public String categoryIsExistException(CategoryAlreadyExistsException categoryAlreadyExistsException, Model model) {
        model.addAttribute("message", categoryAlreadyExistsException.getMessage());
        log.info("exceptionController, categoryIsExistException - success");
        return "categoryExistException";
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public String categoryIsNotFoundException(CategoryNotFoundException categoryNotFoundException, Model model) {
        model.addAttribute("message", categoryNotFoundException.getMessage());
        log.info("exceptionController, categoryIsNotFoundException - success");
        return "categoryIsNotFoundException";
    }

    @ExceptionHandler(CategoriesNotFoundException.class)
    public String categoriesIsNotFoundException(CategoriesNotFoundException categoriesNotFoundException, Model model) {
        model.addAttribute("message", categoriesNotFoundException.getMessage());
        log.info("exceptionController, categoriesIsNotFoundException - success");
        return "categoriesIsNotFoundException";
    }

    @ExceptionHandler(TechnicsNotFoundException.class)
    public String technicsIsNotFoundException(TechnicsNotFoundException technicsNotFoundException, Model model) {
        model.addAttribute("message", technicsNotFoundException.getMessage());
        log.info("exceptionController, technicsIsNotFoundException - success");
        return "technicsIsNotFoundException";
    }

    @ExceptionHandler(TechnicNotFoundException.class)
    public String technicIsNotFoundException(TechnicNotFoundException technicNotFoundException, Model model) {
        model.addAttribute("message", technicNotFoundException.getMessage());
        log.info("exceptionController, technicIsNotFoundException - success");
        return "technicIsNotFoundException";
    }

    @ExceptionHandler(TechnicAlreadyExistsException.class)
    public String technicIsExistException(TechnicAlreadyExistsException technicAlreadyExistsException, Model model) {
        model.addAttribute("message", technicAlreadyExistsException.getMessage());
        log.info("exceptionController, technicIsExistException - success");
        return "technicIsExistException";
    }

    @ExceptionHandler(UsersNotFoundException.class)
    public String usersNotFoundException(UsersNotFoundException usersNotFoundException, Model model) {
        model.addAttribute("message", usersNotFoundException.getMessage());
        log.info("exceptionController, usersNotFoundException - success");
        return "usersIsNotFoundException";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String orderIsNotFoundException(OrderNotFoundException orderNotFoundException, Model model) {
        model.addAttribute("message", orderNotFoundException.getMessage());
        log.info("exceptionController, orderIsNotFoundException - success");
        return "orderIsNotFoundException";
    }

    @ExceptionHandler(OrdersNotFoundException.class)
    public String ordersIsNotFoundException(OrdersNotFoundException ordersNotFoundException, Model model) {
        model.addAttribute("message", ordersNotFoundException.getMessage());
        log.info("exceptionController, ordersIsNotFoundException - success");
        return "ordersIsNotFoundException";
    }

    @ExceptionHandler(NotEnoughFundsException.class)
    public String notEnoughFundsException(NotEnoughFundsException notEnoughFundsException, Model model) {
        model.addAttribute("message", notEnoughFundsException.getMessage());
        log.info("exceptionController, notEnoughFundsException - success");
        return "notEnoughFundsException";
    }
}
