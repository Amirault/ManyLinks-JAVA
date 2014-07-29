

function verifCheckAssoc()
{
	var flag = false;
	
	if (document.getElementsByName("SelectFav[]").length >= 1)
	{
		for(var i = 0; i < document.getElementsByName("SelectFav[]").length; i++)
		{
			if (document.getElementsByName("SelectFav[]")[i].checked)
			{
				flag=true;
			}
		}
	}
		
	if (flag == false)
	{
		alert("Aucun élement sélectionné");
	}
	else 
	{
		$("#myModalValidAssoc").modal('show');
	}
	
	return false;
}
