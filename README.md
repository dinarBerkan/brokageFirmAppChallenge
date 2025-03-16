Brokage Firm Challenge Application

	This is a Spring Boot application that simulates a broke firm application. It was implemented with in IntelliJ IDEA 2024.3.4.1 with using Java 17 and Gradle.  
 	The project has 2 main controllers:
 		OrderController("/order"): The endpoint used for creating, deleting and listing orders. It has 3 enpoints for 3 mentioned operations:
	 		"/create": POST endpoint for creating orders. Example Request:
					{
    				"customerId": 100,
    				"assetName": "XAU",
    				"side":"SELL",
    				"size":4,
    				"price":25
					}
			"/list": GET endpoint for listing orders of a customer. It takes customerId, fromDate and toDate as request parameters. Format of fromDate and toDate is "yyyy-MM-dd".
	 		"/delete": POST endpoint for deleting orders on PENDING Status. Example Request:
					{
    				"orderId" : 3
					}
		AssetController("/asset"): The endpoint used for listing assets of a customer. It has an enpoints for mentioned operation:
	 		"/list": GET endpoint for listing assets of a customer. It takes customerId as request parameter.

	You can find example requests in testBrokerApp.postman_collection on main project directory. 
	 		
 
