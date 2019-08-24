package ru.geekbrains.jsf;

import ru.geekbrains.persist.RoleRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class RoleService implements Serializable {

    @Inject
    private RoleRepository roleRepository;

    @Transactional
    public List<RoleRepr> getAllRoles() {
        return roleRepository.getAllRoles().stream()
                .map(RoleRepr::new)
                .collect(Collectors.toList());
    }
}
