# web_crawler
Toy web crawler which is crawling shopping.com

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

