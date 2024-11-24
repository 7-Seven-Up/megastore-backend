package com._up.megastore.data.reports;

public record OrdersByStateReportResponse(
        Integer inProgressOrders,
        Integer finishedOrders,
        Integer inDeliveryOrders,
        Integer deliveredOrders,
        Integer cancelledOrders,
        Integer totalOrders
) {
}
