export const calculateProfit = (order) => {
    if (order && order.orderItem?.buyPrice && order.orderItem?.sellPrice) {
        return `$${(order.orderItem.sellPrice - order.orderItem.buyPrice).toFixed(5)}`;
    }
    return "-";
};
