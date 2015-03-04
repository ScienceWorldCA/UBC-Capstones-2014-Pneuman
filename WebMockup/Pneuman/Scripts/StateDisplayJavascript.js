setTimeout(function(){
    window.parent.document.getElementById("pneuman_state_refresh").src = window.parent.document.getElementById("pneuman_state_refresh").src;
}, 2000);

function updateState(state){
    var state_name = "Blackout";
    var state_color = "black";

    switch(state) {
        case 0:
            state_name = "Blackout";
            state_color = "black";
            break;
        case 1:
            state_name = "Automatic";
            state_color = "green";
            break;
        case 2:
            state_name = "Manual";
            state_color = "yellow";
            break;
        default:
            state_name = "Blackout";
            state_color = "black";
            break;
    }

    window.parent.document.getElementById("pneuman_control_state").innerHTML = state_name;
    window.parent.document.getElementById("pneuman_control_state").style.backgroundColor = state_color;
}