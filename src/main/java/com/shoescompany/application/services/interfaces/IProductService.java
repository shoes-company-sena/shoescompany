package com.shoescompany.application.services.interfaces;

import com.shoescompany.domain.dtos.ProductDTO;
import com.shoescompany.domain.entities.Product;
import com.shoescompany.domain.records.ProductResponse;

public interface IProductService extends IBaseService<ProductResponse, ProductDTO> {
}
