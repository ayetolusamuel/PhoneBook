var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1Db2yUZ31WzqF2aeFs0bX1s6lDlb-8pBdYSQvRXlevMY/edit#gid=0"); //spreadsheet link

var sheet = ss.getSheetByName('records'); // be very careful ... it is the sheet name .. so it should match


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




function add_customer_details(e){

 var serial_number = ""+sheet.getLastRow(); // Item1
 var id_number  = "set"+Math.floor(Math.random() * 90000); // 5 random number concatenate set String
 var date =  new Date();
  date = ( date.getDate() + '/' +(date.getMonth()+1)+ '/'  +  date.getFullYear())
 var customer_name = e.parameter.product_name;
 var customer_location = e.parameter.platform;
 var customer_phone_number = e.parameter.quantity;
 var product_name = e.parameter.store_name;
 var remarks = e.parameter.shipment;

  sheet.appendRow([serial_number,id_number,date,customer_name,customer_location,customer_phone_number,product_name,remarks]);

  return ContentService.createTextOutput("Data Saved Successfully").setMimeType(ContentService.MimeType.TEXT);
}




function delete_customer_details(request){

//var output = ContentService.createTextOutput();
    var id = request.parameter.id_number;
   var flag = 0;


    var lr = sheet.getLastRow();


  for (var i=1;i<=lr;i++) {
         var rid = sheet.getRange(i, 2).getValue();
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



function read_all_customer_details(e){


var records={};
  var rows = sheet.getRange(2,1,sheet.getLastRow() - 1,sheet.getLastColumn()).getValues();
      data = [];

  for (var r = 0, l = rows.length; r < l; r++) {
    var row     = rows[r],
        record  = {};


    record['serial_number'] = row[0];
    record['id_number'] = row[1];
    record['date'] = row[2];
    record['product_name'] = row[3];
   record['platform']=row[4];
    record['quantity']=row[5];
    record[' store_name']=row[6];
    record['shipment'] = row[7];


    data.push(record);

   }
  records.items = data;
  var result=JSON.stringify(records);
  return ContentService.createTextOutput(result).setMimeType(ContentService.MimeType.JSON);


}


function update_customer_details(request){

  var flag=0;

  var id_number = request.parameter.id_number;
  var date_ = request.parameter.date_;
  var product_name = request.parameter.product_name;
  var platform = request.parameter.platform;
  var quantity = request.parameter.quantity;
  var store_name = request.parameter.store_name;
  var shipment = request.parameter.shipment;



  var lr= sheet.getLastRow();
  for(var i=1;i<=lr;i++){
    var rid = sheet.getRange(i, 2).getValue();
    if(rid==id_number){
      sheet.getRange(i,3).setValue(date_);
      sheet.getRange(i,4).setValue(product_name);
      sheet.getRange(i,5).setValue(platform);
      sheet.getRange(i,6).setValue(quantity);
      sheet.getRange(i,7).setValue(store_name);
      sheet.getRange(i,8).setValue(shipment);
      var result= " value for " + id_number+ " is updated successfully.";
      flag=1;
    }
}
  if(flag==0)
    var result="Record  not found for " +id_number;

   result = JSON.stringify({
    "result": result
  });

  return ContentService
  .createTextOutput(request.parameter.callback + "(" + result + ")")
  .setMimeType(ContentService.MimeType.JAVASCRIPT);



}



