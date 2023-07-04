package ra.shopping.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import ra.shopping.dto.request.CatalogDTO;
import ra.shopping.dto.response.ResponseMessage;
import ra.shopping.model.Catalog;
import ra.shopping.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/catalogs")
public class CatalogController {
    @Autowired
    ICatalogService catalogService;
    @GetMapping
    public ResponseEntity<List<Catalog>> showListCatalog(){
      List<Catalog> list = catalogService.getAll();
      if (list.isEmpty()){
          return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> createCatalog(@RequestBody CatalogDTO catalogDTO){
        Catalog catalog = new Catalog();
        catalog.setName(catalogDTO.getName());
        catalog.setDescription(catalogDTO.getDescription());
        catalog.setCountry(catalogDTO.getCountry());
        catalog.setImage(catalogDTO.getImage());
        catalog.setStatus(true);
        catalogService.save(catalog);
        return new ResponseEntity<>(new ResponseMessage("create is success!"),HttpStatus.OK);
    }
    @PutMapping("update")
    @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<Catalog> updateCatalog(@RequestBody Catalog catalog){
        Catalog cata = catalogService.findById(catalog.getId());
        if (cata == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catalogService.save(catalog);
        return new ResponseEntity<>(catalog,HttpStatus.OK);
    }
@PutMapping("delete/{id}")
@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
    public ResponseEntity<ResponseMessage> deleteCatalog(@PathVariable("id") Long id){
         catalogService.changeStatusCatalog(id);
         return new ResponseEntity<>(new ResponseMessage("delete is success!"),HttpStatus.OK);
}
}
