package com.padeoe.njunet.settings;

import android.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.padeoe.njunet.R;

/**
 * Created by padeoe on 2016/6/1.
 */
public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_about, null);
        builder.setView(view)
                .setPositiveButton(R.string.feedback, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //打开用户邮件App
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{(String) getResources().getText(R.string.author_email)});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getText(R.string.app_name) + (String) getResources().getText(R.string.feedback));
                        getActivity().startActivity(Intent.createChooser(emailIntent, getResources().getText(R.string.send_email)));
                    }
                });
        TextView versionTextView = (TextView) view.findViewById(R.id.version);
        try {
            String versionName = this.getActivity().getPackageManager()
                    .getPackageInfo(this.getActivity().getPackageName(), 0).versionName;
            versionTextView.setText(getResources().getText(R.string.version) + versionName);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return builder.create();
    }
}
