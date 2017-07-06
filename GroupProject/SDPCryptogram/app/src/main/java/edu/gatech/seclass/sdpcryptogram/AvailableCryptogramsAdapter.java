package edu.gatech.seclass.sdpcryptogram;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
            intent.putExtra("CRYPTOGRAM_ID", mCryptogram.cryptoId);
            intent.putExtra("CRYPTOGRAM_ENCODED", mCryptogram.encodedPhrase);
            intent.putExtra("CRYPTOGRAM_SOLUTION", mCryptogram.solutionPhrase);

            context.startActivity(intent);
        }

        public void bindCryptogram(Cryptogram c) {
            mCryptogram = c;
            Log.v("Cryptogram", c.solutionPhrase);
            cryptogramId.setText(c.cryptoId);
        }
    }

    public AvailableCryptogramsAdapter(ArrayList<Cryptogram> cryptograms) {
        Log.v("All Cryptogram", String.valueOf(cryptograms.size()));

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
