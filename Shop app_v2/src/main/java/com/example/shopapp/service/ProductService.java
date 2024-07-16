package com.example.shopapp.service;

import com.example.shopapp.dto.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.entity.Category;
import com.example.shopapp.entity.Product;
import com.example.shopapp.entity.ProductImage;
import com.example.shopapp.form.CreatingProductForm;
import com.example.shopapp.form.ProductFilterForm;
import com.example.shopapp.form.UpdateProductForm;
import com.example.shopapp.repository.ICategoryRepository;
import com.example.shopapp.repository.IProductImageRepository;
import com.example.shopapp.repository.IProductRepository;
import com.example.shopapp.specification.ProductSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService  implements IProductService{
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IProductImageRepository productImageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product createProduct(CreatingProductForm form) throws DataNotFoundException {
        // Tìm kiếm Category theo ID từ form
        Optional<Category> categoryOpt = categoryRepository.findById(form.getCategoryId());

        // Nếu Category không tồn tại, ném ngoại lệ DataNotFoundException
        if (!categoryOpt.isPresent()) {
            throw new DataNotFoundException("Cannot find category with id=" + form.getCategoryId());
        }

        Category category = categoryOpt.get();

        // Tạo đối tượng Product mới từ thông tin trong form
        Product product = new Product();
        product.setName(form.getName());
        product.setPrice(form.getPrice());
        product.setThumbnail(form.getThumbnail());
        product.setDescription(form.getDescription());
        product.setCategory(category);
        // Lưu Product và trả về đối tượng đã lưu
        return productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable, ProductFilterForm form) {
        Specification<Product> where = ProductSpecification.builtWhere(form);
        return productRepository.findAll(where, pageable);
    }

//    @Override
//    public Page<Product> getAllProducts(Pageable pageable) {
//        return  productRepository.findAll(pageable);
//    }

    @Override
    public Product getProductById(int id) throws DataNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Don't find Product with id="+id));
    }

    @Override
    public void updateProduct(UpdateProductForm form) {
        Product existingProduct = modelMapper.map(form,Product.class);
            productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(product -> productRepository.delete(product));
//        productRepository.deleteById(id);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByName(name);

    }

    @Override
    public boolean isExistsProduct(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(
            int productId,
            ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(()-> new DataNotFoundException(
                        "Cannot find product with id "+productImageDTO.getProductId()));


        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //k cho insert qua 5 anh
       int size = productImageRepository.findByProductId(productId).size();
       if(size>=ProductImage.Maximum_Images_Per_Product)
       {
           throw  new InvalidParamException("Number of images must be <="+
                   ProductImage.Maximum_Images_Per_Product);
       }
        return  productImageRepository.save(newProductImage);
    }

}
