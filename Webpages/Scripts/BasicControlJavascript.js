function submit_control(control_code){
	var controls = [0,0,0];
	controls[control_code] = 1;
	document.getElementById("control_forwards").value = controls[0];
	document.getElementById("control_backwards").value = controls[1];
	document.getElementById("control_stop").value = controls[2];
	document.getElementById("control_form").submit();
}