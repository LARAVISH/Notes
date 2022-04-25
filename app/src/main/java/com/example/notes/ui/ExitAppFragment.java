package com.example.notes.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;

public class ExitAppFragment extends Fragment {

    public ExitAppFragment() {
        super(R.layout.fragment_exit_app);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_exit).setOnClickListener(view1 -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.to_leave_app))
                    .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> ((Activity) requireContext()).finish())
                    .setNegativeButton(getString(R.string.no), (dialogInterface, i)
                            -> Toast.makeText(requireContext(), getString(R.string.no)
                            , Toast.LENGTH_SHORT).show()).show();
        });

    }
}
