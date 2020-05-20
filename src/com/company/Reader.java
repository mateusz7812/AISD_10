package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Reader {
    public Map<String, String[]> ReadAllLines() throws IOException {
        var path = Paths.get("odmiany.txt");
        var charset = Charset.defaultCharset();
        return Files.readAllLines(
                path,
                charset
        ).stream().collect(
                Collectors.toMap(
                        (s) -> s.split(",")[0].trim(),
                        (s) -> Arrays.stream(s.split(",")).skip(1).map(String::trim).toArray(String[]::new),
                        (oldValue, newValue) -> oldValue)
        );
    }
}
