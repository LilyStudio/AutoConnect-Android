package com.padeoe.autoconnect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;

//import com.avos.avoscloud.AVObject;

/**
 * Created by Kangkang on 2015/6/6.
 */

/**
 * 显示App相关信息的fragment
 * 包含应用名称，版本号，问题反馈
 */
public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        builder.setView(view)
                .setPositiveButton(R.string.feedback, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //打开用户邮件App
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("plain/text");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{(String) getResources().getText(R.string.author_email)});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, (String) getResources().getText(R.string.app_name) + (String) getResources().getText(R.string.feedback));
                        getActivity().startActivity(Intent.createChooser(emailIntent, (String) getResources().getText(R.string.send_email)));
                    }
                })
                .setNegativeButton(R.string.like, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //通过LeanCloud服务发送用户的点赞
                        AVObject Like = new AVObject("Like");
                        Like.put("hello", "x");
                        Like.saveInBackground();
                        Toast.makeText(getActivity(), (String) getResources().getText(R.string.thankyou), Toast.LENGTH_SHORT).show();
                    }
                });
        TextView versionTextView = (TextView) view.findViewById(R.id.version);
        try {
            String versionName = this.getActivity().getPackageManager()
                    .getPackageInfo(this.getActivity().getPackageName(), 0).versionName;
            versionTextView.setText((String) getResources().getText(R.string.version) + versionName);
        } catch (PackageManager.NameNotFoundException e) {

        }
        return builder.create();
    }
}

