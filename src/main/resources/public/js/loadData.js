function populate_basic_table(url,table,delete_check_box)
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
		        for(var column in x)
                {
                    cols.push(x[column]);
                }
		        cols.push("<a href='/category/edit/"+x.id+"'  id=\"edit_action\""+x.id+" ><i class=\"fa fa-pencil-square-o fa-lg\" aria-hidden=\"true\"></i> Edit</a>");
		        cols.push("<a href='/category/delete/"+x.id+"' id=\"delete_action\""+x.id+" class=\"delete_action\"><i class=\"fa fa-trash-o fa-lg\" aria-hidden=\"true\"></i> Delete</a>");
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
});
