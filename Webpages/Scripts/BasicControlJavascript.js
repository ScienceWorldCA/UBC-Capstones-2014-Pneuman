function submit_control(control_code){
	var controls = [0,0,0];
	controls[control_code] = 1;
	window.frames['hidden_control_frame'].contentDocument.getElementById("control_forwards").value = controls[0];
	window.frames['hidden_control_frame'].contentDocument.getElementById("control_backwards").value = controls[1];
	window.frames['hidden_control_frame'].contentDocument.getElementById("control_stop").value = controls[2];
	window.frames['hidden_control_frame'].contentDocument.getElementById("control_manual").value = 1;
	window.frames['hidden_control_frame'].contentDocument.getElementById("control_form").submit();
}

function rotate(degrees){
	document.getElementById("rotary_div").style.webkitTransform = "rotate(" + degrees + "deg)";
	document.getElementById("rotary_div").style.transform = "rotate(" + degrees + "deg)";
	document.getElementById("rotary_div").style.msTransform = "rotate(" + degrees + "deg)";
}

function disableControls(){
	window.parent.$("#greyOutDiv").fadeIn(1000);
}

function enableControls(){
	window.parent.$("#greyOutDiv").fadeOut(1000);
}