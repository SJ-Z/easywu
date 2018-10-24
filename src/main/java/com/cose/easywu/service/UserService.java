package com.cose.easywu.service;

import com.cose.easywu.exception.UserException;
import com.cose.easywu.po.User;

public interface UserService {

    void regist(User user);
    boolean checkEmailExist(String email);
    boolean checkNickExist(String nick);
    void updateUserState(String u_id, String u_code, int u_state);
    String login(String u_email, String u_pwd) throws UserException;
}
