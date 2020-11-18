"""
 This code sample demonstrates an implementation of the Lex Code Hook Interface
 in order to serve a bot which manages dentist appointments.
 Bot, Intent, and Slot models which are compatible with this sample can be found in the Lex Console
 as part of the 'MakeAppointment' template.

 For instructions on how to set up and test this bot, as well as additional samples,
 visit the Lex Getting Started documentation http://docs.aws.amazon.com/lex/latest/dg/getting-started.html.
"""

import json
import dateutil.parser
import datetime
import time
import os
import math
import random
import logging
import boto3
from boto3.dynamodb.conditions import Key, Attr

logger = logging.getLogger()
logger.setLevel(logging.DEBUG)

dynamodb = boto3.resource('dynamodb',region_name='us-east-1') 
table = dynamodb.Table('Graph')


""" --- Helpers to build responses which match the structure of the necessary dialog actions --- """





def FindDistance(intent_request):
    """
    Performs dialog management and fulfillment for booking a dentists appointment.

    Beyond fulfillment, the implementation for this intent demonstrates the following:
    1) Use of elicitSlot in slot validation and re-prompting
    2) Use of confirmIntent to support the confirmation of inferred slot values, when confirmation is required
    on the bot model and the inferred slot values fully specify the intent.
    """
    first_City = intent_request['currentIntent']['slots']['FirstCity']
    
    return first_City
    
""" --- Intents --- """


def dispatch(intent_request):
    """
    Called when the user specifies an intent for this bot.
    """

    logger.debug('##################dispatch userId={}, intentName={}#########'.format(intent_request['userId'],intent_request))
     
    intent_name = intent_request['currentIntent']['name']
    first_City = intent_request['currentIntent']['slots']['FirstCity']
    second_City = intent_request['currentIntent']['slots']['SecondCity']
    cities = intent_request['currentIntent']['slots']['Cities']
    city = intent_request['currentIntent']['slots']['City']
    recentIntentSummaryView = intent_request['recentIntentSummaryView']
    oldCity = None
    if (recentIntentSummaryView != None and recentIntentSummaryView != []):
        oldCity = recentIntentSummaryView[0]['slots']['City']
       
    
    #output_session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    # Dispatch to your bot's intent handlers
    if intent_name == 'FindDistanceBetweenCity':
        
        if first_City != None:
            if first_City != second_City:
                response = table.scan( FilterExpression=Attr('Source').eq(first_City) & Attr('Destination').eq(second_City))
                try:
                    item = response['Items'][0]['Distance']
                except IndexError:
                    item = '1'
                return  {
                                'dialogAction': {
                                        'type': 'ElicitIntent',
                                        'message': {
                                                    'contentType': 'PlainText',
                                                    'content': item
                                                    }
                                     }
                                }
                
                    
            else:
                item = '0'
                return  {
                                'dialogAction': {
                                        'type': 'ElicitIntent',
                                        'message': {
                                                    'contentType': 'PlainText',
                                                    'content': item
                                                    }
                                     }
                                }
            
        if cities != None:
              
            return  {
                        'dialogAction': {
                                'type': 'ElicitIntent',
                                'message': {
                                            'contentType': 'PlainText',
                                            'content': 'Source?'
                                            }
                             }
                        }
                        
        if (city != None and oldCity is None):
            
            return {
                        'dialogAction': {
                                'type': 'ElicitSlot',
                                'message': {
                                            'contentType': 'PlainText',
                                            'content': 'Destination?'
                                            },
                                "intentName": intent_name,
                                "slots": {
                                      "City": city,
                                         },
                                "slotToElicit" : "City"
                            }
                    }
                    
        if oldCity != None:
            
            print('############'+oldCity+city)
            if oldCity != city:
                response = table.scan( FilterExpression=Attr('Source').eq(oldCity) & Attr('Destination').eq(city))
                try:
                    item = response['Items'][0]['Distance']
                except IndexError:
                    item = '1'
                return  {
                            'dialogAction': {
                                    'type': 'ElicitSlot',
                                    'message': {
                                                'contentType': 'PlainText',
                                                'content': item
                                                },
                                    "intentName": intent_name,
                                    "slotToElicit" : "FirstCity"
                                }
                          
                            }
            else:
                item = '0'
                return  {
                            'dialogAction': {
                                    'type': 'ElicitSlot',
                                    'message': {
                                                'contentType': 'PlainText',
                                                'content': item
                                                },
                                    "intentName": intent_name,
                                    "slotToElicit" : "FirstCity"
                                }
                          
                            }
            
    raise Exception('Intent with name ' + intent_name + ' not supported')


""" --- Main handler --- """


def lambda_handler(event, context):
    """
    Route the incoming request based on intent.
    The JSON body of the request is provided in the event slot.
    """
    # By default, treat the user request as coming from the America/New_York time zone.
    os.environ['TZ'] = 'America/New_York'
    time.tzset()
    logger.debug('event.bot.name={}'.format(event['bot']['name']))

    return dispatch(event)
