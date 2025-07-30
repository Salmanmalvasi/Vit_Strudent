package com.salmanmalvasi.vitstudent.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import com.salmanmalvasi.vitstudent.models.Attendance;

@Dao
public interface AttendanceDao {
    @Insert
    Completable insert(List<Attendance> attendance);

    @Query("DELETE FROM attendance")
    Completable delete();
}
