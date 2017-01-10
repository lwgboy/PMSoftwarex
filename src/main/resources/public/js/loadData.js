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
                var count=0;
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
		        	if(url.indexOf("/product/")>-1)
		        	{
		        		count++;
		        		if(count==3)
		        		{
		        			flag=true;
		        			break;
		        		}
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

function formDialog(message, onConfirm,modalId){
    var fClose = function(){
		modal.modal("hide");
    };
    var modal = $("#"+modalId);
    modal.modal("show");
    var message=modal.find(".message");
    message.empty().append(message);
    $("#confirmOk").one('click', onConfirm);
    $("#confirmOk").one('click', fClose);
    $("#confirmCancel").one("click", fClose);
}

function calculateTotal(form)
{
	var totalCost=0;
	$.each(form.find(".quantity"),function(key, quantity){
		rate=$("#"+quantity.id.replace('.quantity','.rate').replace(/\./g,"\\."))
		totalCost+=parseFloat(quantity.value || 0)*parseFloat(rate.val() || 0);
	});
	form.find('.totalCost').val(totalCost.toFixed(2));
}

$(document).ready(function() {
	
	jQuery('.rate, .quantity').on('input', function() {
	    calculateTotal($(this).closest('form'));
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
    /*var variantTypes = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        // The url points to a json file that contains an array of country names
        prefetch: '/variantType/list'
    });
    variantTypes.initialize();*/
    // Initializing the typeahead with remote dataset without highlighting
    /*$('.variantType').typeahead({
    	hint:true,
    	highlight:true
    }, {
        name: 'variantTypes',
        source: variantTypes.ttAdapter(),
        display: 'name',
        limit: 10  Specify max number of suggestions to be displayed 
    }).on('typeahead:selected', function(event, data){ 
    	var idx="#"+event.currentTarget.id.replace('.name','.id').replace(/\./g,"\\.");
    	$(idx).val(data.id);
    });
    $('.variantType').typeahead({
        onSelect: function(item) {
            console.log(item);
        },
        displayField: 'name',
        ajax: {
            url: "/variantType/list",
            timeout: 500,
            displayField: "title",
            triggerLength: 1,
            method: "get",
            loadingClass: "loading-circle",
            preDispatch: function (query) {
                //showLoadingMask(true);
                return {
                    search: query
                }
            },
            preProcess: function (data) {
                //showLoadingMask(false);
                if (data.success === false) {
                    // Hide the list, there was some error
                    return false;
                }
                // We good!
                return data;
            }
        }
    });
	*/
    $('input.variantType').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/variantType/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	
	$('input.variant').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/variant/list', { query: query }, function (data) 
	    			{
	    				for(var i = 0; i < data.length; i++) {
	    					var obj = data[i];
	    					if(obj.name === null) {
	    						data.splice(i, 1);
	    						i--;
	    					}
	    				}
	            		return process(data);
	    			});
	    }
	})
	
	$('input.measurement').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/measurement/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.tagType').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/tagType/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.category').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/category/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.section').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/section/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.division').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/division/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.style').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/style/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.brand').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/brand/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.vendor').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/vendor/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.transporter').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/transporter/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('input.productType').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    {
	    	return $.get('/productType/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})	
/*	$('input.stageType').typeahead({
			afterSelect: function(data)
			{
				if(data.name=="Create New")
				{
					alert("hi");
				}
				else
				{
					var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
			    	$(idx).val(data.id);
				}
			},
		    source:  function (query, process) 
		    {
		    	
		    	return $.get('/stageType/list', { query: query }, function (data) 
		    			{
		            		return process(data);
		    			});
		    	
		    },
		    addItem: {"name":"Create New"}
		    
	})
*/	
	$('input.stageType').typeahead({
			afterSelect: function(data)
			{
					var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
			    	$(idx).val(data.id);
			},
		    source:  function (query, process) 
		    {
		    	
		    	return $.get('/stageType/list', { query: query }, function (data) 
		    			{
		            		return process(data);
		    			});
		    }
	})

	$('input.route').typeahead({
		afterSelect: function(data)
		{
			var idx="#"+$(this)[0].$element[0].id.replace('.name','.id').replace(/\./g,"\\.");
	    	$(idx).val(data.id);
		},
	    source:  function (query, process) 
	    { 	
	    	return $.get('/route/list', { query: query }, function (data) 
	    			{
	            		return process(data);
	    			});
	    }
	})
	$('.variantRoute').on('click',function(event){
		event.preventDefault();
		ele=$(this);
		var id="#"+ele[0].id.replace(/\./g,"\\.").replace('variantRoute','variantRouteRow');
		$(id).toggle("medium");
	});
});
