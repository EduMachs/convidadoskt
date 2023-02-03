package com.example.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.constants.DatabaseConstants
import com.example.convidados.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDatabase = GuestDatabase(context);

    // Singleton
    companion object {

        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }

        fun save() {}
        fun update() {}
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDatabase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues();
            values.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)

            db.insert(DatabaseConstants.GUEST.TABLE_NAME, null, values)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDatabase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues();
            values.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, presence)
            values.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)

            val selection = "${DatabaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DatabaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(guestId: Int): Boolean {
        return try {
            val db = guestDatabase.writableDatabase

            val selection = "${DatabaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guestId.toString())
            db.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDatabase.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.ID,
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                projection,
                null, null, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))

                }
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            return list
        }
        return list;
    }

    fun get(guestId: Int): GuestModel? {

        var guest: GuestModel? = null

        try {
            val db = guestDatabase.readableDatabase

            val projection = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.ID,
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = "${DatabaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guestId.toString())

            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                projection,
                selection, args, null, null, null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE))

                    guest = GuestModel(guestId, name, presence == 1)

                }
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            return guest
        }
        return guest;
    }

    fun getPresent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))

                }
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            return list
        }
        return list;
    }

    fun getAbsent(): List<GuestModel> {

        val list = mutableListOf<GuestModel>()

        try {
            val db = guestDatabase.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE))

                    list.add(GuestModel(id, name, presence == 1))

                }
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            return list
        }
        return list;
    }



}