package com.example.MajorProject;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ControllerClass {
    @PostMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Test Successful");
    }

    @PostMapping("/upload-data")
    public ResponseEntity<List<PairString>> uploadData(@RequestParam("students") MultipartFile students, @RequestParam("teachers") MultipartFile teachers, @RequestParam("seating") MultipartFile seating) throws IOException, CsvException {

        Reader studentReader = new InputStreamReader(students.getInputStream());
        CSVReader studentCsvReader = new CSVReaderBuilder(studentReader).build();
        List<String[]> studentRows = studentCsvReader.readAll();

        Reader teacherReader = new InputStreamReader(teachers.getInputStream());
        CSVReader teacherCsvReader = new CSVReaderBuilder(teacherReader).build();
        List<String[]> teacherRows = teacherCsvReader.readAll();

        Reader seatingReader = new InputStreamReader(seating.getInputStream());
        CSVReader seatingCsvReader = new CSVReaderBuilder(seatingReader).build();
        List<String[]> seatingRows = seatingCsvReader.readAll();

//        //check rooms rows = teacher rows
//        if(teacherRows.size() != seatingRows.size()){
//            return ResponseEntity.badRequest().body("Number of rooms and teachers do not match");
//        }
//
//        //check students rows = seating rows * capacity
//        int totalCapacity = 0;
//        for(String[] row : seatingRows) {
//            totalCapacity += Integer.parseInt(row[1]);
//        }
//        if(totalCapacity < studentRows.size()){
//            return ResponseEntity.badRequest().body("Number of students exceed capacity");
//        }

        // Save the data to your database
        ArrayList<PairString> teachersResult = new ArrayList<>();
        for(int i=0;i<teacherRows.size();i++){
            PairString pairString = new PairString();
            pairString.str1 = teacherRows.get(i)[0];
            pairString.str2 = seatingRows.get(i)[0];
            teachersResult.add(pairString);
        }

        return ResponseEntity.ok(teachersResult);
    }
}
