package br.com.codenation.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.codenation.model.OrderItem;
import br.com.codenation.model.Product;
import br.com.codenation.repository.ProductRepository;
import br.com.codenation.repository.ProductRepositoryImpl;

public class OrderServiceImpl implements OrderService {

	private ProductRepository productRepository = new ProductRepositoryImpl();

	private Double calculo(OrderItem item) {
		Product produto = productRepository.findById(item.getProductId()).orElse(null);
		if (produto != null) {
			return produto.getValue() * item.getQuantity() * (produto.getIsSale().equals(true) ? 0.8 : 1);
		}
		return null;
	}

	/**
	 * Calculate the sum of all OrderItems
	 */
	@Override
	public Double calculateOrderValue(List<OrderItem> items) {
		List<Double> valoresCalculados = items.stream().map(this::calculo).collect(Collectors.toList());
		return valoresCalculados.stream().reduce(0.0, (x, y) -> x + y, Double::sum);
	}

	/**
	 * Map from idProduct List to Product Set
	 */
	@Override
	public Set<Product> findProductsById(List<Long> ids) {
		return ids.stream().map(id -> productRepository.findById(id)).filter(Optional::isPresent).map(Optional::get)
				.collect(Collectors.toSet());

	}

	/**
	 * Calculate the sum of all Orders(List<OrderIten>)
	 */
	@Override
	public Double calculateMultipleOrders(List<List<OrderItem>> orders) {
		return orders.stream().map(this::calculateOrderValue).reduce(0.0, (x, y) -> x + y, Double::sum);
	}

	/**
	 * Group products using isSale attribute as the map key
	 */
	@Override
	public Map<Boolean, List<Product>> groupProductsBySale(List<Long> productIds) {
		return findProductsById(productIds).stream().collect(Collectors.groupingBy(Product::getIsSale));
	}

}