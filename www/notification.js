window.notification = function(str){
    cordova.exec(function(su){console.log("ok")}, function(err){alert(err)},"NotificationPlugin","noti", [str]);
}
