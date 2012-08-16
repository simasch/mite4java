<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Report, Admin Panel</title>
    <script type="text/javascript">
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

<form method="post" name="gettimeentry" action="getselectedreportentrytxls.html" onsubmit="return validateForm()" >

    
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
