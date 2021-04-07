package com.munderhill.affirmation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.munderhill.affirmation.R;
import com.munderhill.affirmation.entities.Affirmation;

import java.util.List;

public class EditAffirmationsAdapter extends RecyclerView.Adapter<EditAffirmationsAdapter.AffirmationViewHolder> {

    /* need to create based off
    * https://developer.android.com/guide/topics/ui/layout/recyclerview
     */
    private List<Affirmation> affirmationList;

    public EditAffirmationsAdapter(List<Affirmation> affirmationsList) {
        this.affirmationList = affirmationsList;
    }

    @Override
    public AffirmationViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.edit_affirmations_list, viewGroup, false);
        return new AffirmationViewHolder(view);
    }

    public int cutAfterXSpaces(String str, int spaces) {
        char[] strArray = str.toCharArray();
        int index = -1;
        int count = 0;
        boolean ignoreUntilNotSpace = true;
        for(int i = 0; i < strArray.length; i++) {
            if(ignoreUntilNotSpace) {
                if(c != ' ') ignoreUntilNotSpace = false;
            } else {
                if (c == ' ') {
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

    @Override
    public int getItemCount() {
        return affirmationList.size();
    }

    @Override
    public void onBindViewHolder(AffirmationViewHolder viewHolder, final int position) {

        // add position string
        viewHolder.getTextView().setText(String.valueOf(position));
        // format affirmationString and add to viewHolder
        String affirmationString = affirmationList.get(position).getAffirmationString();
        int indexToCut = cutAfterXSpaces(affirmationString,4);
        if(indexToCut > -1) {
            affirmationString = affirmationString.substring(0, indexToCut);
            affirmationString += "...";
        }
        viewHolder.getTextView().setText(affirmationString);
        // button for edit. button for delete. button for rearrange.
        // WORK ON BUTTONS!!!
        // May have to look up how to rearrange recyclerView....

    }

    public static class AffirmationViewHolder extends RecyclerView.ViewHolder {
        private final TextView affirmationNumber;
        private final TextView affirmationString;

        public AffirmationViewHolder(View view) {
            super(view);
            affirmationString = (TextView) view.findViewById(R.id.affirmationText);
            affirmationNumber = (TextView) view.findViewById(R.id.affirmationNumber);

        }

        public TextView getTextView() {
            return textView;
        }
    }
}
