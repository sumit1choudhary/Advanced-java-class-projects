package com.shopsphere.service;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.Product;
import java.util.List;

public class ProductService {
    private final ProductDAO dao = new ProductDAO();

    public List<Product> list(String q) throws Exception {
        return dao.all(q);
    }

    public List<Product> list(String q, int categoryId, double minPrice,
                              double maxPrice, String sort) throws Exception {
        return dao.all(q, categoryId, minPrice, maxPrice, sort);
    }

    public Product get(int id) throws Exception {
        return dao.find(id);
    }
}
