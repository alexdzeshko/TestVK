package com.github.pavelkv96.testvk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pavelkv96.libs.api.Dialog;

import java.util.ArrayList;

/**
 * Created by Pavel on 23.11.2017.
 */

public class DialogsAdapter extends RecyclerView.Adapter<DialogsAdapter.DialogsViewHolder> {
    Context mContext;
    ArrayList<Dialog> mDialogArrayList;

    public DialogsAdapter(Context pContext, ArrayList<Dialog> pDialogArrayList) {
        this.mContext = pContext;
        this.mDialogArrayList = pDialogArrayList;
    }

    @Override
    public DialogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View row = inflater.inflate(R.layout.dialog_row,parent,false);
        DialogsViewHolder dialogsViewHolder = new DialogsViewHolder(row);

        return dialogsViewHolder;
    }

    @Override
    public void onBindViewHolder(DialogsViewHolder holder, int position) {
        holder.unread_message_text_view.setText(String.valueOf(mDialogArrayList.get(position).getUnRead()));
        if (String.valueOf(mDialogArrayList.get(position).getMessageObject().mTitle).equals(""))
        {holder.friend_name_text_view.setText(String.valueOf(mDialogArrayList.get(position).getMessageObject().mUserId));}
        else{holder.friend_name_text_view.setText(String.valueOf(mDialogArrayList.get(position).getMessageObject().mTitle));}
        holder.body_message_text_view.setText(String.valueOf(mDialogArrayList.get(position).getMessageObject().mBody));
        holder.time_last_message_text_view.setText(String.valueOf(mDialogArrayList.get(position).getMessageObject().mDate));
        //Picasso.with(mContext).load(mDialogArrayList.get(position).getMessageObject()).into(holder.friend_photo_image_view);
    }

    @Override
    public int getItemCount() {
        return mDialogArrayList.size();
    }

    static class DialogsViewHolder extends RecyclerView.ViewHolder{
        TextView friend_name_text_view;
        TextView time_last_message_text_view;
        TextView body_message_text_view;
        TextView unread_message_text_view;
        ImageView friend_photo_image_view;

        DialogsViewHolder(View itemView) {
            super(itemView);
            friend_name_text_view = itemView.findViewById(R.id.friend_name_text_view);
            time_last_message_text_view = itemView.findViewById(R.id.time_last_message_text_view);
            body_message_text_view = itemView.findViewById(R.id.body_message_text_view);
            unread_message_text_view = itemView.findViewById(R.id.unread_message_text_view);
            friend_photo_image_view = itemView.findViewById(R.id.friend_photo_image_view);
        }
    }
}
