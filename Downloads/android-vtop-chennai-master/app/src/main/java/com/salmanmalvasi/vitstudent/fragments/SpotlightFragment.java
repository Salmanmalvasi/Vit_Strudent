package com.salmanmalvasi.vitstudent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.salmanmalvasi.vitstudent.R;
import com.salmanmalvasi.vitstudent.adapters.SpotlightGroupAdapter;
import com.salmanmalvasi.vitstudent.helpers.AppDatabase;
import com.salmanmalvasi.vitstudent.models.Spotlight;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SpotlightFragment extends Fragment {

    private RecyclerView recyclerView;
    private SpotlightGroupAdapter adapter;
    private CompositeDisposable disposables = new CompositeDisposable();

    public SpotlightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_view);
        ImageButton backButton = view.findViewById(R.id.image_button_back);

        // Setup back button
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                // Go back to previous fragment
                requireActivity().getSupportFragmentManager().popBackStack();
            });
        }

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Load data
        loadSpotlightData();

        // Firebase Analytics
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "SpotlightFragment");
        FirebaseAnalytics.getInstance(requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        return view;
    }

    private void loadSpotlightData() {
        disposables.add(
            AppDatabase.getInstance(requireContext()).spotlightDao().get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    spotlights -> {
                        if (spotlights.isEmpty()) {
                            // Show empty state
                            Toast.makeText(requireContext(), "No spotlight announcements available", Toast.LENGTH_SHORT).show();
                        } else {
                            // Create adapter with spotlights
                            adapter = new SpotlightGroupAdapter(spotlights);
                            recyclerView.setAdapter(adapter);
                        }
                    },
                    throwable -> {
                        Toast.makeText(requireContext(), "Failed to load spotlight data", Toast.LENGTH_SHORT).show();
                    }
                )
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }
}
