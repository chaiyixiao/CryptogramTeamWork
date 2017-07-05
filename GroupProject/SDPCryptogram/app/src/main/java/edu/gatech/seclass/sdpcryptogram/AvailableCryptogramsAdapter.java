package edu.gatech.seclass.sdpcryptogram;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by wc on 05/07/2017.
 */

public class AvailableCryptogramsAdapter extends RecyclerView.Adapter<AvailableCryptogramsAdapter.CryptogramHolder> {

    private ArrayList<Cryptogram> mCryptograms;


    public static class CryptogramHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView cryptogramId;
        private TextView progress;
        private Cryptogram mCryptogram;

        private static final String CRYPTOGRAM_KEY = "CRYPTOGRAM_KEY";


        public CryptogramHolder(View v) {
            super(v);
            cryptogramId = (TextView) v.findViewById(R.id.available_cryptogram_id);
            progress = (TextView) v.findViewById(R.id.available_cryptogram_progress);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, PlayCryptogramActivity.class);
            // TODO: passing cryptogram id as param
            intent.putExtra(CRYPTOGRAM_KEY, cryptogramId.getText().toString());
            context.startActivity(intent);
        }

        public void bindCryptogram(Cryptogram c) {
            mCryptogram = c;
            // set id progress textView
        }
    }

    public AvailableCryptogramsAdapter(ArrayList<Cryptogram> cryptograms) {
        mCryptograms = cryptograms;
    }


    @Override
    public AvailableCryptogramsAdapter.CryptogramHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_cryptograms_recycler_item_row, parent, false);
        return new CryptogramHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(CryptogramHolder holder, int position) {
        Cryptogram crypto = mCryptograms.get(position);
        holder.bindCryptogram(crypto);
    }

    @Override
    public int getItemCount() {
        return mCryptograms.size();
    }
}
