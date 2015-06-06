package com.padeoe.autoconnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
/**
 * Created by Kangkang on 2015/6/6.
 */

public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.feedback, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        /* Create the Intent */
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{(String) getResources().getText(R.string.author_email)});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) getResources().getText(R.string.app_name)+(String) getResources().getText(R.string.feedback));
                        getActivity().startActivity(Intent.createChooser(emailIntent, (String) getResources().getText(R.string.send_email)));
                    }
                })
                .setNegativeButton(R.string.like, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AVObject Like = new AVObject("Like");
                        Like.put("hello", "x");
                        Like.saveInBackground();
                        Toast.makeText(getActivity(), (String) getResources().getText(R.string.thankyou), Toast.LENGTH_SHORT).show();
                    }
                });
        TextView versionTextView=(TextView)view.findViewById(R.id.version);
        try{
            String versionName=this.getActivity().getPackageManager()
                    .getPackageInfo(this.getActivity().getPackageName(), 0).versionName;
            versionTextView.setText((String) getResources().getText(R.string.version)+versionName);
        }
        catch(PackageManager.NameNotFoundException e){

        }
        return builder.create();
    }
}

