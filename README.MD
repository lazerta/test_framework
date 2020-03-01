# Selenium Framework 
A Data driven  selenium framework.
## Supported Platform
window 
Mac
saucelab 
browser stack
## Supported Browser 
Chrome, Firefox

## Instructions
All driver must be placed under resources/driver
windows driver under resources/driver/windows
mac driver under resources/driver/mac
Base configuration is done in application.yaml under resources
## Application.yaml
options with default value
```yaml
   browser:
        name : "chrome"
         version: "80.0.3987.116"
          
         options: # a list 
     Env:
       os: "windows"
       osVersion: "10"
       isCloud : false
       cloudName:  # string 
       url: "https://www.google.com"
       implicitWaitTime: 0
       pageLoadTimeout: 300
       cloudUsername:
       cloudAccessKey:
       Capability: # map 
       SystemInfo: # map System info for extent report. 
```
