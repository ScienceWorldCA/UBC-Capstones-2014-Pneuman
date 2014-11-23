var MAX_PRESSURE = 50;

setTimeout(function(){
   window.parent.document.getElementById("variable_refresh_page").src = window.parent.document.getElementById("variable_refresh_page").src;
}, 20);

function get_pressure_percentage(pressure){
	var pressure_percentage = pressure / MAX_PRESSURE * 100;
	if (pressure_percentage > 100){return 100 }
	else if (pressure_percentage < 0){return 0 }
	else {return pressure_percentage}
}

function get_hsl_color(pressure_percentage){
	return (1.2 - pressure_percentage / 100) * 120;
}

function adjust_pressure_bars(pressure1, pressure2, pressure3, pressure4){
	var pressure_percentage1 = get_pressure_percentage(pressure1);
	var pressure_percentage2 = get_pressure_percentage(pressure2);
	var pressure_percentage3 = get_pressure_percentage(pressure3);
	var pressure_percentage4 = get_pressure_percentage(pressure4);
	window.parent.document.getElementById("pressure_fill_1").style.height = pressure_percentage1 + "%";
	window.parent.document.getElementById("pressure_fill_2").style.height = pressure_percentage2 + "%";
	window.parent.document.getElementById("pressure_fill_3").style.height = pressure_percentage3 + "%";
	window.parent.document.getElementById("pressure_fill_4").style.height = pressure_percentage4 + "%";
}

function adjust_pressure_bar_color(pressure1, pressure2, pressure3, pressure4){
	var pressure_percentage1 = get_pressure_percentage(pressure1);
	var pressure_percentage2 = get_pressure_percentage(pressure2);
	var pressure_percentage3 = get_pressure_percentage(pressure3);
	var pressure_percentage4 = get_pressure_percentage(pressure4);
	var color1 = get_hsl_color(pressure_percentage1);
	var color2 = get_hsl_color(pressure_percentage2);
	var color3 = get_hsl_color(pressure_percentage3);
	var color4 = get_hsl_color(pressure_percentage4);
	window.parent.document.getElementById("pressure_fill_1").style.backgroundColor = "hsl(" + color1 + ", 100%, 50%)";
	window.parent.document.getElementById("pressure_fill_2").style.backgroundColor = "hsl(" + color2 + ", 100%, 50%)";
	window.parent.document.getElementById("pressure_fill_3").style.backgroundColor = "hsl(" + color3 + ", 100%, 50%)";
	window.parent.document.getElementById("pressure_fill_4").style.backgroundColor = "hsl(" + color4 + ", 100%, 50%)";
}

function set_peressure_labels(pressure1, pressure2, pressure3, pressure4){
	window.parent.document.getElementById("piston_label_1").innerHTML = Math.floor(pressure1) + " psi"
	window.parent.document.getElementById("piston_label_2").innerHTML = Math.floor(pressure2) + " psi"
	window.parent.document.getElementById("piston_label_3").innerHTML = Math.floor(pressure3) + " psi"
	window.parent.document.getElementById("piston_label_4").innerHTML = Math.floor(pressure4) + " psi"
}

function refresh_pressure_displays(pressure1, pressure2, pressure3, pressure4){
	adjust_pressure_bar_color(pressure1, pressure2, pressure3, pressure4);
	adjust_pressure_bars(pressure1, pressure2, pressure3, pressure4);
	set_peressure_labels(pressure1, pressure2, pressure3, pressure4);
}

function pressure_adjustment_test_driver(){
	var pressure1 = Math.random() * 50;
	var pressure2 = Math.random() * 50;
	var pressure3 = Math.random() * 50;
	var pressure4 = Math.random() * 50;
	refresh_pressure_displays(pressure1, pressure2, pressure3, pressure4);
}

function rotate(degrees){
	window.parent.document.getElementById("rotary_display").innerHTML = "Position: " + degrees + "&ordm;";
	window.parent.document.getElementById("rotary_div").style.webkitTransform = "rotate(" + degrees + "deg)";
	window.parent.document.getElementById("rotary_div").style.transform = "rotate(" + degrees + "deg)";
	window.parent.document.getElementById("rotary_div").style.msTransform = "rotate(" + degrees + "deg)";
}