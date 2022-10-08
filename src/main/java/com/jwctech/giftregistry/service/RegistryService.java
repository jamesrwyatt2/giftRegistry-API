package com.jwctech.giftregistry.service;

import com.jwctech.giftregistry.model.Registry;
import com.jwctech.giftregistry.payload.RegistryPayload;

import java.util.List;

public interface RegistryService {


    RegistryPayload createRegistry(Registry registry);

    RegistryPayload findRegistry(Long id);

    RegistryPayload updateRegistry(Long id, Registry uiReg);

    List<RegistryPayload> allRegistry();

    boolean deleteRegistry(Long id);

    Registry findById(Long id);

    RegistryPayload mapToPayload(Registry registry);
}
