package com.example.quoter;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CopyListener{
    RecyclerView recycler_home;
    RequestManager manager;
    QuoteRecyclerAdapter adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main); // Mostra o layout da MainActivity

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_bar_layout);

        recycler_home = findViewById(R.id.recycler_home);

        manager = new RequestManager(this);

        // Pega as frases da API
        try {
            manager.GetAllQuotes(listener);
            dialog = new ProgressDialog(this);
            dialog.setTitle("Loading...");
            dialog.show();
        } catch (Exception exception) {
            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }

    }


    // Cria o menu (três pontinhos)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    // Dita o que acontecerá quando cada opção do menu for selecionada
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            // Atualiza as frases
            case R.id.refresh_quotes:
                try {
                    manager.GetAllQuotes(listener);
                    Toast.makeText(MainActivity.this, "Quotes refreshed!", Toast.LENGTH_SHORT).show();
                } catch (Exception exception) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
                return true;

            // Mostra o nome dos criadores do aplicativo
            case R.id.creators:
                Toast.makeText(MainActivity.this, "Created by Lucas Cerimeli and João Victor Alves", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.github_repository:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/LCerimeli/Quoter"));
                startActivity(browserIntent);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Verifica a reposta da API
    private final QuoteResponseListener listener = new QuoteResponseListener() {
        @Override
        public void didFetch(List<QuoteResponse> responses, String message) {
            showData(responses);
            dialog.dismiss();
        }

        @Override
        public void didError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };


    // Exibe as informações
    private void showData(List<QuoteResponse> responses) {
        recycler_home.setHasFixedSize(true);
        recycler_home.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new QuoteRecyclerAdapter(MainActivity.this, responses, this);
        recycler_home.setAdapter(adapter);
    }

    // Copia a frase quando o botão de copiar é pressionado
    @Override
    public void onCopyClicked(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Copied data", text);
        clipboardManager.setPrimaryClip(data);
        Toast.makeText(MainActivity.this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
    }
}