### Overview
This repository is part of the AscentLister Project and contains the mobile app. The goal for this project was to create an mobile app where I can log my climbing route ascents.

The project contains the following repos
https://github.com/OliverFrey/AscentLister
https://github.com/OliverFrey/AscentListerAPI

### How to use it
1. Set up the AscentListerAPI. For instructions see the readme in the repository
2. Pull the source code and add a local.properties with the following values:
   properties
   KEYCLOAK_AUTH_URL=<your-auth-url>
   KEYCLOAK_CLIENT_ID=<your-api-client-id>
   KEYCLOAK_CLIENT_SECRET=<your-api-client-secret>
   BASE_URL=<your-ipa-url>
3. Run the app on your local device
