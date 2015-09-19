package com.padeoe.autoconnect.ui;

import android.app.Activity;
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
import com.padeoe.autoconnect.R;

/**
 * Created by padeoe on 2015/9/18.
 */
public class ExplainPermissionFragment extends DialogFragment {
    ExplainPermissionListener mListener;
    public interface ExplainPermissionListener {
        public void explainOverClick(DialogFragment dialog);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.permission_dialog, null);
        builder.setView(view)
                .setPositiveButton(R.string.understand_permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.explainOverClick(ExplainPermissionFragment.this);
                    }
                })
        .setTitle(R.string.permission_tips_title)
        .setMessage(R.string.explain_storage_permission);
        return builder.create();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ExplainPermissionListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement UpdateListener");
        }
    }
}
