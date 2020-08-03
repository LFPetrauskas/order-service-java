package br.com.codenation.service;

import br.com.codenation.model.OrderItem;
import br.com.codenation.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class OrderServiceSubmitTest {

    private OrderService orderService = new OrderServiceImpl();

    @Test
    public void testCalculateOrderValue() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        assertNotNull(this.orderService.calculateOrderValue(items));
    }

    @Test
    public void testCalculateOrderValueTotal() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        Assert.assertEquals(Double.valueOf(850.0), this.orderService.calculateOrderValue(items));
    }

    @Test
    public void testCalculateOrderValueTotalWithSale() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        items.add(new OrderItem(8l, 1l));
        Assert.assertEquals(Double.valueOf(1010.0), this.orderService.calculateOrderValue(items));
    }

    @Test
    public void testCalculateOrderValueTotalWithJustSale() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(8l, 1l));
        Assert.assertEquals(Double.valueOf(160.0), this.orderService.calculateOrderValue(items));
    }

    @Test
    public void testFindProductsByIdIsNotNull() {
        assertNotNull(this.orderService.findProductsById(Arrays.asList(1l, 2l, 3l, 4l, 5l)).size());
    }

    @Test
    public void testFindProductsByIdSize() {
        Assert.assertEquals(5l, this.orderService.findProductsById(Arrays.asList(1l, 2l, 3l, 4l, 5l)).size());
    }

    @Test
    public void testFindProductsByIdSize2() {
        Assert.assertEquals(2l, this.orderService.findProductsById(Arrays.asList(1l,2l,Long.MAX_VALUE)).size());
    }

    @Test
    public void testCalculateMultipleOrders() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        List<OrderItem> items2 = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        List<OrderItem> items3 = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        assertNotNull(this.orderService.calculateMultipleOrders(Arrays.asList(items, items2, items3)));
    }

    @Test
    public void testCalculateTotalValueMultipleOrders() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        List<OrderItem> items2 = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        List<OrderItem> items3 = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        Assert.assertEquals(Double.valueOf(2550.0), this.orderService.calculateMultipleOrders(Arrays.asList(items, items2, items3)));
    }

    @Test
    public void testCalculateTotalValueMultipleOrdersWithSale() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(1l, 3l));
        items.add(new OrderItem(2l, 2l));
        items.add(new OrderItem(8l, 1l));
        List<OrderItem> items2 = new ArrayList<>();
        items2.add(new OrderItem(1l, 3l));
        items2.add(new OrderItem(2l, 2l));
        Assert.assertEquals(Double.valueOf(1860.0), this.orderService.calculateMultipleOrders(Arrays.asList(items, items2)));
    }

    @Test
    public void testGroupProducts() {
        Map<Boolean, List<Product>> groupedProducts = this.orderService.groupProductsBySale(Arrays.asList(1l, 2l, 12l));
        Assert.assertEquals(2l, groupedProducts.get(false).size());
        Assert.assertEquals(1l, groupedProducts.get(true).size());
    }
}
