package com.munderhill.affirmation.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;
import com.munderhill.affirmation.activities.EditAffirmationsActivity;
import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

public class EditAffirmationsAdapter extends RecyclerView.Adapter<EditAffirmationsAdapter.AffirmationViewHolder> {

    /* need to create based off
    * https://developer.android.com/guide/topics/ui/layout/recyclerview
     */
    private List<Affirmation> affirmationList;
    private AppClass appClass;
    private Context context;
    private AffirmationViewHolder affirmationViewHolder;

    public EditAffirmationsAdapter(List<Affirmation> affirmationsList, Context context) {
        this.affirmationList = affirmationsList;
        context = context;
        appClass = (AppClass) context.getApplicationContext();
    }

    @Override
    public AffirmationViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_affirmations_list, viewGroup, false);
        affirmationViewHolder = new AffirmationViewHolder(view,context);
        return affirmationViewHolder;
    }

    public int cutAfterXSpaces(String str, int spaces) {
        char[] strArray = str.toCharArray();
        int index = -1;
        int count = 0;
        boolean ignoreUntilNotSpace = true;
        for(int i = 0; i < strArray.length; i++) {
            if(ignoreUntilNotSpace) {
                if(strArray[i] != ' ') ignoreUntilNotSpace = false;
            } else {
                if (strArray[i] == ' ') {
                    count++;
                    index = i;
                }
                if (count == spaces) {
                    return index;
                }
            }
        }
        return -1;
    }

    public void eventListenerEditButton(View view){
        // open a new activity called EditAffirmationsActivity for the affirmationNumber in question
        Intent intent = new Intent(context,EditAffirmationsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("affirmationNumber",Integer.parseInt(affirmationViewHolder.getAffirmationNumber().getText().toString()));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return affirmationList.size();
    }

    @Override
    public void onBindViewHolder(AffirmationViewHolder viewHolder, final int position) {

        // add position string
        viewHolder.getAffirmationNumber().setText(String.valueOf(position+1));
        // format affirmationString and add to viewHolder
        String affirmationString = affirmationList.get(position).getAffirmationString();
        int indexToCut = cutAfterXSpaces(affirmationString,4);
        if(indexToCut > -1) {
            affirmationString = affirmationString.substring(0, indexToCut);
            affirmationString += "...";
        }
        viewHolder.getAffirmationString().setText(affirmationString);
    }

    public void setAffirmationList(List<Affirmation> newAffirmationList) {
        affirmationList = newAffirmationList;
    }

    public static class AffirmationViewHolder extends RecyclerView.ViewHolder {
        private final TextView affirmationNumber;
        private final TextView affirmationString;
        private AppClass appClass;
        private Context context;

        public AffirmationViewHolder(View view, Context context) {
            super(view);
            affirmationString = (TextView) view.findViewById(R.id.affirmationText);
            affirmationNumber = (TextView) view.findViewById(R.id.affirmationNumber);
            appClass = (AppClass) view.getContext().getApplicationContext();
            context = context;
        }

        public TextView getAffirmationNumber() {
            return affirmationNumber;
        }
        public TextView getAffirmationString() {
            return affirmationString;
        }

    }
}
