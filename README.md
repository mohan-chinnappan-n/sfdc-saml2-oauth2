### Notes on SFDC OAuth2 Flows

---------

- [SAML Bearer Assertion Flow ](./SAML-Bearer-Assertion-Flow.md)

Token endpoint: POST: https://login|test.salesforce.com/services/oauth2/token

grant_type=saml2-bearer

response: access_token

-----

- [JWT Bearer Token Flow ](./JWT-Bearer-Token-Flow.md)

Token endpoint: POST: https://login|test.salesforce.com/services/oauth2/token

POST: JWT

response: access_token

-------

- [Web Server Flow ](./Web-server-flow.md)

Verification endpoint: GET :https://login|test.salesforce.com/services/oauth2/authorize

response_type=code

Token endpoint: POST: https://login|test.salesforce.com/services/oauth2/token

grant_type=authorization_code

response: access_token

--------


- [User-Agent OAuth Authentication Flow](https://developer.salesforce.com/docs/atlas.en-us.api_rest.meta/api_rest/intro_understanding_user_agent_oauth_flow.htm)

Authorize endpoint: https://login|test.salesforce.com/services/oauth2/authorize

response_type=token


-----
