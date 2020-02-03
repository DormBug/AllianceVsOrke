package com.war.orke.datasource.naming;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static java.lang.Character.toUpperCase;

/**
 * Strategy need for changing table and column naming by entity name.
 * Without adding property 'table' in @Entity - our custom strategy will do it for us
 *
 * If class with name 'ColonyInfo' will be annotated by @Entity and our context will find and scan it,
 * then our table in database will be named like 'colony_info'
 *
 * And if field with name 'colonyProperties' of this class will be annotated by @Column,
 * then in table will be created column with name 'Colony_Properties'
 *
 * */
public class OrkePhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return toPhysicalColumnName(name, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        LinkedList<String> parts = splitAndReplace( name.getText() );
        if (!"seq".equalsIgnoreCase(parts.getLast())) {
            parts.add( "seq" );
        }
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(
                join(parts),
                name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        List<String> parts = splitAndReplace(name.getText());
        return jdbcEnvironment.getIdentifierHelper().toIdentifier(join(parts), name.isQuoted());
    }

    private LinkedList<String> splitAndReplace(String name) {
        LinkedList<String> result = new LinkedList<>();
        for (String part : StringUtils.splitByCharacterTypeCamelCase(name)) {
            if (part != null && !part.trim().isEmpty()) {
                result.add(part.toLowerCase(Locale.ROOT));
            }
        }
        return result;
    }

    private String join(List<String> parts) {
        boolean firstPass = true;
        String separator = Strings.EMPTY;
        StringBuilder joined = new StringBuilder();
        for (String part : parts) {
            char firstChar = part.charAt(0);
            String upperChar = String.valueOf(toUpperCase(firstChar));
            part = part.replaceFirst(String.valueOf(firstChar), upperChar);
            joined.append(separator).append(part);
            if ( firstPass ) {
                firstPass = false;
                separator = "_";
            }
        }
        return joined.toString();
    }
}

