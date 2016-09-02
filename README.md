![distributed-chat](images/distributed-chat_logo.png)
# distributed-chat
Distributed chat system using the Java RMI API. 

## Installation
Set-up the servers (nodes/routers): users must launch the servers and manually configure each of them with a text file containing their routing table. The Dijkstra and Kruskal algorithms are used to compute the routes.

The syntax used for the routing tables is the following:
* First line: 

		R[router_number] [router_RMI_address]
		
* Then the routing table: 

		R[router_number][dest_router_number] [router_RMI_address] [dest_router_RMI_address] [cost_of_the_link] [used_networking_interface]
		
An example can be found at *conf_file/topology.txt*. 

## Project information
This software was developed in the context of a group university project in March 2014.	
