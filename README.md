# Building a Chatbot by using pat platform (Based on Springboot + google cloud) 


## 1. Building a spring Rest Service:

For the first step to build the whole service, it is easy to follow the spring boot reference:  
https://spring.io/guides/gs/rest-service/

## 2. Connect to the pat service:
Firstly, check the reference of the PAT API: https://patai.staging.wpengine.com/developers/docs/#1522210528105-06a4bd3b-bc0c  

Please be ware of the the interface is based on oauth2 auth protocol.  

More information of the usage, please check the following reference:   
 http://patai.staging.wpengine.com/developers_old/sample-bot-code/
 
## 3. Integrating with Facebook Messenger:

Our application is in fact, a bridge between the PAT Service and Messenger:  

### 3.1 Apply a developer account of Facebook
It is vital to apply a developer account and build an APP on the Facebook platform.  
Please check this link to get more information:  
https://github.com/messenger4j/messenger4j-spring-boot-quickstart-template

### 3.2 Integrating Messenger4j into your project:
The messenger4j is an open source project to let Java program connect to Messenger:  
https://github.com/messenger4j/messenger4j  

#### Import messenger4j via Maven:
````
<dependency>
  <groupId>com.github.messenger4j</groupId>
  <artifactId>messenger4j</artifactId>
  <version>{m4j-version}</version>
</dependency>
````

#### Configuration
In order to get your chatbot working you have to provide the following settings:
```
messenger4j.appSecret = ${MESSENGER_APP_SECRET}
messenger4j.verifyToken = ${MESSENGER_VERIFY_TOKEN}
messenger4j.pageAccessToken = ${MESSENGER_PAGE_ACCESS_TOKEN}
```
The configuration is located in `src/resources/application.properties`.

#### Routing the message between Messenger and PAT
It operating code is under the `src/main/java/net/ulab/travelbot/controller.java`  

There are two interface we need to develop:  

##### Webhook verification endpoint. 
The passed verification token (as query parameter) must match the configured verification token. In case this is true, the passed challenge string must be returned by this endpoint.
 ````
     @RequestMapping(method = RequestMethod.GET)
     public ResponseEntity<String> verifyWebhook(@RequestParam(MODE_REQUEST_PARAM_NAME) final String mode,
                                                 @RequestParam(VERIFY_TOKEN_REQUEST_PARAM_NAME) final String verifyToken, @RequestParam(CHALLENGE_REQUEST_PARAM_NAME) final String challenge) {
         logger.debug("Received Webhook verification request - mode: {} | verifyToken: {} | challenge: {}", mode, verifyToken, challenge);
         try {
             this.messenger.verifyWebhook(mode, verifyToken);
             return ResponseEntity.ok(challenge);
         } catch (MessengerVerificationException e) {
             logger.warn("Webhook verification failed: {}", e.getMessage());
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
         }
     }
 ````
 
 ##### Callback endpoint responsible for processing the inbound messages and events.  

the function `handleTextMessageEvent` is used to fetch the data. 
 ````
     @RequestMapping(method = RequestMethod.POST)
     public ResponseEntity<Void> handleCallback(@RequestBody final String payload, @RequestHeader(SIGNATURE_HEADER_NAME) final String signature) {
         logger.debug("Received Messenger Platform callback - payload: {} | signature: {}", payload, signature);
         try {
             this.messenger.onReceiveEvents(payload, of(signature), event -> {
                 if (event.isTextMessageEvent()) {
                     handleTextMessageEvent(event.asTextMessageEvent());
                 } else if (event.isAttachmentMessageEvent()) {
                     handleAttachmentMessageEvent(event.asAttachmentMessageEvent());
                 } else if (event.isQuickReplyMessageEvent()) {
                     handleQuickReplyMessageEvent(event.asQuickReplyMessageEvent());
                 } else if (event.isPostbackEvent()) {
                     handlePostbackEvent(event.asPostbackEvent());
                 } else if (event.isAccountLinkingEvent()) {
                     handleAccountLinkingEvent(event.asAccountLinkingEvent());
                 } else if (event.isOptInEvent()) {
                     handleOptInEvent(event.asOptInEvent());
                 } else if (event.isMessageEchoEvent()) {
                     handleMessageEchoEvent(event.asMessageEchoEvent());
                 } else if (event.isMessageDeliveredEvent()) {
                     handleMessageDeliveredEvent(event.asMessageDeliveredEvent());
                 } else if (event.isMessageReadEvent()) {
                     handleMessageReadEvent(event.asMessageReadEvent());
                 } else {
                     handleFallbackEvent(event);
                 }
             });
             logger.info("Processed callback payload successfully");
             return ResponseEntity.status(HttpStatus.OK).build();
         } catch (MessengerVerificationException e) {
             logger.warn("Processing of callback payload failed: {}", e.getMessage());
             return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
         }
     }
 ````
 
 After that, the Messenger is integrated with PAT API
 
 ## 4. Release to the google cloud or AWS (Or your own data center)
 Please reference the google cloud document:  
 https://cloud.google.com/source-repositories/docs/quickstart-deploying-from-source-repositories-to-app-engine  
 
 