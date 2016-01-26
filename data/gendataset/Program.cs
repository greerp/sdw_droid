using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Office.Interop.Excel;

namespace gendataset
{
    class Program
    {

        static void Main(string[] args)
        {
            var filepath = @"C:\Users\greepau\AndroidStudioProjects\sdw_droid\data\seeddata\GenerateTTData.xlsm";

            Application app = new Application();
            // Optional, but recommended if the user shouldn't see Excel.
            app.Visible = false;
            app.ScreenUpdating = false;
            // AddToMru parameter is optional, but recommended in automation scenarios.

            var workbook = app.Workbooks.Open(filepath, AddToMru: false);

            Worksheet entriesWorksheet = workbook.Worksheets["entries"];
            Worksheet handicapsWorksheet = workbook.Worksheets["handicaps"];


            var events = new Events(workbook.Worksheets["events"]);
            foreach (var evt in events) {
            }


        }
    }
}
