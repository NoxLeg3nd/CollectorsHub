package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Listing;
import com.unitbv.collectorshub.model.entities.Product;
import com.unitbv.collectorshub.repositories.ListingRepository;
import com.unitbv.collectorshub.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2

public class ProductService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProductRepository productRepository;
    private final ListingRepository listingRepository;

    public ResponseEntity<AddProductDTO> addProduct(AddProductDTO addProductDTO) {
        Product product = Product.builder().name(addProductDTO.getProductName()).
                description(addProductDTO.getProductDescription()).
                collection(addProductDTO.getProductCollection()).
                category(addProductDTO.getProductCategory()).
                manufactureYear(addProductDTO.getManufactureYear()).
                userId(addProductDTO.getUserId()).
                build();
        if(product.getName().isEmpty()) {
            return new ResponseEntity<>(addProductDTO, HttpStatus.BAD_REQUEST);
        }
        productRepository.save(product);
        return new ResponseEntity<>(addProductDTO, HttpStatus.OK);
    }

    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOs = productRepository.findAll().stream()
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

        return ResponseEntity.ok(productDTOs);
    }

    public ResponseEntity<RemoveProductDTO> removeProduct(RemoveProductDTO removeProductDTO) {
        Optional<Product> product = productRepository.
                findByNameAndCollectionAndManufactureYear(
                        removeProductDTO.getName(),
                        removeProductDTO.getCollection(),
                        removeProductDTO.getManufactureYear());
        if(product.isEmpty()) {
            return new ResponseEntity<>(removeProductDTO, HttpStatus.BAD_REQUEST);
        }

        productRepository.delete(product.get());
        return new ResponseEntity<>(removeProductDTO, HttpStatus.OK);
    }

    public ResponseEntity<EditProductDTO> editProduct(String name, String collection, Integer manufactureYear, EditProductDTO editProductDTO) {
        Optional<Product> product = productRepository.findByNameAndCollectionAndManufactureYear(name,  collection, manufactureYear);
        if(product.isEmpty()) {
            return new ResponseEntity<>(editProductDTO, HttpStatus.BAD_REQUEST);
        }
        product.get().setName(editProductDTO.getNewProductName());
        product.get().setDescription(editProductDTO.getNewProductDescription());
        product.get().setCategory(editProductDTO.getNewProductCategory());
        product.get().setManufactureYear(editProductDTO.getNewManufactureYear());
        product.get().setImage(editProductDTO.getNewProductImage());
        product.get().setDescription(editProductDTO.getNewProductDescription());
        productRepository.save(product.get());
        return new ResponseEntity<>(editProductDTO, HttpStatus.OK);
    }

    public ResponseEntity<ProductDTO> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(objectMapper.convertValue(product.get(), ProductDTO.class), HttpStatus.OK);
    }

    public ResponseEntity<AddListingDTO> addListing(AddListingDTO addListingDTO) {
        Listing listing = Listing.builder().
                productId(addListingDTO.getProductId()).
                link(addListingDTO.getLink()).
                contact(addListingDTO.getContact()).
                isActive(addListingDTO.getIsActive()).
                price(addListingDTO.getPrice()).
                description(addListingDTO.getDescription()).
                build();

        if(listing.getProductId() == null) {
            return new ResponseEntity<>(addListingDTO, HttpStatus.BAD_REQUEST);
        }

        listingRepository.save(listing);
        return new ResponseEntity<>(addListingDTO, HttpStatus.OK);
    }

    public ResponseEntity<EditListingDTO> editListing(Long listingId, EditListingDTO editListingDTO) {
        Optional<Listing> listing = listingRepository.findByListingId(listingId);
        if(listing.isEmpty()) {
            return new ResponseEntity<>(editListingDTO, HttpStatus.NOT_FOUND);
        }
        listing.get().setDescription(editListingDTO.getDescription());
        listing.get().setContact(editListingDTO.getContact());
        listing.get().setLink(editListingDTO.getLink());
        listing.get().setIsActive(editListingDTO.getIsActive());
        listing.get().setPrice(editListingDTO.getPrice());
        listingRepository.save(listing.get());
        return new ResponseEntity<>(editListingDTO, HttpStatus.OK);
    }

    public ResponseEntity<List<ListingDTO>> getAllListings() {
        List<ListingDTO> listingDTOs = listingRepository.findAll().stream()
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

        return ResponseEntity.ok(listingDTOs);
    }

}
