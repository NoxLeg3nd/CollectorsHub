package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Listing;
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
public class ListingService {

    private final ListingRepository listingRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public AddListingDTO addListing(AddListingDTO addListingDTO) {
        if (addListingDTO.getLink().isBlank() || addListingDTO.getContact().isBlank()) {
            throw new ApiException("Fields cannot be empty", 400);
        }

        if(productRepository.findById(addListingDTO.getProductId()).isEmpty()) {
            throw new ApiException("Product not found in database", 404);
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
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ApiException("Listing not found", 404));

        if (editListingDTO.getNewLink().isBlank() || editListingDTO.getNewContact().isBlank()) {
            throw new ApiException("Fields cannot be empty", 400);
        }

        if(productRepository.findById(editListingDTO.getNewProductId()).isEmpty()) {
            throw new ApiException("Product not found in database", 404);
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

    public Page<ListingDTO> getAllListings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.findAll(pageable)
                .map(listing -> {
                    ProductDTO product = productService.getProductById(listing.getProductId());
                    return ListingDTO.builder()
                            .id(listing.getId())
                            .product(product)
                            .link(listing.getLink())
                            .contact(listing.getContact())
                            .isActive(listing.getIsActive())
                            .price(listing.getPrice())
                            .description(listing.getDescription())
                            .build();
                });
    }
}