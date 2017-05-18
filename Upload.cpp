/**
Hello!
This is the client that sends the computer name.
Have a good day!
**/


#include <Windows.h>
#include <iostream>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <arpa/inet.h>

using namespace std;

#define INFO_BUFFER_SIZE 32767
TCHAR  infoBuf[INFO_BUFFER_SIZE];
DWORD  bufCharCount = INFO_BUFFER_SIZE;

// Get and display the name of the computer.
int sockfd;


if( !GetComputerName( infoBuf, &bufCharCount ) )
  printError( TEXT("GetComputerName") );
_tprintf( TEXT("\nComputer name:      %s"), infoBuf );
