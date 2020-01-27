package com.war.orke.datasource.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.IdentifierHelper;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrkePhysicalNamingStrategyTest {

    @Mock
    private Identifier identifier;
    @Mock
    private JdbcEnvironment jdbcEnvironment;
    @Mock
    private IdentifierHelper mockIdentifierHelper;

    private PhysicalNamingStrategy strategy = new OrkePhysicalNamingStrategy();

    @Test
    void testToPhysicalCatalogName() {
        Identifier result = strategy.toPhysicalCatalogName(identifier, jdbcEnvironment);
        assertEquals(identifier, result);
    }

    @Test
    void testToPhysicalSchemaName() {
        Identifier result = strategy.toPhysicalSchemaName(identifier, jdbcEnvironment);
        assertEquals(identifier, result);
    }

    @Test
    void testToPhysicalTableName() {
        preMockObjects();
        Identifier expected = new Identifier("Text", false);

        when(mockIdentifierHelper.toIdentifier("Text", false)).thenReturn(expected);

        Identifier actual = strategy.toPhysicalTableName(identifier, jdbcEnvironment);
        assertEquals(expected, actual);
    }

    @Test
    void testToPhysicalSequenceName() {
        preMockObjects();
        Identifier expected = new Identifier("Text_Seq", false);

        when(mockIdentifierHelper.toIdentifier("Text_Seq", false)).thenReturn(expected);

        Identifier actual = strategy.toPhysicalSequenceName(identifier, jdbcEnvironment);
        assertEquals(expected, actual);
    }

    @Test
    void testToPhysicalColumnName() {
        preMockObjects();
        Identifier expected = new Identifier("Text", false);

        when(mockIdentifierHelper.toIdentifier("Text", false)).thenReturn(expected);

        Identifier actual = strategy.toPhysicalColumnName(identifier, jdbcEnvironment);
        assertEquals(expected, actual);
    }

    private void preMockObjects() {
        when(identifier.getText()).thenReturn("text");
        when(identifier.isQuoted()).thenReturn(false);
        when(jdbcEnvironment.getIdentifierHelper()).thenReturn(mockIdentifierHelper);
    }
}