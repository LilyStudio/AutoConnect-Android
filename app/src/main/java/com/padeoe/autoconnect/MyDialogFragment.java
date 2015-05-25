package com.padeoe.autoconnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;

/**
 * Created by Kangkang on 2015/5/23.
 */
public class MyDialogFragment extends android.support.v4.app.DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.feedback, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /* Create the Intent */
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"padeoe@gmail.com"});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AutoConnect问题反馈");
                        getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        // sign in the user ...
                    }
                })
                .setNegativeButton(R.string.like, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AVObject Like = new AVObject("Like");
                        Like.put("hello", "x");
                        Like.saveInBackground();
                        Toast.makeText(getActivity(), "已发送，谢谢！^_^", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }
}
