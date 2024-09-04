package com.shoescompany.infrastructure.controllers.api;

import com.shoescompany.application.services.interfaces.ICategoryService;
import com.shoescompany.domain.dtos.ApiResponseDTO;
import com.shoescompany.domain.dtos.CategoryDTO;
import com.shoescompany.domain.records.CategoryResponse;
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
@RequestMapping("/v1")
@Tag(name = "Categorías", description = "Operaciones relacionadas con categorías")
public class CategoryRESTController {

    private final ICategoryService categoryService;

    public CategoryRESTController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    @Operation(
            summary = "Obtener todas las categorías",
            description = "Recupera una lista de todas las categorías disponibles",
            tags = {"Categorías"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de categorías recuperada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<List<CategoryResponse>>> findAll() {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Todas las categorías encontradas.", true, categoryService.findAll())
        );
    }

    @GetMapping("/category/{id}")
    @Operation(
            summary = "Obtener una categoría por ID",
            description = "Recupera una categoría específica utilizando su ID",
            tags = {"Categorías"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la categoría a recuperar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría encontrada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Categoría encontrada.", true, categoryService.findById(id))
        );
    }

    @PostMapping("/category")
    @Operation(
            summary = "Guardar una nueva categoría",
            description = "Crea una nueva categoría con la información proporcionada",
            tags = {"Categorías"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información de la categoría a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría creada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> save(@RequestBody CategoryDTO categoryDTO) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_SAVED + "categoría", true, categoryService.save(categoryDTO))
        );
    }

    @PutMapping("/category/{id}")
    @Operation(
            summary = "Actualizar una categoría existente",
            description = "Actualiza una categoría existente utilizando su ID y la información proporcionada",
            tags = {"Categorías"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la categoría a actualizar",
                    required = true,
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información de la categoría a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría actualizada exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) throws Exception {
        categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_UPDATED + "categoría", true, null)
        );
    }

    @DeleteMapping("/category/{id}")
    @Operation(
            summary = "Eliminar una categoría",
            description = "Marca una categoría como inactiva en lugar de eliminarla físicamente",
            tags = {"Categorías"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la categoría a eliminar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría eliminada exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<Void>> remove(@PathVariable Long id) throws Exception {
        categoryService.delete(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Categoría eliminada.", true, null)
        );
    }

    @PatchMapping("/category/{id}/activate")
    @Operation(
            summary = "Activar una categoría",
            description = "Marca una categoría como activa utilizando su ID",
            tags = {"Categorías"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la categoría a activar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Categoría activada exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoría no encontrada"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<CategoryResponse>> activate(@PathVariable Long id) throws Exception {
        categoryService.activate(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_UPDATED + "Categoría activada", true, null)
        );
    }
}
