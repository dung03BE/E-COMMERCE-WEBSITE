package com.example.shopapp.controller;


import com.example.shopapp.DTO.ProductDTO;
import com.example.shopapp.DTO.ProductImageDTO;
import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.Exception.InvalidParamException;
import com.example.shopapp.Response.ProductListResponse;
import com.example.shopapp.entity.Product;
import com.example.shopapp.entity.ProductImage;
import com.example.shopapp.form.CreatingProductForm;
import com.example.shopapp.form.ProductFilterForm;
import com.example.shopapp.form.UpdateProductForm;
import com.example.shopapp.service.IProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@Validated
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody CreatingProductForm form)
            throws  DataNotFoundException {
        Product newProduct = productService.createProduct(form);

        return new ResponseEntity<>("CREATED",HttpStatus.CREATED);
    }
    @PostMapping(value = "uploads/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable int id,@ModelAttribute("files")  List<MultipartFile> files)
    {

        try {
            Product existingProduct = productService.getProductById(id);
            files = files ==null ? new ArrayList<MultipartFile>():files;
            if(files.size()>ProductImage.Maximum_Images_Per_Product)
            {
                return ResponseEntity.badRequest().body("Chỉ được update "+ProductImage.Maximum_Images_Per_Product+ " ảnh");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file:files) {
                if(file.getSize()==0)
                {
                    continue;
                }
                //kiem tra kich thuoc va dịnh dang file
                if(file.getSize()>30*1024*1024) //>30MB
                {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is so large!(Maxium =30MB)");
                }
                String contenType = file.getContentType();//check dinh dang phải file ảnh k
                if(contenType==null || !contenType.startsWith("image/"))
                {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File isn't an image");
                }
                //luu file và update thumbnail
                String filename = storeFile(file);
                //luu vao productimage
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                                ProductImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    private String storeFile(MultipartFile file ) throws IOException {
        if(!isImageFile(file)||file.getOriginalFilename()==null)
        {
            throw  new IOException("Invalid image file format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //thêm uuid vào trc tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() +"_"+filename;
        //đường dẫn đến thư mục muốn lưu file
        Path upLoadDir = Paths.get("uploads");
        //kiemtra và tạo thưc mục nếu k tồn tại
        if(!Files.exists(upLoadDir))
        {
            Files.createDirectories(upLoadDir);
        }
        //đường dẫn đầy đủ tên file
        Path destination = Paths.get(upLoadDir.toString(),uniqueFilename);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    private boolean isImageFile(MultipartFile file)
    {
        String contentType = file.getContentType();
        return contentType!=null && contentType.startsWith("image/");
    }
    @GetMapping
    public Page<ProductDTO> getAllProducts(@PageableDefault(page = 0, size = 5) Pageable pageable, ProductFilterForm form)
    {
        //map k sài đc cho page
            //page của Product
            Page<Product> productPage= productService.getAllProducts(pageable,form);
            //lấy dữ liệu của trang product map với productDto
            List<ProductDTO> productDto  = modelMapper.map(productPage.getContent(),new TypeToken<List<ProductDTO>>(){}.getType());
            //tao ra 1 page Productdtopage mới
            Page<ProductDTO> productDtoPage = new PageImpl<>(productDto,pageable,productPage.getTotalElements());
            return productDtoPage;
    }

    @GetMapping("{id}")
    public ProductDTO getProductById(@PathVariable int id) throws DataNotFoundException {
        Product productById = productService.getProductById(id);
        return modelMapper.map(productById,ProductDTO.class);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@Valid @RequestBody UpdateProductForm form)
    {
        form.setId(id);
        productService.updateProduct(form);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        productService.deleteProduct(id);
        return new ResponseEntity<>("OK",HttpStatus.OK);
    }
    @GetMapping("/name/{name}")
    public ProductDTO getProductByName(@PathVariable String name)
    {
        Product products  = productService.getProductByName(name);
        return modelMapper.map(products,ProductDTO.class);
    }
}
