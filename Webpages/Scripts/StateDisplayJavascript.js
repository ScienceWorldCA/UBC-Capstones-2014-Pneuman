setTimeout(function(){
    window.parent.document.getElementById("pneuman_state_refresh").src = window.parent.document.getElementById("pneuman_state_refresh").src;
}, 200);

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

    window.parent.document.getElementById("pneuman_state_title").innerHTML = state_name;
    window.parent.document.getElementById("pneuman_state_display").style.backgroundColor = state_color;
}