package com.github.vic.silabas4j;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.yaml.snakeyaml.Yaml;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class SilabasTest {

    private static final String YAML_RESOURCE = "silabas-test.yaml";
    private static final String[] WORD_EXPECTATIONS = {
            "syllables"
    };

    private Silabas subject;

    @Parameterized.Parameter(value = 0)
    public String word;

    @Parameterized.Parameter(value = 1)
    public Collection<String> expectedSyllables;


    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() throws Exception {
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = SilabasTest.class.getClassLoader().getResourceAsStream(YAML_RESOURCE);
        Map<String,Map<String,Object>> testCases = yaml.loadAs(resourceAsStream, Map.class);
        Object[] objects = testCases.entrySet().stream().map(SilabasTest::entryData).toArray();
        return Arrays.<Object[]>asList(objects);
    }

    private static Object[] entryData(Map.Entry<String, Map<String,Object>> entry) {
        Stream word = Stream.of(entry.getKey());
        Map<String, Object> map = entry.getValue();
        Stream expectations = Arrays.stream(WORD_EXPECTATIONS).map(map::get);
        return Stream.concat(word, expectations).toArray();
    }

    @Before
    public void before() {
       this.subject = Silabas.process(word);
    }

    @Test
    public void testSyllables() throws Exception {
        Collection<CharSequence> syllables = subject.getSyllables();
        assertArrayEquals("Syllables for: "+word, expectedSyllables.toArray(), syllables.toArray());
    }

}