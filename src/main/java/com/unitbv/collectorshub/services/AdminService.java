package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Listing;
import com.unitbv.collectorshub.model.entities.Product;
import com.unitbv.collectorshub.model.entities.Review;
import com.unitbv.collectorshub.model.entities.User;
import com.unitbv.collectorshub.repositories.*;
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
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ListingRepository listingRepository;
    private final ReviewRepository reviewRepository;
    private final FavouritesRepository favouritesRepository;

    private void verifyAdmin(Long requesterId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new ApiException("Requester not found", 404));
        if (!"ADMIN".equals(requester.getRole())) {
            throw new ApiException("Access denied", 403);
        }
    }

    public AdminStatsDTO getStats(Long requesterId) {
        verifyAdmin(requesterId);
        return AdminStatsDTO.builder()
                .totalUsers(userRepository.count())
                .totalProducts(productRepository.count())
                .totalListings(listingRepository.count())
                .activeListings(listingRepository.countByIsActiveTrue())
                .totalReviews(reviewRepository.count())
                .totalFavourites(favouritesRepository.count())
                .build();
    }

    public List<GetUserDTO> getAllUsers(Long requesterId) {
        verifyAdmin(requesterId);
        return userRepository.findAll().stream()
                .map(user -> GetUserDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    public void deleteUser(Long requesterId, Long id) {
        verifyAdmin(requesterId);

        if (id.equals(requesterId)) {
            throw new ApiException("You cannot delete your own account from the admin panel", 400);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", 404));

        List<Listing> listings = listingRepository.findAllByUserId(id, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        for (Listing listing : listings) {
            favouritesRepository.deleteAll(favouritesRepository.findAllByListing_Id(listing.getId()));
        }
        listingRepository.deleteAll(listings);

        List<Product> products = productRepository.findAllByUser_Id(id, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        productRepository.deleteAll(products);

        reviewRepository.deleteAll(reviewRepository.findAllByReviewedUser_Id(id));
        reviewRepository.deleteAll(reviewRepository.findAllByReviewingUser_Id(id));
        favouritesRepository.deleteAll(favouritesRepository.findAllByUser_Id(id));

        userRepository.delete(user);
    }

    public void promoteUser(Long requesterId, Long id) {
        verifyAdmin(requesterId);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", 404));
        user.setRole("ADMIN");
        userRepository.save(user);
    }

    public void demoteUser(Long requesterId, Long id) {
        verifyAdmin(requesterId);
        if (id.equals(requesterId)) {
            throw new ApiException("You cannot demote yourself", 400);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException("User not found", 404));
        user.setRole("USER");
        userRepository.save(user);
    }

    public Page<ListingDTO> getAllListings(Long requesterId, int page, int size) {
        verifyAdmin(requesterId);
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.findAll(pageable)
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

    public Page<ReviewDTO> getAllReviews(Long requesterId, int page, int size) {
        verifyAdmin(requesterId);
        Pageable pageable = PageRequest.of(page, size);
        return reviewRepository.findAll(pageable)
                .map(r -> ReviewDTO.builder()
                        .id(r.getId())
                        .reviewedUserId(r.getReviewedUser().getId())
                        .reviewingUserId(r.getReviewingUser().getId())
                        .reviewingUsername(r.getReviewingUser().getUsername())
                        .comment(r.getComment())
                        .opinion(r.getOpinion())
                        .build());
    }

    public void deleteReview(Long requesterId, Long id) {
        verifyAdmin(requesterId);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ApiException("Review not found", 404));
        reviewRepository.delete(review);
    }

    public void deleteListing(Long requesterId, Long id) {
        verifyAdmin(requesterId);
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ApiException("Listing not found", 404));
        favouritesRepository.deleteAll(favouritesRepository.findAllByListing_Id(id));
        listingRepository.delete(listing);
    }
}