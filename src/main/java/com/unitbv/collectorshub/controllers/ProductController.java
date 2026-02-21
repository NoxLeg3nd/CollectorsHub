package com.unitbv.collectorshub.controllers;

import com.unitbv.collectorshub.model.dto.*;
import com.unitbv.collectorshub.model.entities.Listing;
import com.unitbv.collectorshub.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/addProduct")
    public ResponseEntity<AddProductDTO> addProduct(@RequestBody AddProductDTO addProductDTO) {
        return productService.addProduct(addProductDTO);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/removeProductByNameCollectionAndManufactureYear")
    public ResponseEntity<RemoveProductDTO> removeProductByNameCollectionAndManufactureYear(@RequestBody RemoveProductDTO removeProductDTO) {
        return productService.removeProduct(removeProductDTO);
    }

    @PutMapping("/editProduct")
    public ResponseEntity<EditProductDTO> editProduct(@RequestParam String name, @RequestParam String collection, @RequestParam Integer manufactureYear, @RequestBody EditProductDTO editProductDTO) {
        return productService.editProduct(name, collection, manufactureYear, editProductDTO);
    }

    @GetMapping("/getProductById")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/addListing")
    public ResponseEntity<AddListingDTO> addListing(@RequestBody AddListingDTO addListingDTO) {
        return productService.addListing(addListingDTO);
    }

    @PutMapping("/editListing")
    public ResponseEntity<EditListingDTO> editListing(@RequestParam Long id ,@RequestBody EditListingDTO editListingDTO) {
        return productService.editListing(id ,editListingDTO);
    }

    @GetMapping("/getAllListings")
    public ResponseEntity<List<ListingDTO>> getAllListings() {
        return productService.getAllListings();
    }
}
