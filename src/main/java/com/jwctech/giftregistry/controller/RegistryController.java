package com.jwctech.giftregistry.controller;

import com.jwctech.giftregistry.model.Registry;
import com.jwctech.giftregistry.service.RegistryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registry")
@CrossOrigin
public class RegistryController {

    private final RegistryService registryService;

    public RegistryController(RegistryService registryService){
        this.registryService = registryService;
    }

    @RequestMapping("/test")
    public ResponseEntity testPoint(){
        return ResponseEntity.ok("All good!");
    }

    // Post Mapping for create Registry
    @PostMapping
    public ResponseEntity createRegistry(@RequestBody Registry uiReg){
        System.out.println("Create Entry: " + uiReg);
        return ResponseEntity.ok(registryService.createRegistry(uiReg));
    }
    // Get Mapping for get Registry by Id
    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistry(@PathVariable(name = "id")Long id){
        return new ResponseEntity<>(registryService.findRegistry(id), HttpStatus.OK);

    }
    // Put Mapping for Update Registry by Id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegistry(@PathVariable(name = "id")Long id, @RequestBody Registry uiReg) {
        System.out.println("Update Registry: "+uiReg.toString());
        return new ResponseEntity<>((registryService.updateRegistry(id, uiReg)), HttpStatus.OK);
    }
    // Delete Mapping for Delete Registry by Id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistry(@PathVariable(name = "id")Long id) {

        return ResponseEntity.ok(registryService.deleteRegistry(id));
    }

    // Get all Registries
    @GetMapping
    public ResponseEntity<?> getAllRegistries(){
        return new ResponseEntity<>(registryService.allRegistry(), HttpStatus.OK);
    }



}
