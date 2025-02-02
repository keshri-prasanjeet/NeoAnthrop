/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface StringResponse
 */
export interface StringResponse {
    /**
     * 
     * @type {string}
     * @memberof StringResponse
     */
    response?: string;
}

/**
 * Check if a given object implements the StringResponse interface.
 */
export function instanceOfStringResponse(value: object): value is StringResponse {
    return true;
}

export function StringResponseFromJSON(json: any): StringResponse {
    return StringResponseFromJSONTyped(json, false);
}

export function StringResponseFromJSONTyped(json: any, ignoreDiscriminator: boolean): StringResponse {
    if (json == null) {
        return json;
    }
    return {
        
        'response': json['response'] == null ? undefined : json['response'],
    };
}

export function StringResponseToJSON(json: any): StringResponse {
    return StringResponseToJSONTyped(json, false);
}

export function StringResponseToJSONTyped(value?: StringResponse | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'response': value['response'],
    };
}

