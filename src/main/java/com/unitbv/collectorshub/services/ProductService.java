package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Listing;
import com.unitbv.collectorshub.model.entities.Product;
import com.unitbv.collectorshub.repositories.ListingRepository;
import com.unitbv.collectorshub.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .collection(product.getCollection())
                        .manufactureYear(product.getManufactureYear())
                        .userId(product.getUserId())
                        .build())
                .toList();
    }

    public void removeProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found", 404));
        productRepository.delete(product);
    }

    public EditProductDTO editProduct(String name, String collection, Integer manufactureYear, EditProductDTO editProductDTO) {
        Product product = productRepository.findByNameAndCollectionAndManufactureYear(name,  collection, manufactureYear).orElseThrow(() -> new ApiException("Product not found", 404));
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

    public AddListingDTO addListing(AddListingDTO addListingDTO) {

        if (addListingDTO.getLink().isEmpty() || addListingDTO.getContact().isEmpty()) {

            throw new ApiException("Fields cannot be empty", 400);
        }

        Listing listing = Listing.builder()
                .productId(addListingDTO.getProductId())
                .link(addListingDTO.getLink())
                .contact(addListingDTO.getContact())
                .isActive(addListingDTO.getIsActive())
                .price(addListingDTO.getPrice())
                .description(addListingDTO.getDescription())
                .build();

        listingRepository.save(listing);
        return addListingDTO;
    }


    public EditListingDTO editListing(Long id, EditListingDTO editListingDTO) {
        Listing listing = listingRepository.findById(id).orElseThrow(() -> new ApiException("Listing not found", 404));
        if(editListingDTO.getNewLink().isEmpty() || editListingDTO.getNewContact().isEmpty()) {
            throw new ApiException("Fields cannot be empty", 400);
        }
        listing.setDescription(editListingDTO.getNewDescription());
        listing.setContact(editListingDTO.getNewContact());
        listing.setLink(editListingDTO.getNewLink());
        listing.setPrice(editListingDTO.getNewPrice());
        listing.setIsActive(editListingDTO.getNewIsActive());
        listing.setProductId(editListingDTO.getNewProductId());

        listingRepository.save(listing);
        return editListingDTO;
    }

    public List<ListingDTO> getAllListings() {
        return listingRepository.findAll().stream()
                .map(listing -> ListingDTO.builder()
                        .id(listing.getId())
                        .productId(listing.getProductId())
                        .link(listing.getLink())
                        .contact(listing.getContact())
                        .isActive(listing.getIsActive())
                        .price(listing.getPrice())
                        .description(listing.getDescription())
                        .build())
                .toList();
    }

}
