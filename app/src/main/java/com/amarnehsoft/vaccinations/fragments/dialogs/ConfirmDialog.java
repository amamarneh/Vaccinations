package com.amarnehsoft.vaccinations.fragments.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.amarnehsoft.vaccinations.R;

import java.util.UUID;

/**
 * Created by jcc on 12/3/2017.
 */

public abstract class ConfirmDialog extends AlertDialog.Builder{

    public ConfirmDialog(@NonNull final Context context) {
        super(context);

        setTitle(title())
                .setMessage(msg())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onPositive();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    public abstract String title();
    public abstract String msg();
    public abstract void onPositive();
}
