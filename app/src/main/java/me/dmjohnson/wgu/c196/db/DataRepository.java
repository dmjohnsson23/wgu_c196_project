package me.dmjohnson.wgu.c196.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataRepository {
    private AppDatabase db;
    private Executor executor;
    private LiveData<List<Term>> terms;

    public DataRepository(Context context){
        executor = Executors.newSingleThreadExecutor();
        db = AppDatabase.getInstance(context);
    }

    public LiveData<List<Term>> getTerms(){
        if (terms == null){
            terms = db.getDao().getTerms();
        }
        return terms;
    }

    public void addTestTerms(){
        final List<Term> testTerms = Arrays.asList(
            new Term("Term 1", null, null),
            new Term("Term 2", null, null),
            new Term("Term 3", null, null)
        );
        addTerms(testTerms);
    }

    private void addTerms(List<Term> terms) {
        executor.execute(()->db.getDao().addTerms(terms));
    }
}
