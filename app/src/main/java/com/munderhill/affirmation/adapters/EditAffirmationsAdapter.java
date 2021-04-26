package com.munderhill.affirmation.adapters;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.munderhill.affirmation.AppClass;
import com.munderhill.affirmation.R;

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
        View view;
        // Set layout based on screen size
        Configuration configuration = viewGroup.getResources().getConfiguration();
        if(configuration.smallestScreenWidthDp < 400) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.edit_affirmations_list_320ldpi_480mdpi_400ldpi, viewGroup, false);
        } else if (configuration.smallestScreenWidthDp > 800) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.edit_affirmations_list_xhdpi_landscape, viewGroup, false);;
        } else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.edit_affirmations_list_420_560dpi, viewGroup, false);
        }
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
                    if(index > 25) return 25; //max length = 45
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

        // add position number
        viewHolder.setAffirmationNumber(position+1);
        // add image
        byte[] imageByteArray = affirmationList.get(position).getImageToSave();
        viewHolder.getImageView().setImageBitmap(
                BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length)
        );
        // format affirmationString and add to viewHolder
        String affirmationString = affirmationList.get(position).getAffirmationString();
        int indexToCut = cutAfterXSpaces(affirmationString,4);
        if(indexToCut > -1) {
            if(indexToCut > 24) affirmationString.substring(0, indexToCut);
            else {affirmationString = affirmationString.substring(0, indexToCut);}
            affirmationString += "...";
        } else if (indexToCut == -1 && affirmationString.length() > 25) {
            affirmationString = affirmationString.substring(0, 25);
            affirmationString += "...";
        }
        viewHolder.getAffirmationString().setText(affirmationString);
    }

    public void setAffirmationList(List<Affirmation> newAffirmationList) {
        affirmationList = newAffirmationList;
    }

    public static class AffirmationViewHolder extends RecyclerView.ViewHolder {
        private int affirmationNumber;
        private final ImageView imageView;
        private final TextView affirmationString;
        private AppClass appClass;
        private Context context;

        public AffirmationViewHolder(View view, Context context) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            affirmationString = (TextView) view.findViewById(R.id.affirmationText);
            appClass = (AppClass) view.getContext().getApplicationContext();
            context = context;
        }

        public int getAffirmationNumber() {
            return affirmationNumber;
        }

        public void setAffirmationNumber(int affirmationNumber) {
            this.affirmationNumber = affirmationNumber;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getAffirmationString() {
            return affirmationString;
        }

    }
}
