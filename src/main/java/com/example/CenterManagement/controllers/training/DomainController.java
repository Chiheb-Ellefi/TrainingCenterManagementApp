package com.example.CenterManagement.controllers.training;

import com.example.CenterManagement.dto.training.DomainDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.services.training.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/domains")
public class DomainController {
    private final DomainService domainService;
    @Autowired
    DomainController(DomainService domainService) {
        this.domainService = domainService;
    }
    @PostMapping
    public ResponseEntity<DomainDto> createDomain(@RequestBody DomainDto domainDto) {
        if(domainDto == null) {
            throw new BadRequestException("Invalid Data");
        }
       DomainDto response= domainService.createDomain(domainDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<DomainDto>> getAllDomains() {
        List<DomainDto> response= domainService.getAllDomains();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
     public ResponseEntity<DomainDto> getDomain(@PathVariable Long id) {
        if(id == null) {
            throw new BadRequestException("Invalid domain id");
        }
        DomainDto response=domainService.getDomain(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDomain(@PathVariable Long id) {
        if(id == null) {
            throw new BadRequestException("Invalid domain id");
        }
        domainService.deleteDomain(id);
        return new ResponseEntity<>("Domain deleted", HttpStatus.NO_CONTENT);
    }


}
