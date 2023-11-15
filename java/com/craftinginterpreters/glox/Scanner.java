package com.craftinginterpreters.glox;

import java.util.ArrayList;
import java.util.List;

import static com.craftinginterpreters.glox.TokenType.*; 

class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
  private int current = 0;
  private int line = 1;

  Scanner(String source) {
    this.source = source;
  }

  List<Token> scanTokens() {
    // 是否扫描结束
    while (!isAtEnd()) {
    // 记录下一个开始的索引
      start = current;
      scanToken();
    }
    // 添加end of file 标记
    tokens.add(new Token(EOF, "", null, line));
    return tokens;
  }
  private void scanToken() {
    // 获取字符
    char c = advance();
    switch (c) {
      case '(': addToken(LEFT_PAREN); break;
      case ')': addToken(RIGHT_PAREN); break;
      case '{': addToken(LEFT_BRACE); break;
      case '}': addToken(RIGHT_BRACE); break;
      case ',': addToken(COMMA); break;
      case '.': addToken(DOT); break;
      case '-': addToken(MINUS); break;
      case '+': addToken(PLUS); break;
      case ';': addToken(SEMICOLON); break;
      case '*': addToken(STAR); break; 
      case '!':
      addToken(match('=') ? BANG_EQUAL : BANG);
      break;
    case '=':
      addToken(match('=') ? EQUAL_EQUAL : EQUAL);
      break;
    case '<':
      addToken(match('=') ? LESS_EQUAL : LESS);
      break;
    case '>':
      addToken(match('=') ? GREATER_EQUAL : GREATER);
      break;
      default:
      Lox.error(line, "Unexpected character.");
      break;
    }
  }
  // 判断是否已经处理完所有字符
  private boolean isAtEnd() {
    // 索引
    return current >= source.length();
  }
  // 
  private boolean match(char expected) {
    if (isAtEnd()) return false;
    if (source.charAt(current) != expected) return false;

    current++;
    return true;
  }

  private char advance() {
    // 获取下一个字符
    current++;
    return source.charAt(current - 1);
  }
  // 根据TokenType初始化Token
  private void addToken(TokenType type) {
    addToken(type, null);
  }

  private void addToken(TokenType type, Object literal) {
    // 字符串分割为 对应遍历出的字符
    String text = source.substring(start, current);
    tokens.add(new Token(type, text, literal, line));
  }
}
