package com.example.quoter;


import android.content.Context;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


// Classe responsável por fazer a conexão com a API
public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.quotable.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    //Interface com uma lista de 100 frases aleatórias da API
    private interface CallQuotes{
        @GET("quotes/random?limit=100")
        Call<List<QuoteResponse>> callQuotes();
    }

    // Pega as frases da lista
    public void GetAllQuotes(QuoteResponseListener listener){
        CallQuotes callQuotes = retrofit.create(CallQuotes.class);
        Call<List<QuoteResponse>> call = callQuotes.callQuotes();
        call.enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(Call<List<QuoteResponse>> call, Response<List<QuoteResponse>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "Request unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }

                listener.didFetch(response.body(), response.message());

            }

            @Override
            public void onFailure(Call<List<QuoteResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }


}
