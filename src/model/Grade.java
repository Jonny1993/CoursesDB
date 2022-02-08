/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Author: Vagif
package model;

import java.util.ArrayList;

/**
 *
 * @author vagif
 */
        public class Grade
        {
            ArrayList<ArrayList<Course>> rows;
            private int students, year;
            public Grade(int students, int year)
            {
                this.students = students;
                rows = new ArrayList<>();
                this.year = year;
            }

            public ArrayList<ArrayList<Course>> getRows() {
                return rows;
            }
            
            public void getSize()
            {
                
            }

            public void setRows(ArrayList<ArrayList<Course>> rows) {
                this.rows = rows;
            }

            public int getStudents() {
                return students;
            }

            public void setStudents(int students) {
                this.students = students;
            }
            
            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
            
            public void addRow(ArrayList<Course> courses)
            {
                rows.add(courses);
            }           
        }
