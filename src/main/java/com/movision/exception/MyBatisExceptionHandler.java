package com.movision.exception;

import java.io.IOException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.NestedIOException;

/**
 * 捕获mapper.xml异常
 *
 * @author zhuangyuhao
 * @date 2017年2月9日
 */
public class MyBatisExceptionHandler extends SqlSessionFactoryBean {
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        try {
            return super.buildSqlSessionFactory();
        } catch (NestedIOException e) {
            e.printStackTrace();
            throw new NestedIOException("Failed to parse mapping resource: ''", e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

}
