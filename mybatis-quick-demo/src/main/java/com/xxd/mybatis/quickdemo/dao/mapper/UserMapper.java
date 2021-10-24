package com.xxd.mybatis.quickdemo.dao.mapper;

import com.xxd.mybatis.quickdemo.dao.entity.UserEntity;


public interface UserMapper {
  /**
   * 根据id查询用户
   *
   * @param id
   * @return
   */
  UserEntity selectUserById(Long id);

  void updateForName(String id, String name);
}
