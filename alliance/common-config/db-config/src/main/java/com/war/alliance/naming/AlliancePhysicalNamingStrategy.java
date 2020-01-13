package com.war.alliance.naming;

import static java.lang.Character.toUpperCase;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class AlliancePhysicalNamingStrategy implements PhysicalNamingStrategy {

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

