package ru.geekbrains.jsf;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserServiceLocalBean {

    void merge(UserRepr user);

    void delete(int id);

    UserRepr findById(int id);

    boolean existsById(int id);

    List<UserRepr> getAllUsers();
}
