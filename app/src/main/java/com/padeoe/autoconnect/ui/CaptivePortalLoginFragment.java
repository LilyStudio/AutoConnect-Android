package com.padeoe.autoconnect.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.padeoe.autoconnect.R;

/**
 * Created by padeoe on 2015/10/25.
 */
public class CaptivePortalLoginFragment extends DialogFragment{
    public String SSID;
    public String portalUrl;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.portal_login_dialog, null);
        builder.setView(view)
                .setNegativeButton(R.string.nop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(portalUrl));
                        startActivity(intent);
                    }
                })
                .setTitle(R.string.sign_in_portal)
                .setMessage(SSID);
        return builder.create();
    }
    public void showDialog(FragmentManager fragmentManager){
        this.show(fragmentManager,"CaptivePortalLoginFragment");
    }
    public void setSSID(String SSID){
        this.SSID=SSID;
    }
    public void setUrl(String portalUrl){
        this.portalUrl=portalUrl;
    }
}
