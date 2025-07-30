package com.salmanmalvasi.vitstudent.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import com.salmanmalvasi.vitstudent.models.Receipt;

@Dao
public interface ReceiptsDao {
    @Insert
    Completable insert(List<Receipt> receipts);

    @Query("DELETE FROM receipts")
    Completable deleteAll();

    @Query("SELECT * FROM receipts ORDER BY date DESC, number DESC")
    Single<List<Receipt>> getReceipts();
}
