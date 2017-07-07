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
    private ArrayList<PlayCryptogram> mPlayCryptograms;
    private String username = "";

    public static class CryptogramHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView cryptogramId;
        private TextView progress;
        private Cryptogram mCryptogram;
        private PlayCryptogram mPlayCryptogram;
        private String username = "";


        public CryptogramHolder(View v) {
            super(v);
            cryptogramId = (TextView) v.findViewById(R.id.available_cryptogram_id);
            progress = (TextView) v.findViewById(R.id.available_cryptogram_progress);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            mPlayCryptogram.progress = "In progress";
            // TODO: modify mPlayCryptogram data and store in db (in Activity)

            Intent intent = new Intent(context, PlayCryptogramActivity.class);
            intent.putExtra("CRYPTOGRAM_ID", mCryptogram.cryptoId);
            intent.putExtra("CRYPTOGRAM_ENCODED", mCryptogram.encodedPhrase);
            intent.putExtra("CRYPTOGRAM_SOLUTION", mCryptogram.solutionPhrase);
            intent.putExtra("USERNAME", username);
            context.startActivity(intent);
        }

        public void bindCryptogram(String u, Cryptogram c, PlayCryptogram p) {
            username = u;
            mCryptogram = c;
            mPlayCryptogram = p;
            cryptogramId.setText(c.cryptoId);
            progress.setText(p.progress);
        }
    }

    public AvailableCryptogramsAdapter(String username, ArrayList<Cryptogram> cryptograms, ArrayList<PlayCryptogram> playCryptograms) {
        this.username = username;
        this.mCryptograms = cryptograms;
        this.mPlayCryptograms = playCryptograms;
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
        PlayCryptogram playCryptogram = new PlayCryptogram(username, crypto.cryptoId);
        for (PlayCryptogram mPlayCryptogram : mPlayCryptograms) {
            if (crypto.cryptoId.equals(mPlayCryptogram.cryptogramId)) {
                playCryptogram = mPlayCryptogram;
            }
        }
        holder.bindCryptogram(username, crypto, playCryptogram);
    }

    @Override
    public int getItemCount() {
        return mCryptograms.size();
    }
}
