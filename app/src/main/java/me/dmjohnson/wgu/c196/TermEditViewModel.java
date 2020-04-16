package me.dmjohnson.wgu.c196;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import me.dmjohnson.wgu.c196.db.DataRepository;
import me.dmjohnson.wgu.c196.db.Term;

public class TermEditViewModel extends AndroidViewModel {
    private DataRepository repository;
    private MutableLiveData<Term> term;

    public TermEditViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }

    public MutableLiveData<Term> getTerm(int id) {
        if (term == null){
            term = repository.getTermMutable(id);
        }
        return term;
    }

    public MutableLiveData<Term> getTerm(){
        if (term == null){
            term = new MutableLiveData<>();
            term.postValue(new Term());
        }
        return term;
    }

    public void saveTerm(Term term) {
        this.term.postValue(term);
        repository.saveTerm(term);
    }
}
