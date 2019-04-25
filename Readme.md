The swaggercode gen utility can be download on the Maven repository at : https://mvnrepository.com/artifact/io.swagger/swagger-codegen-cli

Data classes were generate with version 2.4.4 using this command line.


java -jar swagger-codegen-cli-2.4.4.jar generate -i swaggerBackend.json -l kotlin -o gen/kotlin2 --model-package com.nsimtech.rastersclient.data