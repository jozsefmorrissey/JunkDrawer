getone_blocking.c - download one web document using blocking I/O.
  Read this to see what a simple implementation looks like.

getone.c - download one web document using nonblocking I/O with select(), poll(), and epoll().  
  Read this to see how to use select() and epoll().  poll() is a just a bonus.
  You can also run this with additional command line arguments to see the
  HTTP headers returned by the server and the request sent to the server.

mget.c - tool for downloading mutiple web documents simultaneously using nonblocking I/O.
  This is compiled to mget_select and mget using control_loop_select.c and
  control_loop_epoll.c, respectively.

control_loop_select.c - sample implementation of download loop that waits for
  nonblocking I/O using select().

control_loop_epoll.c - a download loop that waits for nonblocking I/O using epoll().
   ============ THIS IS EMPTY -- you will implement this ============

common.{c,h} - functions used in a few places in this project
http_simple.{c,h} - functions implementing simple HTTP operations like
  formatting requests and parsing headers
download.{c,h} - an object used to download a document from the web using nonblocking I/O

Sample input files for mget or mget_select:
  files.one - one small web page
  files.many - a few moderately-sized web pages and images
  files.large - one large download
  files.err - a few queries that should fail in various ways
