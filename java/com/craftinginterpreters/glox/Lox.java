package com.craftinginterpreters.glox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Lox {
  static boolean hadError = false;
  public static void main(String[] args) throws IOException {
    // 参数大于1时
    if (args.length > 1) {
      // 退出
      System.out.println("Usage: glox [script]");
      System.exit(64); 
    } else if (args.length == 1) {
      // 从文件启动
      runFile(args[0]);
    } else {
      // 命令行启动
      runPrompt();
    }
  }

  private static void runFile(String path) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(path));
    run(new String(bytes, Charset.defaultCharset()));
  }

  private static void runPrompt() throws IOException {
    // 读取用户输入(单个字符)
    InputStreamReader input = new InputStreamReader(System.in);
    // 带缓存区的读取 
    BufferedReader reader = new BufferedReader(input);
    // 创建一个无限循环
    for (;;) { 
      System.out.print("> ");
      // 读取一行
      String line = reader.readLine();
      // 读取失败 结束循环
      if (line == null) break;
      // 执行语句
      run(line);
    }
  }

  private static void run(String source) {
    Scanner scanner = new Scanner(source);
    List<Token> tokens = scanner.scanTokens();

    // For now, just print the tokens.
    for (Token token : tokens) {
      System.out.println(token);
    }
  }

  static void error(int line, String message) {
    report(line, "", message);
  }

  private static void report(int line, String where,
                             String message) {
    System.err.println(
        "[line " + line + "] Error" + where + ": " + message);
    hadError = true;
  }
}

