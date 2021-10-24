package com.xxd.mybatis.quickdemo;

import com.xxd.mybatis.quickdemo.dao.entity.UserEntity;
import com.xxd.mybatis.quickdemo.dao.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class QuickDemo {
  public static void main(String[] args) throws IOException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    try (SqlSession session = sqlSessionFactory.openSession()) {
      UserMapper userMapper = session.getMapper(UserMapper.class);
      UserEntity userEntity = userMapper.selectUserById(1L);
      System.out.println(userEntity);
    }
  }
}
