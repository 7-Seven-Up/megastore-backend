package com._up.megastore.services.implementations;

import com._up.megastore.controllers.requests.OrderDetailRequest;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.OrderDetail;
import com._up.megastore.data.model.Product;
import com._up.megastore.data.repositories.IOrderDetailRepository;
import com._up.megastore.services.interfaces.IOrderDetailService;
import com._up.megastore.services.interfaces.IProductService;
import com._up.megastore.services.mappers.OrderDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService implements IOrderDetailService {

    private final IProductService productService;
    private final IOrderDetailRepository orderDetailRepository;

    public OrderDetailService(
            IProductService productService,
            IOrderDetailRepository orderDetailRepository
    ) {
        this.productService = productService;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<OrderDetail> saveOrderDetails(List<OrderDetailRequest> orderDetailsRequests, Order order) {
        return orderDetailsRequests.stream()
                .map(request -> saveOrderDetail(order, request))
                .collect(Collectors.toList());
    }

    private OrderDetail saveOrderDetail(Order order, OrderDetailRequest orderDetailRequest) {
        Product product = productService.findProductByIdOrThrowException(orderDetailRequest.productId());
        productService.discountProductStock(orderDetailRequest.quantity(), product);
        return orderDetailRepository.save( OrderDetailMapper.toOrderDetail(order, product, orderDetailRequest));
    }
}
