package com.shoescompany.infrastructure.controllers.api;

import com.shoescompany.application.services.interfaces.IColorService;
import com.shoescompany.domain.dtos.ApiResponseDTO;
import com.shoescompany.domain.dtos.ColorDTO;
import com.shoescompany.domain.records.ColorResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/v1")
@Tag(name = "Color", description = "Operaciones relacionadas con colores")
public class ColorRESTController {

    private final IColorService colorService;

    public ColorRESTController(IColorService colorService) {
        this.colorService = colorService;
    }

    @Operation(
            summary = "Obtener todos los colores",
            description = "Obtiene una lista de todos los colores.",
            tags = {"Color"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Colores encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ColorResponse.class)
                            )
                    )
            }
    )
    @GetMapping("/colors")
    public ResponseEntity<ApiResponseDTO<List<ColorResponse>>> findAll() {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Todos los colores encontrados.", true, colorService.findAll())
        );
    }

    @Operation(
            summary = "Obtener un color por ID",
            description = "Obtiene un color específico utilizando su ID.",
            tags = {"Color"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del color",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Color encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ColorResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Color no encontrado")
            }
    )
    @GetMapping("/color/{id}")
    public ResponseEntity<ApiResponseDTO<ColorResponse>> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Color encontrado.", true, colorService.findById(id))
        );
    }

    @Operation(
            summary = "Guardar un nuevo color",
            description = "Crea y guarda un nuevo color.",
            tags = {"Color"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del color a guardar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ColorDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Color guardado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ColorResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/color")
    public ResponseEntity<ApiResponseDTO<ColorResponse>> save(@RequestBody ColorDTO colorDTO) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Color guardado.", true, colorService.save(colorDTO))
        );
    }

    @Operation(
            summary = "Actualizar un color",
            description = "Actualiza un color existente.",
            tags = {"Color"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del color a actualizar",
                    required = true,
                    example = "1"
            ),
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del color a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ColorDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Color actualizado"),
                    @ApiResponse(responseCode = "404", description = "Color no encontrado")
            }
    )
    @PutMapping("/color/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> update(@PathVariable Long id, @RequestBody ColorDTO colorDTO) throws Exception {
        colorService.update(id, colorDTO);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Color actualizado.", true, null)
        );
    }

    @Operation(
            summary = "Eliminar un color",
            description = "Elimina un color estableciendo su estado a inactivo.",
            tags = {"Color"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del color a eliminar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Color eliminado"),
                    @ApiResponse(responseCode = "404", description = "Color no encontrado")
            }
    )
    @DeleteMapping("/color/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> remove(@PathVariable Long id) throws Exception {
        colorService.delete(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Color eliminado.", true, null)
        );
    }

    @Operation(
            summary = "Activar un color",
            description = "Activa un color estableciendo su estado a activo.",
            tags = {"Color"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del color a activar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Color activado"),
                    @ApiResponse(responseCode = "404", description = "Color no encontrado")
            }
    )
    @PatchMapping("/color/{id}/activate")
    public ResponseEntity<ApiResponseDTO<Void>> activate(@PathVariable Long id) throws Exception {
        colorService.activate(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Color activado.", true, null)
        );
    }
}
