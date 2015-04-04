package com.github.vic.silabas4j;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class SilabasTest {

    private static Map<String, Map<String, Object>> testCases;

    @BeforeClass
    public static void setUp() throws Exception {
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = SilabasTest.class.getClassLoader().getResourceAsStream("silabas-test.yaml");
        testCases = yaml.loadAs(resourceAsStream, Map.class);
    }


    @Test
    public void testSilabas() throws Exception {
        testCases.forEach((String word, Map asserts)-> {
            Silabas silabas = Silabas.process(word);
            Object[] expected = ((ArrayList) asserts.get("syllables")).toArray();
            Object[] actual = silabas.getSyllables().toArray();
            assertArrayEquals("Syllables for: "+word, expected, actual);
        });
    }
}