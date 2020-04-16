package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import me.dmjohnson.wgu.c196.db.DataRepository;
import me.dmjohnson.wgu.c196.db.Term;

public class TermListViewModel extends AndroidViewModel {
    LiveData<List<Term>> terms;
    private DataRepository repository;

    public TermListViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        terms = repository.getTerms();
    }
}
