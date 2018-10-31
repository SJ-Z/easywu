package com.cose.easywu.mapper;

import com.cose.easywu.po.User;

public interface UserMapper {

    // 插入新用户
    void insertUser(User user);
    // 按用户邮箱查询，返回用户id
    String selectByEmail(String u_email);
    // 按用户昵称查询，返回用户id
    String selectByNick(String u_nick);
    // 按用户id和用户激活码更新用户状态
    void updateUserState(User user);
    // 按用户邮箱和密码查询，返回用户id
    String selectIdByEmailAndPwd(User user);
    // 按用户id查询用户账号状态，返回用户账号状态
    int selectStateById(String u_id);
    // 按用户邮箱更新激活码
    void updateUserCodeByEmail(User user);
    // 按用户邮箱和激活码查询，返回用户id
    String selectByEmailAndCode(User user);
    // 按用户邮箱修改账户密码
    void updateUserPwdByEmail(User user);
    // 按用户id查询用户个人信息
    User selectById(String u_id);
    // 按用户id更新用户性别
    void updateSexById(User user);
    // 按用户id更新用户昵称
    void updateNickById(User user);
    // 按用户id更新用户头像
    void updatePhotoById(User user);
}
