package com.mcp.code_review.analyzer;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JavaAnalyzer {

    public CompilationUnit parse(String path) throws IOException {
        return StaticJavaParser.parse(new File(path));
    }

}