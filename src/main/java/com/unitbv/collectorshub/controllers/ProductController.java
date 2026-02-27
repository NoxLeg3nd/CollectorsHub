package com.unitbv.collectorshub.controllers;

import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.services.ListingService;
import com.unitbv.collectorshub.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    private final ProductService productService;
    private final ListingService listingService;

    @PostMapping("/addProduct")
    public ResponseEntity<AddProductDTO> addProduct(@RequestBody AddProductDTO addProductDTO) {
        return  ResponseEntity.ok(productService.addProduct(addProductDTO));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/removeProduct/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/editProduct")
    public ResponseEntity<EditProductDTO> editProduct(@RequestParam Long id, @RequestBody EditProductDTO editProductDTO) {
        return ResponseEntity.ok(productService.editProduct(id, editProductDTO));
    }

    @GetMapping("/getProductById")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/addListing")
    public ResponseEntity<AddListingDTO> addListing(@RequestBody AddListingDTO addListingDTO) {
        return ResponseEntity.ok(listingService.addListing(addListingDTO));
    }

    @PutMapping("/editListing")
    public ResponseEntity<EditListingDTO> editListing(@RequestParam Long id, @RequestBody EditListingDTO editListingDTO) {
        return ResponseEntity.ok(listingService.editListing(id, editListingDTO));
    }

    @GetMapping("/getAllListings")
    public ResponseEntity<List<ListingDTO>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }
}
