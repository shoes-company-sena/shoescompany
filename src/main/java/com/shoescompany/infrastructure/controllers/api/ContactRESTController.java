package com.shoescompany.infrastructure.controllers.api;

import com.shoescompany.application.services.interfaces.IContactService;
import com.shoescompany.domain.dtos.ApiResponseDTO;
import com.shoescompany.domain.dtos.ContactDTO;
import com.shoescompany.domain.records.ContactResponse;
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
@Tag(name = "Contactos", description = "Operaciones relacionadas con contactos")
public class ContactRESTController {

    private final IContactService contactService;

    public ContactRESTController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    @Operation(
            summary = "Obtener todos los contactos",
            description = "Recupera una lista de todos los contactos disponibles",
            tags = {"Contactos"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de contactos recuperada exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContactResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<List<ContactResponse>>> findAll() {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Todos los contactos encontrados.", true, contactService.findAll())
        );
    }

    @GetMapping("/contact/{id}")
    @Operation(
            summary = "Obtener un contacto por ID",
            description = "Recupera un contacto específico utilizando su ID",
            tags = {"Contactos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del contacto a recuperar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContactResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<ContactResponse>> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Contacto encontrado.", true, contactService.findById(id))
        );
    }

    @PostMapping("/contact")
    @Operation(
            summary = "Guardar un nuevo contacto",
            description = "Crea un nuevo contacto con la información proporcionada",
            tags = {"Contactos"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información del contacto a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContactDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ContactResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<ContactResponse>> save(@RequestBody ContactDTO contactDTO) throws Exception {
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_SAVED + "contacto", true, contactService.save(contactDTO))
        );
    }

    @PutMapping("/contact/{id}")
    @Operation(
            summary = "Actualizar un contacto existente",
            description = "Actualiza un contacto existente utilizando su ID y la información proporcionada",
            tags = {"Contactos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del contacto a actualizar",
                    required = true,
                    example = "1"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Información del contacto a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ContactDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto actualizado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<ContactResponse>> update(@PathVariable Long id, @RequestBody ContactDTO contactDTO) throws Exception {
        contactService.update(id, contactDTO);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_UPDATED + "contacto", true, null)
        );
    }

    @DeleteMapping("/contact/{id}")
    @Operation(
            summary = "Eliminar un contacto",
            description = "Marca un contacto como inactivo en lugar de eliminarlo físicamente",
            tags = {"Contactos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del contacto a eliminar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto eliminado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<Void>> remove(@PathVariable Long id) throws Exception {
        contactService.delete(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>("Contacto eliminado.", true, null)
        );
    }

    @PatchMapping("/contact/{id}/activate")
    @Operation(
            summary = "Activar un contacto",
            description = "Marca un contacto como activo utilizando su ID",
            tags = {"Contactos"},
            parameters = @Parameter(
                    name = "id",
                    description = "ID del contacto a activar",
                    required = true,
                    example = "1"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contacto activado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Contacto no encontrado"
                    )
            }
    )
    public ResponseEntity<ApiResponseDTO<ContactResponse>> activate(@PathVariable Long id) throws Exception {
        contactService.activate(id);
        return ResponseEntity.ok(
                new ApiResponseDTO<>(ApiResponseDTO.DATA_UPDATED + "Contacto activado", true, null)
        );
    }
}
