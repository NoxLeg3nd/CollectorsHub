package com.unitbv.collectorshub.controllers;

import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/getStats")
    public ResponseEntity<AdminStatsDTO> getStats(@RequestParam Long requesterId) {
        return ResponseEntity.ok(adminService.getStats(requesterId));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<GetUserDTO>> getAllUsers(@RequestParam Long requesterId) {
        return ResponseEntity.ok(adminService.getAllUsers(requesterId));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam Long requesterId, @RequestParam Long id) {
        adminService.deleteUser(requesterId, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/promoteUser")
    public ResponseEntity<Void> promoteUser(@RequestParam Long requesterId, @RequestParam Long id) {
        adminService.promoteUser(requesterId, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/demoteUser")
    public ResponseEntity<Void> demoteUser(@RequestParam Long requesterId, @RequestParam Long id) {
        adminService.demoteUser(requesterId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllListings")
    public ResponseEntity<Page<ListingDTO>> getAllListings(
            @RequestParam Long requesterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllListings(requesterId, page, size));
    }

    @DeleteMapping("/deleteListing")
    public ResponseEntity<Void> deleteListing(@RequestParam Long requesterId, @RequestParam Long id) {
        adminService.deleteListing(requesterId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllReviews")
    public ResponseEntity<Page<ReviewDTO>> getAllReviews(
            @RequestParam Long requesterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getAllReviews(requesterId, page, size));
    }

    @DeleteMapping("/deleteReview")
    public ResponseEntity<Void> deleteReview(@RequestParam Long requesterId, @RequestParam Long id) {
        adminService.deleteReview(requesterId, id);
        return ResponseEntity.noContent().build();
    }
}