package com.nuban.generator;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nuban")
@AllArgsConstructor
public class NubanController {
    private NubanService nubanService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> generateNuban(@RequestBody DetailsDto dto) throws InvalidDetailsException {

        System.out.println("in controller.");
        if (dto.getInstitutionCode().length() < 3) {
            throw new InvalidDetailsException("Bank code should be 3 digits.");
        }
        if (dto.getSerialNumber().length() < 9) {
            throw new InvalidDetailsException("Serial number should be 9 digits.'");
        }
        return ResponseEntity.ok(nubanService.generate(dto));

    }

}
