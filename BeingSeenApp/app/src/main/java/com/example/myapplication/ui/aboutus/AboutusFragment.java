package com.example.myapplication.ui.aboutus;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.databinding.AboutusBinding;
import com.example.myapplication.databinding.AboutusfragmentBinding;
import com.example.myapplication.databinding.FragmentNotificationsBinding;

public class AboutusFragment extends Fragment {

    private AboutUsViewModel aboutUsViewModel;
    private AboutusfragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutUsViewModel =
                new ViewModelProvider(this).get(com.example.myapplication.ui.aboutus.AboutUsViewModel.class);

        binding = AboutusfragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView =binding.textAboutus;
        aboutUsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}