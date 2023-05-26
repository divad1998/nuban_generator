package com.nuban.generator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NubanService {
    private NubanRepository repository;

    public String generate(DetailsDto dto) {
        var combinedString =  dto.getInstitutionCode().concat(dto.getSerialNumber());
        int result = 0;
        for (int i = 0; i < combinedString.length(); i++) {
            int intValue = Character.getNumericValue(combinedString.charAt(i));
            if (i != 4 && i != 7 && i != 10) {
                result += intValue * 3;
            } else {
                result += intValue* 7;
            }
        }

        //find check digit
        var modulus = result % 10;
        var checkDigit = 10 - modulus; //if modulus is 0, 0 will be used as check digit

        Nuban nuban = new Nuban();
        if (checkDigit != 10) {
            nuban.setNumber(dto.getSerialNumber().concat(String.valueOf(checkDigit)));
        } else {
            nuban.setNumber(dto.getSerialNumber().concat("0"));
        }

        Nuban savedNuban = repository.save(nuban);
        return savedNuban.getNumber();
    }
}