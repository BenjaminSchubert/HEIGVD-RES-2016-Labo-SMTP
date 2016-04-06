# HEIGVD-RES-2016-Labo-SMTP

## What's this about ?
A simple SMTP client created in the RES course at HEIG-VD in order to become familiar with the protocol.

The idea of this project is to send a forged e-mail to a group of victims. The content of the mail is selected among a set of pre-defined messages and the various groups of victims are formed from a list of e-mails.

## Installation and running

Dependencies: vagrant (for the mock server) and a version of the JDK/JRE (for the client).

1. Download or clone the sources.
2. Go to root folder (where the .vagrant file is) and type `$ vagrant up`.
3. Compile and run the client.
4. You will have to provide two arguments: the file containing target e-mails and the number of groups you want to create.
