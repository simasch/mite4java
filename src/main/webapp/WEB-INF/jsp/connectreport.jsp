<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Report, Admin Panel</title>
    <style type="text/css">
        .container {
                float: left;
                padding: 3px;
        }
        .container select{
                width: 20em;
        }
        input[type="button"]{
                width: 5em;
        }
        .low{
                position: relative; 
                top: 35px;
        }
    </style>
    <script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
    <script type="text/javascript">

    $(function() {
		$(".low input[type='button']").click(function(){
				var arr = $(this).attr("name").split("2");
				var from = arr[0];
				var to = arr[1];
				$("#" + from + " option:selected").each(function(){
           $("#" + to).append($(this).clone());
           $(this).remove();
    
           document.gettimeentry.selectedFields.value = "";

           for (i=0;i<document.gettimeentry.itemsToAdd.length;i++)
            document.gettimeentry.selectedFields.value = document.gettimeentry.selectedFields.value + document.gettimeentry.itemsToAdd.options[i].value + ";";

           
        });
		});
    });
    
    
    function validateForm()
    {

    	   
        if(document.gettimeentry.subDomainName.value == "")
        {
     	   
        alert("please enter sundomain name");   
        return false;
        
        }
     	  
        if(document.gettimeentry.miteApiKey.value == "")
 	   {
 	   
 	   alert("please enter Mite api key"); 
 	   return false;
 	   
 	   }
        
    }
    </script>
</head>
<body>

<h3>User information</h3>

<form method="post" name="gettimeentry" action="getreportentrytxls.html" onsubmit="return validateForm()" >

    
	<table>
	<tr>
		<td><label path="subDomainName"><spring:message code="label.subdomain"/></label></td>
		<td><input name="subDomainName" path="subDomainName" /></td> 
	</tr>
	<tr>
		<td><label path="miteApiKey"><spring:message code="label.miteapikey"/></label></td>
		<td><input name="miteApiKey" path="miteApiKey" /></td>
	</tr>
	
    </table>


	<div>
	<br>
	<br>
	<br>
    <input type="submit" value="<spring:message code="label.connectreoport"/>"/>
    </div>


</form>

</body>
</html>
