var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1Db2yUZ31WzqF2aeFs0bX1s6lDlb-8pBdYSQvRXlevMY/edit#gid=0"); //spreadsheet link

var sheet = ss.getSheetByName('details'); // be very careful ... it is the sheet name .. so it should match


function doPost(e){
var action = e.parameter.action;

  if(action =="update_user_details"){
   return update_user_details(e);
  }

if(action == 'add_user_details'){
  return add_user_details(e);

}

  if(action =="delete_user_details"){
   return delete_user_details(e);

    }
}


function doGet(e){

var action = e.parameter.action;

  if(action == 'read_user_details')

  {
    return read_all_user_details(e);

  }


  }




function add_user_details(e){

 var serial_number = ""+sheet.getLastRow(); // Item1
 //var id_number  = "set"+Math.floor(Math.random() * 90000); // 5 random number concatenate set String
 var date_ =  new Date();
 date_ = ( date_.getDate() + '/' +(date_.getMonth()+1)+ '/'  +  date_.getFullYear())
 var full_name = e.parameter.full_name;
 var phone_number = e.parameter.phone_number;
 var email_address = e.parameter.email_address;
 var resident_address = e.parameter.resident_address;
 sheet.appendRow([serial_number,date_,full_name,phone_number,email_address,resident_address]);

  return ContentService
  .createTextOutput("Data Saved Successfully")
  .setMimeType(ContentService.MimeType.TEXT);
}




function delete_user_details(request){
     var id = request.parameter.phone_number;
     var flag = 0;


    var lr = sheet.getLastRow();


  for (var i=1;i<=lr;i++) {
         var rid = sheet.getRange(i, 4).getValue();
    console.log("ID : "+rid);
    console.log(""+ id);
        if (rid == id) {
              sheet.deleteRow(i);
          var result = id+ " value deleted successfully";
            flag = 1;
        }

    }

    if (flag == 0)
        var result = " id not found!";



    result = JSON.stringify({
        "result ": result
    });

    return ContentService
        .createTextOutput(result)
        .setMimeType(ContentService.MimeType.JAVASCRIPT);


}



function read_all_user_details(e){


var records={};
  var rows = sheet.getRange(2,1,sheet.getLastRow() - 1,sheet.getLastColumn()).getValues();
      data = [];

  for (var r = 0, l = rows.length; r < l; r++) {
    var row     = rows[r],
        record  = {};

 record['serial_number'] = row[0];
    record['date'] = row[1];
    record['full_name'] = row[2];
   record['phone_number']=row[3];
    record['email_address']=row[4];
    record['resident_address']=row[5];

    data.push(record);

   }
  records.items = data;
  var result=JSON.stringify(records);
  return ContentService.createTextOutput(result).setMimeType(ContentService.MimeType.JSON);


}


function update_user_details(request){

  var flag=0;
  var results ="";

  var date_ = request.parameter.date_;
  var full_name = request.parameter.full_name;
  var phone_number = request.parameter.phone_number;
  var email_address = request.parameter.email_address;
  var resident_address = request.parameter.resident_address;



  var lr= sheet.getLastRow();
  for(var i=1;i<=lr;i++){
    var rid = sheet.getRange(i, 4).getValue();
    if(rid==phone_number){

      sheet.getRange(i,3).setValue(full_name);
      sheet.getRange(i,4).setValue(phone_number);
      sheet.getRange(i,5).setValue(email_address);
      sheet.getRange(i,6).setValue(resident_address);
      results = " value for " + phone_number+ " is updated successfully.";
      flag=1;
    }
}
  if(flag==0){
     var result="Record  not found for " +phone_number;

  }


   results = JSON.stringify({
    "result": results
  });

  return ContentService
  .createTextOutput(results)
  .setMimeType(ContentService.MimeType.JAVASCRIPT);



}



