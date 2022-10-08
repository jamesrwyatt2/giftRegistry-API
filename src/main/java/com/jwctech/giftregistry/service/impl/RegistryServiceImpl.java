package com.jwctech.giftregistry.service.impl;

import com.jwctech.giftregistry.exception.ResourceNotFoundException;
import com.jwctech.giftregistry.model.Registry;
import com.jwctech.giftregistry.payload.RegistryPayload;
import com.jwctech.giftregistry.repository.RegistryRepo;
import com.jwctech.giftregistry.service.RegistryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistryServiceImpl implements RegistryService {

    private RegistryRepo registryRepo;
    private ModelMapper mapper;

    public RegistryServiceImpl(RegistryRepo registryRepo, ModelMapper mapper){
        this.registryRepo = registryRepo;
        this.mapper = mapper;
    }

    @Override
    public RegistryPayload createRegistry(Registry registry) {

        return mapToPayload(registryRepo.save(registry));
    }

    @Override
    public RegistryPayload findRegistry(Long id) {
         Registry found = findById(id);
         System.out.println("Found Registry: " +found);
         return mapToPayload(found);
    }

    @Override
    public RegistryPayload updateRegistry(Long id, Registry uiReg) {
        Registry found = findById(id);

        found.setTitle(uiReg.getTitle());
        found.setDescription(uiReg.getDescription());
        found.setDate(uiReg.getDate());

        Registry reg= registryRepo.save(found);
        return mapToPayload(reg);
    }

    @Override
    public List<RegistryPayload> allRegistry() {
        List<Registry> registries = registryRepo.findAll();

        return registries.stream().map(registry -> mapToPayload(registry)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteRegistry(Long id) {
        Registry found = findById(id);
//        System.out.println("In delete: "+ found);
        registryRepo.deleteById(found.getId());
        return true;
    }

    public Registry findById(Long id){
        return registryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Registry","id", id.toString()));
    }

    public RegistryPayload mapToPayload(Registry registry){
        RegistryPayload payload = mapper.map(registry, RegistryPayload.class);

//        RegistryPayload payload = new RegistryPayload();
//        payload.setId(registry.getId());
//        payload.setTitle(registry.getTitle());
//        payload.setDescription(registry.getDescription());
//        payload.setContent(registry.getContent());

        return payload;
    }

    public Registry mapToRegistry(RegistryPayload payload){
        Registry registry = mapper.map(payload, Registry.class);
        return registry;
    }

}
