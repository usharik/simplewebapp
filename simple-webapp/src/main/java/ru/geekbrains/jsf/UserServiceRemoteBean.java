package ru.geekbrains.jsf;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface UserServiceRemoteBean {

    List<UserRepr> getAllUsers();
}
