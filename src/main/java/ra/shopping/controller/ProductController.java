package ra.shopping.controller;

import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import ra.shopping.dto.request.ProductDTO;
import ra.shopping.dto.response.ResponseMessage;
import ra.shopping.model.Product;
import ra.shopping.service.ICatalogService;
import ra.shopping.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    IProductService productService;
@Autowired
    ICatalogService catalogService;
    @GetMapping
    public ResponseEntity<?> showList(Pageable pageable) {
        Page<Product> list = productService.getAllByStar(pageable);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("create")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setCatalog(catalogService.findById(productDTO.getCatalogId()));
        product.setStatus(true);
        product.setType(true);
        productService.save(product);

       return new ResponseEntity<>(new ResponseMessage("Create success"),HttpStatus.OK);
    }
    @PutMapping("update")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product prd = productService.findById(product.getId());
        if (prd == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.save(product);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
    @PutMapping("delete/{id}")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable("id") Long id){
        productService.changeStatusProduct(id);
        return new ResponseEntity<>(new ResponseMessage("delete is success!"),HttpStatus.OK);
    }
    @GetMapping("byName/{name}")
    public ResponseEntity<List<Product>> searchByName(@PathVariable("name") String name){
        List<Product> list = productService.findProductByName(name);
        if (list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("byCatalog/{id}")
    public ResponseEntity<List<Product>> searchByCatalog(@PathVariable("id") Long id){
        List<Product> list = productService.findProductByCatalog_Id(id);
        if (list.isEmpty()){
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @PutMapping("star/{id}")
    public ResponseEntity<ResponseMessage> updateStar(@PathVariable("id") Long id){
        productService.updateStar(id);
        return new ResponseEntity<>(new ResponseMessage("update is success!"),HttpStatus.OK);
    }
}
