package me.dmjohnson.wgu.c196;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import me.dmjohnson.wgu.c196.util.Globals;

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

        // Get terms
        terms = new ArrayList<>();
        model = ViewModelProviders.of(this).get(TermListViewModel.class);

        // Setup recylcerview and fields
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        newTermButton = findViewById(R.id.add_button);


        // Get data from terms
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

        // Setup listeners
        newTermButton.setOnClickListener(v-> onClickNewTerm());

        // Since this is the main activity in the app, we need to create the notification channel
        // for the alerts here
        CharSequence name = getString(R.string.notification_channel_name);
        String description = getString(R.string.notification_channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(Globals.NOTIFICATION_CHANNEL_ID_ALERTS, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

    }

    private void onClickNewTerm() {
        Intent intent = new Intent(this, TermEditActivity.class);
        startActivity(intent);
    }


}
