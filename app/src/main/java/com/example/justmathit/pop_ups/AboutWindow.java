package com.example.justmathit.pop_ups;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AboutWindow extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Just Math It! Android Application.")
                .setMessage("University of Piraeus." +
                        "\nApp created & developed by George Asimakopoulos." +
                        "\n\n© Copyright 2023.")
                .setPositiveButton("OK", (dialogInterface, i) -> {});

        return builder.create();
    }
}
