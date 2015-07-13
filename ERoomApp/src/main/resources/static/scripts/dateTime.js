// Simple javascript to update current time

// Get the current time element and update it to display the current time by default
var currentTime = document.getElementById("currentTime");
var currentDate = document.getElementById("currentDate");
updateTime();
updateDate();

// Set the updateTime method to trigger every second
setInterval(updateTime, 1000);

// Display the current time in a nice format
function updateTime() {
	var dt = new Date();
	var hours = dt.getHours();
	var minutes = dt.getMinutes();
	var seconds = dt.getSeconds();
	if (hours < 10) {
		hours = "0" + hours;
	}
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	if (seconds < 10) {
		seconds = "0" + seconds;
	}
	currentTime.innerHTML = hours + ":" + minutes + ":" + seconds;
}

function updateDate() {
	var today = new Date();
	var montharray=new Array("January","February","March","April","May","June",
            "July","August","September","October","November","December")
	var strDate = 'd-m-Y'
	  .replace('Y', today.getFullYear())
	  .replace('m', montharray[today.getMonth()])
	  .replace('d', today.getDate());
	
	
	currentDate.innerHTML = strDate;
}

$(document).ready(function() {  
	 setInterval(function() {
	    $('.box').animate( { color: '#40e0d0' }, 300)
	    .animate( { color: '#049cdb' }, 300); 
	    }, 1000);
	 });

