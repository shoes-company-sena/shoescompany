package com.shoescompany.infrastructure.controllers.api;

import com.shoescompany.application.services.interfaces.IBrandService;
import com.shoescompany.domain.dtos.ApiResponseDTO;
import com.shoescompany.domain.dtos.BrandDTO;
import com.shoescompany.domain.records.BrandResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;



@RestController
@RequestMapping("/v1")
@Tag(name = "Marca", description = "Operaciones relacionadas con marcas")
public class BrandRESTController {

    private final IBrandService brandService;

    public BrandRESTController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(
            summary = "Obtener todas las marcas",
            description = "Obtiene una lista de todas las marcas.",
            tags = {"Marca"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Marcas encontradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BrandResponse.class)
                            )
                    )
            }
    )
    @GetMapping(value = "/brands")
    public ResponseEntity<ApiResponseDTO<List<BrandResponse>>> findAll() {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Todas las marcas encontradas.", true, brandService.findAll())
        );
    }

    @Operation(
            summary = "Obtener una marca por ID",
            description = "Obtiene una marca específica utilizando su ID.",
            tags = {"Marca"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la marca",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Marca encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BrandResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
            }
    )
    @GetMapping("/brand/{id}")
    public ResponseEntity<ApiResponseDTO<BrandResponse>> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Marca encontrada.", true, brandService.findById(id))
        );
    }

    @Operation(
            summary = "Guardar una nueva marca",
            description = "Crea y guarda una nueva marca.",
            tags = {"Marca"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la marca a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Marca guardada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BrandResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/brand")
    public ResponseEntity<ApiResponseDTO<BrandResponse>> save(@RequestBody BrandDTO brandDTO) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Marca guardada.", true, brandService.save(brandDTO))
        );
    }

    @Operation(
            summary = "Actualizar una marca",
            description = "Actualiza una marca existente.",
            tags = {"Marca"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la marca a actualizar",
                    required = true,
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la marca a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BrandDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca actualizada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
            }
    )
    @PutMapping("/brand/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> update(@PathVariable Long id, @RequestBody BrandDTO brandDTO) throws Exception {
        brandService.update(id, brandDTO);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Marca actualizada.", true, null)
        );
    }

    @Operation(
            summary = "Eliminar una marca",
            description = "Elimina una marca estableciendo su estado a inactivo.",
            tags = {"Marca"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la marca a eliminar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca eliminada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
            }
    )
    @DeleteMapping("/brand/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> remove(@PathVariable Long id) throws Exception {
        brandService.delete(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Marca eliminada.", true, null)
        );
    }

    @Operation(
            summary = "Activar una marca",
            description = "Activa una marca estableciendo su estado a activo.",
            tags = {"Marca"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID de la marca a activar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Marca activada"),
                    @ApiResponse(responseCode = "404", description = "Marca no encontrada")
            }
    )
    @PatchMapping("/brand/{id}/activate")
    public ResponseEntity<ApiResponseDTO<Void>> activate(@PathVariable Long id) throws Exception {
        brandService.activate(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Marca activada.", true, null)
        );
    }
}
