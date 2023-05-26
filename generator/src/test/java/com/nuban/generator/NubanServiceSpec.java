package com.nuban.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NubanServiceSpec {

    //Algo:
    //send detailsDto
    //expect generated Nuban

    @Mock
    NubanRepository repository;

    @InjectMocks
    NubanService nubanService;

    @DisplayName("Returns generated NUBAN")
    @Test
    void generateNuban() {
        var detailsDto = new DetailsDto();
        detailsDto.setInstitutionCode("033");
        detailsDto.setSerialNumber("123456789");

        Nuban nuban = new Nuban();
        nuban.setNumber("1234567897");

        when(repository.save(any())).thenReturn(nuban);

        var generatedNuban = nubanService.generate(detailsDto);

        Assertions.assertEquals("1234567897", generatedNuban);

        Mockito.verify(repository, times(1)).save(any());
    }
}