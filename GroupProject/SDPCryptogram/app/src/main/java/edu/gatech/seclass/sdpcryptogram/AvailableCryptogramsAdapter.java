package edu.gatech.seclass.sdpcryptogram;

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

public class AvailableCryptogramsAdapter extends RecyclerView.Adapter<AvailableCryptogramsAdapter.CryptogramHolder> implements View.OnClickListener {

    private ArrayList<Cryptogram> mCryptograms;
    private ArrayList<PlayCryptogram> mPlayCryptograms;
    private String username = "";

    private static OnItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position

            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static class CryptogramHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {

        private TextView cryptogramId;
        private TextView progress;
        private TextView incorrectNum;

        public CryptogramHolder(View v) {
            super(v);
            cryptogramId = (TextView) v.findViewById(R.id.available_cryptogram_id);
            progress = (TextView) v.findViewById(R.id.available_cryptogram_progress);
            incorrectNum = (TextView) v.findViewById(R.id.available_cryptogram_incorrect);
        }

        public void bindCryptogram(String u, Cryptogram c, PlayCryptogram p) {

            cryptogramId.setText(c.cryptoId);
            progress.setText(p.progress);
            incorrectNum.setText(String.valueOf(p.numIncorrectSubmission));

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
        CryptogramHolder ch = new CryptogramHolder(inflatedView);
        //register click listener
        inflatedView.setOnClickListener(this);
        return ch;
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
        holder.itemView.setTag(position);
        holder.bindCryptogram(username, crypto, playCryptogram);
    }

    @Override
    public int getItemCount() {
        return mCryptograms.size();
    }

}
