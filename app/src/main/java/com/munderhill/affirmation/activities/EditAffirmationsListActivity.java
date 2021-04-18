package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.adapters.EditAffirmationsAdapter;
import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EditAffirmationsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppClass appClassReference;
    private EditAffirmationsAdapter editAffirmationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_affirmations_list);
        // initialize view and set layout manager
        recyclerView = findViewById(R.id.editAffirmationsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // get affirmationList from AppClass
        appClassReference = (AppClass) getApplicationContext();
        List<Affirmation> affirmationList = appClassReference.getAffirmationList();
        // Instantiate RecyclerView adapter and bind to RecyclerView
        editAffirmationsAdapter = new EditAffirmationsAdapter(affirmationList, this);
        recyclerView.setAdapter(editAffirmationsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }

    public Spinner buildDropdownSpinner() {
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        Spinner spinner = new Spinner(this);
        int affirmitionListSize = appClassReference.getAffirmationListSize();
        String[] items = new String[affirmitionListSize];
        for(int i = 0; i < affirmitionListSize; i++) {
            items[i] = String.valueOf(i+1);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setGravity(Gravity.CENTER);
        return spinner;
    }

    public void eventListenerChangePositionButton(View view){
        // Getting current position in Affirmations List
        EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder = (EditAffirmationsAdapter.AffirmationViewHolder)
                recyclerView.findContainingViewHolder(view);
        int currentPosition = affirmationViewHolder.getAffirmationNumber();
        Spinner spinner = buildDropdownSpinner();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
                .setIcon(null)
                .setTitle("Position")
                .setMessage("Move to position:")
                .setView(spinner)
                .setPositiveButton("Ok", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int newPosition = Integer.parseInt(spinner.getSelectedItem().toString());
                                if(newPosition < 1) newPosition = 1;
                                else if(newPosition > appClassReference.getAffirmationListSize()) {
                                    newPosition = appClassReference.getAffirmationListSize();
                                }
                                Single<Integer> moveInAffirmationList = appClassReference.moveInAffirmationList(currentPosition,
                                        newPosition);
                                Single<List<Affirmation>> reinitialize = appClassReference.initializeAffirmationList();
                                CompositeDisposable compositeDisposable = new CompositeDisposable();
                                compositeDisposable.add(
                                    Single.concat(moveInAffirmationList,reinitialize)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doAfterTerminate(() -> {
                                                    editAffirmationsAdapter.notifyDataSetChanged();
                                                    compositeDisposable.dispose();}
                                            )
                                            .subscribe(result -> {if(result instanceof List) {
                                                appClassReference.setAffirmationList((List<Affirmation>) result);
                                                editAffirmationsAdapter.setAffirmationList((List<Affirmation>) result);
                                            }})
                                );
                            }
                        })
                .setNeutralButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert);
        // https://stackoverflow.com/questions/4406804/how-to-control-the-width-and-height-of-the-default-alert-dialog-in-android
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(600,650);
    }

    public void eventListenerDeleteButton(View view){
        // Getting current position in Affirmations List
        EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder = (EditAffirmationsAdapter.AffirmationViewHolder)
                recyclerView.findContainingViewHolder(view);
        new AlertDialog.Builder(this)
                .setTitle("Delete Affirmation")
                .setMessage("Are you sure you would like to permanently delete this affirmation?")
                .setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int currentPosition = affirmationViewHolder.getAffirmationNumber();
                                // deleting element in this position
                                Single<Integer> delete = appClassReference.deleteFromAffirmationList(currentPosition-1);
                                Single<Integer> reorganize = appClassReference.reorganizeAfterDelete(currentPosition);
                                Single<List<Affirmation>> reinitialize = appClassReference.initializeAffirmationList();
                                CompositeDisposable compositeDisposable = new CompositeDisposable();
                                compositeDisposable.add(
                                    Single.concat(delete,reorganize,reinitialize)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doAfterTerminate(() -> {
                                                editAffirmationsAdapter.notifyDataSetChanged();
                                                compositeDisposable.dispose();}
                                            )
                                            .subscribe(result -> {if(result instanceof List) {
                                                appClassReference.setAffirmationList((List<Affirmation>) result);
                                                editAffirmationsAdapter.setAffirmationList((List<Affirmation>) result);
                                            }})
                                );
                            }})
                .setNeutralButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void eventListenerEditButton(View view){
        // open a new activity called EditAffirmationsActivity for the affirmationNumber in question
        Intent intent = new Intent(this,EditAffirmationsActivity.class);
        Bundle bundle = new Bundle();
        EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder = (EditAffirmationsAdapter.AffirmationViewHolder)
                recyclerView.findContainingViewHolder(view);
        int currentPositionInList = affirmationViewHolder.getAffirmationNumber();
        bundle.putInt("affirmationNumber",currentPositionInList-1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // takes user back to home when back button on top is clicked.
    @Override
    public boolean onSupportNavigateUp() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        return true;
    }
}
