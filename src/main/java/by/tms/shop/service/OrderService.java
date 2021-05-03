package by.tms.shop.service;

import by.tms.shop.entity.*;
import by.tms.shop.repository.OrderRepository;
import by.tms.shop.repository.TechnicRepository;
import by.tms.shop.service.exception.NotEnoughFundsException;
import by.tms.shop.service.exception.OrderNotFoundException;
import by.tms.shop.service.exception.OrdersNotFoundException;
import by.tms.shop.service.exception.TechnicsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TechnicRepository technicRepository;

    public void addOrder(Order newOrder) {
        orderRepository.save(newOrder);
        log.info("orderService, addOrder - success");
    }

    public Order findOrderById(long id) {
        if (orderRepository.findAll().size() != 0) {
            if(orderRepository.existsOrderById(id)) {
                log.info("orderService, findOrderById - success");
                return orderRepository.findOrderById(id);
            } else {
                log.info("orderService, OrderNotFoundException - success");
                throw new OrderNotFoundException("Order or user is not found");
            }
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders are not found");
        }
    }

    public String orderAmountByOrderId(long id) {
        if(orderRepository.existsOrderById(id)) {
            double price = 0;
            List<Technic> technics;
            technics = orderRepository.findOrderById(id).getTechnicList();
            for (int i = 0; i < technics.size(); i++) {
                price+= Double.parseDouble(technics.get(i).getPrice());
            }
            log.info("orderService, orderAmountByOrderId - success");
            return Double.toString(price);
        } else {
            log.info("orderService, OrderNotFoundException - success");
            throw new OrderNotFoundException("Order or user is not found");
        }
    }

    public List<Order> findAllOrdersByUserLogin(String login) {
        if (orderRepository.findOrdersByUserLogin(login).size() != 0) {
            log.info("orderService, findAllOrdersByUserLogin - success");
            return orderRepository.findOrdersByUserLogin(login);
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders by this login are not found");
        }
    }

    public List<Order> findAllOrdersByOrderStatus(OrderStatus orderStatus) {
        if (orderRepository.findOrdersByOrderStatus(orderStatus).size() != 0) {
            log.info("orderService, findAllOrdersByOrderStatus - success");
            return orderRepository.findOrdersByOrderStatus(orderStatus);
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders by this status are not found");
        }
    }

    public List<Order> findAllOrders() {
        if (orderRepository.findAll().size() != 0) {
            log.info("orderService, findAllOrders - success");
            return orderRepository.findAll();
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders are not found");
        }
    }

    public void removeTechnicFromOrderByIdAndUserLogin(long orderId, Technic technic, String login) {
        if (orderRepository.findAll().size() != 0) {
            if(orderRepository.existsOrderById(orderId) && orderRepository.findOrderById(orderId).getUser().getLogin().equals(login)) {
                Order order = orderRepository.findOrderById(orderId);
                for (int i = 0; i < order.getTechnicList().size(); i++) {
                    Technic technic1 = order.getTechnicList().get(i);
                    if(order.getTechnicList().contains(technic)) {
                        technic1.setOrder(null);
                        technicRepository.save(technic1);
                        order.getTechnicList().remove(technic1);
                        orderRepository.save(order);
                    }
                }
                log.info("orderService, removeTechnicFromOrderByIdAndUserLogin - success");
            } else {
                log.info("orderService, OrderNotFoundException - success");
                throw new OrderNotFoundException("Order is not found");
            }
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders are not found");
        }
    }

    public void purchaseTechnicByUserIdAndOrderId(long userId, long orderId) {
        if (orderRepository.findAll().size() != 0) {
            if (orderRepository.existsOrderByIdAndUserId(orderId, userId)) {
                double tempWallet = 0;
                double tempUserWallet = Double.parseDouble(orderRepository.findOrderById(orderId).getUser().getWallet());
                for (int i = 0; i < orderRepository.findOrderById(orderId).getTechnicList().size(); i++) {
                    tempWallet += Double.parseDouble(orderRepository.findOrderById(orderId).getTechnicList().get(i).getPrice());
                }
                if (tempUserWallet >= tempWallet) {
                    double newWallet = tempUserWallet - tempWallet;
                    orderRepository.findOrderById(orderId).getUser().setWallet(Double.toString(newWallet));
                    Order order = orderRepository.findOrderById(orderId);
                    order.setOrderStatus(OrderStatus.DELIVERED);
                    LocalDate date = LocalDate.now();
                    order.setOrderDeliveredDate(date.toString());
                    orderRepository.save(order);
                    log.info("orderService, purchaseTechnicByUserIdAndOrderId - success");
                } else {
                    Order order = orderRepository.findOrderById(orderId);
                    order.setOrderStatus(OrderStatus.PROCESSED);
                    orderRepository.save(order);
                    log.info("orderService, NotEnoughFundsException - success");
                    throw new NotEnoughFundsException("Up your wallet");
                }
            } else {
                log.info("orderService, OrderNotFoundException - success");
                throw new OrderNotFoundException("Order or user is not found");
            }
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders are not found");
        }
    }

    public void updateOrderByIdAndTechnicList(long id, List<Technic> technics) {
        if (orderRepository.findAll().size() != 0) {
            if (orderRepository.existsOrderById(id)) {
                Order order = orderRepository.findOrderById(id);
                order.setTechnicList(technics);
                orderRepository.save(order);
                log.info("orderService, updateOrderByIdAndTechnicList - success");
            } else {
                log.info("orderService, OrderNotFoundException - success");
                throw new OrderNotFoundException("Order is not found");
            }
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders are not found");
        }
    }

    public List<Order> findOrdersByLogin(String login) {
        log.info("orderService, findOrdersByLogin - success");
        return orderRepository.findOrdersByUserLogin(login);
    }

    public void checkOrderForTechnicStatus(Order order) {
        if (orderRepository.findAll().size() != 0) {
            if(orderRepository.existsOrderById(order.getId())) {
                List<Technic> technics = new ArrayList<>();
                for (Technic technic : order.getTechnicList()) {
                    if(technic.getTechnicStatus().equals(TechnicStatus.ACTIVE)) {
                        technics.add(technic);
                    }
                }
                if(technics.size() != order.getTechnicList().size()) {
                    updateOrderByIdAndTechnicList(order.getId(), technics);
                    log.info("orderService, TechnicsNotFoundException - success");
                    throw new TechnicsNotFoundException("Your order is update, because some technics are sold");
                }
            } else {
                log.info("orderService, OrderNotFoundException - success");
                throw new OrderNotFoundException("Order is not found");
            }
        } else {
            log.info("orderService, OrdersNotFoundException - success");
            throw new OrdersNotFoundException("Orders are not found");
        }
    }
    @Transactional
    public void deleteOrderById(long id) {
        orderRepository.deleteOrderById(id);
        log.info("orderService, deleteOrderById - success");
    }
}
