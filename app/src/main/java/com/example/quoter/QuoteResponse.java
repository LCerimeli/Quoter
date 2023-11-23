package com.example.quoter;


// Classe responsável por pegar o conteúdo das frases e o autor
public class QuoteResponse {
    String content = "";
    String author = "";

    public String getText() {
        return content;
    } // Retorna o conteúdo da frase

    public String getAuthor() {
        return author;
    } // Retorna o nome do autor
}
