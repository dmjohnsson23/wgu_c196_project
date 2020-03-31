package me.dmjohnson.wgu.c196;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import me.dmjohnson.wgu.c196.db.Term;
import me.dmjohnson.wgu.c196.ui.TermListAdapter;

public class TermListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TermListAdapter adapter;
    private TermListViewModel model;
    private List<Term> terms;
    private FloatingActionButton newTermButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        terms = new ArrayList<>();
        model = ViewModelProviders.of(this).get(TermListViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newTermButton = findViewById(R.id.add_button);
        newTermButton.setOnClickListener((View view)-> onClickNewTerm());

        model.terms.observe(this, newTerms -> {
            terms.clear();
            terms.addAll(newTerms);

            if (adapter== null){
                adapter = new TermListAdapter(terms, TermListActivity.this);
                recyclerView.setAdapter(adapter);
            }
            else{
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void onClickNewTerm() {
        Intent intent = new Intent(this, TermEditActivity.class);
        startActivity(intent);
    }


}
