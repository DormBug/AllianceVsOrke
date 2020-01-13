package com.war.orke.repository;

import com.war.orke.annotation.InjectSQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class ColonyJdbcRepository {

    @InjectSQLQuery("com/war/orke/repository/updateColonyInfo.sql")
    private String updateColonyInfo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void updateColonyInfo(String colonyName, BigInteger population) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("name", colonyName)
                .addValue("populationCount", population);
        jdbcTemplate.update(updateColonyInfo, source);
    }
}
