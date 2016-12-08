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
                    var delete_b="<input type='checkbox' class='delete_check' id=chk_"+x.id+" value="+x.id+" />";
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


	$('#enquiry_delete_trigger').on('click',function(){
		event.preventDefault();
		var ids="";
		$.each($("#enquiry_table").find('.delete_check'),function(i,element)
		{
				if($(element).is(":checked"))
				{
					ids+=$(element).val()+",";
				}
				$('#enquiry_ids').val(ids);
		});
		if(ids!="")	
		{
			form=$("#enquiry_form");
			$.ajax({
			type: form.attr('method'),
			url: "http://vitalhealthstaffing.com/Admin/enquiry_delete.php",
			data: form.serialize()
			}).done(function(data) {
				populate_enquiry();
				form.trigger("reset");
			}).fail(function(data) {
			});
		}
		else{
			alert("Please select record to be deleted");
		}
	});
	
	$('#apply_select_all').change(function(){
		$.each($("#apply_table").find('.delete_check'),function(i,element){
				var set=false;
				if($("#apply_select_all").is(":checked"))
				{
					set=true;
				}
				$(element).prop("checked", set);
			});
	});
	$('#facility_select_all').change(function(){
		$.each($("#facility_table").find('.delete_check'),function(i,element){
				var set=false;
				if($("#facility_select_all").is(":checked"))
				{
					set=true;
				}
				$(element).prop("checked", set);
			});
	});
	$('#contact_select_all').change(function(){
		$.each($("#contact_table").find('.delete_check'),function(i,element){
				var set=false;
				if($("#contact_select_all").is(":checked"))
				{
					set=true;
				}
				$(element).prop("checked", set);
			});
	});
	$('#enquiry_select_all').change(function(){
		$.each($("#enquiry_table").find('.delete_check'),function(i,element){
				var set=false;
				if($("#enquiry_select_all").is(":checked"))
				{
					set=true;
				}
				$(element).prop("checked", set);
			});
	});
	
});
