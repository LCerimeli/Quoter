package com.example.quoter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// RecyclerView com os blocos de frases
class QuoteViewHolder extends RecyclerView.ViewHolder {

    TextView textView_quote, textView_author;
    Button button_copy;
    public QuoteViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_quote = itemView.findViewById(R.id.textView_quote);
        textView_author = itemView.findViewById(R.id.textView_author);
        button_copy = itemView.findViewById(R.id.button_copy);
    }
}

// Adapter para exibir as informações da RecyclerView
public class QuoteRecyclerAdapter extends RecyclerView.Adapter<QuoteViewHolder> {

    Context context;
    List<QuoteResponse> list;
    CopyListener listener;

    public QuoteRecyclerAdapter(Context context, List<QuoteResponse> list, CopyListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuoteViewHolder(LayoutInflater.from(context).inflate(R.layout.list_quote, parent, false));
    }


    // Conteúdo de um bloco de frase
    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        holder.textView_quote.setText(list.get(position).getText()); // Conteúdo da frase
        holder.textView_author.setText(list.get(position).getAuthor()); // Nome do autor
        holder.button_copy.setOnClickListener(new View.OnClickListener() { // Botão de copiar
            @Override
            public void onClick(View view) {
                listener.onCopyClicked(list.get(holder.getAdapterPosition()).getText());
            }
        });
    }

    // Retorna o número de itens de uma lista
    @Override
    public int getItemCount() {
        return list.size();
    }
}


