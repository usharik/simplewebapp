package ru.geekbrains.jsf;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.rest.UserServiceRest;

import javax.ejb.*;
import javax.jws.WebService;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@WebService(endpointInterface = "ru.geekbrains.jsf.UserServiceWs", serviceName = "UserService")
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class UserServiceBean implements UserServiceRemoteBean, UserServiceLocalBean, UserServiceRest {

    @EJB
    private UserRepository userRepository;

    @Transactional
    public void merge(UserRepr user) {
        User merged = userRepository.merge(new User(user));
        user.setId(merged.getId());
    }

    @Transactional
    public void delete(int id) {
        userRepository.delete(id);
    }

    @Transactional
    public UserRepr findById(int id) {
        return new UserRepr(userRepository.findById(id));
    }

    @Transactional
    public boolean existsById(int id) {
        return userRepository.findById(id) != null;
    }

    @Override
    @Transactional
    public List<UserRepr> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

}
