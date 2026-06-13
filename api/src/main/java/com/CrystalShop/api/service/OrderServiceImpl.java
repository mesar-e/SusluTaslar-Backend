package com.CrystalShop.api.service;

import com.CrystalShop.api.dto.OrderItemRequest;
import com.CrystalShop.api.dto.OrderItemResponse;
import com.CrystalShop.api.dto.OrderRequest;
import com.CrystalShop.api.dto.OrderResponse;
import com.CrystalShop.api.entity.Order;
import com.CrystalShop.api.entity.OrderItem;
import com.CrystalShop.api.entity.Product;
import com.CrystalShop.api.entity.User;
import com.CrystalShop.api.enums.OrderStatus;
import com.CrystalShop.api.enums.Role;
import com.CrystalShop.api.exception.ApiException;
import com.CrystalShop.api.repository.OrderRepository;
import com.CrystalShop.api.repository.ProductRepository;
import com.CrystalShop.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    private User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiException("Kullanıcı bulunamadı!", HttpStatus.UNAUTHORIZED));
        }
        throw new ApiException("Sisteme giriş yapmanız gerekiyor!", HttpStatus.UNAUTHORIZED);
    }

    private OrderResponse convertToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setUserId(order.getUser().getId());
        response.setTotalPrice(order.getTotalPrice());
        response.setStatus(order.getStatus());
        response.setOrderDate(order.getOrderDate());

        List<OrderItemResponse> itemResponses = order.getItems().stream().map(item -> {
            OrderItemResponse itemResp = new OrderItemResponse();
            itemResp.setId(item.getId());
            itemResp.setProductId(item.getProduct().getId());
            itemResp.setProductName(item.getProduct().getName());
            itemResp.setQuantity(item.getQuantity());
            itemResp.setPrice(item.getPrice());
            return itemResp;
        }).collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        User buyer = getLoggedInUser();

        Order order = new Order();
        order.setUser(buyer);
        order.setStatus(OrderStatus.PENDING);

        BigDecimal totalOrderPrice = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ApiException("Ürün bulunamadı! ID: " + itemRequest.getProductId(), HttpStatus.NOT_FOUND));


            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new ApiException("Yetersiz stok! '" + product.getName() + "' ürününden sadece " + product.getStockQuantity() + " adet kaldı.", HttpStatus.BAD_REQUEST);
            }


            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            productRepository.save(product);


            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalOrderPrice = totalOrderPrice.add(itemTotal);

            order.getItems().add(orderItem);
        }

        order.setTotalPrice(totalOrderPrice);
        Order savedOrder = orderRepository.save(order);

        return convertToResponse(savedOrder);
    }

        @Override
        public List<OrderResponse> getMyOrders() {
            User loggedInUser = getLoggedInUser();
            List<Order> myOrders = orderRepository.findByUserId(loggedInUser.getId());

            return myOrders.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        }

        @Override
        public List<OrderResponse> getAllOrders() {
            return orderRepository.findAll().stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ApiException("Sipariş bulunamadı! ID: " + id, HttpStatus.NOT_FOUND));

        User loggedInUser = getLoggedInUser();
        if (loggedInUser.getRole() != Role.ADMIN && !order.getUser().getId().equals(loggedInUser.getId())) {
            throw new ApiException("Erişim Reddedildi: Sadece kendi siparişlerinizi iptal edebilirsiniz!", HttpStatus.FORBIDDEN);
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new ApiException("Bu sipariş zaten iptal edilmiş!", HttpStatus.BAD_REQUEST);
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();

            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());

            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        Order updatedOrder = orderRepository.save(order);

        return convertToResponse(updatedOrder);
    }
}
