package com.salmanmalvasi.vitstudent.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.salmanmalvasi.vitstudent.helpers.AppDatabase;
import com.salmanmalvasi.vitstudent.helpers.SettingsRepository;
import com.salmanmalvasi.vitstudent.interfaces.ExamsDao;
import com.salmanmalvasi.vitstudent.interfaces.TimetableDao;
import com.salmanmalvasi.vitstudent.models.Exam;
import com.salmanmalvasi.vitstudent.models.Timetable;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            return;
        }

        if (!SettingsRepository.isSignedIn(context)) {
            return;
        }

        SettingsRepository.clearNotificationPendingIntents(context);

        AppDatabase appDatabase = AppDatabase.getInstance(context);
        TimetableDao timetableDao = appDatabase.timetableDao();
        ExamsDao examsDao = appDatabase.examsDao();

        timetableDao
                .getTimetable()
                .subscribeOn(Schedulers.single())
                .subscribe(new SingleObserver<List<Timetable>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<Timetable> timetable) {
                        for (int i = 0; i < timetable.size(); ++i) {
                            try {
                                SettingsRepository.setTimetableNotifications(context, timetable.get(i));
                            } catch (Exception ignored) {
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });

        examsDao
                .getExams()
                .subscribeOn(Schedulers.single())
                .subscribe(new SingleObserver<List<Exam>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onSuccess(@NonNull List<Exam> exams) {
                        SettingsRepository.setExamNotifications(context, exams);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }
}
