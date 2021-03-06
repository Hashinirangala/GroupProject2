package com.WasteManagementSystem.Backend.controller;

import javax.validation.Valid;

import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import com.WasteManagementSystem.Backend.entity.CollectedWaste;
import com.WasteManagementSystem.Backend.entity.ProductCat;
//import com.WasteManagementSystem.Backend.entity.Product;
//import com.WasteManagementSystem.Backend.entity.ProductCat;
import com.WasteManagementSystem.Backend.repository.ProductCatRepository;
//import com.WasteManagementSystem.Backend.service.ProductService;




@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductCatController {
	
	@Autowired
	private ProductCatRepository productcatRepository;
	
	
	@PostMapping("/productCats")
    public ProductCat createProductCat(@Valid @RequestBody ProductCat productcat) {
        return productcatRepository.save(productcat);
    }
	
	@GetMapping("/productCats")
    public List<ProductCat> getAllProductCats() {
        return productcatRepository.findAll();
    }
	
	@DeleteMapping("/productCats/{id}")
    public Map<String, Boolean> deleteProductCat(@PathVariable(value = "id") int productCatId)
         throws ResourceNotFoundException {
		ProductCat productCat = productcatRepository.findById(productCatId)
       .orElseThrow(() -> new ResourceNotFoundException("category not found for this id :: " + productCatId));

		productcatRepository.delete(productCat);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
	
	@GetMapping("/productCats/{id}")
    public ResponseEntity<ProductCat> getProductCatsById(@PathVariable(value = "id") int productCatId)
        throws ResourceNotFoundException {
		ProductCat productCat = productcatRepository.findById(productCatId)
          .orElseThrow(() -> new ResourceNotFoundException("category not found for this id :: " + productCatId));
        return ResponseEntity.ok().body(productCat);
    }


	@PutMapping("/productCats/{id}")
    public ResponseEntity<ProductCat> updateProductCat(@PathVariable(value = "id") int productCatId,
         @Valid @RequestBody ProductCat productCatDetails) throws ResourceNotFoundException {
		ProductCat productCat =productcatRepository.findById(productCatId)
        .orElseThrow(() -> new ResourceNotFoundException("category not found for this id :: " + productCatId));

		
		productCat.setName(productCatDetails.getName());
		productCat.setDescription(productCatDetails.getDescription());
		productCat.setImg(productCatDetails.getImg());
		
		
       
        final ProductCat updatedProductCat = productcatRepository.save(productCat);
        return ResponseEntity.ok(updatedProductCat);
    }
}
