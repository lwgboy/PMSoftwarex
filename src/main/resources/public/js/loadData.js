function populate_basic_table(url,table,type)
{
    $.ajax(
    {
	    url: url,
		dataType: 'json',
		success: function(s)
		{
		    table.fnClearTable();
		    $.each(s, function(i, x)
		    {
                var cols = new Array();
                var flag=false;
		        for(var column in x)
                {
		        	if(!$.isArray(x[column]))
		        	{
		        		if(!$.isPlainObject(x[column]))
		        		{
		        			cols.push(x[column]);
		        		}
		        		else
			        	{
			        		flag=true;
			        	}
		        	}
		        	else
		        	{
		        		flag=true;
		        	}
                    
                }
		        if(flag==true)
		        {
		        	cols.push("<a href='/"+type+"/view/"+x.id+"'  id=\"view_action\""+x.id+" class=\"view_action\"><i class=\"fa fa-eye fa-lg\" aria-hidden=\"true\"></i> View</a>");
		        }
		        cols.push("<a href='/"+type+"/edit/"+x.id+"'  id=\"edit_action\""+x.id+" class=\"edit_action\"><i class=\"fa fa-pencil-square-o fa-lg\" aria-hidden=\"true\"></i> Edit</a>");
		        cols.push("<a href='/"+type+"/delete/"+x.id+"' id=\"delete_action\""+x.id+" class=\"delete_action\"><i class=\"fa fa-trash-o fa-lg\" aria-hidden=\"true\"></i> Delete</a>");
                table.fnAddData(cols);
		    });
		},
		error: function(e)
		{
		   console.log(e.responseText);
		}
	});
}

function confirmDialog(message, onConfirm){
    var fClose = function(){
		modal.modal("hide");
    };
    var modal = $("#confirmModal");
    modal.modal("show");
    $("#confirmMessage").empty().append(message);
    $("#confirmOk").one('click', onConfirm);
    $("#confirmOk").one('click', fClose);
    $("#confirmCancel").one("click", fClose);
}

$(document).ready(function() {


	$('.table_delete_trigger').on('click',function(){
		event.preventDefault();
		var ids="";
		table=$(this).parent().parent().parent().parent();
		$.each(table.find('.delete_check'),function(i,element)
		{
				if($(element).is(":checked"))
				{
					ids+=$(element).val()+",";
				}
				$(this).parent().find(".selected_ids").val(ids);
		});
		if(ids!="")	
		{
			form=table.closest("form");
			$.ajax({
			type: form.attr('method'),
			url: form.attr('action'),
			data: form.serialize()
			}).done(function(data) {
				form.trigger("reset");
			}).fail(function(data) {
			});
		}
		else{
			alert("Please select record to be deleted");
		}
	});
	
	$('body').on('click', '.delete_action', function (e) {
		var $form=$(this).closest('form'); 
	    e.preventDefault();
	    var link=e.currentTarget.href;
	    confirmDialog("Are you Sure?", function(){
	    	window.location = link
		});
	   });
	
	$('.select_all_check_box').change(function(){
	    table=$(this).parent().parent().parent().parent();
    		$.each(table.find('.delete_check'),function(i,element){
    				var set=false;
    				if($(".select_all_check_box").is(":checked"))
    				{
    					set=true;
    				}
    				$(element).prop("checked", set);
    			});
    	});
	
	// Sonstructs the suggestion engine
    var variantTypes = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.whitespace,
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        // The url points to a json file that contains an array of country names
        prefetch: '/variantType/variantTypes'
    });
    
    // Initializing the typeahead with remote dataset without highlighting
    $('.variantType').typeahead(null, {
        name: 'variantTypes',
        source: variantTypes,
        limit: 10 /* Specify max number of suggestions to be displayed */
    });

	
});
