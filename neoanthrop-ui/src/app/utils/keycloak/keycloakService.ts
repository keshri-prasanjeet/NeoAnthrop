import Keycloak from "keycloak-js";

const keycloakConfig = {
    url: "https://d930-2401-4900-1f25-626b-f121-20e8-b018-d1fa.ngrok-free.app",
    realm: "neo-anthrop",
    clientId: "neo-anthrop-app"
};
const keycloak = new Keycloak(keycloakConfig);