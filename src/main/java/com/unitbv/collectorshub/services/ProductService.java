package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Product;
import com.unitbv.collectorshub.repositories.ListingRepository;
import com.unitbv.collectorshub.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2

public class ProductService {
    private final ProductRepository productRepository;
    private final ListingRepository listingRepository;

    public AddProductDTO addProduct(AddProductDTO addProductDTO) {
        Product product = Product.builder().name(addProductDTO.getProductName()).
                description(addProductDTO.getProductDescription()).
                collection(addProductDTO.getProductCollection()).
                category(addProductDTO.getProductCategory()).
                manufactureYear(addProductDTO.getManufactureYear()).
                userId(addProductDTO.getUserId()).
                build();
        if(product.getName().isBlank() || product.getCategory().isBlank()
                || product.getCollection().isBlank() || product.getDescription().isBlank()) {
            throw new ApiException("Product details cannot be empty", 400);
        }

        productRepository.save(product);
        return addProductDTO;
    }

    public Page<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .collection(product.getCollection())
                        .manufactureYear(product.getManufactureYear())
                        .userId(product.getUserId())
                        .build());
    }

    public Page<ProductDTO> getAllProductsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByUserId(userId, pageable)
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .collection(product.getCollection())
                        .manufactureYear(product.getManufactureYear())
                        .image(product.getImage())
                        .userId(product.getUserId())
                        .build());
    }

    public void removeProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found", 404));
        productRepository.delete(product);
    }

    public EditProductDTO editProduct(Long id, EditProductDTO editProductDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ApiException("Product not found", 404));
        if(editProductDTO.getNewProductName().isBlank() || editProductDTO.getNewProductCategory().isBlank()
                || editProductDTO.getNewProductDescription().isBlank() || editProductDTO.getNewProductCollection().isBlank()) {
            throw new ApiException("Fields cannot be empty", 400);
        }
        product.setName(editProductDTO.getNewProductName());
        product.setDescription(editProductDTO.getNewProductDescription());
        product.setCategory(editProductDTO.getNewProductCategory());
        product.setManufactureYear(editProductDTO.getNewManufactureYear());
        product.setImage(editProductDTO.getNewProductImage());
        productRepository.save(product);
        return editProductDTO;
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ApiException("Product not found", 404));
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .collection(product.getCollection())
                .manufactureYear(product.getManufactureYear())
                .image(product.getImage())
                .description(product.getDescription())
                .userId(product.getUserId()).
                build();
    }
    public ProductDTO getProductByUserId(Long userId) {
        Product product = productRepository.findByUserId(userId).orElseThrow(() -> new ApiException("Product not found with provided userId", 404));
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .collection(product.getCollection())
                .manufactureYear(product.getManufactureYear())
                .image(product.getImage())
                .description(product.getDescription())
                .userId(product.getUserId()).
                build();
    }
}
