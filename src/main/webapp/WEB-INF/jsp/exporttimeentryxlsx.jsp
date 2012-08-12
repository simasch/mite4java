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

    	   
    
        
    }
    </script>
</head>
<body>

<h3>User information</h3>

<form:form method="post" name="gettimeentry" action="gettimeentryselectedfilelds.html" modelAttribute="userInfo" onsubmit="return validateForm()" >

    <form:input name="selectedFields" path="selectedFields" hidden="true"/>
    
	<table width="620" bgcolor="#F3F3D0">
	<tr>
		<td width="150"><form:label path="subDomainName"><spring:message code="label.subdomain"/></form:label></td>
		<td><%=subDomain %><form:input name="subDomainName" path="subDomainName" value="<%=subDomain %>" hidden="true"/></td> 
	</tr>
	<tr>
		<td width="150"><form:label path="miteApiKey"><spring:message code="label.miteapikey"/></form:label></td>
		<td><%=miteapikey %><form:input name="miteApiKey" path="miteApiKey" value="<%=miteapikey %>" hidden="true"/></td>
	</tr>
    </table>	
    <br>
    <br>
    
    <table border="0" cellspacing="0" id="AutoNumber3" width="620" cellpadding="3" bgcolor="#F3F3F3">
    <tr>
    <td align="center">
    Filter by
    </td>
    <td>
    </td>
    <td align="center">
    Group by
    </td>
    </tr>
    
     <tr>
    <td>
    <input type="checkbox" id="customerFl" name="customerFl"  value=1 /> Customer
    </td>
    <td align="center">
    <select size="1" id="customerSelected" name="customerSelected"  width="300" style="width: 300px">
    
     <c:forEach items="${customerData}" var="field">
     
       <option value="${field.name}">${field.name}</option>
       
     </c:forEach>
			
	</select>
	</td>
	<td>
	<input type="checkbox" id="timeframeGr" name="timeframeGr"  value=1 /> Time frame
	</td>
	</tr>
    
    <tr>
    <td>
    <input type="checkbox" id="projectFl" name="projectFl"  value=1 /> Project
    </td>
    <td align="center">
    <select size="1" id="projectSelected" name="projectSelected"  width="300" style="width: 300px">
    
     <c:forEach items="${projectData}" var="field">
     
       <option value="${field.name}">${field.name}</option>
       
     </c:forEach>
			
	</select>
	</td>
	<td>
	<input type="checkbox" id="customerGr" name="customerGr"  value=1 /> Customer
	</td>
	</tr>
	
	 <tr>
    <td>
    <input type="checkbox" id="serviceFl" name="serviceFl"  value=1 /> Service
    </td>
    <td align="center">
    <select size="1" id="serviceSelected" name="serviceSelected" width="300" style="width: 300px">
    
     <c:forEach items="${serviceData}" var="field">
     
       <option value="${field.name}">${field.name}</option>
       
     </c:forEach>
			
	</select>
	</td>
	<td>
	<input type="checkbox" id="projectGr" name="projectGr"  value=1 /> Project
	</td>
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
