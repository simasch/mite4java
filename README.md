mite4java
=========

We have developed API to use externally Mite system. Our API can be used to read data from Mite system. Also new
data can be inserted to Mite syste. We will generate reports from Mite system data.

In each API call we need to pass subdomain and API key of Mite system. The Mite API key is available in My User page of Mite system. Here I have used my test account subdomain "jewelsub" 
and my api key "7da6ddf5db9d29f"

Add new entry in Mite System :
============================
http://localhost:8080/mite4java/postcustomers.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f

http://localhost:8080/mite4java/postservices.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f

http://localhost:8080/mite4java/postprojects.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f

Export Selected Time Entry to Excel file and download :
============================================
First user will connect to Mite by this page. When user press connect button it fowards to report page

http://localhost:8080/mite4java/getreport.html

In the Report page user can select specifiq fields from Time Entry. Then press export button 
to download xlsx Report. In this page user need to enter subdomain name amd Mite api key to generate report.


Export Time Entry to Excel file and download :
============================================
All Time entry will be downloaded by bowser. To download TimeEntry API use example is 

http://localhost:8080/mite4java/gettimeentries.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f


Read all entry from Mite system :
===============================
http://localhost:8080/mite4java/getcustomers.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f

http://localhost:8080/mite4java/getservices.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f

http://localhost:8080/mite4java/getprojects.html?subdomain=jewelsub&apikey=7da6ddf5db9d29f
