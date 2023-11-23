package com.example.quoter;

import java.util.List;

// Listener para verificar a resposta da API
public interface QuoteResponseListener {
    void didFetch(List<QuoteResponse> responses, String message);
    void didError(String message);
}
