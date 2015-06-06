package com.padeoe.autoconnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

/**
 * Created by Kangkang on 2015/6/6.
 */

public class SettingDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.setting_dialog, null);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.complete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("DateFile", 0);
        boolean banAutoRun = sharedPreferences.getBoolean("isBanned", true);
        boolean allowStatics = sharedPreferences.getBoolean("allow_statistics", false);
        Switch autoRunswitch=(Switch)view.findViewById(R.id.autoRunswitch);
        Switch staticsSwitch=(Switch)view.findViewById(R.id.staticsSwitch);
        autoRunswitch.setChecked(banAutoRun);
        staticsSwitch.setChecked(!allowStatics);
        return builder.create();
    }

}

