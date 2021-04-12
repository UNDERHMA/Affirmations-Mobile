package com.munderhill.affirmation.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.adapters.EditAffirmationsAdapter;
import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EditAffirmationsListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppClass appClassReference;
    private EditAffirmationsAdapter editAffirmationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void eventListenerChangePositionButton(View view){
        // Getting current position in Affirmations List
        EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder = (EditAffirmationsAdapter.AffirmationViewHolder)
                recyclerView.findContainingViewHolder(view);
        int currentPosition = getCurrentPosition(affirmationViewHolder);
        // popup alertbuilder with option to set integer position
        EditText editText= new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        new AlertDialog.Builder(this)
                .setTitle("Change Position")
                .setMessage("Enter the position for this affirmation (1 to the number of affirmations you have)")
                .setView(editText)
                .setPositiveButton("Set Position", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int newPosition = Integer.parseInt(editText.getText().toString());
                                if(newPosition < 1) newPosition = 1;
                                else if(newPosition > appClassReference.getAffirmationListSize()) {
                                    newPosition = appClassReference.getAffirmationListSize();
                                }
                                Single<Integer> moveInAffirmationList = appClassReference.moveInAffirmationList(currentPosition,
                                        newPosition);
                                Single<List<Affirmation>> reinitialize = appClassReference.initializeAffirmationList();
                                Single.concat(moveInAffirmationList,reinitialize)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(result -> {if(result instanceof List) {
                                            appClassReference.setAffirmationList((List<Affirmation>) result);
                                            editAffirmationsAdapter.setAffirmationList((List<Affirmation>) result);
                                            recyclerView.invalidate();
                                        }});
                            }
                        })
                .setNeutralButton("Cancel", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void eventListenerDeleteButton(View view){
        // Getting current position in Affirmations List
        EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder = (EditAffirmationsAdapter.AffirmationViewHolder)
                recyclerView.findContainingViewHolder(view);
        int currentPosition = getCurrentPosition(affirmationViewHolder);
        // deleting element in this position
        Single<Integer> delete = appClassReference.deleteFromAffirmationList(currentPosition-1);
        Single<Integer> reorganize = appClassReference.reorganizeAfterDelete(currentPosition);
        Single<List<Affirmation>> reinitialize = appClassReference.initializeAffirmationList();
        Single.concat(delete,reorganize,reinitialize)
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {if(result instanceof List) {
                    appClassReference.setAffirmationList((List<Affirmation>) result);
                    editAffirmationsAdapter.setAffirmationList((List<Affirmation>) result);


                    /// THIS REFRESH METHOD IS NOT WORKING!!!! THE OTHERS AREN'T WORKING EITHER

                    recyclerView.invalidate();

                }});
    }

    public void eventListenerEditButton(View view){
        // open a new activity called EditAffirmationsActivity for the affirmationNumber in question
        Intent intent = new Intent(this,EditAffirmationsActivity.class);
        Bundle bundle = new Bundle();
        EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder = (EditAffirmationsAdapter.AffirmationViewHolder)
                recyclerView.findContainingViewHolder(view);
        int currentPosition = getCurrentPosition(affirmationViewHolder);
        int currentIdOfPosition = getCurrentIdOfPosition(affirmationViewHolder,currentPosition);
        bundle.putInt("affirmationNumber",currentIdOfPosition);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public int getCurrentPosition(EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder) {
        return  Integer.parseInt(affirmationViewHolder.getAffirmationNumber().getText().toString());
    }

    public int getCurrentIdOfPosition(EditAffirmationsAdapter.AffirmationViewHolder affirmationViewHolder, int currentPosition) {
        return appClassReference.getAffirmationList().get(currentPosition-1).getAffirmationId();
    }
}
