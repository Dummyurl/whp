/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.samplestickerapp;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static final class MessageDialogFragment extends DialogFragment {
        private static final String ARG_TITLE_ID = "title_id";
        private static final String ARG_MESSAGE = "message";

        public static DialogFragment newInstance(@StringRes int titleId, String message) {
            DialogFragment fragment = new MessageDialogFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_TITLE_ID, titleId);
            arguments.putString(ARG_MESSAGE, message);
            fragment.setArguments(arguments);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            @StringRes final int title = getArguments().getInt(ARG_TITLE_ID);
            String message = getArguments().getString(ARG_MESSAGE);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dismiss());

            if (title != 0) {
                dialogBuilder.setTitle(title);
            }
            return dialogBuilder.create();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.rateapp) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.christmasstickers.forall")));
            Toast.makeText(this, "Do support us by rating our app", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.shareapp) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Share awesome Malayalam WhatsApp Stickers via *Malayalam Stickers* app.\n \nDownload now: https://play.google.com/store/apps/details?id=com.christmasstickers.forall";
            String shareSubject = "Malayalam Stickers Android App";
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            startActivity(Intent.createChooser(sharingIntent,"Share with friends"));

        } else if (item.getItemId() == R.id.share_button) {
            try {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey, I'm using *Christmas Stickers* for sending awesome Stickers\n \nDownload now: https://play.google.com/store/apps/details?id=com.christmasstickers.forall";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.setPackage("com.whatsapp");
                startActivity(sharingIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Intent sharingIntent1 = new Intent(Intent.ACTION_SEND);
                sharingIntent1.setType("text/plain");
                String shareBody = "Hey, I'm using *Christmas Stickers* for sending awesome Stickers\n \nDownload now: https://play.google.com/store/apps/details?id=com.christmasstickers.forall";
                String shareSubject = "Christmas Stickers Android App";
                sharingIntent1.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent1.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent1,"Share with friends"));
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
