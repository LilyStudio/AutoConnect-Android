package com.padeoe.autoconnect.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import com.padeoe.autoconnect.R;

/**
 * Created by Kangkang on 2015/6/6.
 */

/**
 * 应用设置界面
 * 包括禁止开机自启，禁止数据统计
 */
public class SettingDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_dialog, null);
        builder.setView(view)
                .setPositiveButton(R.string.complete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("DataFile", 0);
        boolean banAutoRun = sharedPreferences.getBoolean("isBanned", false);
        boolean allowStatics = sharedPreferences.getBoolean("allow_statistics", false);
        Switch autoRunswitch = (Switch) view.findViewById(R.id.autoRunswitch);
        Switch staticsSwitch = (Switch) view.findViewById(R.id.staticsSwitch);
        autoRunswitch.setChecked(banAutoRun);
        staticsSwitch.setChecked(!allowStatics);
        return builder.create();
    }

}

