function hide_element(element_id){
    element_id = id_to_selector(element_id);
    $(element_id).fadeOut(1000);
}

function show_element(element_id){
    element_id = id_to_selector(element_id);
    $(element_id).fadeIn( 1000);
}

function id_to_selector(element_id){
    return "#" + element_id;
}