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
                if(delete_check_box)
                {
                    var delete_b="<input type='checkbox' class='delete_check' th:field='*{features}' value="+x.id+" />";
                    cols.push(delete_b);
                }
		        for(var column in x)
                {
                    cols.push(x[column])
                }
                table.fnAddData(cols);
		    });
		},
		error: function(e)
		{
		   console.log(e.responseText);
		}
	});
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
			url: form.attr('url'),
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
