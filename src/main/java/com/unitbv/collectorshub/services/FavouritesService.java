package com.unitbv.collectorshub.services;

import com.unitbv.collectorshub.exceptions.ApiException;
import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Favourites;
import com.unitbv.collectorshub.model.entities.Listing;
import com.unitbv.collectorshub.model.entities.User;
import com.unitbv.collectorshub.repositories.FavouritesRepository;
import com.unitbv.collectorshub.repositories.ListingRepository;
import com.unitbv.collectorshub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class FavouritesService {

    private final FavouritesRepository favouritesRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final ListingService listingService;

    public AddFavouriteDTO addFavourite(AddFavouriteDTO addFavouriteDTO) {
        User user = userRepository.findById(addFavouriteDTO.getUserId())
                .orElseThrow(() -> new ApiException("User not found", 404));

        Listing listing = listingRepository.findById(addFavouriteDTO.getListingId())
                .orElseThrow(() -> new ApiException("Listing not found", 404));

        if (favouritesRepository.existsByUser_IdAndListing_Id(
                addFavouriteDTO.getUserId(), addFavouriteDTO.getListingId())) {
            throw new ApiException("Listing is already in favourites", 409);
        }

        Favourites favourite = Favourites.builder()
                .user(user)
                .listing(listing)
                .build();

        favouritesRepository.save(favourite);
        return addFavouriteDTO;
    }

    public Page<FavouriteDTO> getAllFavouritesByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return favouritesRepository.findAllByUser_Id(userId, pageable)
                .map(favourite -> {
                    ListingDTO listing = listingService.getListingById(favourite.getListing().getId());
                    return FavouriteDTO.builder()
                            .id(favourite.getId())
                            .userId(favourite.getUser().getId())
                            .listing(listing)
                            .build();
                });
    }

    public void removeFavourite(Long id) {
        Favourites favourite = favouritesRepository.findById(id)
                .orElseThrow(() -> new ApiException("Favourite not found", 404));
        favouritesRepository.delete(favourite);
    }
}