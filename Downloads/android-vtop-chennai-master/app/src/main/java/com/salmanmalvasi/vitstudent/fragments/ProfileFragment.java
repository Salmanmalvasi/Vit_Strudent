package com.salmanmalvasi.vitstudent.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.salmanmalvasi.vitstudent.R;
import com.salmanmalvasi.vitstudent.activities.LoginActivity;
import com.salmanmalvasi.vitstudent.adapters.AnnouncementItemAdapter;
import com.salmanmalvasi.vitstudent.adapters.ProfileGroupAdapter;
import com.salmanmalvasi.vitstudent.helpers.SettingsRepository;

public class ProfileFragment extends Fragment {
    private ActivityResultLauncher<Intent> themeSelectorLauncher;

    /*
        User Related Profile Items
     */
    private final ItemData[] personalProfileItems = {
            new ItemData(
                    R.drawable.ic_courses,
                    R.string.courses,
                    context -> SettingsRepository.openViewPagerFragment(
                            (FragmentActivity) context,
                            R.string.courses,
                            ViewPagerFragment.TYPE_COURSES
                    ),
                    null
            ),
            new ItemData(
                    R.drawable.ic_exams,
                    R.string.exam_schedule,
                    context -> SettingsRepository.openViewPagerFragment(
                            (FragmentActivity) context,
                            R.string.exam_schedule,
                            ViewPagerFragment.TYPE_EXAMS
                    ),
                    null
            ),
            new ItemData(
                    R.drawable.ic_receipts,
                    R.string.receipts,
                    context -> SettingsRepository.openRecyclerViewFragment(
                            (FragmentActivity) context,
                            R.string.receipts,
                            RecyclerViewFragment.TYPE_RECEIPTS
                    ),
                    null
            ),
            new ItemData(
                    R.drawable.ic_staff,
                    R.string.staff,
                    context -> SettingsRepository.openViewPagerFragment(
                            (FragmentActivity) context,
                            R.string.staff,
                            ViewPagerFragment.TYPE_STAFF
                    ),
                    null
            ),
            new ItemData(
                    R.drawable.ic_sync,
                    R.string.sync_data,
                    context -> getParentFragmentManager().setFragmentResult("syncData", new Bundle()),
                    profileItem -> {
                        ProgressBar progressBar = new ProgressBar(profileItem.getContext());
                        RelativeLayout extraContainer = profileItem.findViewById(R.id.relative_layout_extra_container);
                        extraContainer.addView(progressBar);

                        getParentFragmentManager().setFragmentResultListener("syncDataState", this, (requestKey, result) -> {
                            if (result.getBoolean("isLoading")) {
                                profileItem.setEnabled(false);
                                extraContainer.setVisibility(View.VISIBLE);
                            } else {
                                profileItem.setEnabled(true);
                                extraContainer.setVisibility(View.GONE);
                            }
                        });
                    }
            )
    };

    /*
        Application Related Profile Items
     */
    private final ItemData[] applicationProfileItems = {
            new ItemData(
                    R.drawable.ic_appearance,
                    R.string.appearance,
                    context -> {
                        String[] themes = {
                                context.getString(R.string.light),
                                context.getString(R.string.dark),
                                context.getString(R.string.system)
                        };

                        SharedPreferences sharedPreferences = SettingsRepository.getSharedPreferences(context);

                        int checkedItem = 2;
                        String theme = sharedPreferences.getString("appearance", "system");

                        if (theme.equals("light")) {
                            checkedItem = 0;
                        } else if (theme.equals("dark")) {
                            checkedItem = 1;
                        }

                        View appearanceView = getLayoutInflater().inflate(R.layout.layout_dialog_apperance, null);
                        MaterialSwitch amoledSwitch = appearanceView.findViewById(R.id.switch_amoled_mode);
                        amoledSwitch.setChecked(sharedPreferences.getBoolean("amoledMode", false));
                        amoledSwitch.setOnCheckedChangeListener((compoundButton, isAmoledModeEnabled) -> {
                            sharedPreferences.edit().putBoolean("amoledMode", isAmoledModeEnabled).apply();
                            // Disable dynamic colors to use our custom black and white theme
                            // Bundle applyDynamicColors = new Bundle();
                            // applyDynamicColors.putBoolean("amoledMode", isAmoledModeEnabled);
                            // getParentFragmentManager().setFragmentResult("applyDynamicColors", applyDynamicColors);
                        });

                        new MaterialAlertDialogBuilder(context)
                                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                                .setSingleChoiceItems(themes, checkedItem, (dialogInterface, i) -> {
                                    if (i == 0) {
                                        sharedPreferences.edit().putString("appearance", "light").apply();
                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                    } else if (i == 1) {
                                        sharedPreferences.edit().putString("appearance", "dark").apply();
                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                    } else {
                                        sharedPreferences.edit().remove("appearance").apply();
                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                    }

                                    dialogInterface.dismiss();
                                })
                                .setView(appearanceView)
                                .setTitle(R.string.appearance)
                                .show();
                    },
                    null
            ),
            new ItemData(
                    R.drawable.ic_notifications,
                    R.string.notifications,
                    context -> {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                        intent.putExtra("app_package", context.getPackageName());
                        intent.putExtra("app_uid", context.getApplicationInfo().uid);
                        intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());

                        context.startActivity(intent);
                    },
                    null
            ),
            new ItemData(
                    R.drawable.ic_appearance,
                    "Theme",
                    "Choose your preferred color theme",
                    context -> {
                        Intent intent = new Intent(context, com.salmanmalvasi.vitstudent.activities.ThemeSelectorActivity.class);
                        themeSelectorLauncher.launch(intent);
                    }
            ),


