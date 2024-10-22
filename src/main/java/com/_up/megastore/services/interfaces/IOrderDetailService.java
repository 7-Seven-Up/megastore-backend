package com._up.megastore.services.interfaces;

import com._up.megastore.controllers.requests.OrderDetailRequest;
import com._up.megastore.data.model.Order;
import com._up.megastore.data.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService {

    List<OrderDetail> saveOrderDetails(List<OrderDetailRequest> orderDetailsRequests, Order order);

}
