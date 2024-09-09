package com.shoescompany.application.services.implementations;

import com.shoescompany.application.services.interfaces.IContactService;
import com.shoescompany.domain.dtos.ContactDTO;
import com.shoescompany.domain.entities.Contact;
import com.shoescompany.domain.enums.State;
import com.shoescompany.domain.records.ContactResponse;
import com.shoescompany.infrastructure.repositories.ContactRepository;
import com.shoescompany.infrastructure.utils.ModelMapperUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService implements IContactService {

    private final ContactRepository contactRepository;
    private final ModelMapperUtils modelMapperUtils;

    public ContactService(ContactRepository contactRepository, ModelMapperUtils modelMapperUtils) {
        this.contactRepository = contactRepository;
        this.modelMapperUtils = modelMapperUtils;
    }

    private Contact findByContact(Long id) throws Exception {
        return this.contactRepository.findById(id).orElseThrow(() -> new Exception("Contacto no encontrado"));
    }

    @Override
    @Cacheable(value = "contacts", key = "'all'")
    public List<ContactResponse> findAll() {
        List<Contact> contacts = this.contactRepository.findAll();
        return contacts.stream()
                .map(this::mapToContactResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "contacts", key = "#id")
    public ContactResponse findById(Long id) throws Exception {
        Contact contact = findByContact(id);
        return mapToContactResponse(contact);
    }

    @Override
    public ContactResponse save(ContactDTO contactDTO) {
        Contact contact = modelMapperUtils.map(contactDTO, Contact.class);
        Contact savedContact = contactRepository.save(contact);
        return mapToContactResponse(savedContact);
    }

    @Override
    @CachePut(value = "contacts", key = "#id")
    public void update(Long id, ContactDTO contactDTO) throws Exception {
        Contact contact = findByContact(id);
        modelMapperUtils.mapVoid(contactDTO, contact);
        contactRepository.save(contact);
    }

    @Override
    @CacheEvict(value = "contacts", key = "#id")
    public void delete(Long id) throws Exception {
        changeState(id, State.Inactivo);
    }

    @Override
    @CachePut(value = "contacts", key = "#id")
    public void activate(Long id) throws Exception {
        changeState(id, State.Activo);
    }

    private void changeState(Long id, State state) throws Exception {
        Contact contact = findByContact(id);
        contact.setState(state);
        contactRepository.save(contact);
    }

    private ContactResponse mapToContactResponse(Contact contact) {
        return new ContactResponse(
                contact.getId(),
                contact.getName(),
                contact.getEmail(),
                contact.getMessage(),
                contact.getPhone()
        );
    }
}
