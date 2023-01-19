package com.service.BaseService;

import com.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSessionFactory;

public interface BaseService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
}
