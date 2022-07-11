package br.com.itau.pixexample.web;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.AlteraChavePixDTO;
import br.com.itau.pixexample.entity.dto.ChavePixDTO;
import br.com.itau.pixexample.entity.dto.FilterDTO;
import br.com.itau.pixexample.service.ChavePixFactory;
import br.com.itau.pixexample.service.enums.StrategyEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api-pix/v1")
@RequiredArgsConstructor
public class PixController {

    private final ChavePixFactory factory;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/chaves")
    @ApiOperation(value = "Criação de Chaves de acordo com o tipo", nickname = "Criação de Chave PIX")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chave PIX criada"),
            @ApiResponse(code = 422, message = "Erro de negócio")
    })
    public ResponseEntity<UUID> createKey(@Valid @RequestBody ChavePixDTO dto) {
        var retorno = factory
                .getStrategy(StrategyEnum.fromStrategyName(dto.getTipoChave().name()))
                .create(modelMapper.map(dto, ChavePix.class));
        return ResponseEntity.ok(retorno);
    }

    @PutMapping(path = "/chaves/{id}")
    @ApiOperation(value = "Alteração de Chaves de acordo com o tipo da chave", nickname = "Alteração de Chave PIX")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Chave PIX Atualizada"),
            @ApiResponse(code = 422, message = "Erro de negócio")
    })
    public ResponseEntity<ChavePixDTO> updateKey(@PathVariable("id") String id, @Valid @RequestBody AlteraChavePixDTO dto) {
        dto.setId(id);
        var chave = factory.getStrategy().update(modelMapper.map(dto, ChavePix.class));
        return ResponseEntity.ok(modelMapper.map(chave, ChavePixDTO.class));
    }

    @GetMapping(path = "/chaves/{id}")
    public ResponseEntity<ChavePixDTO> findOne(@PathVariable("id") String id) {
        var chave = factory.getStrategy(StrategyEnum.CELULAR).find(UUID.fromString(id));
        return ResponseEntity.ok(modelMapper.map(chave, ChavePixDTO.class));
    }

    @GetMapping(path = "/chaves/filters")
    public ResponseEntity<List<ChavePixDTO>> findByFilters(FilterDTO filter) {
        var chaves = factory.getStrategy().findAllByParams(filter);
        return ResponseEntity.ok(chaves.stream().map(chave -> modelMapper.map(chave, ChavePixDTO.class)).collect(Collectors.toList()));
    }

    @DeleteMapping(path = "/chaves/{id}")
    public ResponseEntity<ChavePixDTO> deleteKey(@PathVariable("id") String id) {
        try {
            var chave = factory.getStrategy().delete(UUID.fromString(id));
            return ResponseEntity.ok(modelMapper.map(chave, ChavePixDTO.class));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
