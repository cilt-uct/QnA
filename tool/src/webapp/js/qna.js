	// Toggle visibility of an element
    function toggle_visibility(id) {
       if (document.getElementById(id).style.display == '')
       	document.getElementById(id).style.display = 'none';
       else
       	document.getElementById(id).style.display = '';
	}

	// Toggle visibility of questions of a certain entry id
	function toggle_questions(entry_id,expand_icon,collapse_icon) {
    	var element = document.getElementById(entry_id);
 		var rows = document.getElementsByTagName("tr");

		var expr = new RegExp(element.id +"question-entry:([0-9]*):$");

		for (i=0;i<rows.length;i++) {
			if (!(rows[i].id == "")) {
				if(rows[i].id.match(expr)) {
					toggle_visibility(rows[i].id);
				}
			}
		}

		// Toggle Icon
		toggle_visibility(expand_icon.id);
		toggle_visibility(collapse_icon.id);
    }

    // Used in questions list
    function init_questions_toggle(expand_icon_id,collapse_icon_id,entry_id) {
    	var expand_icon = document.getElementById(expand_icon_id);
    	var collapse_icon = document.getElementById(collapse_icon_id);
    	expand_icon.onclick = function() { toggle_questions(entry_id,expand_icon,collapse_icon);};
    	collapse_icon.onclick = function() { toggle_questions(entry_id,expand_icon,collapse_icon);};
    }

    function change_view(select,form,current_selected) {
    	document.location=form.action+"?viewtype="+select.options[select.selectedIndex].value;
    }

    // View select on question list screen
    function init_view_select(select_id, form_id, options_size, current_selected) {
    	var select = document.getElementById(select_id);
    	var form = document.getElementById(form_id);
		select.onchange = function () { change_view(select,form,current_selected);};
    }

    function toggle_add_answer(link_id,icon_id,div_id) {
	   	toggle_visibility(link_id);
    	toggle_visibility(icon_id);
    	toggle_visibility(div_id);
    }

    // Add an answer in answers screen
    function init_add_answer_toggle(link_id,icon_id,div_id) {
    	var link = document.getElementById(link_id);
    	link.href= "#";
    	link.onclick = function() { toggle_add_answer(link_id,icon_id,div_id);};
    	var icon = document.getElementById(icon_id);
    	icon.onclick = function() { toggle_add_answer(link_id,icon_id,div_id);};
    	icon.style.cursor="pointer";
    }

    function toggle_disabled(element) {
    	if (element.disabled) {
    		element.disabled = false;
    	} else {
    		element.disabled = true;
    	}
    }

    function toggle_mail_notifications_view(site_option,custom_option,update_option,custom_mail_input) {
    	toggle_disabled(site_option);
		toggle_disabled(custom_option);
		toggle_disabled(update_option);
		toggle_disabled(custom_mail_input);
    }

    function init_mail_notifications(notification_id,site_option_id,custom_option_id,update_option_id,custom_mail_input_id) {
    	var notification = document.getElementById(notification_id);
		var site_option = document.getElementById(site_option_id);
		var custom_option = document.getElementById(custom_option_id);
		var update_option = document.getElementById(update_option_id);
		var custom_mail_input = document.getElementById(custom_mail_input_id);

		notification.onchange = function() {toggle_mail_notifications_view(site_option,custom_option,update_option,custom_mail_input)};
    	if (!notification.checked) {
    		site_option.disabled = true;
    		custom_option.disabled = true;
    		update_option.disabled = true;
    		custom_mail_input.disabled = true;
    	}
    }

    // Used to make links perform like submit button
    function make_link_call_command(link_id,command_id) {
    	var link = document.getElementById(link_id);
    	var command = document.getElementById(command_id);
    	link.onclick = function() {command.click(); return false;};
    }
    
    // Used to add new file upload inputs
    // Parameters: div_id = The div to append to
    //			   index_value_id = Index of file input
    //			   id_name = Id to give file input
    function addFileInput(div_id, index_value_id,id_name) {
    	var div = document.getElementById(div_id);
    	var index = document.getElementById(index_value_id);
    	var newInput = document.createElement('input');
    	newInput.type = 'file';
    	newInput.name= 'id_name' + index.value;
    	index.value = index.value + 1;
    	div.appendChild(newInput);
    	div.appendChild(document.createElement('br'));
    }

    function makeCategoriesSortable(content_id) {
    	$(document).ready(function() {
	    	var content = document.getElementById(content_id);
	    	var category = ".sortable_item";
	    	var questionContainer = ".sortablequestion";
	    	var questions = ".editable_item";
	    	//document.getElementById(category_entry);
	    	//var question = document.getElementById(question_entry);

//	    	alert(content.id);
	    	//alert(category.id);
	    	//alert(question.id);

//	    	var expr = new RegExp(element.id +"question-entry:([0-9]*):$");

			$(content).sortable({ items: $(category), revert: true, tree: true });
			//$(questionContainer).sortable({ items: $(questions), revert: true, tree: true });
			//$(content).sortable({ connectWith: $(category) });
			//$(category).sortable({ connectWith: content });
		});
    }

    function makeQuestionsSortable(category_entry) {
    	$(document).ready(function() {
    		$('input:checkbox').each( function() {
				if (this.checked == false) {
					this.checked = true;
				}
			});

    		var category = ".sortablequestion";
    		//document.getElementById(category_id);
    		var questions = ".editable_item";

    		$(category).sortable({ items: $(questions), revert: true, tree: true});
    	});
    }
