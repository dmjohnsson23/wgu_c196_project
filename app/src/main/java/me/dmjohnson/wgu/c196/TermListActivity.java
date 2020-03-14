package me.dmjohnson.wgu.c196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.dmjohnson.wgu.c196.db.Term;
import me.dmjohnson.wgu.c196.ui.TermListAdapter;

public class TermListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TermListAdapter adapter;
    private  TermListViewModel model;
    private List<Term> terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        terms = new ArrayList<>();
        model = ViewModelProviders.of(this).get(TermListViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        model.terms.observe(this, newTerms -> {
            terms.clear();
            terms.addAll(newTerms);
            System.out.println("Update terms data: "+ terms);

            if (adapter== null){
                adapter = new TermListAdapter(terms, TermListActivity.this);
                recyclerView.setAdapter(adapter);
            }
            else{
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fabClickHandler(View view) {
        model.newTerm();
    }


}
