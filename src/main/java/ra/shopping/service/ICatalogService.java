package ra.shopping.service;


import ra.shopping.model.Catalog;

import java.util.List;

public interface ICatalogService extends IGenericService<Catalog,Long>{
    List<Catalog> getAll();
    void changeStatusCatalog(Long id);
}
