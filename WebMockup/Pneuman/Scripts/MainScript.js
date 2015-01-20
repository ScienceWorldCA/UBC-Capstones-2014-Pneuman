function hide_element(element_id){
    //document.getElementById(element_id).style.visibility = 'hidden';
    //document.getElementById(element_id).style.opacity = '0';
    element_id = id_to_selector(element_id);
    $(element_id).fadeOut(1000);
}

function show_element(element_id){
    //document.getElementById(element_id).style.visibility = 'visible';
    //document.getElementById(element_id).style.opacity = '100';
    element_id = id_to_selector(element_id);
    $(element_id).fadeIn( 1000);
}

function id_to_selector(element_id){
    return "#" + element_id;
}