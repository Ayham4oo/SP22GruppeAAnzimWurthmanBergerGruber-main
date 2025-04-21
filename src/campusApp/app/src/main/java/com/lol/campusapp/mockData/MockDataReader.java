package com.lol.campusapp.mockData;

import android.database.sqlite.SQLiteDatabase;

import com.lol.campusapp.SQLite.LectureDataConnection;
import com.lol.campusapp.data.Day;
import com.lol.campusapp.data.Lecture;
import com.lol.campusapp.data.LectureInstance;
import com.lol.campusapp.data.Rhythm;
import com.lol.campusapp.utils.ContextHelper;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class MockDataReader {
    private final int COL_INDEX_VERSIONNR = 0;
    private final int COL_INDEX_TITLE = 1;
    private final int COL_INDEX_FORM = 2;
    private final int COL_INDEX_MODULECODE = 5;
    private final int COL_INDEX_SEMESTER = 7;
    private final int COL_INDEX_PARALELGROUP = 9;
    private final int COL_INDEX_DAY = 10;
    private final int COL_INDEX_STARTTIME = 11;
    private final int COL_INDEX_ENDTIME = 12;
    private final int COL_INDEX_RHYTMM = 13;
    private final int COL_INDEX_FIRSTDATE = 14;
    private final int COL_INDEX_LASTDATE = 15;
    private final int COL_INDEX_ROOM = 16;
    private final int COL_INDEX_LECTURER = 19;

    public static MockDataReader instance = new MockDataReader();

    //contains the lectures that are read
    //Key is the Version_NR of the Lecture
    private HashMap<String, Lecture> readLectures = new HashMap<>();
    private final String fileName = "Veranstaltungsliste.xlsx";
    private InputStream inputStream;
    private SQLiteDatabase db;

    private MockDataReader() {
        try {
            this.inputStream = ContextHelper.getContext().getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Reads Mockdata from a .xlsx file to the given Database.
     * The Mockdata consits of Lectures and LectureInsances.
     * @param db is an open SQLiteDatabase.
     */
    public void loadSampleDataToDB(SQLiteDatabase db) {
        //creating workbook instance that refers to .xls file
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }

        //creating a Sheet object to retrieve the object
        XSSFSheet sheet = wb.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();

        //first Row contains the columnnames and no actual data
        rowIterator.next();

        System.out.println("reading sheet ...");
        //will fill the readLectures Hashmap
        rowIterator.forEachRemaining(row -> handleRow(row));

        System.out.println("reading done. \ninserting Data ...");

        insertIntoDatabase(db);

        System.out.println("inserting done");
    }

    /** Inserts some Mockdata into lecture-table and the lectureInstance-table of the given Database
     * @param db: The SQLiteDatabase, the Mockdata is inserted into
     */
    private void insertIntoDatabase(SQLiteDatabase db) {
        LectureDataConnection conn = new LectureDataConnection();
        readLectures.values().forEach((lecture -> conn.insertLectureWithOpenDB(db, lecture)));
    }

    private void handleRow(Row row) {
        String versionNumber = row.getCell(COL_INDEX_VERSIONNR).getStringCellValue();
        if (readLectures.containsKey(versionNumber)) {
            addLectureInstanceToLecture(row);
        } else {
            addNewLecture(row);
        }
    }

    private void addNewLecture(Row row) {
        Lecture lecture = new Lecture();

        String versionNumber = row.getCell(COL_INDEX_VERSIONNR).getStringCellValue();
        lecture.setVersion_NR(versionNumber);

        lecture.setTitle(row.getCell(COL_INDEX_TITLE).getStringCellValue());
        lecture.setModuleCode(row.getCell(COL_INDEX_MODULECODE).getStringCellValue());
        lecture.setSemester(row.getCell(COL_INDEX_SEMESTER).getStringCellValue());
        lecture.setLecturer(row.getCell(COL_INDEX_LECTURER).getStringCellValue());

        readLectures.put(versionNumber, lecture);

        //add Instancedata of this row to the lecture
        addLectureInstanceToLecture(row);
    }

    private void addLectureInstanceToLecture(Row row) {
        String versionNumber = row.getCell(COL_INDEX_VERSIONNR).getStringCellValue();
        Lecture lecture = readLectures.get(versionNumber);

        LectureInstance lectureInstance = new LectureInstance();

        String paralelGroup = row.getCell(COL_INDEX_PARALELGROUP).getStringCellValue();
        int paralelGroupInt = Integer.parseInt(paralelGroup.substring(0, paralelGroup.indexOf(".")));
        lectureInstance.setParallelGroup(paralelGroupInt);

        lectureInstance.setRoom(row.getCell(COL_INDEX_ROOM).getStringCellValue());

        String dayStr = row.getCell(COL_INDEX_DAY).getStringCellValue();
        lectureInstance.setDay( !dayStr.isEmpty() ? Day.valueOf(dayStr) : null);

        lectureInstance.setStartTime(row.getCell(COL_INDEX_STARTTIME).getStringCellValue());
        lectureInstance.setEndTime(row.getCell(COL_INDEX_ENDTIME).getStringCellValue());
        lectureInstance.setFirstDate(row.getCell(COL_INDEX_FIRSTDATE).getStringCellValue());
        lectureInstance.setLastDate(row.getCell(COL_INDEX_LASTDATE).getStringCellValue());
        lectureInstance.setForm(row.getCell(COL_INDEX_FORM).getStringCellValue());
        lectureInstance.setRhythm(Rhythm.valueOfGerman(row.getCell(COL_INDEX_RHYTMM).getStringCellValue()));

        lecture.getLectureInstances().add(lectureInstance);
    }
}
