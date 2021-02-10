## OnlineMedicineShop

- A SpringBoot REST API built for its [Client](https://github.com/KNehe/OnlineMedicineShop.git)

### Follow the steps below

- Clone this repository
- Create a MySql database with the name  ``` medical ```
- You can create the database with any name you like. Make sure to update the datasource url in ``` application.properties ```
- If your datasource username and password are not the defaults  ``` username(root) and password(" ") ```, then update their values in ``` application.properties ```
- For purchases to work. You need to create an account on [stripe](stripe.com)
and pick your api_key. Navigate to ``` services/StripeService.java ```, in
the constructor, enter the api_key ``` Stripe.apiKey = ""; ```
- Start MySql
- Run the project and test endpoints e.g a post request to  ``` api/v1/users/ ``` to add a new user