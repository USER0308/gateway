package com.dao;

import com.entity.BaseConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RedisLimitDAO extends JpaRepository<BaseConfig,Integer> {

    BaseConfig getByName(String name);


}
