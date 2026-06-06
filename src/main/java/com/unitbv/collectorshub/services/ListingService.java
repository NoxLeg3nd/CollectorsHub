package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Listing;
import com.unitbv.collectorshub.model.entities.Product;
import com.unitbv.collectorshub.model.entities.User;
import com.unitbv.collectorshub.repositories.FavouritesRepository;
import com.unitbv.collectorshub.repositories.ListingRepository;
import com.unitbv.collectorshub.repositories.ProductRepository;
import com.unitbv.collectorshub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class ListingService {

    private final ListingRepository listingRepository;
    private final ProductRepository productRepository;
    private final FavouritesRepository favouritesRepository;
    private final UserRepository userRepository;

    public AddListingDTO addListing(AddListingDTO addListingDTO) {
        if (addListingDTO.getLink().isBlank() || addListingDTO.getContact().isBlank()) {
            throw new ApiException("Fields cannot be empty", 400);
        }

        Product product = productRepository.findById(addListingDTO.getProductId())
                .orElseThrow(() -> new ApiException("Product not found in database", 404));

        User user = userRepository.findById(addListingDTO.getUserId())
                .orElseThrow(() -> new ApiException("User not found", 404));

        Listing listing = Listing.builder()
                .link(addListingDTO.getLink())
                .contact(addListingDTO.getContact())
                .isActive(addListingDTO.getIsActive())
                .price(addListingDTO.getPrice())
                .description(addListingDTO.getDescription())
                .product(product)
                .user(user)
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

        Product product = productRepository.findById(editListingDTO.getNewProductId())
                .orElseThrow(() -> new ApiException("Product not found in database", 404));

        listing.setDescription(editListingDTO.getNewDescription());
        listing.setContact(editListingDTO.getNewContact());
        listing.setLink(editListingDTO.getNewLink());
        listing.setPrice(editListingDTO.getNewPrice());
        listing.setIsActive(editListingDTO.getNewIsActive());
        listing.setProduct(product);

        listingRepository.save(listing);
        return editListingDTO;
    }

    public Page getAllListings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.findAllByIsActiveTrue(pageable)
                .map(listing -> ListingDTO.builder()
                        .id(listing.getId())
                        .link(listing.getLink())
                        .contact(listing.getContact())
                        .isActive(listing.getIsActive())
                        .price(listing.getPrice())
                        .description(listing.getDescription())
                        .product(ProductDTO.builder()
                                .id(listing.getProduct().getId())
                                .name(listing.getProduct().getName())
                                .category(listing.getProduct().getCategory())
                                .collection(listing.getProduct().getCollection())
                                .image(listing.getProduct().getImage())
                                .manufactureYear(listing.getProduct().getManufactureYear())
                                .description(listing.getProduct().getDescription())
                                .userId(listing.getProduct().getUser().getId())
                                .build())
                        .userId(listing.getUser().getId())
                        .build());
    }

    public Long getActiveListingIdByProductId(Long productId) {
        Listing listing = listingRepository.findFirstByProduct_IdAndIsActiveTrue(productId)
                .orElseThrow(() -> new ApiException("Listing not found", 404));
        return listing.getId();
    }

    public Page getAllListingsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.findAllByUser_Id(userId, pageable)
                .map(listing -> ListingDTO.builder()
                        .id(listing.getId())
                        .link(listing.getLink())
                        .contact(listing.getContact())
                        .isActive(listing.getIsActive())
                        .price(listing.getPrice())
                        .description(listing.getDescription())
                        .product(ProductDTO.builder()
                                .id(listing.getProduct().getId())
                                .name(listing.getProduct().getName())
                                .category(listing.getProduct().getCategory())
                                .collection(listing.getProduct().getCollection())
                                .image(listing.getProduct().getImage())
                                .manufactureYear(listing.getProduct().getManufactureYear())
                                .description(listing.getProduct().getDescription())
                                .userId(listing.getProduct().getUser().getId())
                                .build())
                        .userId(listing.getUser().getId())
                        .build());
    }

    public ListingDTO getListingById(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ApiException("Listing not found", 404));
        return ListingDTO.builder()
                .id(listing.getId())
                .product(ProductDTO.builder()
                        .id(listing.getProduct().getId())
                        .name(listing.getProduct().getName())
                        .category(listing.getProduct().getCategory())
                        .collection(listing.getProduct().getCollection())
                        .image(listing.getProduct().getImage())
                        .manufactureYear(listing.getProduct().getManufactureYear())
                        .description(listing.getProduct().getDescription())
                        .userId(listing.getProduct().getUser().getId())
                        .build())
                .link(listing.getLink())
                .contact(listing.getContact())
                .isActive(listing.getIsActive())
                .price(listing.getPrice())
                .description(listing.getDescription())
                .userId(listing.getUser().getId())
                .username(listing.getUser().getUsername())
                .build();
    }

    public void removeListing(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ApiException("Listing not found", 404));
        favouritesRepository.deleteAll(favouritesRepository.findAllByListing_Id(id));
        listingRepository.delete(listing);
    }

    public Page searchListings(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.searchAll(query, pageable)
                .map(listing -> ListingDTO.builder()
                        .id(listing.getId())
                        .link(listing.getLink())
                        .contact(listing.getContact())
                        .isActive(listing.getIsActive())
                        .price(listing.getPrice())
                        .description(listing.getDescription())
                        .product(ProductDTO.builder()
                                .id(listing.getProduct().getId())
                                .name(listing.getProduct().getName())
                                .category(listing.getProduct().getCategory())
                                .collection(listing.getProduct().getCollection())
                                .image(listing.getProduct().getImage())
                                .manufactureYear(listing.getProduct().getManufactureYear())
                                .description(listing.getProduct().getDescription())
                                .userId(listing.getProduct().getUser().getId())
                                .build())
                        .userId(listing.getUser().getId())
                        .username(listing.getUser().getUsername())
                        .build());
    }

    public List<ListingDTO> getRecommendations(Long userId, int limit) {
        List<Product> userProducts = productRepository.findAllByUser_Id(
                userId, PageRequest.of(0, 50)
        ).getContent();

        if (userProducts.isEmpty()) return List.of();

        List<String> categories = userProducts.stream()
                .map(Product::getCategory)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .toList();

        if (categories.isEmpty()) return List.of();

        return listingRepository.findRecommendedByCategories(
                        userId, categories, PageRequest.of(0, limit)
                ).getContent().stream()
                .map(listing -> ListingDTO.builder()
                        .id(listing.getId())
                        .link(listing.getLink())
                        .contact(listing.getContact())
                        .isActive(listing.getIsActive())
                        .price(listing.getPrice())
                        .description(listing.getDescription())
                        .product(ProductDTO.builder()
                                .id(listing.getProduct().getId())
                                .name(listing.getProduct().getName())
                                .category(listing.getProduct().getCategory())
                                .collection(listing.getProduct().getCollection())
                                .image(listing.getProduct().getImage())
                                .manufactureYear(listing.getProduct().getManufactureYear())
                                .description(listing.getProduct().getDescription())
                                .userId(listing.getProduct().getUser().getId())
                                .build())
                        .userId(listing.getUser().getId())
                        .username(listing.getUser().getUsername())
                        .build())
                .toList();
    }

}