            new ItemData(
                    R.drawable.ic_privacy,
                    R.string.privacy_policy,
                    context -> {
                        Intent intent = new Intent(context, com.salmanmalvasi.vitstudent.activities.PrivacyPolicyActivity.class);
                        context.startActivity(intent);
                    },
                    null
            ),
            new ItemData(
                    R.drawable.ic_link,
                    "Open Source",
                    "View source code on GitHub",
                    context -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(android.net.Uri.parse("https://github.com/Salmanmalvasi/Vit_Strudent"));
                        context.startActivity(intent);
                    }
            ),
            new ItemData(
                    R.drawable.ic_sign_out,
                    R.string.sign_out,
                    context -> {
                        AlertDialog signOutDialog = new MaterialAlertDialogBuilder(context)
                                .setMessage(R.string.sign_out_text)
                                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.cancel())
                                .setPositiveButton(R.string.sign_out, (dialogInterface, i) -> {
                                    SettingsRepository.signOut(context);
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                    ((Activity) context).finish();
                                })
                                .setTitle(R.string.sign_out)
                                .create();

                        signOutDialog.show();
                    },
                    null)
    };

    private final ItemData[][] profileItems = {
            personalProfileItems,
            applicationProfileItems
    };

    private final int[] profileGroups = {
            R.string.personal,
            R.string.application
    };



    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        // Firebase Analytics Logging
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ProfileFragment");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Profile");
        FirebaseAnalytics.getInstance(this.requireContext()).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        getParentFragmentManager().setFragmentResultListener("launchSubFragment", this, (requestKey, result) -> {
            String subFragment = result.getString("subFragment");

            if (subFragment.equals("ExamSchedule")) {
                SettingsRepository.openViewPagerFragment(
                        requireActivity(),
                        R.string.exam_schedule,
                        ViewPagerFragment.TYPE_EXAMS
                );
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize ActivityResultLauncher for theme selector
        themeSelectorLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    if (result.getData().getBooleanExtra("theme_changed", false)) {
                        // Theme was changed, recreate the activity
                        requireActivity().recreate();
                    }
                }
            }
        );

        View profileFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        View appBarLayout = profileFragment.findViewById(R.id.app_bar);
        View profileView = profileFragment.findViewById(R.id.nested_scroll_view_profile);

        getParentFragmentManager().setFragmentResultListener("customInsets", this, (requestKey, result) -> {
            int systemWindowInsetLeft = result.getInt("systemWindowInsetLeft");
            int systemWindowInsetTop = result.getInt("systemWindowInsetTop");
            int systemWindowInsetRight = result.getInt("systemWindowInsetRight");
            int bottomNavigationHeight = result.getInt("bottomNavigationHeight");
            float pixelDensity = getResources().getDisplayMetrics().density;

            appBarLayout.setPadding(
                    systemWindowInsetLeft,
                    systemWindowInsetTop,
                    systemWindowInsetRight,
                    0
            );

            profileView.setPaddingRelative(
                    systemWindowInsetLeft,
                    0,
                    systemWindowInsetRight,
                    (int) (bottomNavigationHeight + 20 * pixelDensity)
            );

            // Only one listener can be added per requestKey, so we create a duplicate
            getParentFragmentManager().setFragmentResult("customInsets2", result);
        });

        RecyclerView profileGroups = profileFragment.findViewById(R.id.recycler_view_profile_groups);

        profileGroups.setAdapter(new ProfileGroupAdapter(this.profileGroups, this.profileItems));

        return profileFragment;
    }

    public static class ItemData {
        public final int iconId, titleId;
        public final String title, description;
        public final OnClickListener onClickListener;
        public final OnInitListener onInitListener;

        public ItemData(@DrawableRes int iconId, @StringRes int titleId, OnClickListener onClickListener, OnInitListener onInitListener) {
            this.iconId = iconId;
            this.titleId = titleId;
            this.onClickListener = onClickListener;
            this.onInitListener = onInitListener;

            this.title = null;
            this.description = null;
        }

        public ItemData(@DrawableRes int iconId, String title, String description, OnClickListener onClickListener) {
            this.iconId = iconId;
            this.title = title;
            this.description = description;
            this.onClickListener = onClickListener;

            this.titleId = 0;
            this.onInitListener = null;
        }

        public interface OnClickListener {
            void onClick(Context context);
        }

        public interface OnInitListener {
            void onInit(View profileItem);
        }
    }
}
