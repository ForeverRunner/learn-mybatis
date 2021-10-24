package com.xxd.mybatis.quickdemo;


import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecutorTess {

  private Configuration configuration;

  private Transaction transaction;

  private Connection connection;

  @Before
  public void init() throws IOException, SQLException {
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    configuration = sqlSessionFactory.getConfiguration();
    connection = configuration.getEnvironment().getDataSource().getConnection();
    transaction = configuration.getEnvironment().getTransactionFactory().newTransaction(connection);
  }


  @Test
  public void testSimpleExecutor() throws SQLException {

    SimpleExecutor simpleExecutor = new SimpleExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.xxd.mybatis.quickdemo.dao.mapper.UserMapper.selectUserById");
    List<Object> res1 = simpleExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
    List<Object> res2 = simpleExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
    System.out.println(res1);
  }

  @Test
  public void testReuseExecutor() throws SQLException {
//    SimpleExecutor simpleExecutor = new ();
    System.out.println("testReuseExecutor");
    ReuseExecutor reuseExecutor = new ReuseExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.xxd.mybatis.quickdemo.dao.mapper.UserMapper.selectUserById");
    List<Object> res1 = reuseExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
    List<Object> res2 = reuseExecutor.doQuery(mappedStatement, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, mappedStatement.getBoundSql(1));
    System.out.println(res1);
  }

  @Test
  public void testBatchExecutor() throws SQLException {
    BatchExecutor batchExecutor = new BatchExecutor(configuration, transaction);
    MappedStatement mappedStatement = configuration.getMappedStatement("com.xxd.mybatis.quickdemo.dao.mapper.UserMapper.updateForName");
    Map map = new HashMap<>();
    map.put("arg0", 1);
    map.put("arg1", "zhangsan");
    int result1 = batchExecutor.doUpdate(mappedStatement, map);
    map.put("arg1", "lisi");
    int result2 = batchExecutor.doUpdate(mappedStatement, map);
    List<BatchResult> batchResults = batchExecutor.doFlushStatements(false);
    for (BatchResult batchResult : batchResults) {
      System.out.println(batchResult.getUpdateCounts().length);
    }


  }
}
