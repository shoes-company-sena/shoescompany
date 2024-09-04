package com.shoescompany.infrastructure.controllers.api;

import com.shoescompany.application.services.interfaces.IProductService;
import com.shoescompany.domain.dtos.ApiResponseDTO;
import com.shoescompany.domain.dtos.ProductDTO;
import com.shoescompany.domain.entities.Product;
import com.shoescompany.domain.records.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductRESTController {

    private final IProductService productService;

    public ProductRESTController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    @Operation(
            summary = "Obtener todos los productos",
            description = "Recupera una lista de todos los productos disponibles",
            tags = {"Productos"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos recuperada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<List<ProductResponse>>> findAll() {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Todos los productos encontrados.", true, productService.findAll())
        );
    }

    @GetMapping("/product/{id}")
    @Operation(
            summary = "Obtener un producto por ID",
            description = "Recupera un producto específico utilizando su ID",
            tags = {"Productos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del producto a recuperar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<ProductResponse>> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Producto encontrado.", true, productService.findById(id))
        );
    }

    @PostMapping("/product")
    @Operation(
            summary = "Guardar un nuevo producto",
            description = "Crea un nuevo producto con la información proporcionada",
            tags = {"Productos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información del producto a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<ProductResponse>> save(@RequestBody ProductDTO productDTO) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_SAVED + "producto", true, productService.save(productDTO))
        );
    }

    @PutMapping("/product/{id}")
    @Operation(
            summary = "Actualizar un producto existente",
            description = "Actualiza un producto existente utilizando su ID y la información proporcionada",
            tags = {"Productos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del producto a actualizar",
                    required = true,
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información del producto a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto actualizado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<Product>> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws Exception {
        productService.update(id, productDTO);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_UPDATED + "producto", true, null)
        );
    }

    @DeleteMapping("/product/{id}")
    @Operation(
            summary = "Eliminar un producto",
            description = "Marca un producto como inactivo en lugar de eliminarlo físicamente",
            tags = {"Productos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del producto a eliminar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<Void>> remove(@PathVariable Long id) throws Exception {
        productService.delete(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Producto eliminado.", true, null)
        );
    }

    @PatchMapping("/product/{id}/activate")
    @Operation(
            summary = "Activar un producto",
            description = "Marca un producto como activo utilizando su ID",
            tags = {"Productos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del producto a activar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Producto activado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Producto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<Product>> activate(@PathVariable Long id) throws Exception {
        productService.activate(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_UPDATED + "Producto activado", true, null)
        );
    }
}

