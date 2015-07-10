// Simple javascript to update current time

// Get the current time element and update it to display the current time by default
var currentTime = document.getElementById("currentTime");
updateTime();

// Set the updateTime method to trigger every second
setInterval(updateTime, 1000);

// Display the current time in a nice format
function updateTime() {
	var dt = new Date();
	var hours = dt.getHours();
	var minutes = dt.getMinutes();
	if (hours < 10) {
		hours = "0" + hours;
	}
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	currentTime.innerHTML = "The current time is: " + hours + ":" + minutes;
}
