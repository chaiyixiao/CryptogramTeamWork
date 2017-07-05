package edu.gatech.seclass.sdpcryptogram;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chai on 05/07/2017.
 */

public class PlayCryptogramAdapter extends RecyclerView.Adapter<PlayCryptogramAdapter.CryptogramHolder> {
    private ArrayList<Character> cLetters;
    private ArrayList<Character> solutionLetters;

    //1
    public static class CryptogramHolder extends RecyclerView.ViewHolder {
        //2
        private EditText cLetter;
        private TextView solutionLetter;

        //4
        public CryptogramHolder(View v) {
            super(v);
            cLetter = (EditText) v.findViewById(R.id.cryptogram_letter);
            solutionLetter = (TextView) v.findViewById(R.id.cryptogram_solution_letter);
        }

        public void bindCryptogram(Character itemCryptogram, Character solutionCryptogram) {
            cLetter.setText(itemCryptogram.toString());
            solutionLetter.setText(solutionCryptogram.toString());
        }
    }


    public PlayCryptogramAdapter(ArrayList<Character> encodedList, ArrayList<Character> solutionList ) {
        cLetters = encodedList;
        solutionLetters = solutionList;
    }

    @Override
    public PlayCryptogramAdapter.CryptogramHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.play_cryptogram_item, parent, false);
        return new CryptogramHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(PlayCryptogramAdapter.CryptogramHolder holder, int position) {
        Character itemCryptogram = cLetters.get(position);
        Character itemSolution = solutionLetters.get(position);
        holder.bindCryptogram(itemCryptogram, itemSolution);
    }

    @Override
    public int getItemCount() {
        return solutionLetters.size();
    }
}
