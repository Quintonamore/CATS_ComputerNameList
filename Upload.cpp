/**
Hello!
This is the client that sends the computer name.
Have a good day!
**/


#include <Windows.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <tchar.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <arpa/inet.h>

using namespace std;

#define INFO_BUFFER_SIZE 32767
TCHAR  infoBuf[INFO_BUFFER_SIZE];
DWORD  bufCharCount = INFO_BUFFER_SIZE;

sockaddr_in PLU;

int sockfd = socket(AF_INET, SOCK_DGRAM, 0);
if (sockfd == INVALID_SOCKET){
        cerr<<"Socket Initialization: Error creating socket"<<endl;
        system("pause");
        exit(11);
}

PLU.sin_family = AF_INET;
PLU.sin_port = htons(80);            //Set to port 80 as it is always open
PLU.sin_addr.s_addr = inet_addr(""); //IP for the server computer in the quotes.

char buffer[256];
buffer = GetComputerNameEx(infoBuf, &bufCharCount, null); //This needs checking: 'null' may need to be defined.
sendto(sockfd, buffer, sizeOf(buffer),0,(struct sockaddr *)&PLU, sizeOf(PLU));

if( !GetComputerName( infoBuf, &bufCharCount ) )
  printError( TEXT("GetComputerName") );
_tprintf( TEXT("\nComputer name:      %s"), infoBuf );

sockfd.close(); //Close the socket connection
