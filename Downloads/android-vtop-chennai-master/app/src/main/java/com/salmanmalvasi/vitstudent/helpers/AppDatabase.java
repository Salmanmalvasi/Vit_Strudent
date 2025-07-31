package com.salmanmalvasi.vitstudent.helpers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.salmanmalvasi.vitstudent.interfaces.AttendanceDao;
import com.salmanmalvasi.vitstudent.interfaces.CoursesDao;
import com.salmanmalvasi.vitstudent.interfaces.ExamsDao;
import com.salmanmalvasi.vitstudent.interfaces.MarksDao;
import com.salmanmalvasi.vitstudent.interfaces.ReceiptsDao;
import com.salmanmalvasi.vitstudent.interfaces.SpotlightDao;
import com.salmanmalvasi.vitstudent.interfaces.StaffDao;
import com.salmanmalvasi.vitstudent.interfaces.TimetableDao;
import com.salmanmalvasi.vitstudent.models.Attendance;
import com.salmanmalvasi.vitstudent.models.Course;
import com.salmanmalvasi.vitstudent.models.CumulativeMark;
import com.salmanmalvasi.vitstudent.models.Exam;
import com.salmanmalvasi.vitstudent.models.Mark;
import com.salmanmalvasi.vitstudent.models.Receipt;
import com.salmanmalvasi.vitstudent.models.Slot;
import com.salmanmalvasi.vitstudent.models.Spotlight;
import com.salmanmalvasi.vitstudent.models.Staff;
import com.salmanmalvasi.vitstudent.models.Timetable;

@Database(
        entities = {
                Attendance.class,
                Course.class,
                CumulativeMark.class,
                Exam.class,
                Mark.class,
                Receipt.class,
                Slot.class,
                Spotlight.class,
                Staff.class,
                Timetable.class
        },
        version = 4,
        autoMigrations = {
                @AutoMigration(from = 1, to = 2),
                @AutoMigration(from = 3, to = 4),
        }
)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "vit_student")
                    .addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public static synchronized void deleteDatabase(Context context) {
        if (instance != null) {
            instance.close();
        }

        instance = null;
        context.deleteDatabase("vit_student");
        context.deleteDatabase("vtop"); // Delete the deprecated database (used till < v4.0)
    }



    public abstract AttendanceDao attendanceDao();

    public abstract CoursesDao coursesDao();

    public abstract ExamsDao examsDao();

    public abstract MarksDao marksDao();

    public abstract ReceiptsDao receiptsDao();

    public abstract SpotlightDao spotlightDao();

    public abstract StaffDao staffDao();

    public abstract TimetableDao timetableDao();

    // Manual Migrations
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE receipts RENAME TO receipts_old");
            database.execSQL("CREATE TABLE receipts (number INTEGER NOT NULL PRIMARY KEY, amount REAL, date INTEGER)");
            database.execSQL("INSERT INTO receipts (number, amount) SELECT number, amount FROM receipts_old");
            database.execSQL("UPDATE receipts SET date = 0");
            database.execSQL("DROP TABLE receipts_old");
        }
    };
}
