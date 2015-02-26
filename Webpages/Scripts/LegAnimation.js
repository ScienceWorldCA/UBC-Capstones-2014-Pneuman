var NUM_FRAMES = 21;
var DEGREES = 360.0;

var pneuman_gif = null;

$(document).ready(function(){
    pneuman_gif = new SuperGif({ gif: document.getElementById('pneuman_control_gif') } );
    pneuman_gif.load(1);
});

var movePneumanLegAnimation = function(value){
    pneuman_gif.move_to(Math.floor((value / DEGREES) * NUM_FRAMES));
};

window.setInterval(function(){
    try {
        var newValue = document.getElementById('pneuman_state_refresh').contentWindow.document.getElementById('pneuman_gif_position_input').innerHTML;
        movePneumanLegAnimation(newValue);
    }
    catch(err) {

    }
}, 100);