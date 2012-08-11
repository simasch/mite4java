<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%

String subDomain="";

    try{
	
	    subDomain=request.getParameter("subDomainName");
	
	}catch(Exception e){}


String miteapikey="";

    try{
	
	    miteapikey=request.getParameter("miteApiKey");
	
	}catch(Exception e){}

%>

<html>
<head>
	<title>Export to XLSX, Admin Panel</title>
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

<form:form method="post" name="gettimeentry" action="gettimeentryselectedfilelds.html" modelAttribute="userInfo" onsubmit="return validateForm()" >

    <form:input name="selectedFields" path="selectedFields" hidden="true"/>
    
	<table>
	<tr>
		<td><form:label path="subDomainName"><spring:message code="label.subdomain"/></form:label></td>
		<td><form:input name="subDomainName" path="subDomainName" value="<%=subDomain %>" readonly="true"/></td> 
	</tr>
	<tr>
		<td><form:label path="miteApiKey"><spring:message code="label.miteapikey"/></form:label></td>
		<td><form:input name="miteApiKey" path="miteApiKey" value="<%=miteapikey %>" readonly="true"/></td>
	</tr>
    </table>	

   <h3>Select fields to export</h3>

  <table>
	<tr>
	<td>
    <div class="container">
    <select name="itemsToChoose" id="left" size="8" multiple="multiple">
    
      <c:forEach items="${fieldList}" var="field">
       <option value="${field.fieldId}">
        ${field.fieldName}
      </option>
      </c:forEach>
    </select>
    </div>

    <div class="low container">
    <input name="left2right" value="add" type="button"><br>
    <input name="right2left" value="remove" type="button">
    </div>

    <div class="container">
    <select name="itemsToAdd" id="right" size="8" multiple="multiple"/></select>
    </div>
  
    </td> 
	</tr>
	
	<tr>
	<td >
	<div align="center">
	<br>
	<br>
	<br>
    <input type="submit" value="<spring:message code="label.expotxlsxbutton"/>"/>
    </div>
    </td>
    </tr>
  </table>
  
</form:form>

</body>
</html>
