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

    @Override
    public void onBindViewHolder(AffirmationViewHolder viewHolder, final int position) {
        // bind values here
        // trim string and add ... ?
        viewHolder.getTextView().setText(affirmationList.get(position).getAffirmationString());
        // add position as number, so button can process the number and rearrange for user or open that affirmation
        // May have to look up how to rearrange recyclerView....

    }

    @Override
    public int getItemCount() {
        return affirmationList.size();
    }


    public static class AffirmationViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public AffirmationViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.affirmation_string);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
