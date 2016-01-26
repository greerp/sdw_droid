//****************************************************************************
//
// Date		11/16/2015 7:07:46 PM
// Author	Paul Greer
// 
// Copyright © RedPixie Ltd 2010-2014. All rights reserved.
// The software and associated documentation supplied hereunder are the 
// proprietary information of RedPixie Ltd, 145-157 St John Street, 
// London, EC1V 4PY, United Kingdom and are supplied subject to licence terms.

using System;
using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Security;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Office.Interop.Excel;

namespace gendataset
{
    enum EventsEnum {
        ID = 1,
        Date = 2,
        CourseID = 3
    }

    public class Events:List<Event>
    {
        private List<Event> _eventList;

        public Events(Worksheet sheet) {
            var range = sheet.Range["events"];
            var rows = range.Rows.Count;
            var cells = range.Cells.Count;

            _eventList = new List<Event>();

            for (int i = 1; i < rows; i++) {

                dynamic idObject = ((Range)range.Cells[i, 1]).Value2;
                dynamic dateObject = ((Range)range.Cells[i, 2]).Value2;
                dynamic courseIdObject = ((Range)range.Cells[i, 3]).Value2;

                



                _eventList.Add(new Event() {
                    CourseId = ((Range)range.Cells[i, 3]).Value2,
                    id = ((Range)range.Cells[i, 1]).Value2,
                    Date = ((Range)range.Cells[i, 2]).Value2,
                });

                var cellValue = ((Range)range.Cells[i,2]).Text;
                Console.WriteLine("Value of cell is " + cellValue);
            }

        }
    }

    public class Event
    {
        public int id { get; set; }
        public DateTime Date { get; set; }
        public int CourseId { get; set; }
    }
}