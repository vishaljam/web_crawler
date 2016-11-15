# web_crawler
Toy web crawler which is crawling shopping.com

# How to run ?

Using Commandline:

1. This is a simple spring boot application. And gradle is used as build tool.
2. first install gradle into your machine. You can get it here: https://services.gradle.org/distributions/gradle-2.9-all.zip
3. clone the codebase into your local machine.
4. go to codebase directory using cd command.
5. issue the following command: gradle bootrun
6. Now your web crawler is running and it will automatically start crawling on startup.

Using Eclipse:

1. Clone codebase into your local machine.
2. import this project into eclipse as gradle project.
3. right click on project and use run on server.
4. Now your web crawler is running and it will automatically start crawling on startup.

# Following things are taken care in code:


1. Re-visit policy.
2. Restricting followed links.
3. Focused crawling- considering pages only from shopping.com.
4. Depth restriction to avoid cyclic crawling.

# This crawler currently provides 2 queries


1. fetch by keyword
2. fetch by keyword and page number

# 2 RESTful apis are provided to get result of these 2 queries


1. http://localhost:8080/web_crawler/api/{version}/query/query_by_keyword/{keyword} Request method: GET
2. http://localhost:8080/web_crawler/api/{version}/query/query_by_keyword_and_pageno/{keyword}/{pagenumber} Request method: GET

Note: You can use browser or postman to hit this apis.
e.g. if you want to query for "keyword" use following url in postman:
http://localhost:8080/web_crawler/api/v1/query/query_by_keyword/keyword

if you want to query for "keyword" and "pagenumber" use following url in postman:
http://localhost:8080/web_crawler/api/v1/query/query_by_keyword_and_pageno/keyword/pagenumber

