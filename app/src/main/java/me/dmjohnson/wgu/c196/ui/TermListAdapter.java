package me.dmjohnson.wgu.c196.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.dmjohnson.wgu.c196.R;
import me.dmjohnson.wgu.c196.TermViewActivity;
import me.dmjohnson.wgu.c196.db.Term;

import static me.dmjohnson.wgu.c196.util.Globals.TERM_ID;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.ViewHolder> {

    private final List<Term> terms;
    private final Context context;

    public TermListAdapter(List<Term> terms, Context context) {
        if (terms == null) this.terms = new ArrayList<Term>();
        else this.terms = terms;
        this.context = context;
    }


    @NonNull
    @Override
    public TermListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermListAdapter.ViewHolder holder, int position) {
        final Term term = terms.get(position);
        holder.termName.setText(term.getTitle());
        holder.termId = term.getId();
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setData(List<Term> terms) {
        this.terms.clear();
        this.terms.addAll(terms);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView termName;
        Button editButton;
        int termId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            termName = itemView.findViewById(R.id.term_name);
            editButton = itemView.findViewById(R.id.edit_button);

            editButton.setOnClickListener((View view)-> onClickEditButton());
        }

        private void onClickEditButton() {
            Intent intent = new Intent(context, TermViewActivity.class);
            intent.putExtra(TERM_ID, termId);
            context.startActivity(intent);
        }
    }
}
